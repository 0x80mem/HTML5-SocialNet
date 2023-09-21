package com.jlusw.html.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlusw.html.entity.User;
import com.jlusw.html.mapper.UserMapper;
import com.jlusw.html.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public boolean cancel(long userId) {
        User user=getById(userId);
        if(user==null)
            return false;
        userMapper.deleteById(userId);
        return userMapper.cancel(userId, user.getName(), LocalDateTime.now());
    }
}
