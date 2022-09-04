package io.report;

import com.t2admin.AuthFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication(scanBasePackages = {"io.report.*", "com.t2admin"})
@MapperScan(basePackages = {"io.report.modules.*.dao", "com.t2admin.dao"})
public class AdminApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		//不需要权限校验
		AuthFilter.addUnAuthUrl("(.*.html.*)|(^/statics.*)|(^/staticFile.*)|(.*.ico.*)|(^/$)|(.*/rdppage/main.*)|(.*/AsReport/*)|(.*/rdppub/show.*)|(.*/rdppage/custom.*)|(.*/rdppub/exportFile.*)");

		SpringApplication.run(AdminApplication.class, args);
	}
/*
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AdminApplication.class);
	}*/
}
