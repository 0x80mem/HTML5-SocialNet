package com.jlusw.html.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jlusw.html.entity.Post;
import org.apache.ibatis.annotations.Insert;
import java.time.LocalDateTime;

public interface PostMapper extends BaseMapper<Post> {
    @Insert("insert into share values (#{userId},#{postId},#{time})")
    boolean share(long userId, long postId, LocalDateTime time);
}
