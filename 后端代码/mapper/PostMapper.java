package com.html.nds.mapper;

import com.html.nds.entity.Post;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper extends BaseMapper<Post> {

    List<Integer> queryPostIDByContent(String content);



    List<Integer> queryPostByTime(int startNum,int pageSize);

    List<Integer> queryPostByAuthor(int author);

}
