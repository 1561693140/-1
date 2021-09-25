package com.image.upload.image_location.redis;

public class GoodsKey extends BasePrefix{
    public GoodsKey(int expireSeconds,String prefix) {
        super(expireSeconds,prefix);
    }
    public static GoodsKey getGoodsList= new GoodsKey(60,"gl");
    public static GoodsKey getGoodsDetail= new GoodsKey(1,"gd");
    public static GoodsKey getMiaoshaGoodsStock= new GoodsKey(0,"goodsStock");

}
