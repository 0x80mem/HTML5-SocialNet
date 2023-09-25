package com.jlusw.html.Interceptor;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jlusw.html.entity.User;
import com.jlusw.html.mapper.UserMapper;
import com.jlusw.html.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginHandler implements HandlerInterceptor {
    @Autowired
    UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        User user = (User)request.getSession().getAttribute("user");
        if (user == null ||userService.getOne(new LambdaQueryWrapper<User>().eq(User::getName, user.getName())) == null) {
            //没有登录
            response.sendRedirect(request.getContextPath());
            System.out.println("拦截");
            return false;
        } else {
            return true;
        }
    }
}

