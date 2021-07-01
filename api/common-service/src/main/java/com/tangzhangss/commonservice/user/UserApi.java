package com.tangzhangss.commonservice.user;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.tangzhangss.commonutils.aspect.syslog.SysLog;
import com.tangzhangss.commonutils.base.SysBaseApi;
import com.tangzhangss.commonutils.base.SysContext;
import com.tangzhangss.commonutils.resultdata.Result;
import com.tangzhangss.commonutils.utils.BaseUtil;
import com.tangzhangss.commonutils.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserApi extends SysBaseApi<UserEntity, UserService> {

    @Autowired
    UserDao userDao;

    @PostMapping("/login")
    public Result login(@RequestBody UserEntity user) {
        return new Result(HttpStatus.OK, myService.verifyLogin(user));
    }

    @GetMapping("/test")
    @SysLog("测试")
    public Result test() {
        return new Result(HttpStatus.OK, SysContext.getUser());
    }

    /*
    修改密码
     */
    @PostMapping("/modify_pwd")
    public Result modifyPwd(@RequestBody UserEntity user) {
        user.setPassword(BaseUtil.twiceMd5Salt(user.getPassword()));
        userDao.save(user);
        return Result.ok().data(user);
    }

    /*
    修改租户的密码
    自己当前客户管理员账号有权限-所以=修改当前用户的密码
     */
    @PostMapping("/modify_pwd_by_client")
    public Result modifyPwdByClient(@RequestBody JSONObject params) {
        String password = params.getString("password");
        UserEntity user = JSONUtil.toBean(SysContext.getUser(),UserEntity.class);
        if (!user.getUsername().equals(user.getClientId())) ExceptionUtil.throwException("无权限执行此操作");
        user.setPassword(BaseUtil.twiceMd5Salt(password));
        userDao.save(user);
        return Result.ok().data(user);
    }

}
