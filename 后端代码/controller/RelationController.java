package com.html.nds.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.html.nds.common.R;

import com.html.nds.entity.PostV;
import com.html.nds.entity.Relation;
import com.html.nds.mapper.RelationMapper;
import com.html.nds.service.IContentService;
import com.html.nds.service.INodeService;
import com.html.nds.service.IPostService;
import com.html.nds.service.IRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/rel")
@Transactional
@CrossOrigin
@Configuration
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
    public R<PostV> collect(Integer postId, Integer userId) {
        //增加子节点
        Integer postCollected = relationMapper.getChiNodeByType("collected", postId);
        Integer userCollect = relationMapper.getChiNodeByType("collect", userId);
        relationService.addChildNode(userCollect, postId);
        try {
            relationService.addChildNode(postCollected, userId);
        } catch (Exception ignored) {
        }
        contentService.titleAsNumUpdate(postCollected, 1);
        return R.success(nodeService.getNode(postCollected));
    }

    @DeleteMapping("/cancelCollect")
    public R<PostV> cancelCollect(Integer postId, Integer userId) {
        //删除子节点
        Integer userCollect = relationMapper.getChiNodeByType("collect", userId);
        Integer postCollected = relationMapper.getChiNodeByType("collected", postId);
        relationService.removeChildNode(userCollect, postId);
        relationService.removeChildNode(postCollected, userId);
        contentService.titleAsNumUpdate(postCollected, -1);
        return R.success(nodeService.getNode(postCollected));
    }

    @PostMapping("/like")
    public R<PostV> like(Integer postId, Integer userId) {
        //增加子节点
        Integer userLike = relationMapper.getChiNodeByType("like", userId);
        Integer postLiked = relationMapper.getChiNodeByType("liked", postId);

        relationService.addChildNode(userLike, postId);
        try {
            relationService.addChildNode(postLiked, userId);
        } catch (Exception ignored) {

        }
        contentService.titleAsNumUpdate(postLiked, 1);
        R<PostV> r = R.success(nodeService.getNode(postLiked));
        //已收藏
        try {
            collect(postId, userId);
        } catch (Exception e) {
            r.setMsg("已收藏");
        }
        return r;
    }

    @DeleteMapping("/cancelLike")
    public R<PostV> cancelLike(Integer postId, Integer userId) {
        //删除子节点
        Integer postLiked = relationMapper.getChiNodeByType("liked", postId);
        Integer userLike = relationMapper.getChiNodeByType("like", userId);

        relationService.removeChildNode(userLike, postId);
        relationService.removeChildNode(postLiked, userId);

        contentService.titleAsNumUpdate(postLiked, -1);
        return R.success(nodeService.getNode(postLiked));
    }

    @PostMapping("/share")
    public R<String> share(Integer postId, Integer userId) {

        Integer postShared = relationMapper.getChiNodeByType("shared", postId);
        Integer userShare = relationMapper.getChiNodeByType("share", userId);

        relationService.addChildNode(userShare, postId);
        try {
            relationService.addChildNode(postShared, userId);
        } catch (Exception ignored) {
        }
        contentService.titleAsNumUpdate(postShared, 1);
        return R.success();
    }

    @DeleteMapping("/cancelShare")
    public R<String> cancelShare(Integer postId, Integer userId) {
        Integer postShared = relationMapper.getChiNodeByType("shared", postId);
        Integer userShare = relationMapper.getChiNodeByType("share", userId);
        //删除子节点
        relationService.removeChildNode(userShare, postId);
        relationService.removeChildNode(postShared, userId);
        contentService.titleAsNumUpdate(postShared, -1);
        return R.success();
    }


    @PostMapping("/subscribe")
    public R<String> subscribe(Integer userToSub, Integer user) {
        //userToSub粉丝增加
        Integer fans = relationMapper.getChiNodeByType("fans", userToSub);
        relationService.addChildNode(fans, user);
        //user关注增加
        Integer subscribe = relationMapper.getChiNodeByType("subscribe", user);
        relationService.addChildNode(subscribe, userToSub);

        return R.success();
    }

    @DeleteMapping("/cancelSubscribe")
    public R<String> cancelSubscribe(Integer user, Integer userToSub) {
        //删除子节点
        Integer fans = relationMapper.getChiNodeByType("fans", userToSub);
        relationService.removeChildNode(fans, user);
        //user关注增加
        Integer subscribe = relationMapper.getChiNodeByType("subscribe", user);
        relationService.removeChildNode(subscribe, userToSub);

        return R.success();
    }

    @GetMapping("/beLiked")
    public R<Boolean> beLiked(Integer postId, Integer userId) {
        Integer userLike = relationMapper.getChiNodeByType("like",userId);
        if (relationService.getOne(new LambdaQueryWrapper<Relation>().eq(Relation::getParent, userLike).eq(Relation::getChild, postId)) != null)
            return R.success(true);
        return R.success(false);
    }
    @GetMapping("/beCollected")
    public R<Boolean> beCollected(Integer postId, Integer userId) {
        Integer userCollect = relationMapper.getChiNodeByType("collect",userId);
        if (relationService.getOne(new LambdaQueryWrapper<Relation>().eq(Relation::getParent, userCollect).eq(Relation::getChild, postId)) != null)
            return R.success(true);
        return R.success(false);
    }

    @GetMapping("/hasRelation")
    public R<Boolean> hasRelation(Integer parId, Integer chiId) {
        if (relationService.getOne(new LambdaQueryWrapper<Relation>().eq(Relation::getParent, parId).eq(Relation::getChild, chiId)) != null)
            return R.success(true);
        return R.success(false);
    }

}
