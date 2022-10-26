package com.tangzhangss.commonutils.querydsl;

import com.google.common.collect.ImmutableList;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.FactoryExpressionBase;
import com.querydsl.core.types.Visitor;
import com.tangzhangss.commonutils.base.SysBaseEntity;
import lombok.SneakyThrows;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryResultMap extends FactoryExpressionBase<Map<String,?>> {
    private final ImmutableList<Expression<?>> args;

    public QueryResultMap(List<Expression<?>> args) {
        super((Class) Map.class);
        this.args = ImmutableList.copyOf(args);
    }

    @Override
    public List<Expression<?>> getArgs() {
        return args;
    }

    @SneakyThrows
    @Override
    @Nullable
    public Map<String,?> newInstance(Object... args) {
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            Object param = args[i];
            if (param instanceof SysBaseEntity) {
                // 如果是对象则遍厉对象里的属性
                //得到class
                Class clazz = param.getClass();
                //得到所有属性, 包括父类
                // 遍历往上获取父类，直至最后一个父类
                for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
                    // 获取当前类所有的字段
                    Field[] field = clazz.getDeclaredFields();
                    for (Field f : field) {
                        f.setAccessible(true);
                        map.put(f.getName(),f.get(param));
                    }
                }
                continue;
            }
            String key = this.getArgs().get(i).toString();
            if(key.contains(" as ")){
                key = key.split(" as ")[1];
            }else{
                String [] arr = key.split("\\.");
                key = arr[arr.length-1];
                // 使用了函数的后面会带一个），得删了
                key = key.replace(")","");
            }
            map.put(key, args[i]);
        }
        return map;
    }




    @Nullable
    @Override
    public <R, C> R accept(Visitor<R, C> v, @Nullable C context) {
        return v.visit(this, context);
    }
}
