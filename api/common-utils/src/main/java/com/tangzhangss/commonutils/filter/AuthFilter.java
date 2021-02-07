package com.tangzhangss.commonutils.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tangzhangss.commonutils.base.SysContext;
import com.tangzhangss.commonutils.service.RedisService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Component
@WebFilter
@PropertySource({"classpath:application.properties"})
public class AuthFilter implements Filter {

    public static final String AUTH = "X-Token";
    //正则匹配
    public static List<String> unAuthUrl = Arrays.asList("^/static.*", "^/user/login",".*/no_auth.*");

    @Value("${custom.debug:false}")
    private boolean isDebug;

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

        JSONObject userInfo = null;

        if (StringUtils.isNotBlank(tzTk)) {
            //token格式为(Header:X-Token)UUID.randomUUID()+"&"+用户ID
            String data = String.valueOf(redisService.get(tzTk));
            if (data!=null) {
                userInfo = JSONObject.parseObject(data);
                //存在该用户重新更新时间
                redisService.set(tzTk, JSON.toJSONString(userInfo),54000l);//15分钟没有操作提示重新登录
            }
        }
        String path = req.getServletPath();//获取请求路径
        // 判断如果没登录则报错
        if (userInfo==null && !allowedPath(path)) return invalidTokenMsgHandle(servletRequest,servletResponse,"长时间没有操作，为了您的数据安全，请重新登录!");

        SysContext.setUser(userInfo);

        return true;
    }

    /**
     * 是否取消校验，某一些路径不需要token
     */
    private boolean allowedPath(String path){
        for (String urlRegex:unAuthUrl){
            if(Pattern.compile(urlRegex).matcher(path).matches())
                return true;
        }

        return false;
    }

    @Override
    public void destroy() {
        SysContext.removeUserContext();
    }
}
