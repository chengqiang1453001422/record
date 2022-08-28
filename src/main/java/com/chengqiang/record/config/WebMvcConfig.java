package com.chengqiang.record.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.chengqiang.record.interceptor.AuthenticationInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	@Autowired
	private AuthenticationInterceptor authenticationInterceptor;
	
	@Value( value = "${record.path.uploadFolder}")
    private String uploadFolder;
	
//	@Override
//	public void addCorsMappings(CorsRegistry registry) {
//		registry.addMapping("/**").allowedOriginPatterns("*").allowCredentials(true).maxAge(3600).allowedMethods("GET", "POST","OPTIONS");
//	}

//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		//默认的资源映射需要填写，不然不能正常访问
//		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
//		//配置外部资源目录的映射，/files目录为前端访问的路径，后面配置静态资源的绝对路径
//		registry.addResourceHandler("/files/**").addResourceLocations("file:"+uploadFolder);
//		//调用基类的方法
//		//WebMvcConfigurer.super.addResourceHandlers(registry);
//	}
	
	/**
	 * app拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		List<String> list = new ArrayList<>();
		list.add("/file/**");
		list.add("/filetransfer/**");
		list.add("/user/**");
		list.add("/record/**");
		list.add("/deposit/**");
		list.add("/share/savesharefile");
		registry.addInterceptor(authenticationInterceptor)
			.addPathPatterns(list)
			.excludePathPatterns("/file",
					"/filetransfer/downloadfile",
					"/user/login",
					"/user/getImage",
					"/user/getBaseImage",
					"/user/register");
	}
}
