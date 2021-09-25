package com.image.upload.image_location.redis;

public class UserKey extends BasePrefix{
    // 过期时间
    public static final int TOKEN_EXPIRE = 3600*24*2;
    public UserKey(int expireSeconds,String prefix) {
        super(expireSeconds,prefix);
    }
    public static UserKey token = new UserKey(TOKEN_EXPIRE,"tk");
    public static UserKey getByUserName = new UserKey(0,"username");

}
