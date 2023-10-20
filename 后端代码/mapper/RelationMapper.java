package com.html.nds.mapper;

import com.html.nds.entity.NodeDTO;
import com.html.nds.entity.Relation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RelationMapper extends BaseMapper<Relation> {
    public List<NodeDTO> getChiNodes(int parId);

}
