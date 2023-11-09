package com.html.nds.service;

import com.html.nds.entity.ContentDTO;
import com.html.nds.entity.Post;
import com.baomidou.mybatisplus.extension.service.IService;


public interface IPostService extends IService<Post> {
    boolean initPost(ContentDTO content, Integer author);
}
