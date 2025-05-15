package com.itheima.config;

import com.itheima.interceptor.DemoInterceptor;
import com.itheima.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
//
//    //自定义的拦截器对象
//    @Autowired //自动注入
////    private DemoInterceptor demoInterceptor;
//    private TokenInterceptor tokenInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        //注册自定义拦截器对象
//        registry.addInterceptor(tokenInterceptor)
//                .addPathPatterns("/**")//设置拦截器拦截的请求路径（ /** 表示拦截所有请求）
//                .excludePathPatterns("/login");//设置不拦截的请求路径（ /login 表示不拦截登录请求）
//    }
}