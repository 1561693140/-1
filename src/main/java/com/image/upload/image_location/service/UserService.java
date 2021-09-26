package com.image.upload.image_location.service;


import com.image.upload.image_location.Result.CodeMsg;

import com.image.upload.image_location.dao.UserDao;
import com.image.upload.image_location.domain.User;
import com.image.upload.image_location.exception.GlobalException;
import com.image.upload.image_location.redis.RedisService;
import com.image.upload.image_location.redis.UserKey;
import com.image.upload.image_location.util.UUIDUtil;
import com.image.upload.image_location.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Service
public class UserService {
    @Autowired
    RedisService redisService;

    @Autowired
    UserDao userDao;

    public static final String COOKIE_NAME_TOKEN = "token";
    private static Logger log = LoggerFactory.getLogger(UserService.class);

    public User getByUserName(String userName){

        // 取缓存
        User user = redisService.get(UserKey.getByUserName,""+userName, User.class);
        if(user!=null){
            return user;
        }
        // 如果缓存中没有就去取数据库取数据库
        user = userDao.getByUserName(userName);
        // 这里如果为null的话也往redis里写，证明这个id就直接没有，但是要考虑缓存一致性问题
        // 所以改为如果没有的话统一读一遍数据库
        if (!Objects.isNull(user)) {
            redisService.set(UserKey.getByUserName,""+userName, user);
        }

        return user;
    }
  

    public void login(HttpServletResponse response, LoginVo loginVo) {
        if(loginVo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String username = loginVo.getUsername();
        String password = loginVo.getPassword();
        if(StringUtils.isBlank(username)) {
            throw new GlobalException(CodeMsg.USERNAME_EMPTY);
        }

        if(StringUtils.isBlank(password)) {
            throw new GlobalException(CodeMsg.PASSWORD_EMPTY);
        }

        User user = getByUserName(username);
        if(Objects.isNull(user)) {
            throw new GlobalException(CodeMsg.USERNAME_NOT_EXIST);
        }

        if(!StringUtils.equals(password, user.getPassword())) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }

        // 生成唯一token并放入response中
        String token = UUIDUtil.uuid();
        log.info(username+" "+token);
        addCookie(response,user,token);
    }

    private void addCookie(HttpServletResponse response,User user,String token){

        redisService.set(UserKey.token,token,user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN,token);
        cookie.setMaxAge(UserKey.token.expireSeconds());
//        System.out.println(MiaoshaUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }


    public User getByToken(HttpServletResponse response, String token) {
        return null;
    }
}
