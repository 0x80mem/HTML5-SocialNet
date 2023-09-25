package com.jlusw.html.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlusw.html.entity.Follow;
import com.jlusw.html.entity.Post;

import java.time.LocalDateTime;

public interface PostService  extends IService<Post>{

    /**post share+1 */
    boolean share(long userId, long postId);
    /**post 检测like是否存在 like+1 */
    boolean like(long userId, long postId);
    /**post 检测like是否存在 like-1 */
    boolean cancelLike(long userId, long postId);
    /**post collect+1*/
    boolean collect(Long postId);


}
