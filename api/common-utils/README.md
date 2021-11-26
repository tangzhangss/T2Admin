## COMMON-UTILS PAGES

### 日志

```
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {
    String value() default "";
}

@Pointcut("@annotation(com.tangzhangss.commonutils.aspect.syslog.SysLog)")
public void logPointCut() {}

@Around("logPointCut()")
public Object around(ProceedingJoinPoint point) throws Throwable {
   long beginTime = System.currentTimeMillis();
   //执行方法
   Object result = point.proceed();
   //执行时长(毫秒)
   long time = System.currentTimeMillis() - beginTime;
   //保存日志
   saveSysLog(point, time, JSONUtil.toJsonPrettyStr(result));
   return result;
}
```

----
### 通用接口服务

```
/**
 * 构建查询条件
 * @param key 如：Ａ＠ＥＱ Ａ.B@IN A@LIKE  A_EQ,B_LIKE@OR
 * 多字段OR查询  A_EQ,B_LIKE@OR => (A@EQ=value or B@LIKE=value)
 *            也可省略_EQ,_LIKE 默认_EQ
 */
protected Predicate getPredicate(String key, Object value,CriteriaBuilder builder,Root<T> root){
    Predicate predicate = null;
    String [] arr = key.split("@");
    Path expression = null;
    String sKey = arr[0];

    //没有值，如: id@EQ=
//        if (StringUtils.isBlank(String.valueOf(value))) return null;
    //is null 判断
    if (StringUtils.upperCase(String.valueOf(value)).equals("NULL"))value=null;

    //必须至少包含id@EQ，即分割之后两个元素
    if (arr.length>=2) {


        //A.b_EQ,B_LIKE@OR="str"
        if(arr[1].toUpperCase().equals("OR")){
            List<Predicate> predicateList = new ArrayList<>();
            String[] sList = sKey.split(",");
            for(int i=0;i<sList.length;i++){
                String[] s = sList[i].split("_");
                String tKey = s[0];
                String tC="EQ";
                if(s.length>1){
                    tC = s[1];
                }
                predicateList.add(getPredicate(tKey+"@"+tC,value,builder,root));
            }
            return builder.or(predicateList.toArray(new Predicate[predicateList.size()]));
        }


        //url传过来全是String类型的这里需要转换一下_有些条件不需要转换（如：范围查询单独转换）
        if(isParseObject(arr[1])){
            value = parseObject(sKey,value);
        }
        // 多表查询，格式 A.name@EQ=zhangsan
        if (sKey.contains(".")) {
            String[] names = StringUtils.split(sKey, ".");
            for (int i = 0; i < names.length; i++) {
                String tbName = names[i];
                if (expression == null) {
                    expression = root.get(tbName);
                } else {
                    if (expression.getJavaType() == Map.class) {
                    } else {
                        expression = expression.get(tbName);
                    }
                }
            }
        }else {
            expression = root.get(sKey);
        }

        switch (arr[1].toUpperCase()){
            case "EQ":
                if (null==value) predicate = builder.isNull(expression);
                else predicate = builder.equal(expression, value);
                break;
            case "LIKE":
                predicate = builder.like(expression, "%" + value + "%");
                break;
            case "GT":
                predicate = builder.greaterThan(expression, (Comparable)Convert.convert(value.getClass(),value));
                break;
            case "LT":
                predicate = builder.lessThan(expression,  (Comparable)Convert.convert(value.getClass(),value));
                break;
            case "GTE":
                predicate = builder.greaterThanOrEqualTo(expression,  (Comparable)Convert.convert(value.getClass(),value));
                break;
            case "LTE":
                predicate = builder.lessThanOrEqualTo(expression,  (Comparable)Convert.convert(value.getClass(),value));
                break;
            case "NEQ":
                if (null==value) predicate = builder.isNotNull(expression);
                else predicate = builder.notEqual(expression, value);
                break;
            case "IN":{
                CriteriaBuilder.In<Object> in = builder.in(expression);
                String[] inArr = value.toString().split(",",-1);
                for (int i = 0; i < inArr.length; i++) {
                    Object s = inArr[i];
                    //每一个单独转换
                    s = parseObject(sKey, s);
                    in.value(s);
                }
                predicate = in;
                break;
            }
            case "NIN":{
                CriteriaBuilder.In<Object> in = builder.in(expression);
                String[] inArr = value.toString().split(",");
                for (int i = 0; i < inArr.length; i++) {
                    Object s = inArr[i];
                    //每一个单独转换
                    s = parseObject(sKey, s);
                    in.value(s);
                }
                predicate = builder.not(in);
                break;
            }
            default:
                //其他的都不是默认等于
                predicate = builder.equal(expression, value);
                break;
        }
    }


    return predicate;
}
/**
 * 检测和设置编码 多个
 */
public void checkAndSetCodeFormula(List<TT> datas,String [] codes,String columnName) throws Exception{
    if (datas.size()!=codes.length) ExceptionUtil.throwException("参数错误,数据记录数与编码数量不一致");
    //查询编码是否存在于数据库
    //20200126
    //给一个偏移量，如果存在相同的编码改为:  编码-偏移量
    //如 C0001 => C0001-1
    Map map = new HashMap();
    map.put(columnName+"@IN",StringUtils.join(codes,","));
    List<TT> items = getWithMap(map);
    //编码重复的加上偏移量
    for (int i = 0; i < items.size(); i++) {
        int index = Arrays.binarySearch(codes, BaseUtil.readAttributeValue(items.get(i),columnName));
        if (index==-1)continue;
        String code = codes[i];
        int offset=1;
        String newCode;
        for (;;){
            newCode = code+"-"+offset;
            Object o = getOneWithMapString(columnName+"@EQ:"+newCode);
            if(o==null)break;
            offset++;
        }
        codes[i] = newCode;
    }
    //将新的code赋值给datas
    for (int i = 0; i < datas.size(); i++) {
        BaseUtil.setAttributeValue(datas.get(i),columnName,codes[i]);
    }
}
```    

