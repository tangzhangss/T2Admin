package io.report.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.report.common.utils.ServerUtil;
import io.report.modules.bddp.entity.BddpConstant;

/**
 * WebMvc配置
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${report.bddp.path}")
	private String bddppath;
    @Value("${report.relative-path}")
    private Boolean relativePath;
    @Value("${report.material.path}")
    private String materialpath;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        
        registry.addResourceHandler("/statics/**").addResourceLocations("classpath:/statics/");
        registry.addResourceHandler("/bddpConfig/**").addResourceLocations("file:"+ServerUtil.getDataPath(relativePath, bddppath,BddpConstant.BDDPFILE_PATH));
        registry.addResourceHandler("/bddpDiyTag/**").addResourceLocations("file:"+ServerUtil.getDataPath(relativePath, bddppath,BddpConstant.DIYTAGS_PATH));
        registry.addResourceHandler("/mapfiles/**").addResourceLocations("file:"+ServerUtil.getDataPath(relativePath, bddppath,BddpConstant.MAPFILE_PATH));
        registry.addResourceHandler("/material/**").addResourceLocations("file:"+ServerUtil.getDataPath(relativePath, materialpath));
    }
    @Bean
    public ServletRegistrationBean<com.pro.servlet.AsReport> servletRegistrationBean() {
      return new ServletRegistrationBean<com.pro.servlet.AsReport>(new com.pro.servlet.AsReport(), "/AsReport");
    }

//    @Override
//    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
//        ObjectMapper objectMapper = jackson2HttpMessageConverter.getObjemctMapper();
//
//        //生成json时，将所有Long转换成String
//        SimpleModule simpleModule = new SimpleModule();
//        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
//        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
//        objectMapper.registerModule(simpleModule);
//
//        jackson2HttpMessageConverter.setObjectMapper(objectMapper);
//        converters.add(0, jackson2HttpMessageConverter);
//    }

}
