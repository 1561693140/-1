package com.image.upload.image_location.service;


import com.image.upload.image_location.Result.CodeMsg;
import com.image.upload.image_location.domain.User;
import com.image.upload.image_location.exception.GlobalException;
import com.image.upload.image_location.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.commons.lang3.StringUtils;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class UserService {

    public static final String COOKIE_NAME_TOKEN = "token";
    private static Logger log = LoggerFactory.getLogger(UserService.class);

    
  

    public boolean login(HttpServletResponse response, LoginVo loginVo) {
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

        if (!(username.equals("admin") && password.equals("123456"))) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        return true;
    }

}
