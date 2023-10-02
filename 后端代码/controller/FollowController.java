package com.jlusw.html.controller;

import com.jlusw.html.common.R;
import com.jlusw.html.entity.Follow;
import com.jlusw.html.entity.PostDTO;
import com.jlusw.html.entity.User;
import com.jlusw.html.service.IFollowService;
import com.jlusw.html.service.IGroupService;
import com.jlusw.html.service.PostService;
import com.jlusw.html.service.UserService;
import com.jlusw.html.service.impl.FollowServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/follow")
@Transactional
public class FollowController {
    @Autowired
    IGroupService<Follow> iGroupService;
    @Autowired
    private UserService userService;
    @Autowired
    private FollowServiceImpl followService;
    private User getUser(HttpServletRequest request) {
        //检查登陆状态
        if (request.getSession().getAttribute("user") == null) {
            return null;
        }
        long userId = (long) request.getSession().getAttribute("user");
        return userService.getById(userId);
    }

    /**
     * 1.创建分组
     * @param groupName 分组名称
    */
    @PostMapping("/createFollowGroup")
    public R<String> createFollowGroup(HttpServletRequest request, String groupName) {
        User user = getUser(request);
        if (user == null)
            return R.error();
        if (iGroupService.createGroup(user.getId(), groupName))
            return R.success();
        return R.error();
    }

    /**
     * 2.删除分组
     * @param groupName 分组名称
     */
    @DeleteMapping("/deleteFollowGroup")
    public R<String> deleteFollowGroup(HttpServletRequest request, String groupName) {
        User user = getUser(request);
        if (user == null)
            return R.error();
        if (iGroupService.deleteGroup(user.getId(), groupName))
            return R.success();
        return R.error();
    }

    /**
     * 3.更改分组名称
     * @param oldGroupName 当前分组名称
     * @param newGroupName 新分组名称
     */
    @PatchMapping("/changeFollowName")
    public R<String> changeGroupName(HttpServletRequest request, String oldGroupName, String newGroupName) {
        if(Objects.equals(oldGroupName, "默认分组") || Objects.equals(newGroupName, "默认分组"))
            return R.error();
        User user = getUser(request);
        if (user == null)
            return R.error();
        if (iGroupService.changeGroupName(user.getId(), oldGroupName, newGroupName))
            return R.success();
        return R.error();
    }

    /**
     * 4.获取所有分组名称
     */
    @GetMapping("/getGroupNames")
    public R<List<String>> getGroupNames(HttpServletRequest request) {
        User user = getUser(request);
        if (user == null)
            return R.error();
        List<String> names = iGroupService.getGroupNames(user.getId());
        if (names != null)
            return R.success(names);
        return R.error();
    }

    /**
     * 5.关注用户
     * @param goalId 关注用户id
     * @param groupName 关注分组名称
     */
    @PostMapping("/follow")
    public R<List<String>> follow(HttpServletRequest request, Long goalId, String groupName) {
        User user = getUser(request);
        if (user == null)
            return R.error();
        if(Objects.equals(goalId, user.getId()))
            return R.error();
        if (groupName.isEmpty())
            groupName = "默认分组";
        if (iGroupService.insert(user.getId(), goalId, groupName)) {
            return R.success();
        }
        return R.error();
    }

    /**
     * 6.取消关注用户
     * @param goalId 关注用户id
     * @param groupName 关注用户所在收藏分组
     */
    @DeleteMapping("/cancelFollow")
    public R<String> cancelFollow(HttpServletRequest request, Long goalId, String groupName) {
        User user = getUser(request);
        if (user == null)
            return R.error();
        if(Objects.equals(user.getId(), goalId))
            return R.error();
        if (iGroupService.remove(user.getId(), goalId, groupName))
            return R.success();
        return R.error();
    }

    /**
     * 7.关注用户更改关注分组
     * @param goalId 关注用户id
     * @param oldGroupName 当前分组id
     * @param newGroupName 新分组id
     */
    @PatchMapping("/moveTo")
    public R<String> moveTo(HttpServletRequest request, Long goalId, String oldGroupName, String newGroupName) {
        User user = getUser(request);
        if (user == null)
            return R.error();
        if (iGroupService.moveTo(user.getId(), goalId, oldGroupName, newGroupName))
            return R.success();
        return R.error();
    }

    /**
     * 8.按时间顺序获取关注用户发布内容
     * @param groupName 关注分组
     * @param startNum 起始位置
     * @param pageSize 页面大小
     * @return Post视图 List
     */
    @GetMapping("/getFollowPagesByTime")
    public R<List<PostDTO>> getFollowPagesByTime(HttpServletRequest request, String groupName, Long startNum, Long pageSize){
        User user = getUser(request);
        if (user == null)
            return R.error();
        List<PostDTO> postDTOS = followService.getFollowPagesOrderByTime(user.getId(), groupName, startNum, pageSize);
        if (postDTOS != null)
            return R.success(postDTOS);
        return R.error();
    }

    /**
     * 9.获取关注分组所有关注用户id
     * @param groupName 关注分组
     * @return user id List
     */
    @GetMapping("/shareFollowGroup")
    public R<List<Object>> shareFollowGroup(HttpServletRequest request, String groupName){
        User user = getUser(request);
        if (user == null)
            return R.error();
        List<Object> follows = followService.shareGroup(user.getId(), groupName);
        if (follows != null)
            return R.success(follows);
        return R.error();
    }

}
