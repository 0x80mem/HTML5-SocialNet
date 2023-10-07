package com.html.nds.service;

import com.html.nds.entity.Post;
import com.baomidou.mybatisplus.extension.service.IService;
import com.html.nds.entity.PostDTO;


public interface IPostService extends IService<Post> {
    boolean initPost(PostDTO postDTO,Integer author);
}
