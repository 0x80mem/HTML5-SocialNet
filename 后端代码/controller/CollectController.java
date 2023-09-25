package com.jlusw.html.controller;


import com.jlusw.html.common.R;
import com.jlusw.html.entity.Collect;
import com.jlusw.html.entity.PostDTO;
import com.jlusw.html.entity.User;
import com.jlusw.html.service.ICollectService;
import com.jlusw.html.service.IGroupService;
import com.jlusw.html.service.PostService;
import com.jlusw.html.service.UserService;
import com.jlusw.html.service.impl.CollectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/collect")
@Transactional
public class CollectController {
    @Autowired
    private IGroupService<Collect> iGroupService;
    @Autowired
    private CollectServiceImpl collectService;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    private User getUser(HttpServletRequest request) {
        //session获取id
        long userId = (long) request.getSession().getAttribute("user");
        return userService.getById(userId);
    }

    /**
     * 1.创建收藏分组
     * @param groupName 收藏分组名称
     */
    @PostMapping("/createCollectGroup")
    public R<String> createCollectGroup(HttpServletRequest request, String groupName) {
        //检查登陆状态
        User user = getUser(request);
        if (user == null)
            return R.error("");
        //创建收藏分组
        if (iGroupService.createGroup(user.getId(), groupName))
            return R.success("");
        return R.error("");
    }

    /**
     * 2.删除收藏分组
     * @param groupName 收藏分组名称
     */
    @DeleteMapping("/deleteCollectGroup")
    public R<String> deleteCollectGroup(HttpServletRequest request, String groupName) {
        //检查登陆状态
        User user = getUser(request);
        if (user == null)
            return R.error("");
        //删除收藏分组
        if (iGroupService.deleteGroup(user.getId(), groupName))
            return R.success("");
        return R.error("");
    }

    /**
     * 3.更改收藏分组名称
     * @param oldGroupName 当前收藏分组名称
     * @param newGroupName 新收藏分组名称
     */
    @PatchMapping("/changeGroupName")
    public R<String> changeGroupName(HttpServletRequest request, String oldGroupName, String newGroupName) {
        // //检查登陆状态
        User user = getUser(request);
        if (user == null)
            return R.error("");
        //更改收藏分组名称
        if (iGroupService.changeGroupName(user.getId(), oldGroupName, newGroupName))
            return R.success("");
        return R.error("");
    }

    /**
     * 4.获取所有收藏分组名称
     * @return 收藏分组名称 List
     */
    @GetMapping("/getGroupNames")
    public R<List<String>> getGroupNames(HttpServletRequest request) {
        //检查登陆状态
        User user = getUser(request);
        if (user == null)
            return R.error();
        //获取所有收藏分组名称
        List<String> names = iGroupService.getGroupNames(user.getId());
        if (names != null)
            return R.success(names);
        return R.error();
    }

    /**
     * 5.收藏帖子
     * @param postId 帖子ID
     * @param groupName 收藏分组名称
     */
    @PostMapping("/collect")
    public R<String> collect(HttpServletRequest request, Long postId, String groupName) {
        //检查登陆状态
        User user = getUser(request);
        if (user == null)
            return R.error();
        //groupName缺省值
        if (groupName==null||groupName.isEmpty())
            groupName = "默认收藏夹";
        //收藏帖子
        if (iGroupService.insert(user.getId(), postId, groupName) && postService.collect(postId)) {
            return R.success();
        }
        return R.error();
    }

    /**
     * 6.取消收藏
     * @param postId 帖子ID
     * @param groupName 收藏分组
     */
    @DeleteMapping("/cancelCollect")
    public R<String> cancelCollect(HttpServletRequest request, Long postId, String groupName) {
        //检查登陆状态
        User user = getUser(request);
        if (user == null)
            return R.error();
        //取消收藏
        if (iGroupService.remove(user.getId(), postId, groupName))
            return R.success();
        return R.error();
    }

    /**
     * 7.移动帖子收藏分组
     * @param postId  帖子ID
     * @param oldGroupName 当前收藏分组
     * @param newGroupName 新收藏分组
     */
    @PatchMapping("/moveTo")
    public R<String> moveTo(HttpServletRequest request, Long postId, String oldGroupName, String newGroupName) {
        //检查登陆状态
        User user = getUser(request);
        if (user == null)
            return R.error();
        if (iGroupService.moveTo(user.getId(), postId, oldGroupName, newGroupName))
            return R.success();
        return R.error();
    }

    /**
     * 8.按时间顺序获取收藏分组帖子
     * @param groupName 收藏分组
     * @param startNum 起始位置
     * @param pageSize 页面大小
     * @return PostDTO List
     */
    @GetMapping("/getCollectByTime")
    public R<List<PostDTO>> getCollectByTime(HttpServletRequest request, String groupName, Long startNum, Long pageSize) {
        //检查登陆状态
        User user = getUser(request);
        if (user == null)
            return R.error();
        List<PostDTO> postDTOS = collectService.getCollectPagesOrderByTime(user.getId(), groupName, startNum, pageSize);
        if (postDTOS != null)
            return R.success(postDTOS);
        return R.error();
    }

    /**
     * 9.分享收藏内容
     * @param groupName 收藏分组
     * @return 收藏分组所有帖子id
     */
    @GetMapping("/shareCollectGroup")
    public R<List<Object>> shareCollectGroup(HttpServletRequest request, String groupName){
        //检查登陆状态
        User user = getUser(request);
        if (user == null)
            return R.error();
        List<Object> collects = collectService.shareGroup(user.getId(), groupName);
        if (collects != null)
            return R.success(collects);
        return R.error();
    }


}
