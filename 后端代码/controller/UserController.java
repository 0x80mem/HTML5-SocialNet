package com.html.nds.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.html.nds.common.GlobalException;
import com.html.nds.common.R;
import com.html.nds.common.DTOUtil;
import com.html.nds.entity.*;
import com.html.nds.mapper.RelationMapper;
import com.html.nds.service.IContentService;
import com.html.nds.service.INodeService;
import com.html.nds.service.IRelationService;
import com.html.nds.service.IUserService;
import com.html.nds.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/user")
@Transactional
@CrossOrigin
@Configuration
public class UserController {
    @Autowired
    INodeService nodeService;
    @Autowired
    IUserService userService;
    @Autowired
    IRelationService relationService;
    @Autowired
    RelationMapper relationMapper;
    @Autowired
    IContentService contentService;
    @Autowired
    Avatar avatar;
    /**
     * 1. 登陆
     *
     * @param request request
     * @param user    用户信息
     * @return 登录结果
     */
    @PostMapping("/login")
    public R<NodeDTO> login(HttpServletRequest request, @RequestBody User user) {


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
        Integer uid = tuser.getId();
        //登录成功，将用户id存入Session并返回登录成功结果
        request.getSession().setAttribute("user", uid);

        //生成UserDTO
        UserDTO userDTO = DTOUtil.toUserDTO(tuser);
        //生成NodeDTO
        NodeDTO nodeDTO = DTOUtil.nodeToDTO(nodeService.getById(uid));
        nodeDTO.setChiNodes(relationService.getChiNodes(uid));
        R<NodeDTO> r = R.success(nodeDTO);
        r.add("userInfo", userDTO);
        return r;
    }


    /**
     * 2. 注册
     *
     * @param user 用户信息
     * @return 注册结果
     */

    @PostMapping("/register")
    public R<String> register(@RequestBody User user) {

        //查询用户是否已存在
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getName, user.getName());
        User tuser = userService.getOne(queryWrapper);
        if (tuser != null) {
            return R.error("用户已存在");
        }
        //增加用户
        Integer uid = nodeService.generateId();
        if (!nodeService.save(new Node(uid, uid, "user")))
            return R.error();

        user.setId(uid);
        if (userService.save(user)) {
            //初始化
            Integer t = nodeService.generateId();
            nodeService.save(new Node(t, uid, "collect"));
            relationService.save(new Relation(uid, t));

            t = nodeService.generateId();
            nodeService.save(new Node(t, uid, "subscribe"));
            relationService.save(new Relation(uid, t));

            t = nodeService.generateId();
            nodeService.save(new Node(t, uid, "fans"));
            relationService.save(new Relation(uid, t));

            t = nodeService.generateId();
            nodeService.save(new Node(t, uid, "share"));
            relationService.save(new Relation(uid, t));

            t = nodeService.generateId();
            nodeService.save(new Node(t, uid, "like"));
            relationService.save(new Relation(uid, t));

            Content content = new Content(uid, user.getName(), "");
            contentService.save(content);
            return R.success();
        }

