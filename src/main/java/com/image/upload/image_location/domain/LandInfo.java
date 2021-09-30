package com.image.upload.image_location.domain;

import java.util.Date;

public class LandInfo {
    private int landinfoid;
    private String descinfo;
    private String position;
    private int userid;
    private Date uploaddt;
    private int changeid;
    private Date changedt;
    private String isvalid;
    private String ischeck;

    public int getLandinfoid() {
        return landinfoid;
    }

    public void setLandinfoid(int landinfoid) {
        this.landinfoid = landinfoid;
    }

    public String getDescinfo() {
        return descinfo;
    }

    public void setDescinfo(String descinfo) {
        this.descinfo = descinfo;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public Date getUploaddt() {
        return uploaddt;
    }

    public void setUploaddt(Date uploaddt) {
        this.uploaddt = uploaddt;
    }

    public int getChangeid() {
        return changeid;
    }

    public void setChangeid(int changeid) {
        this.changeid = changeid;
    }

    public Date getChangedt() {
        return changedt;
    }

    public void setChangedt(Date changedt) {
        this.changedt = changedt;
    }

    public String getIsvalid() {
        return isvalid;
    }

    public void setIsvalid(String isvalid) {
        this.isvalid = isvalid;
    }

    public String getIscheck() {
        return ischeck;
    }

    public void setIscheck(String ischeck) {
        this.ischeck = ischeck;
    }


}
