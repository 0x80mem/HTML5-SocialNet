package com.html.nds.mapper;

import com.html.nds.entity.NodeDTO;
import com.html.nds.entity.Relation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RelationMapper extends BaseMapper<Relation> {
    List<Integer> getChiNodes(int parId);
    List<Integer> getParNodes(int chiId);
    Integer getChiNodeByType(String type,int parId);
}
