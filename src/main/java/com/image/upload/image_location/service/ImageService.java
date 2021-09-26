package com.image.upload.image_location.service;


import com.image.upload.image_location.Result.CodeMsg;
import com.image.upload.image_location.dao.UserDao;
import com.image.upload.image_location.domain.User;
import com.image.upload.image_location.exception.GlobalException;
import com.image.upload.image_location.redis.RedisService;
import com.image.upload.image_location.redis.UserKey;
import com.image.upload.image_location.util.UUIDUtil;
import com.image.upload.image_location.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Service
public class ImageService {

    public boolean uploadImage(){
        return false;
    }



}
