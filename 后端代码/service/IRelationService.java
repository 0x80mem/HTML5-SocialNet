package com.html.nds.service;

import com.html.nds.entity.Relation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface IRelationService extends IService<Relation> {
    void addChildNode(Integer parNodeId, Integer chiNodeId);
    void removeChildNode(Integer parNodeId, Integer chiNodeId);
    void addParNode(Integer parNodeId, Integer chiNodeId);
    List<Integer> getChiNodes(Integer parNodeId);
    List<Integer> getParNodes(Integer chiNodeId);

}
