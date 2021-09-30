package com.image.upload.image_location.controller;


import com.image.upload.image_location.Result.CodeMsg;
import com.image.upload.image_location.Result.Result;
import com.image.upload.image_location.access.AccessLimit;
import com.image.upload.image_location.domain.User;
import com.image.upload.image_location.service.ImageService;

import com.image.upload.image_location.vo.ImageInfoVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;


@Controller
@RequestMapping("/image")
public class ImageController {

    @Value("${user.originImage.path}")
    private String file_path;

    @Value("${user.web.url}")
    private String base_url;

    private static Logger log = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    private ImageService imageService;

    @RequestMapping("/upload")
    @ResponseBody
    @AccessLimit(maxCount = 100, seconds = 10)
    public Result<String> upload(@RequestParam(value = "file") MultipartFile file, User user) {
        if(user == null) {
            return Result.error(CodeMsg.NOT_LOG_IN);
        }
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
        return Result.success(base_url+"image/"+file.getOriginalFilename());
    }

    @RequestMapping("/uploadImageInfo")
    @ResponseBody
    @AccessLimit(seconds = 5,maxCount = 50)
    public Result<String> uploadImageInfo(ImageInfoVO imageInfoVO, User user) {
        if(user == null){
            return Result.error(CodeMsg.NOT_LOG_IN);
        }
        imageService.uploadImage(imageInfoVO,user);
        return Result.success("success");

    }




}
