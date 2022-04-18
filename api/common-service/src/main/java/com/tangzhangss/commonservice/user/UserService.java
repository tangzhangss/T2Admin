package com.tangzhangss.commonservice.user;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.tangzhangss.commonservice.aspect.preauthorize.PreAuthorizeEntity;
import com.tangzhangss.commonservice.aspect.preauthorize.PreAuthorizeService;
import com.tangzhangss.commonservice.common.BaseConfig;
import com.tangzhangss.commonutils.base.SysBaseService;
import com.tangzhangss.commonutils.base.SysContext;
import com.tangzhangss.commonutils.config.Attribute;
import com.tangzhangss.commonutils.i18n.Translator;
import com.tangzhangss.commonutils.resultdata.ResultCode;
import com.tangzhangss.commonutils.service.RedisService;
import com.tangzhangss.commonutils.utils.BaseUtil;
import com.tangzhangss.commonutils.utils.ExceptionUtil;
import com.tangzhangss.commonutils.utils.HashMapUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserService extends SysBaseService<UserEntity, UserDao> {


    @Autowired
    RedisService redisService;

    @Autowired
    UserDao userDao;

    @Autowired
    PreAuthorizeService preAuthorizeService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteAll(List<UserEntity> data) {
        this.beforeDeleteData(data);
        try {
            myDao.deleteAll(data);
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionUtil.throwException("该用户已经被其他数据关联，请删除关联数据再删除此用户");
        }
        this.afterDeleteData(data);
    }

    @Override
    public void beforeDeleteData(List<UserEntity> users) {
        users.forEach(user -> {
            //如果username等于clientId => 是企业管理员账户
            if (user.getClientId().equals(user.getUsername()) && !SysContext.getClientId().equals(Attribute.SUPER_ADMIN_CLIENT_ID)) {
                ExceptionUtil.throwException("企业管理员账号不可删除");
            }
        });
    }

    /**
     * 删除之前操作
     * <p>
     * 对用户密码进行判断，没有密码默认123456
     */
    @Override
    protected void beforeSaveData(UserEntity user){
        super.beforeSaveData(user);

        //用户登录账户不能重复--全局判断
        UserEntity oldUser = userDao.findFirstByUsername(user.getUsername());
        if (oldUser != null && !oldUser.getId().equals(user.getId()))
            ExceptionUtil.throwException("登录账户被占用，请修改（推荐使用手机号码）");

        //密码操作
        if (StringUtils.isBlank(user.getPassword()))
            user.setPassword(BaseUtil.twiceMd5Salt(BaseConfig.USER_DEFAULT_PASSWORD));
        if (StringUtils.isBlank(user.getUsername()))
            user.setUsername(user.getPhone());

        //如果username等于clientId => 是企业管理员账户
        if (user.getClientId().equals(user.getUsername()) && !SysContext.getClientId().equals(Attribute.SUPER_ADMIN_CLIENT_ID)) {
            ExceptionUtil.throwException("企业管理员账号不可修改,请点击右上角[头像]-[企业信息]进行修改");
        }

    }

    /*
        用户类默认查询所有（当前租户下）_usable(禁用or正常)
     */
    @Override
    protected boolean isQueryAll() {
        return true;
    }

    /*
        验证用户名和密码
         */
    public UserEntity verifyLogin(UserEntity user) {
        //验证账号
        //没有登录不能使用SysBaseService里面的方法-大多数方法都会用到当前用户信息
//        UserEntity realUser = getOneWithMapString("username@EQ="+user.getUsername());
        UserEntity realUser = myDao.findFirstByUsername(user.getUsername());
        if (realUser == null) ExceptionUtil.throwException(Translator.get("user_name_not_exist"), ResultCode.USER_LOGIN_FAILED);
        //验证密码
        if (!realUser.getPassword().equals(BaseUtil.twiceMd5Salt(user.getPassword())))
            ExceptionUtil.throwException(Translator.get("password_incorrect"), ResultCode.USER_LOGIN_FAILED);
        //验证账号是否被禁用
        if (!realUser.isUsable()) ExceptionUtil.throwException("当前账号已被禁用，不可登录", ResultCode.USER_LOGIN_FAILED);

        //登录之后生成和返回Token
        String token = UUID.randomUUID() + "&" + realUser.getId();
        SysContext.setUser(JSONUtil.parseObj(realUser));
        //根据用户角色查询用户权限
        String roleNames = realUser.getRoleNames();
        HashSet<String> authorizeSet = new HashSet<>();
        realUser.setAuthorizeSet(authorizeSet);
        if(StringUtils.isNotBlank(roleNames)){
            List<PreAuthorizeEntity> preAuthorizeEntityList = preAuthorizeService.getWithMap(new HashMapUtil().put("roleName@IN", roleNames).get());
            preAuthorizeEntityList.forEach(preAuthorizeEntity -> {
                String tag = preAuthorizeEntity.getTag();
                if(StringUtils.isNotBlank(tag)){
                    authorizeSet.addAll(Arrays.asList(tag.split(",")));
                }
            });
            //再存一次更新权限
            redisService.set(token, JSON.toJSONString(realUser), 54000l);
        }

        realUser.setToken(token);//token只在登录的时候返回——其他时候不返回

        return realUser;
    }
}
