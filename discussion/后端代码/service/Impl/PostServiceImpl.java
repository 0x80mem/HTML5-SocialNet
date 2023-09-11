package com.jlusw.html.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlusw.html.entity.Post;
import com.jlusw.html.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlusw.html.mapper.PostMapper;

import java.time.LocalDateTime;

@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {
    @Autowired
    PostMapper postMapper;
    @Override
    public boolean share(long userId, long postId) {

        //post表share+1
        UpdateWrapper<Post> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", postId).setSql("share=share+1");
        update(updateWrapper);

        //share表存入记录
        return  postMapper.share(userId,postId,LocalDateTime.now());
    }
}
