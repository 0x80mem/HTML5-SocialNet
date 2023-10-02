package com.jlusw.html.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlusw.html.entity.Collect;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jlusw.html.entity.PostDTO;
import com.jlusw.html.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface CollectMapper extends BaseMapper<Collect> {
    @Select("select distinct `group`from collect where user_id=#{userId}")
    List<String> getGroupNames(Long userId);
    List<PostDTO> getCollectPages(Long userId, String groupName, Long startNum, Long pageSize);
    List<Object> shareGroup(Long userId, String groupName);

}
