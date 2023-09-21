package com.jlusw.html.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlusw.html.entity.User;

public interface UserService extends IService<User> {
    /**
     * 用户注销
     * @param userId 注销用户id
     * @return 注销结果
     */
    boolean cancel(long userId);
}
