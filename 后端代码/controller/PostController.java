package com.html.nds.controller;

import com.html.nds.common.R;
import com.html.nds.entity.*;
import com.html.nds.mapper.PostMapper;
import com.html.nds.service.*;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/post")
@Transactional
@CrossOrigin
@Configuration
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

    public User getUser(HttpServletRequest request) {
        //检查登陆状态
        if (request.getSession().getAttribute("user") == null) {
            return null;
        }
        int userId = (int) request.getSession().getAttribute("user");
        return userService.getById(userId);
    }

    public R<List<PostV>> getPosts(List<Integer> idList) {

        if (idList.isEmpty())
            return R.error();
        List<Node> nodes = new ArrayList<>();
        for(Integer i:idList){
            Node node =nodeService.getById(i);
            if(node!=null)
                nodes.add(node);
        }
        if (nodes.isEmpty())
            return R.error();
        //获取 PostDTO
        List<PostV> postVs = new ArrayList<>();

        for (Node node : nodes) {
            NodeDTO nodeDTO = new NodeDTO(node);
            //获取子节点
            nodeDTO.setChiNodes(relationService.getChiNodes(node.getId()));
            nodeDTO.setContent(nodeService.getContent(node.getId()));
            postVs.add(new PostV(nodeDTO));

        }
        return R.success(postVs);
    }

    /**
     * 1 发帖
     *
     * @param images         图片
     * @param articleContent 帖子
     * @return 返回发帖结果
     */
    @PostMapping("/post")

    public R<String> post(MultipartFile[] images, @RequestParam List<String> articleContent, String title,Integer userId) {
        System.out.println(articleContent);
        if (articleContent == null || userId == null)
            return R.error("参数为null");
        ContentDTO contentDTO = getDto(articleContent,images,title);
        System.out.println("contentDTO"+contentDTO);
        if (postService.initPost(contentDTO, userId))
            return R.success();
        return R.error();

    }
    @PostMapping("/comment")

    public R<String> comment(MultipartFile[] images, @RequestParam List<String> articleContent, String title,Integer userId,Integer parId) {

        if (articleContent == null || userId == null)
            return R.error("参数为null");
        ContentDTO contentDTO = getDto(articleContent,images,title);
        if(nodeService.createNode(parId,"comment",contentDTO,userId,true)!=null)
            return R.success();
        return R.error();
    }
    /**
     * 图片url替换文字空列表获得ContentDTO
     * @param articleContent 文章内容列表
     * @return ContentDTO
     */
    private ContentDTO getDto(List<String> articleContent,MultipartFile[] images,String title) {
        //保存路径
        String uploadDirectory = "/usr/app/dist/img/";

//        String uploadDirectory = "D:/1_img/";
        //图片url
        String serverBaseUrl = "http://47.93.10.201/img/";
//        String serverBaseUrl = "D:/1_img/";
        List<String> imageUrls = new ArrayList<>();
        int imgNum = 0;
        for (String s : articleContent) {
            if (s.isEmpty())
                imgNum++;
        }
        if(images==null){
            if(imgNum!=0)
                return null;
        }else{
            if (imgNum != images.length)
                return null;
            //保存图片并生成url列表
            for (MultipartFile image : images) {
                if (!image.isEmpty()) {
                    try {
                        // 保存图片到服务器的img文件夹
                        String uid = UUID.randomUUID().toString();
                        String fileName = image.getOriginalFilename().replaceFirst(".*\\.", uid + ".");
                        String filePath = uploadDirectory + fileName;

                        File dest = new File(filePath);
                        image.transferTo(dest);

                        // 生成可以访问的URL
                        String imageUrl = serverBaseUrl + fileName;
                        // 将URL添加到列表
                        imageUrls.add(imageUrl);
                    } catch (IOException e) {
                        e.printStackTrace();
                        imageUrls.add("Error uploading image.");
                    }
                } else {
                    imageUrls.add("Image is empty.");
                }
            }
        }
        StringBuilder stringBuilder=new StringBuilder();

        int j=0;
        for (String s : articleContent) {
            if (s.isEmpty())
                stringBuilder.append(imageUrls.get(j++));
            else
                stringBuilder.append(StringEscapeUtils.unescapeHtml4(s));
        }

        System.out.println(stringBuilder);
        return new ContentDTO(title, stringBuilder.toString());
       /* List<Object> combinedData = new ArrayList<>();
        int j=0;
        for (String s : articleContent) {
            if (s.isEmpty())
                combinedData.add(imageUrls.get(j++));
            else
                combinedData.add(StringEscapeUtils.unescapeHtml4(s));
        }

        System.out.println(combinedData);
        // 使用Gson将数据转换为JSON格式的字符串
        Gson gson = new Gson();
        String jsonData = gson.toJson(combinedData);
        return new ContentDTO(title, jsonData);*/
    }


    /**
     * 2 删除帖子
     *
     * @param request request
     * @param id      删除帖子的id
     * @return 返回删除结果
     */
    @DeleteMapping("/delete")
    public R<String> delete(HttpServletRequest request, Integer id) {

        //查询帖子是否存在
        Post post = postService.getById(id);
        if (post == null)
            return R.error();
        Node node = nodeService.getById(id);
        //权限确认 （当前为发帖者拥有删帖权限）
        User user = getUser(request);
        if (user == null || !user.getId().equals(node.getAuthor()))
            return R.error("无删贴权限");

        //逻辑删除
        postService.removeById(id);

        return R.success();
    }

    /**
     * 3.1 关键字查找帖子
     *
     * @param content 帖子内容关键字
     * @return 返回内容含关键字帖子集合
     */
    @GetMapping("/queryByContent")
    public R<List<PostV>> queryByContent(String content) {
        //获取帖子ID
        List<Integer> idList = postMapper.queryPostIDByContent(content);
        return getPosts(idList);
    }

    /**
     * 3.2 发布者id查询帖子
     *
     * @param userId 发布者id
     * @return 返回查询结果
     */
    @GetMapping("/queryByAuthor")
    public R<List<PostV>> queryByAuthor(int userId) {

        //查询发布者id等于userID的post

        List<Integer> posts = postMapper.queryPostByAuthor(userId);

        //查询为空
        if (posts.isEmpty())
            return R.error("");

        return getPosts(posts);
    }

    /**
     * 3.3 分页查询(按时间排序)
     *
     * @param startNum 查询起始位置  0 和 1 都代表从第一条记录开始
     * @param pageSize 每页记录条数
     * @return 返回分页记录及信息
     */
    @GetMapping("/queryPageOrderByTime")
    public R<List<PostV>> queryPageOrderByTime(Integer startNum, Integer pageSize) {

        //查询结果时间降序排序
        List<Integer> idList = postMapper.queryPostByTime(startNum, pageSize);
        System.out.println(idList);
        if (idList == null)
            return R.error();
        return getPosts(idList);
    }


}
