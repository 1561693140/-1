package com.image.upload.image_location.redis;

public class OrderKey extends BasePrefix{
    public OrderKey( String prefix) {
        super( prefix);
    }

    public static OrderKey getMiaoshaOrderByUidGid  = new OrderKey("moug");
}
