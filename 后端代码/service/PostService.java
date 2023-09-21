package com.jlusw.html.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlusw.html.entity.Post;

import java.time.LocalDateTime;

public interface PostService  extends IService<Post> {
    /**
     * 转发帖子
     * @param userId 转发者id
     * @param postId 帖子id
     * @return 转发结果
     */
    boolean share(long userId, long postId);

}