----
### TOKEN

```
public boolean checkToken(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {

    HttpServletRequest req = (HttpServletRequest)servletRequest;

    String tzTk = req.getHeader(AUTH);

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
    String path = req.getServletPath();//获取请求路径
    // 判断如果没登录则报错
    if (userInfo==null && !allowedPath(path)) return invalidTokenMsgHandle(servletRequest,servletResponse, Translator.get("unauthorized"));

    //设置匿名请求的用户信息
    if(userInfo==null){
        userInfo = getAnonymousSysContext();
    }
    SysContext.setUser(userInfo);

    return true;
}
```

----
### JpaQuery

```
/**
 * querydsl 查询方法
 * 这个方法带分页  不能多字段分组
 * 默认带clientId,不会携带usable=true,,需要请在request和paramsMap 添加 usable@eq,true or false
 *
 * 条件构建不支持级联（A.b@EQ --- 20210421支持）
 * 不支持排序分组，如果有排序分组请在调用此方法之前处理query对象
 * @param request 前端请求--构建查询条件-格式如：base里面的get()
 * @param paramsMap 后端条件构建
 * @param entityPathMap 后端条件构建
 *                      A.b@EQ  =>  map.put("A",entityPath)
 *                      A.B.c@EQ  =>  map.put("A.B",entityPath)
 * @return
 */
public QueryResults getQueryFetchResults(HttpServletRequest request, Map<String, String> paramsMap,Map<String,EntityPath> entityPathMap) {
    JPAQuery query = jpaQuery.get();
    if (query == null) { return null; }
    HandleJPAQuery(request,paramsMap,entityPathMap);
    return query.fetchResults();
}
```
----
### Jekyll Themes

Your Pages site will use the layout and styles from the Jekyll theme you have selected in your [repository settings](https://github.com/tangzhangss/commonutils.github.io/settings/pages). The name of this theme is saved in the Jekyll `_config.yml` configuration file.

----
### Support or Contact

Having trouble with Pages? Check out our [documentation](https://docs.github.com/categories/github-pages-basics/) or [contact support](https://support.github.com/contact) and we’ll help you sort it out.
