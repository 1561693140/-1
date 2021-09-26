package com.image.upload.image_location.controller;


import com.image.upload.image_location.Result.CodeMsg;
import com.image.upload.image_location.Result.Result;
import com.image.upload.image_location.access.AccessLimit;
import com.image.upload.image_location.domain.User;
import com.image.upload.image_location.exception.GlobalException;
import com.image.upload.image_location.service.UserService;
import com.image.upload.image_location.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;


@Controller
@RequestMapping("/image")
public class ImageController {

    @Value("${user.file.path}")
    private String file_path;

    @Value("${user.web.url}")
    private String base_url;

    private static Logger log = LoggerFactory.getLogger(ImageController.class);

    @RequestMapping("/upload")
    @ResponseBody
//    @AccessLimit(maxCount = 30, seconds = 10)
    public Result<String> upload(@RequestParam(value = "file") MultipartFile file, User user) {
        if(file.isEmpty()) {
            return Result.error(CodeMsg.FILE_EMPTY);
        }
        File image = new File(file_path + file.getOriginalFilename());

        try {
            file.transferTo(image);
            log.info("图片上传成功,user:{}",user);
        } catch (IOException e) {
            log.error(e.toString(), e);
            return Result.error(CodeMsg.FILE_ERROR);
        }
        return Result.success(base_url+file.getOriginalFilename());
    }

}
