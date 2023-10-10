package com.html.nds.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.html.nds.entity.NodeDTO;
import com.html.nds.entity.Relation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface IRelationService extends IService<Relation> {
    boolean addChildNode(Integer parNode, Integer chiNode);
    boolean removeChildNode(Integer parNode, Integer chiNode);
    List<NodeDTO> getChiNodes(Integer parNode);


}
