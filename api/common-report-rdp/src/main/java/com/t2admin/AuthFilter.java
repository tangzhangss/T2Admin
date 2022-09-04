package com.t2admin;


import cn.hutool.core.collection.ListUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;


@Component
@WebFilter
public class AuthFilter implements Filter {

    public static final String AUTH = "X-Token";
    //正则匹配
    private static List<String> unAuthUrl = ListUtil.list(false,"^/static/.*", "^/user/login",".*/no_auth$");

    //增加不授权的url
    public static void addUnAuthUrl(String urlRegex){
        unAuthUrl.add(urlRegex);
    }

    @Autowired
    RedisService redisService;

    @Override
    public void init(FilterConfig filterConfig){}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            if(checkToken(servletRequest,servletResponse)){
                filterChain.doFilter(servletRequest,servletResponse);
            }
        }catch (Exception e){
            errorRequestMsgHandle(servletRequest,servletResponse,e.getMessage());
        }

    }

    public boolean errorRequestMsgHandle(ServletRequest request,ServletResponse response,String errprMessage) throws ServletException, IOException {
        request.setAttribute("errorMessage",errprMessage);
        request.getRequestDispatcher("/filterException/500").forward(request,response);
        return false;
    }

    public boolean invalidTokenMsgHandle(ServletRequest request,ServletResponse response,String errprMessage) throws ServletException, IOException {
        request.setAttribute("errorMessage",errprMessage);
        request.getRequestDispatcher("/filterException/401").forward(request,response);
        return false;
    }

    public boolean checkToken(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {

        HttpServletRequest req = (HttpServletRequest)servletRequest;

        String tzTk = req.getHeader(AUTH);
        //ajax特殊字符转义
        tzTk = StringEscapeUtils.unescapeHtml(tzTk);

        JSONObject userInfo = null;

        if (StringUtils.isNotBlank(tzTk)) {
            //token格式为(Header:X-Token)UUID.randomUUID()+"&"+用户ID
            Object data = redisService.get(tzTk);
            if (data!=null) {
                userInfo = JSONUtil.parseObj(data);
                //存在该用户重新更新时间
                redisService.set(tzTk, JSONUtil.toJsonStr(userInfo),54000l);//15分钟没有操作提示重新登录
            }
        }
//        String path = req.getServletPath();//获取请求路径
        // 判断如果没登录则报错
//        if (userInfo==null && !allowedPath(path)) return invalidTokenMsgHandle(servletRequest,servletResponse, "unauthorized");

        //设置匿名请求的用户信息
        if(userInfo==null){
            userInfo = getAnonymousSysContext();
        }
        SysContext.setUser(userInfo);

        return true;
    }

    /**
     * 是否取消校验，某一些路径不需要token
     */
    private boolean allowedPath(String path){
        for (String urlRegex:unAuthUrl){
            if(Pattern.compile(urlRegex).matcher(path).matches()){
                return true;
            }
        }
        return false;
    }

    /**
     * 处理没有登录的用户请求 上下文
     */
    private JSONObject getAnonymousSysContext() {
        JSONObject context = new JSONObject();
        context.set("id",Attribute.NO_AUTH_USER_ID);
        context.set("name",Attribute.NO_AUTH_USER_NAME);
        context.set("clientId",Attribute.NO_AUTH_CLIENT_ID);

        return context;
    }

    @Override
    public void destroy() {
        SysContext.removeUserContext();
    }
}
