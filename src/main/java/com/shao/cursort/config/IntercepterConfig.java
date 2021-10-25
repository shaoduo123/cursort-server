package com.shao.cursort.config;

import com.shao.cursort.interceptor.AuthorizationIntercepter;
import com.shao.cursort.interceptor.CurrentUserMethodArgumentResolver;
import com.shao.cursort.interceptor.CurrentoFolderMethodArgumentResolver;
import com.shao.cursort.interceptor.FolderIntercepter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * 拦截器配置
 */
@Configuration
public class IntercepterConfig implements WebMvcConfigurer {

//    @Value("${file.userfiles-path}")
//    private String filePath;

    /**
     * 登录校验拦截器
     *
     * @return
     */
    @Bean
    public AuthorizationIntercepter setAuthorizationIntercepter(){
        System.out.println("注入了handler");
        return new  AuthorizationIntercepter();
    }

    /**
     * CurrentUser 注解参数解析器
     *
     * @return
     */
    @Bean
    public CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver() {
        return new CurrentUserMethodArgumentResolver();
    }


    /**
     * 当前文件拦截器
     *
     * @return
     */
    @Bean
    public FolderIntercepter setFolderIntercepter(){
        System.out.println("folder");
        return new  FolderIntercepter();
    }

    /**
     * CurrentFolder注解参数解析器
     *
     * @return
     */
    @Bean
    public CurrentoFolderMethodArgumentResolver CurrentoFolderMethodArgumentResolver() {
        return new CurrentoFolderMethodArgumentResolver();
    }

    /**
     * 参数解析器
     *
     * @param argumentResolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(currentUserMethodArgumentResolver()) ;
        argumentResolvers.add(CurrentoFolderMethodArgumentResolver()) ;

        WebMvcConfigurer.super.addArgumentResolvers(argumentResolvers);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/")
                .addResourceLocations("classpath:/static/page/")
                .addResourceLocations("classpath:/static/templates/") ;
               // .addResourceLocations("file:" + filePath);
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(setAuthorizationIntercepter())
                .addPathPatterns("/**");
        registry.addInterceptor(setFolderIntercepter())
                .addPathPatterns("/file/**");
        WebMvcConfigurer.super.addInterceptors(registry);
       // System.out.println("wodoale");

    }
}