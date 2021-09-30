package com.image.upload.image_location.config;


import com.image.upload.image_location.access.AccessInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${user.originImage.path}")
    private String origin_image_path;

    @Value("${user.thumbImage.path}")
    private String thumb_image_path;


    @Autowired
    UserArgumentResolvers userArgumentResolvers;

    @Autowired
    AccessInterceptor accessInterceptor;

    @Override
    //将参数解析器注册到Spring容器中
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userArgumentResolvers);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(accessInterceptor);

    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){

        registry.addResourceHandler("/image/**").addResourceLocations("file:"+origin_image_path);
        registry.addResourceHandler("/thumbImage/**").addResourceLocations("file:/"+thumb_image_path);

    }


}
