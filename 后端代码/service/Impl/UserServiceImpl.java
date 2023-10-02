package com.jlusw.html.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlusw.html.entity.Follow;
import com.jlusw.html.entity.Post;
import com.jlusw.html.entity.User;
import com.jlusw.html.mapper.FollowMapper;
import com.jlusw.html.mapper.PostMapper;
import com.jlusw.html.mapper.UserMapper;
import com.jlusw.html.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;


@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    FollowMapper followMapper;
    @Autowired
    PostMapper postMapper;


    @Override
    public boolean cancel(long userId) {
        User user=getById(userId);
        if(user==null)
            return false;
        userMapper.deleteById(userId);
        return userMapper.cancel(userId, user.getName(), LocalDateTime.now());
    }




}
