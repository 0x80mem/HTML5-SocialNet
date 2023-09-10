package com.jlusw.html.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jlusw.html.common.R;
import com.jlusw.html.entity.User;
import com.jlusw.html.mapper.UserMapper;
import com.jlusw.html.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public R<User> login(HttpServletRequest request, @RequestBody User user) {


        String password = user.getPassword();
        //password = DigestUtils.md5DigestAsHex(password.getBytes());

        //根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getName, user.getName());
        User tuser = userService.getOne(queryWrapper);

        //如果没有查询到则返回登录失败结果
        if (tuser == null) {
            return R.error("登录失败");
        }

        //密码比对，如果不一致则返回登录失败结果
        if (!tuser.getPassword().equals(password)) {
            return R.error("登录失败");
        }

        //登录成功，将员工id存入Session并返回登录成功结果
        request.getSession().setAttribute("user", tuser.getId());
        System.out.println(user);
        return R.success(null);
    }


    /**
     * 注册
     * @param request
     * @param user
     * @return
     */

    @PostMapping("/register")
    public R<String> register(HttpServletRequest request, @RequestBody User user) {

        //查询用户是否已存在
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getName, user.getName());
        User tuser = userService.getOne(queryWrapper);
        if (tuser != null) {
            return R.error("用户已存在");
        }

        //增加用户
        userService.save(user);
        return R.success("注册成功");
    }

    /**
     * 退出
     * @param request
     * @return
     */

    @GetMapping("/logout")
    public R<String> logout(HttpServletRequest request) {

        request.getSession().removeAttribute("user");
        return R.success("退出成功");
    }

    /**
     * 检查登陆状态
     * @param request
     * @return
     */
    User checkState(HttpServletRequest request){
        //获取session用户id
        if (request.getSession().getAttribute("user") == null)
            return null;
        int id = (int) request.getSession().getAttribute("user");

        //查询用户是否存在
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId, id);
        User user = userService.getOne(queryWrapper);
        return user;
    }


    /**
     * 注销账号
     * @param request
     * @return
     */

    @DeleteMapping("/cancell")
    public R<String> cancell(HttpServletRequest request) {

        //检查登陆状态
        User user=checkState(request);
        if (user == null)
            return R.error("登陆状态异常");

        //删除用户
        userService.removeById(user.getId());
        return R.success("注销成功");
    }

    /**
     * 修改用户名
     * @param request
     * @param name
     * @return
     */
    @PatchMapping("/setName")
    public R<String> setName(HttpServletRequest request, String name) {

        //检查登陆状态
        User user=checkState(request);
        if (user == null)
            return R.error("登陆状态异常");

        //检查新旧名称是否一致
        if (name.equals(user.getName()))
            return R.error("新旧名称相同");

        //更新数据
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", user.getId()).set("name", name);
        userService.update(updateWrapper);
        return R.success("修改成功");
    }

    /**
     * 修改密码
     * @param request
     * @param password
     * @return
     */
    @PatchMapping("/setPassword")
    public R<String>setPassword(HttpServletRequest request, String password){

        //检查登陆状态
        User user=checkState(request);
        if (user == null)
            return R.error("登陆状态异常");

        //检查新旧密码是否一致
        if (password.equals(user.getPassword()))
            return R.error("新旧密码相同");

        //更新数据
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", user.getId()).set("password", password);
        userService.update(updateWrapper);
        return R.success("修改成功");
    }

    /**
     * queryByID
     * @param id
     * @return
     */
    @GetMapping("/queryByID")
    public R<User> queryByID(int id){
        return R.success(userService.getById(id));
    }
    /**
     * 名称精准查询
     * @param name
     * @return
     */
    @GetMapping("/accurateQueryByName")
    public R<User> accurateQueryByName(String name){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getName, name);
        User user = userService.getOne(queryWrapper);
        return R.success(user);
    }
    /**
     * 名称模糊查询
     * @param name
     * @return
     */
    @GetMapping("/fuzzyQueryByName")
    public R<List<User>> fuzzyQueryByName(String name){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(User::getName, name);
        List<User> users = userService.list(queryWrapper);
        return R.success(users);
    }
}
