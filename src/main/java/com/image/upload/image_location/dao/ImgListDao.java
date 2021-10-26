package com.image.upload.image_location.dao;

import com.image.upload.image_location.domain.ImgList;
import com.image.upload.image_location.vo.ImageInfoVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ImgListDao {
    @Insert("insert into imglist(landinfoid,imgpath,thumbimgpath) values(#{landinfoid},#{imgpath},#{thumbimgpath})")
    int insertImage(ImgList imgList);
    @Select("select * from imglist")
    List<ImgList> listALL();

}
