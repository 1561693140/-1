package com.image.upload.image_location.task;
import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.image.upload.image_location.domain.ImgList;
import com.image.upload.image_location.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling

public class ClearTempPics {
    @Value("${user.originImage.path}")
    private String origin_file_path;
    private String origin_file_path1 = "D:\\javawork\\image_location\\image\\origin";

    @Value("${user.thumbImage.path}")
    private String thumbImage_file_path;
    private String thumbImage_file_path1 = "D:\\javawork\\image_location\\image\\thumb";

    @Value("${user.web.url}")
    private String base_url;

    @Autowired
    private ImageService imageService;

    /*
     * 定时任务；定期删除临时图片
     *
     * */

    @Scheduled(fixedRate = 1000*60*60*24)
    private void clearPics() {
        System.out.println("运行一次");
        File origin_image_file  = new File(origin_file_path1);
        File thumb_image_file  = new File(thumbImage_file_path1);

        File [] origin_images = origin_image_file.listFiles();
        File [] thumb_images = thumb_image_file.listFiles();
        if (origin_images== null || thumb_images== null) {
            return;
        }

        List<ImgList> imgLists = imageService.listAllImage();
        Set<String> originImageSet = new HashSet<>();
        Set<String> thumbImageSet = new HashSet<>();
        for(ImgList img:imgLists){
            originImageSet.add(img.getImgpath());
            thumbImageSet.add(img.getThumbimgpath());
        }
        List<File> needToRemove = new LinkedList<>();
        for(File origin_image:origin_images){
            String file_name = base_url + "image/"+ origin_image.getName();
            if (!originImageSet.contains(file_name)){
                needToRemove.add(origin_image);
            }
        }
        for(File thumb_image:thumb_images){
            String file_name = base_url + "thumbImage/image/"+thumb_image.getName();
            if (!thumbImageSet.contains(file_name)){
                needToRemove.add(thumb_image);
            }
        }
        for (File file: needToRemove) {
            file.delete();
        }


    }
}
