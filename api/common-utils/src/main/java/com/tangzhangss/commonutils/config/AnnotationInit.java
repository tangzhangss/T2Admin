package com.tangzhangss.commonutils.config;

import com.tangzhangss.commonutils.base.SysBaseEntity;
import com.tangzhangss.commonutils.base.SysDynamic;
import com.tangzhangss.commonutils.utils.ExceptionUtil;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.FieldInfo;
import javassist.bytecode.annotation.Annotation;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.reflections.Reflections;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;

/**
 * 初始化注解
 *
 * 动态加入注解
 *
 * 无用，猜测无spring代理有关
 */
//@Component
public class AnnotationInit implements CommandLineRunner{
    @Override
    public void run(String... args) throws Exception {
        Reflections reflections = new Reflections("");
        Set<Class<? extends SysBaseEntity>> subTypesOf = reflections.getSubTypesOf(SysBaseEntity.class);

        subTypesOf.forEach(subTypes->{
            if(subTypes.isAnnotationPresent(SysDynamic.class)){
                addAnnotation(subTypes.getTypeName(), DynamicInsert.class.getTypeName());
                addAnnotation(subTypes.getTypeName(), DynamicUpdate.class.getTypeName());
            }
        });
    }

    /**
     * 功能，动态的给类属性添加注解
     *
     * @param className 类名
     * @param annotationName 注解类型名称
     */
    public static void addAnnotation(String className, String annotationName) {
        try {
            ClassPool pool = ClassPool.getDefault();
            CtClass ct = pool.get(className);
            ClassFile classFile = ct.getClassFile();
            ConstPool constPool = classFile.getConstPool();
            AnnotationsAttribute attribute = (AnnotationsAttribute) classFile.getAttribute(AnnotationsAttribute.visibleTag);
            Annotation annotation = new Annotation(annotationName,constPool);
            attribute.addAnnotation(annotation);
            //将注解添加到类上
            classFile.addAttribute(attribute);
        } catch (Exception e) {
            ExceptionUtil.throwException(e.getMessage());
        }
    }
}
