package com.jlusw.html.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlusw.html.common.R;
import com.jlusw.html.entity.Collect;
import com.jlusw.html.entity.Post;
import com.jlusw.html.entity.User;
import com.jlusw.html.mapper.PostMapper;
import com.jlusw.html.service.ICollectService;
import com.jlusw.html.service.IGroupService;
import com.jlusw.html.service.PostService;
import com.jlusw.html.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/post")
@CrossOrigin
@Transactional
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private IGroupService<Collect> collectIGroupService;
    private User user;
    private Post post;

    public void getUser(HttpServletRequest request){
        //检查登陆状态
        if( request.getSession().getAttribute("user")==null){
            user=null;
            return ;
        }
        long userId = (long) request.getSession().getAttribute("user");
        user= userService.getById(userId);
    }
    /**
     * 1 发帖
     * @param request request
     * @param post 帖子
     * @return 返回发帖结果
     */
    @PostMapping("/post")
    public R<String> post(HttpServletRequest request, @RequestBody Post post) {

        //检查post是否为空
        if (post == null)
            return R.error("post is null");
        //检查登陆状态
        getUser(request);
        if (user == null)
            return R.error("登陆异常");
        //post
        post.setUserId(user.getId());
        post.setTime(LocalDateTime.now());
        if (postService.save(post))
            return R.success();
        return R.error();
    }

    /**
     * 2 删除帖子
     * @param request request
     * @param id 删除帖子的id
     * @return 返回删除结果
     */
    @DeleteMapping("/delete")
    public R<String> post(HttpServletRequest request, Long id) {

        //查询帖子是否存在
        post = postService.getById(id);
        if (post == null)
            return R.error();

        //权限确认 （当前为发帖者拥有删帖权限）
        getUser(request);
        if (user == null || !user.getId().equals(post.getUserId()))
            return R.error("无删贴权限");

        //逻辑删除
        postService.removeById(id);

        return R.success();
    }

    /**
     * 3.1 关键字查找帖子
     * @param content 帖子内容关键字
     * @return 返回内容含关键字帖子集合
     */
    @GetMapping("/queryByContent")
    public R<List<Post>> queryByContent(String content) {

        //查询内容含content的帖子
        LambdaQueryWrapper<Post> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Post::getContent, content);
        List<Post> posts = postService.list(queryWrapper);

        //查询结果为空
        if (posts.isEmpty())
            return R.error();

        return R.success(posts);
    }

    /**
     * 3.2 发布者id查询帖子
     * @param userId 发布者id
     * @return 返回查询结果
     */
    @GetMapping("/queryByAuthor")
    public R<List<Post>> queryByAuthor(long userId) {

        //查询发布者id等于userID的post
        LambdaQueryWrapper<Post> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Post::getUserId, userId);
        List<Post> posts = postService.list(queryWrapper);

        //查询为空
        if (posts.isEmpty())
            return R.error();

        return R.success(posts);
    }

    /**
     * 3.3 分页查询(按时间排序)
     * @param startNum 查询起始位置  0 和 1 都代表从第一条记录开始
     * @param pageSize 每页记录条数
     * @return 返回分页记录及信息
     */
    @GetMapping("/queryPageOrderByTime")
    public IPage<Post> queryPageOrderByTime(Integer startNum, Integer pageSize) {

        //设定起始位置以及每页大小
        Page<Post> page = new Page<>(startNum, pageSize);

        //查询结果时间降序排序
        LambdaQueryWrapper<Post> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Post::getTime);

        return postMapper.selectPage(page, queryWrapper);
    }

    /**
     * 4 转发
     * @param request request
     * @param postId 转发帖子id
     * @return 返回转发结果
     */
    @PostMapping("/share")
    public R<String> sharePost(HttpServletRequest request,long postId){

        //检查登陆状态
        getUser(request);
        if (user == null)
            return R.error("登陆异常");

        //查询帖子是否存在
        post = postService.getById(postId);
        if (post == null)
            return R.error("帖子不存在");


        if(postService.share(user.getId(),postId))
            return R.success();
        return R.error();
    }

    @PostMapping("/like")
    public R<String> likePost(HttpServletRequest request,long postId){
        //检查登陆状态
        getUser(request);
        if (user == null)
            return R.error("登陆异常");
        //查询帖子是否存在
        post = postService.getById(postId);
        if (post == null)
            return R.error("帖子不存在");
        //更新like
        if(postService.like(user.getId(),postId)){
            //成功默认收藏
            if(collectIGroupService.insert(user.getId(),postId,"默认收藏夹"))
                postService.collect(postId);
            else {
                //否则尝试创建默认收藏夹 再次收藏
                collectIGroupService.createGroup(user.getId(), "默认收藏夹");
                if(collectIGroupService.insert(user.getId(),postId,"默认收藏夹"))
                    postService.collect(postId);
            }
            return R.success();
        }
        return R.error();
    }
    @DeleteMapping("/cancelLike")
    public R<String> cancelLikePost(HttpServletRequest request,long postId){
        //检查登陆状态
        getUser(request);
        if (user == null)
            return R.error("登陆异常");
        //查询帖子是否存在
        post = postService.getById(postId);
        if (post == null)
            return R.error("帖子不存在");

        if(postService.cancelLike(user.getId(),postId))
            return R.success();

        return R.error();
    }



}
