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
    private final INodeService nodeService;
    private final IContentService contentService;

    @Autowired
    public PostServiceImpl(INodeService nodeService, IContentService contentService) {
        this.nodeService = nodeService;
        this.contentService = contentService;
    }
    @Override
    public boolean initPost(ContentDTO contentDTO,Integer author) {
        //检查post是否为空
        if (contentDTO == null)
           return false;
        //创建node
        Integer nodeId=nodeService.createNode(null,"post",author);
        //post存入信息，保存
        Post post = new Post();
        post.setId(nodeId);
        save(post);

        //保存content
        Content content = new Content(contentDTO);
        content.setId(nodeId);
        contentService.save(content);

        //初始化 liked shared collected
        Integer liked = nodeService.createNode(nodeId,"liked",new ContentDTO("0",""),author);

        Integer shared = nodeService.createNode(nodeId,"shared",new ContentDTO("0",""),author);

        Integer collected = nodeService.createNode(nodeId,"collected",new ContentDTO("0",""),author);

        return true;
    }


}
