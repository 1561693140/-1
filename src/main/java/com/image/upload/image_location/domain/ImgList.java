package com.image.upload.image_location.domain;

public class ImgList {
    private int imglistid;
    private int landinfoid;
    private String imgpath;
    private String thumbimgpath;

    public int getImglistid() {
        return imglistid;
    }

    public void setImglistid(int imglistid) {
        this.imglistid = imglistid;
    }

    public int getLandinfoid() {
        return landinfoid;
    }

    public void setLandinfoid(int landinfoid) {
        this.landinfoid = landinfoid;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public String getThumbimgpath() {
        return thumbimgpath;
    }

    public void setThumbimgpath(String thumbimgpath) {
        this.thumbimgpath = thumbimgpath;
    }




}
