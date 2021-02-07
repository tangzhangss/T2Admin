package com.tangzhangss.commonservice.client;

import cn.hutool.core.convert.Convert;
import com.tangzhangss.commonservice.common.BaseConfig;
import com.tangzhangss.commonservice.user.UserDao;
import com.tangzhangss.commonservice.user.UserEntity;
import com.tangzhangss.commonservice.user.UserService;
import com.tangzhangss.commonutils.exception.ServiceException;
import com.tangzhangss.commonutils.resultdata.Result;
import com.tangzhangss.commonutils.uidgenerator.UidGeneratorService;
import com.tangzhangss.commonutils.utils.BaseUtil;
import org.apache.commons.lang.StringUtils;
import org.hibernate.query.criteria.internal.OrderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TupleElement;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;

@Service
@PropertySource({"classpath:application.properties"})
public class ClientService {
    @Autowired
    public ClientDao myDao;

    @Autowired
    public UserService userService;
    @Autowired
    public UserDao userDao;

    //日志打印
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${spring.datasource.driverClassName:mysql}")
    private String driverClassName;

    @Autowired
    protected EntityManager entityManager;


    @Autowired
    protected UidGeneratorService uidGeneratorService;

    //获取分组分页数据
    private Page<ClientEntity> getGroupByAndPageResult(HttpServletRequest request, String groupValue) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = builder.createTupleQuery();
        Root root = cq.from(getGenericType(0));
        getQueryByParams(root, cq, builder, request, null);
        queryByGroup(groupValue, builder, root, cq);
        int count = getCqCount(cq, builder);
        TypedQuery<Tuple> tq = entityManager.createQuery(cq);
        int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        tq.setFirstResult((pageIndex - 1) * pageSize);
        tq.setMaxResults(pageSize);
        List<Tuple> tupleResult = tq.getResultList();
        // 组成Map格式
        List<Map<String, Object>> list = new ArrayList<>();
        for (Tuple t : tupleResult) {
            List<TupleElement<? extends Object>> elements = t.getElements();
            Map<String, Object> mp = new HashMap<>();
            for (TupleElement e : elements) {
                mp.put(e.getAlias(), t.get(e));
            }
            list.add(mp);
        }
        // 构造分页对象并返回
        PageRequest pageRequest = PageRequest.of((pageIndex - 1), pageSize);
        return new PageImpl(list, pageRequest, count);
    }

    /**
     * 获取查询语句的count
     *
     * @param cq      语句
     * @param builder build
     * @return 总行数
     */
    protected int getCqCount(CriteriaQuery cq, CriteriaBuilder builder) {
        CriteriaQuery<Long> subquery = builder.createQuery(Long.class);
        Root root;
        if (cq.getRestriction() != null) {
            subquery.where(cq.getRestriction());
        }
        if (cq.getGroupRestriction() != null) {
            subquery.having(cq.getGroupRestriction());
        }
        boolean bGroup = false;
        if (cq.getGroupList() != null && !cq.getGroupList().isEmpty()) {
            bGroup = true;
            subquery.groupBy(cq.getGroupList());
        }
        if (cq.getRoots().isEmpty()) {
            root = subquery.from(getGenericType(1));
        } else {
            subquery.getRoots().addAll(cq.getRoots());
            root = subquery.getRoots().iterator().next();
        }
        if (cq.isDistinct()) {
            subquery.select(builder.countDistinct(root));
        } else {
            subquery.select(builder.count(root));
        }
        TypedQuery<Long> tp = entityManager.createQuery(subquery);
        // countCq.from(subquery.getJavaType());
        // 有分组的话直接取size就是总行数了
        if (bGroup) return tp.getResultList().size();
        // 否则就取第一个
        return tp.getSingleResult().intValue();
    }

    /**
     * 根据分组条件查询
     * api调用
     * 如：&groupBy=id,code
     */
    protected CriteriaQuery queryByGroup(String groupValue, CriteriaBuilder builder, Root<ClientDao> root1, CriteriaQuery cq) {
        String[] groupArr = groupValue.split(",");
        List<Selection<? extends Object>> selectionList = new ArrayList<>();
        List<Expression> exList = new ArrayList<>();
        for (int i = 0; i < groupArr.length; i++) {
            Path p = getExpression(groupArr[i], root1);
            p.alias(groupArr[i]);
            selectionList.add(p);
            exList.add(p);
        }
        cq.groupBy(exList);
        cq.multiselect(selectionList);
        return cq;
    }

    public Page<ClientEntity> get(HttpServletRequest request, Map<String, String> paramsMap) {
        int iPgIndex = 1;//默认第一页
        int iPgSize = 10;//默认1页10个
        Sort sort;//分页
        Specification specification;

        if (request != null) {
            String sPageIndex = request.getParameter("pageIndex");
            String sPageSize = request.getParameter("pageSize");
            if (StringUtils.isNotBlank(sPageIndex)) iPgIndex = Integer.parseInt(sPageIndex);
            if (StringUtils.isNotBlank(sPageSize)) iPgSize = Integer.parseInt(sPageSize);
        }
        /*
            前端有分组查询单独处理
            这里的分组查询只是为了分页,以及配置表格搜索，（前端表格组件接口统一）没有真实数据，所以前端使用分组查询之后，后端需要自己写接口构建数据
         */
        String groupValue = request.getParameter("groupBy");
        if (StringUtils.isNotBlank(groupValue)) {
            return getGroupByAndPageResult(request, groupValue);
        }

        if (paramsMap != null) {//paramsMap用于开人人员调用-不分页
            iPgSize = Integer.MAX_VALUE;
        }

        if (iPgIndex <= 0) iPgIndex = 1;
        PageRequest page = PageRequest.of(iPgIndex - 1, iPgSize);
        specification = getCommonSpecification(request, paramsMap);
        Page<ClientEntity> pageList = myDao.findAll(specification, page);

        return pageList;
    }

    /**
     * 获取通用的筛选条件，前台请求的格式需要是sFieldName@sOperator=sValue , 无sOperator时默认为 不拼接的
     *
     * @param request
     * @return
     */
    protected Specification getCommonSpecification(HttpServletRequest request, Map<String, String> paramMap) {
        return new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery cq, CriteriaBuilder builder) {
                cq = getQueryByParams(root, cq, builder, request, paramMap);
                if (cq == null) return null;
                return cq.getRestriction();
            }
        };
    }

    private CriteriaQuery getQueryByParams(Root root, CriteriaQuery cq, CriteriaBuilder builder, HttpServletRequest request, Map<String, String> paramMap) {
        List<Predicate> predicates = new ArrayList<>();

        if (request != null) {
            // 自定义的排序
            List<Order> orders = new ArrayList<>();
            orders = getOrderByStr(request.getParameter("orderBy"), root);
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String key = parameterNames.nextElement();
                String value = request.getParameter(key);
                if (key.equals("iPageSize")) continue;
                if (key.equals("iPageIndex")) continue;
                if (key.equals("orderBy")) continue;
                if (key.equals("queryAll")) continue;
                if (value == null || value.isEmpty()) continue;
                Predicate predicate = getPredicate(key, value, builder, root);
                if (predicate == null) continue;
                predicates.add(predicate);
            }

            //分组查询不能带这个条件
            if (StringUtils.isBlank(request.getParameter("groupBy")) && paramMap == null) {
                // 默认按创建时间排序
                orders.add(new OrderImpl(getExpression("createTime", root), false));
            }
            cq.orderBy(orders);
        }
        if (paramMap != null) {
            Iterator<Map.Entry<String, String>> it = paramMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                Predicate predicate = getPredicate(entry.getKey(), entry.getValue(), builder, root);
                if (predicate == null) continue;
                predicates.add(predicate);
            }
        }

        return cq.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
    }

    protected Predicate getPredicate(String key, Object value, CriteriaBuilder builder, Root<ClientEntity> root) {
        Predicate predicate = null;
        String[] arr = key.split("@");
        Path expression = null;
        String sKey = arr[0];

        //没有值，如: id@EQ=
        if (value == null || value.toString().isEmpty()) return null;
        //必须至少包含id@EQ，即分割之后两个元素
        if (arr.length >= 2) {
            //url传过来全是String类型的这里需要转换一下_有些条件不需要转换（如：范围查询单独转换）
            if (isParseObject(arr[1])) {
                value = parseObject(sKey, value);
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
            } else {
                expression = root.get(sKey);
            }

            switch (arr[1].toUpperCase()) {
                case "EQ":
                    if (null == value || value.equals("null")) predicate = builder.isNull(expression);
                    else predicate = builder.equal(expression, value);
                    break;
                case "LIKE":
                    predicate = builder.like(expression, "%" + value + "%");
                    break;
                case "GT":
                    predicate = builder.greaterThan(expression, (Comparable) Convert.convert(value.getClass(), value));
                    break;
                case "LT":
                    predicate = builder.lessThan(expression, (Comparable) Convert.convert(value.getClass(), value));
                    break;
                case "GTE":
                    predicate = builder.greaterThanOrEqualTo(expression, (Comparable) Convert.convert(value.getClass(), value));
                    break;
                case "LTE":
                    predicate = builder.lessThanOrEqualTo(expression, (Comparable) Convert.convert(value.getClass(), value));
                    break;
                case "NEQ":
                    if (null == value || value.equals("null")) predicate = builder.isNotNull(expression);
                    else predicate = builder.notEqual(expression, value);
                    break;
                case "IN": {
                    CriteriaBuilder.In<Object> in = builder.in(expression);
                    String[] inArr = value.toString().split(",");
                    for (int i = 0; i < inArr.length; i++) {
                        Object s = inArr[i];
                        //每一个单独转换
                        s = parseObject(sKey, s);
                        in.value(s);
                    }
                    predicate = in;
                    break;
                }
                case "NIN": {
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

    // 根据字符串获取排序，规则 sField1@DESC,sField2,sField3
    private List<Order> getOrderByStr(String str, Root root) {
        List<Order> orders = new ArrayList<>();
        if (StringUtils.isBlank(str)) return orders;
        // 获取前端传过来的排序
        String orderBy = str;
        if (StringUtils.isNotBlank(orderBy)) {
            String[] arr = orderBy.split(",");
            for (int i = 0; i < arr.length; i++) {
                String orderItem = arr[i];
                // 默认按正序排
                if (!orderItem.contains("@")) {
                    // orders.add(new Sort.Order(Sort.Direction.ASC, orderItem));
                    orders.add(new OrderImpl(getExpression(orderItem, root), true));
                    continue;
                }
                String[] sArr = orderItem.split("@");
                if (sArr[1].equals("DESC")) {
//                    orders.add(new Sort.Order(Sort.Direction.DESC, sArr[0]));
                    orders.add(new OrderImpl(getExpression(sArr[0], root), false));
                    continue;
                }
                orders.add(new OrderImpl(getExpression(sArr[0], root), true));
            }
        }
        return orders;

    }

    private Path getExpression(String sKey, Root root) {
        Path expression = null;
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
        } else {
            expression = root.get(sKey);
        }
        return expression;
    }

    /*
   中途需要 中断或其他操作 直接通过抛出异常的方式来通知
    */
    @Transactional(rollbackFor = Exception.class)
    public Result put(List<ClientEntity> datas) throws Exception {
        for (ClientEntity data : datas) {
            this.checkUnionField(data);
            myDao.save(data);
        }
        return new Result(HttpStatus.OK, datas);
    }

    @Transactional(rollbackFor = Exception.class)
    public Result save(ClientEntity data) throws Exception {
        this.checkUnionField(data);

        if (data.isApproved()) {
            //已经被审核的，更新user信息
            //创建用户
            UserEntity user = userService.getOneCustomWithMapString("clientId@EQ=" + data.getId() + "&username@EQ=" + data.getId());
            if (user == null) {
                user = new UserEntity();
                user.setId(uidGeneratorService.getuid());
                user.setPassword(BaseUtil.twiceMd5Salt(BaseConfig.USER_DEFAULT_PASSWORD));//密码设置
            }
            user.setAvatars(data.getLogo());
            user.setEmail(data.getEmail());
            user.setPhone(data.getPhone());
            user.setName(data.getName());
            user.setUsable(data.isUsable());
            user.setUsername(data.getId());//用户名就是手机号码
            user.setClientId(data.getId());
            user.setRemark("企业管理员账号");
            userDao.save(user);
        }

        myDao.save(data);
        return new Result(HttpStatus.OK, data);
    }

    /*
     校验不能重复的字段
     校验唯一标识字段

     传入List<map> 当前实体类的属性
     ~~~~请将第一个提示字段value放在第一个~~~~
     map.put("param1,param2...","编码") ==》 编码:param1-value,被占用,请修改!
     map.put("param1,param2...","名称") ==》 名称:param1-value,被占用,请修改!
     多个map查到到一个重复就不会再校验下面的直接返回提示
     */
    protected List<Map<String, String>> getCheckFields() {
        return null;
    }

    /**
     * 检测字段的唯一新
     *
     * @param data 新插入|修改的数据
     * @throws Exception
     */
    protected void checkUnionField(ClientEntity data) throws Exception {
        List<Map<String, String>> m = this.getCheckFields();
        if (m == null || m.isEmpty()) return;
        Class<?> aClass = data.getClass();
        //得到属性
        Field field = null;
        // 校验是否存在
        for (Map<String, String> ckItem : m) {
            // 过滤条件的map
            Map<String, String> flMap = new HashMap<>();
            for (String mKey : ckItem.keySet()) {
                String[] keys = mKey.split(",");
                StringBuffer sb = new StringBuffer();
                for (String key : keys) {
                    field = aClass.getDeclaredField(key);
                    //打开私有访问
                    field.setAccessible(true);
                    //获取属性值
                    String value = String.valueOf(field.get(data));
                    flMap.put(key + "@EQ", value);
                    if (sb.length() == 0) sb.append(ckItem.get(mKey)).append("：").append(value).append(",");
                }
                //不是当前记录
                flMap.put("id@NEQ", data.getId());
                sb.append("被占用,请修改");
                // 如果有，则说明重复了
                if (!myDao.findAll(this.getCommonSpecification(null, flMap)).isEmpty()) {
                    throw new ServiceException(sb.toString());
                }
            }
        }
    }


    /*
     根据map查询数据
     */
    public List<ClientEntity> getWithMap(Map<String, String> mp) {
        Page<ClientEntity> p = get(null, mp);
        List<ClientEntity> res = p.getContent();
        //get到的数据都从数据库持久态装为游离态。。。任何修改不映射数据..但是只是当前对象的属性,oneToOne的实体属性注意修改之后还是会更新数据库
//        Session session=em.unwrap(Session.class);
//        res.forEach(session::evict);
        //不能加这个，有的请情况会包  No transactional EntityManager available 异常
        return res;
    }

    /*
    根据mapString查询数据
        key=value&key=value
     */
    public List<ClientEntity> getWithMapString(String mapstr) {
        Map<String, String> mp = new HashMap<>();
        String[] rows = mapstr.split("&");
        for (String row : rows) {
            String[] temp = row.split("=");
            mp.put(temp[0], temp[1]);
        }
        List<ClientEntity> result = getWithMap(mp);
        return result;
    }

    public ClientEntity getOneWithMapString(String mapstr) {
        List<ClientEntity> items = getWithMapString(mapstr);
        if (items.size() > 0) {
            return items.get(0);
        }
        return null;
    }

    /**
     * 获取类中的字段Field对象
     *
     * @param fieldName 字段属性名
     * @param clazz     类
     */
    private Field getField(String fieldName, Class clazz) {
        List<Field> fieldsList = new ArrayList<Field>();//保存所有的field
        do {  // 遍历所有父类字节码对象
            Field[] declaredFields = clazz.getDeclaredFields();  // 获取字节码对象的属性对象数组
            fieldsList.addAll(Arrays.asList(declaredFields));
            clazz = clazz.getSuperclass();  // 获得父类的字节码对象
        } while (clazz != null);
        //遍历field
        for (Field field : fieldsList) {
            if (field.getName().equals(fieldName)) {
                //找到了匹配的
                return field;
            }
        }
        return null;
    }

    /*
  返回当前字段名的类型转换之后的数据
   */
    private Object parseObject(String key, Object value) {
        //获取当前实体类的class对象
        Class entityClass = getGenericType(0);
        String fieldType = "";
        String[] keys = key.split("\\.");
        Field field = null;
        if (keys.length > 1) {//多级
            for (int i = 0; i < keys.length; i++) {
                field = getField(keys[i], entityClass);
                if (null == field) break;
                entityClass = field.getType();
                if (i == (keys.length - 1)) {
                    fieldType = field.getGenericType().toString();
                }
            }
        } else {//一级
            field = getField(key, entityClass);
            if (null != field) fieldType = field.getGenericType().toString();
        }
        switch (fieldType) {
            case "class java.lang.String":
                //Srting直接跳过
                break;
            case "class java.lang.Integer":
            case "int":
                //当都查询Int类型的不会有问题，当时需要使用in范围查询需要进行类型转换
                value = Integer.valueOf(value.toString());
                break;
            case "class java.time.LocalDate":
                //格式 yyyy-MM-dd 前面必须四位
                value = Convert.convert(LocalDate.class, value);
                break;
            case "class java.time.LocalDateTime":
                //格式 精度只能高于---如有需要自行更改
                value = Convert.convert(LocalDateTime.class, value);
                break;
            case "class java.util.Date":
                //格式 精度只能高于---如有需要自行更改
                value = Convert.convert(Date.class, value);
                break;
            case "class java.lang.Boolean":
            case "boolean":
                //格式 精度只能高于---如有需要自行更改
                value = Convert.convert(Boolean.class, value);
                break;
        }

        return value;
    }

    /*
    是否需要进行对象格式转换
     */
    private boolean isParseObject(String key) {
        //不需要转换的条件
        List<String> keys = Arrays.asList("IN", "NIN");
        if (keys.contains(key)) return false;
        return true;
    }


    /**
     * 获取泛型的class
     *
     * @param index 下标
     */
    private Class getGenericType(int index) {
        Class clazz = index == 0 ? ClientEntity.class : ClientDao.class;
        return clazz;
    }

    /**
     * 根据分组条件查询
     * 此接口仅仅供后端调用！！
     *
     * @param whereMap 查询条件(xxx.yyyEQ=,...) 解析规则和baseService,get方法一样
     * @param groupKey
     * @param groupMap 分组条件 (xxxx.yyy,sum)，   *      * 前面数属性，后面是聚合函数
     *                 分组之后如果有聚合函数-属性名就是聚合函数名
     * @return
     */
    public List<Map<String, Object>> queryByGroup(Map whereMap, String[] groupKey, Map groupMap) {
        if (whereMap == null) whereMap = new HashMap();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createTupleQuery();
        Root root = cq.from(getGenericType(0));//具体实体的Root
        /*
            解析查询条件
         */
        cq = this.getQueryByParams(root, cq, cb, null, whereMap);

       /*
            解析分组条件
        */
        if (groupMap != null) {
            cq = this.getGroupByParams(root, cq, cb, groupKey, groupMap);
        }
        List<Tuple> tupleResult = entityManager.createQuery(cq).getResultList();
        //将Tuple的数据拿出来返回list-map类型
        // 组成Map格式
        List<Map<String, Object>> list = new ArrayList<>();
        for (Tuple t : tupleResult) {
            List<TupleElement<? extends Object>> elements = t.getElements();
            Map<String, Object> mp = new HashMap<>();
            for (TupleElement e : elements) {
                String key = e.getAlias();
                Object value = t.get(e);
                //是聚合concat部分,因为这个聚合函数现在pgsql返回的数组形式所以在这里处理一下
                //如果解决了，删除这段代码
                if (key.endsWith("_concat")) {
                    String concat = (String) value;
                    if (Pattern.compile("^\\{.*}$").matcher(concat).matches()) {
                        value = concat.substring(1, concat.length() - 1);
                    }
                }
                //-------------------------------------
                mp.put(key, value);
            }
            list.add(mp);
        }
        return list;
    }


    /**
     * 获取当前CriteriaQuery添加分组条件之后的CriteriaQuery
     */
    private CriteriaQuery getGroupByParams(Root root, CriteriaQuery cq, CriteriaBuilder cb, String[] groupKey, Map<String, String> paramMap) {

        List<Selection<? extends Object>> selectionList = new ArrayList<>();//聚合显示字段
        List<Expression<? extends Object>> exList = new ArrayList<>();//分组字段
        //分组部分
        for (int i = 0; i < groupKey.length; i++) {
            Path p = getExpression(groupKey[i], root);
            p.alias(groupKey[i]);
            exList.add(p);
        }
        //聚合部分
        Iterator<Map.Entry<String, String>> it = paramMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            String key = entry.getKey();
            Path p = getExpression(key, root);
            String func = entry.getValue();
            if (StringUtils.isNotBlank(func)) {
                String[] funcArr = func.split(",");
                for (int i = 0; i < funcArr.length; i++) {
                    String cFunc = funcArr[i];
                    String tfunc = cFunc.toUpperCase();
                    switch (tfunc) {
                        case "SUM":
                            selectionList.add(cb.sum(p).alias(key + "_" + cFunc));
                            break;
                        case "COUNT":
                            selectionList.add(cb.count(p).alias(key + "_" + cFunc));
                            break;
                        case "MAX":
                            selectionList.add(cb.max(p).alias(key + "_" + cFunc));
                            break;
                        case "MIN":
                            selectionList.add(cb.min(p).alias(key + "_" + cFunc));
                            break;
                        case "CONCAT":
                            //数据库调用聚合连接的函数名
                            //下面这里需要再外面包一个concat或则其他的方法，不然会报一个错，原因不知
                            //如果直接调用cb.function生成的sql语句不能执行
                            String sqlName = driverClassName.toUpperCase();

                            if (sqlName.contains("POSTGRESQL")) {
                                //pgsql--返回的是字符串数组
                                selectionList.add(cb.concat("{}", cb.function("array_agg", String.class, p)).alias(key + "_" + cFunc));
                                //一直提示方法不存在-后面再弄-这里解决了请删除上面queryByGroup相关代码
                                //selectionList.add(cb.concat("",cb.function("string_agg", String.class, p,cb.literal(","))).alias(key+"_"+cFunc));

                            }
                            if (sqlName.toUpperCase().contains("MYSQL")) {
                                selectionList.add(cb.concat("", cb.function("GROUP_CONCAT", String.class, p)).alias(key + "_" + cFunc));
                            }
                            break;
                        default:
                            selectionList.add(p.alias(key + "_" + cFunc));
                    }
                }
            } else {
                selectionList.add(p.alias(key));
            }
        }
        return cq.multiselect(selectionList).groupBy(exList);
    }


    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(List<ClientEntity> data) {
        myDao.deleteAll(data);
    }
}
