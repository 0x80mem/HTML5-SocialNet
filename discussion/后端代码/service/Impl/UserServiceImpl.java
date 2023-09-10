package com.jlusw.html.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlusw.html.entity.User;
import com.jlusw.html.mapper.UserMapper;
import com.jlusw.html.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
