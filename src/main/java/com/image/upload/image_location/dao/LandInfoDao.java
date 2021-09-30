package com.image.upload.image_location.dao;

import com.image.upload.image_location.domain.LandInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectKey;

@Mapper
public interface LandInfoDao {
    @Insert("insert into landinfo(descinfo,position,userid,uploaddt,isvalid,ischeck) values ("+
    "#{descinfo},#{position},#{userid},#{uploaddt},#{isvalid},#{ischeck})")
    @SelectKey( resultType=int.class, before=false, statement="select last_insert_id()", keyProperty = "landinfoid")
    int saveImage(LandInfo landInfo);
}
