package com.image.upload.image_location.access;

import com.alibaba.fastjson.JSON;
import com.image.upload.image_location.Result.CodeMsg;
import com.image.upload.image_location.Result.Result;
import com.image.upload.image_location.config.UserContext;
import com.image.upload.image_location.domain.User;
import com.image.upload.image_location.redis.AccessKey;
import com.image.upload.image_location.redis.RedisService;
import com.image.upload.image_location.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;


/*
*
* 这个拦截器其实实现了两个东西，第一用来实现我们定义的接口限流注解@AccessLimit的功能。
*
* 第二个其实还实现了将用户信息存入redis中的功能。
* */
@Service
public class AccessInterceptor extends HandlerInterceptorAdapter {


    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @Override
     public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod){

            //我们在拦截器中判断用户登录，需要用户登录才能访问的接口会标上@AccessLimit，
            //这时候就会调用这个方法来判断用户有没有登录。然后这里的信息会存入ThreadLocal
            //中，参数解析器会就可以拿到用户的数据。
            User user = getUser(request,response);
            //将user信息保存在ThreadLocal中
            UserContext.setUser(user);

            HandlerMethod hm = (HandlerMethod) handler;
            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
            if(accessLimit == null){

                return true;
            }

            int seconds = accessLimit.seconds();
            int maxCount = accessLimit.maxCount();
            boolean needLogin = accessLimit.needLogin();

            String key = request.getRequestURI();
            if(needLogin){
                if(user == null){
                    render(response,CodeMsg.SESSION_ERROR);
                    return false;
                }
                key+="_"+user.getId();

                AccessKey ak = AccessKey.withExpire(seconds);
                Integer count = redisService.get(ak,key,Integer.class);

                if(count==null){
                    redisService.set(ak,key,1);
                }else if(count<maxCount){
                    redisService.incr(ak,key);
                }else{

                    render (response, CodeMsg.ACCESS_LIMIT_REACH);
                    return false;
                }
            }else{
                //do nothing
            }

        }
        return true;
    }

    private void render(HttpServletResponse response,CodeMsg cm) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        String result = JSON.toJSONString(Result.error(cm));
        out.write(result.getBytes(StandardCharsets.UTF_8));
        out.flush();
        out.close();
    }

    private User getUser(HttpServletRequest request, HttpServletResponse response){
        String paramToken = request.getParameter(UserService.COOKIE_NAME_TOKEN);
        String cookieToken = getCookieValue(request,UserService.COOKIE_NAME_TOKEN);

        if(StringUtils.isEmpty(paramToken) && StringUtils.isEmpty(cookieToken)){
            return null;
        }

        String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
        return userService.getByToken(response,token);
    }

    private String getCookieValue(HttpServletRequest request, String cookieNameToken) {
        Cookie[] cookies = request.getCookies();
        if(cookies == null|| cookies.length<=0){
            return null;
        }
        for(Cookie cookie : cookies){
            if(cookie.getName().equals(cookieNameToken)){
                return cookie.getValue();
            }
        }
        return null;

    }
}
