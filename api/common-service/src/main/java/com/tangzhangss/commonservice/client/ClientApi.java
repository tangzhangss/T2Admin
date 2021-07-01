package com.tangzhangss.commonservice.client;

import com.tangzhangss.commonservice.user.UserDao;
import com.tangzhangss.commonutils.resultdata.Result;
import com.tangzhangss.commonutils.uidgenerator.UidGeneratorService;
import com.tangzhangss.commonutils.utils.ExceptionUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/client")
public class ClientApi {
    @Autowired
    ClientDao clientDao;
    @Autowired
    UserDao userDao;
    @Autowired
    ClientService myService;
    @Autowired
    protected UidGeneratorService uidGeneratorService;


    @PutMapping("/no_auth")
    public Result register(@RequestBody List<ClientEntity> clientEntityList) {
        ClientEntity clientEntity = clientEntityList.get(0);
        //查询clientId是否存在
        if (StringUtils.isBlank(clientEntity.getId())) ExceptionUtil.throwException("Id不能为空，请检查");

        //查询客户是否存在
        ClientEntity client = clientDao.findById(clientEntity.getId()).orElseGet(() -> null);

        if (client != null) ExceptionUtil.throwException("Id已经被占用，请重新输入");

        clientDao.save(clientEntity);

        return new Result(HttpStatus.OK, null);
    }

    @GetMapping("/get/{id}")
    public Result getOne(@PathVariable("id") String id) {
        //查询clientId是否存在
        ClientEntity clientEntity = clientDao.findById(id).orElseGet(() -> null);
        return new Result(HttpStatus.OK, clientEntity);
    }


    @GetMapping
    public Result get(HttpServletRequest request) throws Exception {
        return new Result(HttpStatus.OK, myService.get(request, null));
    }

    @PutMapping
    public Result put(@RequestBody List<ClientEntity> data) throws Exception {
        return myService.put(data);
    }

    @DeleteMapping
    public Result delete(@RequestBody List<ClientEntity> data) throws Exception {
        myService.deleteAll(data);
        return Result.ok();
    }

    @PostMapping
    public Result post(@RequestBody ClientEntity data) throws Exception {
        return Result.ok().data(myService.save(data));
    }

    @GetMapping("/get_approved_status")
    public Result getApprovedStatus() {
        Map<Integer, Object> map = new HashMap<>();
        //分组部分
        String[] groupKey = new String[]{"approved"};
        //聚合部分
        Map<String, String> groupMap = new HashMap<>();
        groupMap.put("id", "count");
        groupMap.put("approved", null);
        List<Map<String, Object>> groupRes = myService.queryByGroup(null, groupKey, groupMap);
        groupRes.forEach(item -> {
            Boolean approved = (Boolean) item.get("approved");
            int key = 0;
            if (approved != null && approved) {
                key = 1;
            }
            map.put(key, item.get("id_count"));
        });
        return Result.ok().data(map);
    }

    /*
     通过审核
     1.创建用户
     */
    @PutMapping("/pass_approve/{id}")
    @Transactional(rollbackFor = Exception.class)
    public Result passApprove(@PathVariable("id") String id) throws Exception {
        //获取租户信息-判断是否重复审核
        ClientEntity client = clientDao.findById(id).orElseGet(() -> null);
        if (client.isApproved()) ExceptionUtil.throwException("该企业已审核，不可重复审核!");
        client.setApproved(true);
        client.setAuditTime(LocalDateTime.now());
        myService.save(client);//这里会更新用户的信息
        return Result.ok();
    }


}
