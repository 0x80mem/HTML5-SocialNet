package com.html.nds.entity;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.html.nds.service.IUserService;
import com.html.nds.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Component
public class Avatar {
    private final IUserService userService;

    @Autowired
    public Avatar(IUserService userService) {
        this.userService = userService;
    }

    public String savaAvatar(MultipartFile file, Integer userId) {
        if(userService==null)
            System.out.println("dddd");
        System.out.println("userId"+userId);
        System.out.println("userService"+userService);
        User user = userService.getById(userId);
        System.out.println("user"+user);
        String imageUrl = user.getAvatar();
        try {

            // 保存文件到服务器，这里假设保存在项目的 "uploads" 目录下
            String fileName = file.getOriginalFilename().replaceFirst(".*\\.", user.getId() + ".");
            String uploadDir = "/usr/app/dist/avatar/";
//            String uploadDir = "D:/1_testImg/";

            String filePath = uploadDir + fileName;

            File uploadDirFile = new File(uploadDir);
            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdirs();
            }
            file.transferTo(new File(filePath));
            String serverBaseUrl = "http://47.93.10.201/avatar/";
            imageUrl = serverBaseUrl + fileName;
            user.setAvatar(imageUrl);
            userService.update(user,new UpdateWrapper<User>().eq("id",user.getId()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageUrl;
    }
}
