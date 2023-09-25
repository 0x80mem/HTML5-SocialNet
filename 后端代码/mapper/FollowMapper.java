package com.jlusw.html.mapper;

import com.jlusw.html.entity.Follow;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jlusw.html.entity.PostDTO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FollowMapper extends BaseMapper<Follow> {
    @Select("select distinct `group` from follow where follower_id = #{userId}")
    List<String> getGroupNames(Long userId);
    List<PostDTO> getFollowPages(Long userId, String groupName, Long startNum, Long pageSize);
    List<Object> shareGroup(Long userId, String groupName);
}
