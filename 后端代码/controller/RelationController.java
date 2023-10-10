package com.html.nds.controller;

import com.html.nds.common.R;

import com.html.nds.mapper.RelationMapper;
import com.html.nds.service.IContentService;
import com.html.nds.service.INodeService;
import com.html.nds.service.IPostService;
import com.html.nds.service.IRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/rel")
@Transactional
public class RelationController {
    @Autowired
    IRelationService relationService;
    @Autowired
    RelationMapper relationMapper;
    @Autowired
    IPostService postService;
    @Autowired
    INodeService nodeService;
    @Autowired
    IContentService contentService;

    @PostMapping("/collect")
    public R<String> collect(Integer postId,Integer collected,Integer collectNode, Integer userId) {
        //增加子节点
        relationService.addChildNode(collectNode, postId);
        relationService.addChildNode(collected, userId);
        contentService.titleAsNumUpdate(collected, 1);
        return R.success();
    }

    @PostMapping("/like")
    public R<String> like(Integer postId,Integer liked,Integer collected,Integer likeNode, Integer collectNode, Integer userId) {
        //增加子节点
        relationService.addChildNode(likeNode,postId);
        relationService.addChildNode(liked, userId);
        contentService.titleAsNumUpdate(liked,1);
        //已收藏
        try{
            return collect( postId, collected,collectNode,userId);
        }catch (Exception e){
            return R.success();
        }

    }

    @PostMapping("/share")
    public R<String> share(Integer postId,Integer shared ,Integer shareNode, Integer userId) {
        //增加子节点
        relationService.addChildNode(shareNode,postId);
        relationService.addChildNode(shared, userId);
        contentService.titleAsNumUpdate(shared,1);
        return R.success();
    }

    @DeleteMapping("/cancelLike")
    public R<String> cancelLike(Integer postId,Integer liked,Integer likeNode) {
        //删除子节点
        relationService.removeChildNode(likeNode,postId);
        contentService.titleAsNumUpdate( liked,-1);
        return R.success();
    }

    @DeleteMapping("/cancelCollect")
    public R<String> cancelCollect(Integer postId,Integer collectNode) {
        //删除子节点
        relationService.removeChildNode(collectNode, postId);
        return R.success();
    }

    @DeleteMapping("/cancelShare")
    public R<String> cancelShare(Integer postId,Integer shared,Integer shareNode) {
        //删除子节点
        relationService.removeChildNode(shareNode, postId);
        contentService.titleAsNumUpdate(shared,-1);
        return R.success();
    }

}
