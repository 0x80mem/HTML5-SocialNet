package com.jlusw.html.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlusw.html.entity.Post;
import com.jlusw.html.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlusw.html.mapper.PostMapper;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {
    @Autowired
    PostMapper postMapper;
    final private UpdateWrapper<Post> updateWrapper = new UpdateWrapper<>();

    @Override
    public boolean share(long userId, long postId) {

        //post表share+1
        if (!update(new UpdateWrapper<Post>().eq("id", postId).setSql("share=share+1")))
            return false;
        //share表存入记录
        return postMapper.share(userId, postId, LocalDateTime.now());
    }

    @Override
//    @Transactional
    public boolean like(long userId, long postId) {
        //是否已点赞
        if(postMapper.isExistLike(userId,postId)!=null)
            return false;
        //like新增 post like+1
        if(postMapper.like(userId,postId,LocalDateTime.now()))
            return update(new UpdateWrapper<Post>().eq("id", postId).setSql(" likes = likes+1"));
        return false;
    }

    @Override
    public boolean cancelLike(long userId, long postId) {
        if(postMapper.isExistLike(userId,postId)==null)
            return false;
        //删除like
        if(postMapper.cancelLike(userId,postId))
            return update(new UpdateWrapper<Post>().eq("id", postId).setSql("likes = likes-1"));
        return false;
    }

    @Override
    public boolean collect(Long postId) {
        return update(new UpdateWrapper<Post>().eq("id", postId).setSql("collect = collect+1"));
    }

}
