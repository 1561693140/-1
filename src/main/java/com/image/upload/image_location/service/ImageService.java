package com.image.upload.image_location.service;


import com.image.upload.image_location.Result.CodeMsg;
import com.image.upload.image_location.dao.ImgListDao;
import com.image.upload.image_location.dao.LandInfoDao;
import com.image.upload.image_location.dao.UserDao;
import com.image.upload.image_location.domain.ImgList;
import com.image.upload.image_location.domain.LandInfo;
import com.image.upload.image_location.domain.User;
import com.image.upload.image_location.exception.GlobalException;
import com.image.upload.image_location.redis.RedisService;
import com.image.upload.image_location.redis.UserKey;
import com.image.upload.image_location.util.ImageUtils;
import com.image.upload.image_location.util.UUIDUtil;
import com.image.upload.image_location.vo.ImageInfoVO;
import com.image.upload.image_location.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Objects;

@Service
public class ImageService {


    @Value("${user.originImage.path}")
    private String origin_image_path;

    @Value("${user.web.url}")
    private String base_url;

    @Value("${user.thumbImage.path}")
    private String thumb_image_path;


    @Autowired
    private LandInfoDao landInfoDao;
    @Autowired
    private ImgListDao imgListDao;

    @Transactional
    public void uploadImage(ImageInfoVO imageInfoVO, User user) {
        try {
            for (String url: imageInfoVO.getImageUrls()){

                String origin_name = url.substring(base_url.length());
                String img_path = origin_image_path+origin_name;
                String thumb_path = thumb_image_path+origin_name;
                if(!ImageUtils.zoomOutImage(img_path,thumb_path,10)){
                    throw new Exception();
                }

                LandInfo landInfo = new LandInfo();
                landInfo.setDescinfo(imageInfoVO.getDescription());
                landInfo.setPosition(imageInfoVO.getLatitude()+" "+imageInfoVO.getLongitude());
                landInfo.setUserid(user.getId());
                landInfo.setUploaddt(new Date());
                landInfo.setIscheck("0");
                landInfo.setIsvalid("1");
                int id = landInfoDao.saveImage(landInfo);
                ImgList imgList = new ImgList();
                imgList.setLandinfoid(id);
                imgList.setImgpath(url);
                imgList.setThumbimgpath(base_url+"thumbImage/"+origin_name);
                imgListDao.insertImage(imgList);
            }
        } catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }

    }



}
