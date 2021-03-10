package com.tangzhangss.commonutils.querydsl;

import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSONObject;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tangzhangss.commonutils.base.SysContext;
import com.tangzhangss.commonutils.utils.BaseUtil;
import com.tangzhangss.commonutils.utils.ExceptionUtil;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
public class QueryDslUtil{
    @PersistenceContext
    protected EntityManager em;


    protected static ThreadLocal<JPAQuery> jpaQuery = new ThreadLocal<>();
    protected static ThreadLocal<EntityPath> fromEntity = new ThreadLocal<>();


    /**
     *
     * @param qExpression 查询字段
     * @param entity from的实体
     * @return
     */
    public QueryDslUtil getJPAQuery(List<Expression<?>>qExpression,EntityPath entity){
        fromEntity.set(entity);
        jpaQuery.set(new JPAQueryFactory(em).select(new QueryResultMap(qExpression)).from(entity));
        return this;
    }
    public JPAQuery get(){
        return this.jpaQuery.get();
    }
    /**
     * querydsl 查询方法
     *
     * 条件构建不支持级联（A.b@EQ）
     * 不支持排序分组，如果有排序分组请在调用此方法之前处理query对象
     * @param request 前端请求--构建查询条件-格式如：base里面的get()
     * @param paramsMap 后端条件构建
     * @return
     */
    public QueryResults getQueryResults(HttpServletRequest request, Map<String, String> paramsMap) {
        JPAQuery query = jpaQuery.get();
        if (query == null) {
            return null;
        }
        int iPgIndex = 1;
        int iPgSize = Integer.MAX_VALUE;
        if (request != null){
            String sPageIndex;
            String sPageSize;
            sPageIndex = request.getParameter("iPageIndex");
            sPageSize = request.getParameter("iPageSize");
            if (StringUtils.isNotBlank(sPageIndex)) {
                iPgIndex = Integer.parseInt(sPageIndex);
            }
            if (StringUtils.isNotBlank(sPageSize)) {
                iPgSize = Integer.parseInt(sPageSize);
            }
            Enumeration parameterNames = request.getParameterNames();
            while(parameterNames.hasMoreElements()) {
                String key = (String)parameterNames.nextElement();
                String value = request.getParameter(key);
                //只有包含@的才算作条件构造
                if (key.contains("@")) {
                    BooleanExpression b = this.getQQueryExpression(key, value);
                    if (b != null) {
                        query.where(b);
                    }
                }
            }
        }
        if (paramsMap != null) {
            Iterator it = paramsMap.entrySet().iterator();
            while(it.hasNext()) {
                Map.Entry<String, String> entry = (Map.Entry)it.next();
                BooleanExpression b = this.getQQueryExpression(entry.getKey(), entry.getValue());
                if (b != null) {
                    query.where(b);
                }
            }
        }

        query.offset(iPgIndex-1).limit(iPgSize);

        //必须带上clientId
        BooleanExpression b = getQQueryExpression("clientId@EQ",SysContext.getClientId());
        if(b != null){
            query.where(b);
        }

        return query.fetchResults();
    }
    /**
     * 获取qQuery的条件
     * 不支持级联（A.B@EQ）
     * @param key sName@EQ
     * @param value 111
     * @return
     */
    @SneakyThrows
    public BooleanExpression getQQueryExpression(String key, Object value) {
        if(StringUtils.isBlank(key)){
            ExceptionUtil.throwException("key参数错误!");
        }
        if (key.indexOf("@")==-1) return null;
        String [] arr = key.split("@");
        String sType = arr[1];
        if(key.length()!=2)ExceptionUtil.throwException("key参数错误!");
        Object o = fromEntity;

        SimpleExpression l = (SimpleExpression) BaseUtil.readAttributeValue(o,arr[0]);
        if (l==null) return  null;
        String filedType = l.getType().toString();
        if (!sType.equals("IN")&&!sType.equals("NIN")&&!value.equals("null")) {
            value = BaseUtil.convertObject(filedType,value);
        }
        LiteralExpression ll = null;
        // 这里判断下，String，Number，Date的都不太一样，
        NumberExpression ne = null;
        if (l.getClass().toString().equals("class com.querydsl.core.types.dsl.NumberPath")){
            ne = (NumberExpression) l;
        } else {
            ll = (LiteralExpression) l;
        }
        switch (sType) {
            case "EQ":
                if (value == null || value.equals("null")) return ll==null?ne.isNull():ll.isNull();
                else return ll==null?ne.eq(value):ll.eq(value);
            case "LIKE":
                if (ne!=null) return ne.like("%" + value + "%");
                StringExpression st = (StringExpression) ll;
                return st.like("%" + value + "%");
            case "GT":
                if (ne!=null) {
                    if (ne.getType().toString().equals("class java.lang.Integer")) return ne.gt(Integer.parseInt(value.toString()));
                    else return ne.gt(Double.parseDouble(value.toString()));
                }
                return ll.gt((Comparable) Convert.convert(value.getClass(), value));
            case "LT":
                if (ne!=null) {
                    if (ne.getType().toString().equals("class java.lang.Integer")) return ne.gt(Integer.parseInt(value.toString()));
                    else return ne.gt(Double.parseDouble(value.toString()));
                }
                return ll.lt((Comparable) Convert.convert(value.getClass(), value));
            case "NEQ":
                if (value == null || value.equals("null")) return ne==null?ll.isNotNull():ne.isNotNull();
                    // builder.fun
                else return ne==null?ll.ne(value):ne.ne(value);
            case "IN":
            case "NIN":
                String [] a = value.toString().split(",");
                if (ne!=null) {
                    if (ne.getType().toString().equals("class java.lang.Integer")) {
                        Integer [] inter = new Integer[a.length];
                        for (int m = 0; m < a.length; m++) {
                            inter[m] = Integer.parseInt(a[m]);
                        }
                        if (sType.equals("IN")) return ne.in(inter);
                        else return ne.notIn(inter);
                    } else {
                        Double [] doub = new Double[a.length];
                        for (int m = 0; m < a.length; m++) {
                            doub[m] = Double.parseDouble(a[m]);
                        }
                        if (sType.equals("IN")) return ne.in(doub);
                        else return ne.notIn(doub);
                    }
                }
                if (sType.equals("IN"))return ll.in(a);
                return ll.notIn(a);
            default:
                return null;
        }
    }
}
