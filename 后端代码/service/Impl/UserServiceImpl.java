package com.html.nds.service.impl;

import com.html.nds.entity.User;
import com.html.nds.mapper.UserMapper;
import com.html.nds.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
