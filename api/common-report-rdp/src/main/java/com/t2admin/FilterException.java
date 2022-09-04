package com.t2admin;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/filterException")
public class FilterException {
    @RequestMapping("/500")
    public void Exception1001(HttpServletRequest request) throws Exception {
        throw new Exception((String) request.getAttribute("errorMessage"));
    }

    /**
     * 登录超时需要重新登录
     */
    @RequestMapping("/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result Exception2003(HttpServletRequest request){
        return new Result(HttpStatus.UNAUTHORIZED,request.getAttribute("errorMessage"));
    }
}
