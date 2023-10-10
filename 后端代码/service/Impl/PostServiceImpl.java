package com.html.nds.service.impl;

import com.html.nds.common.DTOUtil;
import com.html.nds.common.R;
import com.html.nds.entity.*;
import com.html.nds.mapper.PostMapper;
import com.html.nds.service.IContentService;
import com.html.nds.service.INodeService;
import com.html.nds.service.IPostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.html.nds.service.IRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements IPostService {
    @Autowired
    INodeService nodeService;
    @Autowired
    IContentService contentService;
    @Autowired
    IRelationService relationService;
    @Override
    public boolean initPost(PostDTO postDTO,Integer author) {
        //检查post是否为空
        if (postDTO == null)
           return false;
        //创建node
        Integer nodeId=nodeService.createNode(null,"post");
        //post存入信息，保存
        Post post = DTOUtil.DTOToPost(postDTO);
        post.setId(nodeId);
        post.setAuthor(author);
        System.out.println("author  "+nodeId);
        save(post);

        //保存content
        Content content = DTOUtil.DTOToContent(postDTO.getNode().getContent());
        content.setId(nodeId);
        contentService.save(content);

        //初始化 liked shared collected
        Integer liked = nodeService.createNode(nodeId,"liked",new ContentDTO("0",""));

        Integer shared = nodeService.createNode(nodeId,"shared",new ContentDTO("0",""));

        Integer collected = nodeService.createNode(nodeId,"collected",new ContentDTO("0",""));

        return true;
    }
}
