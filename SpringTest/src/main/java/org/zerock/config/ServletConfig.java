package org.zerock.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@EnableWebMvc
@ComponentScan(basePackages = { "org.zerock.controller", "org.zerock.exception"})
public class ServletConfig implements WebMvcConfigurer{
	
	//기존 servlet-context.xml 내용 작성하기
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		InternalResourceViewResolver bean = new InternalResourceViewResolver();
			bean.setViewClass(JstlView.class);
			bean.setPrefix("/WEB-INF/views/");
			bean.setSuffix(".jsp");
			registry.viewResolver(bean);
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}
	
	@Bean
	public MultipartResolver multipartResolver() {
		StandardServletMultipartResolver resolver = new StandardServletMultipartResolver();
		return resolver;
	}
	
	/*
	 * @Bean(name="multipartResolver") public CommonsMultipartResolver getResolver()
	 * throws IOException { CommonsMultipartResolver resolver = new
	 * CommonsMultipartResolver();
	 * 
	 * //2MB 지정 resolver.setMaxUploadSizePerFile(1024 * 1024 * 2);
	 * 
	 * //temp upload resolver.setUploadTempDir(new
	 * FileSystemResource("C:\\workspaces\\upload\\temp"));
	 * resolver.setDefaultEncoding("UTF-8");
	 * 
	 * return resolver; }
	 */
}