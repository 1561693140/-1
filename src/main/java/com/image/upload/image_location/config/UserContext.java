package com.image.upload.image_location.config;



/*
*
*  这里使用ThreadLocal来保存用户信息，为什么呢？
*  每个user信息都保存在对应的Thread中
*
* */

import com.image.upload.image_location.domain.User;

public class UserContext {
    private static ThreadLocal<User> userHolder = new ThreadLocal<>();
    public static void  setUser(User user){
        userHolder.set(user);
    }
    public static User  getUser(){
        return userHolder.get();
    }
}
