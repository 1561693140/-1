package com.image.upload.image_location.dao;
import com.image.upload.image_location.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

@Mapper
@Service
public interface UserDao {

    @Select("select * from user where username = #{username}")
    User getByUserName(@Param("username") String userName);
}
