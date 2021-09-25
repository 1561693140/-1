package com.image.upload.image_location.redis;

public class MiaoshaKey extends BasePrefix{
    public MiaoshaKey(int expireSeconds,String prefix) {
        super(expireSeconds,prefix);
    }
    public static MiaoshaKey isGoodsOver= new MiaoshaKey(0,"go");
    public static MiaoshaKey getMiaoshaPath= new MiaoshaKey(60,"mp");
    public static MiaoshaKey verify_code= new MiaoshaKey(300,"vc");
}
