package com.tangzhangss.commonutils.i18n;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 翻译，前端request请求header里面设置accept-language=en-US zh-CN..实现翻译
 */
@Component
public class Translator implements MessageSourceAware {
    private static MessageSource messageSource;
    /**
     * 单Key翻译
     */
    public static String get(String key) {
        return messageSource==null?key:messageSource.getMessage(key, null, key, LocaleContextHolder.getLocale());
    }
    public static String get(String key,Object [] args) {
        return messageSource==null?key:messageSource.getMessage(key, args, key, LocaleContextHolder.getLocale());
    }

    @Resource
    public void setMessageSource(MessageSource messageSource) {
        Translator.messageSource=messageSource;
    }
}
