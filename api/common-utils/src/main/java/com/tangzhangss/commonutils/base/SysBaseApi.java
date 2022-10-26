package com.tangzhangss.commonutils.base;

import com.tangzhangss.commonutils.resultdata.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


public abstract class SysBaseApi<T extends SysBaseEntity,TT extends SysBaseService> {

    /**
     * service
     */
    @Autowired
    protected TT myService;

    @GetMapping
    public Result get(HttpServletRequest request) throws Exception{
        return new Result(HttpStatus.OK,myService.get(request,null));
    }

    @GetMapping("/get/{id}")
    public Result getOne(@PathVariable("id")long id){
        //查询clientId是否存在
        T info = (T) myService.get(id);
        return new Result(HttpStatus.OK,info);
    }

    /**
     * 保存多个对象
     * @param data 数据
     * @return 保存成功的数据
     */
    @PutMapping
    public Result put(@RequestBody List<T> data) throws Exception{
        return myService.put(data);
    }
    /**
     * 保存一个对象
     * @param data 数据
     * @return 保存成功之后的对象
     */
    @PostMapping
    public Result post(@RequestBody T data) throws Exception{
        return Result.ok().data(myService.save(data));
    }

    @DeleteMapping
    public Result delete(@RequestBody List<T> data) throws Exception{
        myService.deleteAll(data);
        return Result.ok();
    }

    @DeleteMapping("/clean")
    public Result clean(@RequestBody Map mp) throws Exception{
        myService.clean(mp);
        return Result.ok();
    }


}
