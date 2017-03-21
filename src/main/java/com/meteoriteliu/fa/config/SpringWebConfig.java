package com.meteoriteliu.fa.config;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.persistence.Column;
import java.util.List;

@EnableWebMvc //<mvc:annotation-driven />
@Configuration
@ComponentScan(basePackages="com.meteoriteliu.fa.controller")
@PropertySource(value="classpath:config.properties")
@EnableSpringDataWebSupport
public class SpringWebConfig extends WebMvcConfigurerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(SpringWebConfig.class);
	
	@Value("${thymeleaf.cacheable}")
	String cacheable;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**")
                        .addResourceLocations("/resources/");
		registry.addResourceHandler("/images/**").addResourceLocations("/resources/images/");
	}
	
	@Bean
	public ServletContextTemplateResolver templateResolver() {
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
		templateResolver.setCacheable("true".equals(cacheable));
		templateResolver.setPrefix("/WEB-INF/views/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode("HTML5");
		templateResolver.setCharacterEncoding("UTF-8");
		templateResolver.setOrder(1);
		return templateResolver;
	}

	@Bean
	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.setTemplateResolver(templateResolver());
		engine.addDialect(new SpringSecurityDialect());
		return engine;
	}
	
	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver r = new CommonsMultipartResolver();
		return r;
	}
	
	@Bean
	public ViewResolver tfResolver() {
		ThymeleafViewResolver tfResolver = new ThymeleafViewResolver();
		tfResolver.setTemplateEngine(templateEngine());
		tfResolver.setCharacterEncoding("UTF-8");
		tfResolver.setOrder(2);
		
	    return tfResolver;
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		GsonHttpMessageConverter msgConverter = new GsonHttpMessageConverter();
		Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
			@Override
			public boolean shouldSkipField(FieldAttributes fieldAttributes) {
				return fieldAttributes.getAnnotation(GsonExclude.class) != null;
			}

			@Override
			public boolean shouldSkipClass(Class<?> aClass) {
				return false;
			}
		}).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		msgConverter.setGson(gson);
		converters.add(msgConverter);
		super.configureMessageConverters(converters);
	}
}
