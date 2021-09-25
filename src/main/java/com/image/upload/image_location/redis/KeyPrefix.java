package com.image.upload.image_location.redis;

public interface KeyPrefix {
    public  int expireSeconds();
    public String getPrefix();
}
