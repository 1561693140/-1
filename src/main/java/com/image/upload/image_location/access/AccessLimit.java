package com.image.upload.image_location.access;


import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/*
* 自定义个一个注解，需要定义一个拦截器来实现它的逻辑
* */
@Retention(RUNTIME)
@Target(METHOD)
public @interface AccessLimit {
   int  seconds();
   int maxCount();
   boolean needLogin()default true;
}
