package com.html.nds.mapper;

import com.html.nds.entity.Post;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.html.nds.entity.PostDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper extends BaseMapper<Post> {

    List<Integer> queryPostIDByContent(String content);

    PostDTO getPostDTO(int id);

    List<Integer> queryPostByTime(int startNum,int pageSize);

}
