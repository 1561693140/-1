package com.image.upload.image_location.controller;


import com.image.upload.image_location.Result.CodeMsg;
import com.image.upload.image_location.Result.Result;
import com.image.upload.image_location.service.UserService;
import com.image.upload.image_location.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;



@Controller
@RequestMapping("/login")
public class LoginController {


    @Autowired
    private UserService userService;
    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping("/to_login")
    public String toLogin() {
        log.info("to_login");
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<Boolean> doLogin(HttpServletResponse response, LoginVo loginVo) {

//        log.info(loginVo.getPassword().toString());
//        System.out.println("进度的");
        log.info("{}", loginVo);
        userService.login(response, loginVo);
        return Result.success(true);


    }
}
