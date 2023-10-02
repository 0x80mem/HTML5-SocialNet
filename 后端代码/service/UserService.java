package com.jlusw.html.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlusw.html.entity.Post;
import com.jlusw.html.entity.User;

import java.util.List;

public interface UserService extends IService<User> {
    /**用户注销*/
    boolean cancel(long userId);


}
