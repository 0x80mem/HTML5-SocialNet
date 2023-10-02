package com.jlusw.html.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jlusw.html.entity.Collect;
import com.jlusw.html.entity.Like;
import com.jlusw.html.entity.Post;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;

public interface PostMapper extends BaseMapper<Post> {
    @Insert("insert  into share values (#{userId},#{postId},#{time})")
    boolean share(long userId, long postId, LocalDateTime time);

    @Select("select * from `like` where user_id=#{userId} and post_id=#{postId}")
    Like isExistLike(long userId, long postId);

    @Insert("insert  into `like` values (#{userId},#{postId},#{time})")
    boolean like(long userId, long postId, LocalDateTime time);

    @Delete("delete from `like` where user_id = #{userId} and post_id = #{postId}")
    boolean cancelLike(long userId, long postId);


}