        return R.error();
    }

    /**
     * 3.退出登录
     *
     * @param request request
     * @return 结果
     */

    @GetMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        if (request.getSession().getAttribute("user") == null)
            return R.error("未登录");
        request.getSession().removeAttribute("user");
        return R.success();
    }

    /**
     * 4. 检查登陆状态
     *
     * @param request request
     * @return 用户信息
     */
    private User checkState(HttpServletRequest request) {
        //获取session用户id
        if (request.getSession().getAttribute("user") == null)
            return null;
        int id = (int) request.getSession().getAttribute("user");

        //查询用户是否存在
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId, id);
        return userService.getOne(queryWrapper);
    }


    /**
     * 5. 注销账号 注销即将用户移入cancelled_user表
     *
     * @param request request
     * @return 注销结果
     */

    @DeleteMapping("/cancel")
    public R<String> cancel(HttpServletRequest request) {
        //检查登陆状态
        User user = checkState(request);
        if (user == null)
            return R.error("登陆状态异常");

        //删除用户
        if (userService.update(new UpdateWrapper<User>().eq("id", user.getId()).set("state", 1)))
            return R.success("注销成功");
        return R.success("注销失败");
    }

    /**
     * 6. 修改用户名
     *
     * @param request request
     * @param name    新用户名
     * @return 修改结果
     */
    @PatchMapping("/setName")
    public R<String> setName(HttpServletRequest request, String name) {

        //检查登陆状态
        User user = checkState(request);
        if (user == null)
            return R.error("登陆状态异常");

        //检查新旧名称是否一致
        if (name.equals(user.getName()))
            return R.error("新旧名称相同");

        //更新数据
        try {
            UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", user.getId()).set("name", name);
            userService.update(updateWrapper);
        } catch (Exception e) {
            throw new GlobalException(0, e.getMessage());
        }

        return R.success("修改成功");
    }

    /**
     * 7. 修改密码
     *
     * @param request  request
     * @param password 新密码
     * @return 修改结果
     */
    @PatchMapping("/setPassword")
    public R<String> setPassword(HttpServletRequest request, String password) {

        //检查登陆状态
        User user = checkState(request);
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
     * 8.1 依据id查询用户
     *
     * @param id 用户id
     * @return 用户信息
     */
    @GetMapping("/queryByID")
    public R<UserDTO> queryByID(Integer id) {
        User user = userService.getById(id);
        if (user == null)
            return R.error("用户不存在");
        user.setPassword("");
        return R.success(DTOUtil.toUserDTO(user));
    }

    /**
     * 8.2 名称精准查询用户
     *
     * @param name 用户名
     * @return 用户信息
     */
    @GetMapping("/accurateQueryByName")
    public R<UserDTO> accurateQueryByName(String name) {

        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getName, name));
        if (user == null)
            return R.error("不存在该用户");
        return R.success(DTOUtil.toUserDTO(user));
    }

    /**
     * 8.3 名称模糊查询用户
     *
     * @param name 用户名
     * @return 用户信息
     */
    @GetMapping("/fuzzyQueryByName")
    public R<List<UserDTO>> fuzzyQueryByName(String name) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(User::getName, name);
        List<User> users = userService.list(queryWrapper);
        return R.success(DTOUtil.toUserDTOs(users));
    }

    @GetMapping("/getLoginState")
    public R<UserDTO> getLoginState(HttpServletRequest request) {
        User user = checkState(request);
        if (user == null)
            return R.error();
        return R.success(new UserDTO(user.getId(), user.getName()));
    }
    @GetMapping("/getUserInfo")
    public R<UserInfoV> getUserInfo(Integer id) {
        User user = userService.getById(id);
        if (user == null)
            return R.error();
        Content content = contentService.getById(user.getId());
        return R.success(new UserInfoV(user.getId(),content.getContent(),content.getTitle(),user.getAvatar()));
    }
    @PostMapping("/changeAvatar")
    public R<String> changeAvatar(HttpServletRequest request, MultipartFile image) {
        User user = checkState(request);
        if (user == null)
            return R.error();
       String url = user.getAvatar();
       String newUrl=avatar.savaAvatar(image,user.getId());
       if(Objects.equals(url, newUrl))
           return R.error();
       return R.success(newUrl);
    }
    @PostMapping("/changeInfo")
    public R<String> changeInfo(HttpServletRequest request,@RequestBody UserInfoV userInfoV) {
        User user = checkState(request);
        if (user == null)
            return R.error();
       user.setName(userInfoV.getUsername());
       userService.update(user,new LambdaQueryWrapper<User>().eq(User::getId,user.getId()));
       Content content = contentService.getById(user.getId());
       content.setTitle(userInfoV.getUsername());
       content.setContent(userInfoV.getBio());
       contentService.update(content,new LambdaQueryWrapper<Content>().eq(Content::getId,user.getId()));
       return R.success();
    }

}