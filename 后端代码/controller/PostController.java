package com.html.nds.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.html.nds.common.DTOUtil;
import com.html.nds.common.R;
import com.html.nds.entity.*;
import com.html.nds.mapper.PostMapper;
import com.html.nds.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/post")
@Transactional
public class PostController {
    @Autowired
    IUserService userService;
    @Autowired
    INodeService nodeService;
    @Autowired
    IPostService postService;
    @Autowired
    PostMapper postMapper;
    @Autowired
    IContentService contentService;
    @Autowired
    IRelationService relationService;

    public User getUser(HttpServletRequest request){
        //检查登陆状态
        if( request.getSession().getAttribute("user")==null){
            return null;
        }
        int userId = (int) request.getSession().getAttribute("user");
        return userService.getById(userId);
    }

    public R<List<PostDTO>> getPosts(List<Integer> idList){
        if(idList.isEmpty())
            return R.error();
        List<Node> nodes=nodeService.list(new QueryWrapper<Node>().in("id",idList));
        if(nodes==null)
            return R.error();
        //获取 PostDTO
        List<PostDTO> posts=new ArrayList<>();
        for(Node node : nodes){
            NodeDTO nodeDTO=DTOUtil.nodeToDTO(node);
            //获取子节点
            nodeDTO.setChiNodes(relationService.getChiNodes(node.getId()));
            nodeDTO.setContent(nodeService.getContent(nodeDTO.getId()));
            posts.add(new PostDTO(nodeDTO,postMapper.selectById(node.getId()).getAuthor()));
            //获取PostDTO

        }
        return R.success(posts);
    }

    /**
     * 1 发帖
     * @param request request
     * @param postDTO 帖子
     * @return 返回发帖结果
     */
    @PostMapping("/post")
    public R<String> post(HttpServletRequest request, @RequestBody PostDTO postDTO) {
        User user = getUser(request);
        if(user==null)
            return R.error();
        if(postService.initPost(postDTO,user.getId()))
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
    public R<String> delete(HttpServletRequest request, Integer id) {

        //查询帖子是否存在
        Post post = postService.getById(id);
        if (post == null)
            return R.error();

        //权限确认 （当前为发帖者拥有删帖权限）
        User user = getUser(request);
        if (user == null || !user.getId().equals(post.getAuthor()))
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
    public R<List<PostDTO>> queryByContent(String content) {
        //获取帖子ID
        List<Integer> idList= postMapper.queryPostIDByContent(content);
        return getPosts(idList);
    }

    /**
     * 3.2 发布者id查询帖子
     * @param userId 发布者id
     * @return 返回查询结果
     */
    @GetMapping("/queryByAuthor")
    public R<List<PostDTO>> queryByAuthor(long userId) {

        //查询发布者id等于userID的post
        LambdaQueryWrapper<Post> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Post::getAuthor, userId);
        List<Post> posts = postService.list(queryWrapper);
        List<Integer> idList=new ArrayList<>();
        for (Post post:posts){
            idList.add(post.getId());
        }
        //查询为空
        if (posts.isEmpty())
            return R.error();

        return getPosts(idList);
    }

    /**
     * 3.3 分页查询(按时间排序)
     * @param startNum 查询起始位置  0 和 1 都代表从第一条记录开始
     * @param pageSize 每页记录条数
     * @return 返回分页记录及信息
     */
    @GetMapping("/queryPageOrderByTime")
    public R<List<PostDTO>> queryPageOrderByTime(Integer startNum, Integer pageSize) {

        //查询结果时间降序排序
        List<Integer> idList=postMapper.queryPostByTime(startNum,pageSize);
        if(idList==null)
            return R.error();
        return getPosts(idList);
    }
}
