package com.jlusw.html.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlusw.html.entity.Collect;
import com.jlusw.html.entity.PostDTO;
import com.jlusw.html.mapper.CollectMapper;
import com.jlusw.html.service.ICollectService;
import com.jlusw.html.service.IGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect> implements ICollectService, IGroupService<Collect> {
    @Autowired
    CollectMapper collectMapper;
    @Override
    public boolean createGroup(Long userId, String groupName) {
        if(isExistGroup(userId,groupName))
            return false;
        return collectMapper.insert(new Collect(userId,-1L,groupName, LocalDateTime.now()))>0;
    }

    @Override
    public boolean deleteGroup(Long userId, String groupName) {
        if(!isExistGroup(userId,groupName))
            return false;
        return collectMapper.delete(new LambdaQueryWrapper<Collect>()
                .eq(Collect::getUserId,userId)
                .eq(Collect::getGroup,groupName)
        )>0;
    }

    @Override
    public boolean changeGroupName(Long userId, String oldGroupName, String newGroupName) {
        if(!isExistGroup(userId,oldGroupName))
            return false;
        return collectMapper.update(null,new LambdaUpdateWrapper<Collect>()
                .eq(Collect::getUserId,userId)
                .eq(Collect::getGroup,oldGroupName)
                .set(Collect::getGroup,newGroupName)
        )>0;
    }

    @Override
    public boolean isExistGroup(Long userId, String groupName) {
        return collectMapper.selectOne(new LambdaQueryWrapper<Collect>()
                .eq(Collect::getUserId,userId)
                .eq(Collect::getPostId,-1L)
                .eq(Collect::getGroup,groupName)
        )!=null;
    }

    @Override
    public List<String> getGroupNames(Long userId) {
        return collectMapper.getGroupNames(userId);
    }

    public boolean isExistRelation(Long userId,Long goalId,String groupName){
        return collectMapper.selectOne(new LambdaQueryWrapper<Collect>()
                .eq(Collect::getUserId,userId)
                .eq(Collect::getPostId,goalId)
                .eq(Collect::getGroup,groupName)
        )!=null;
    }
    @Override
    public boolean insert(Long userId, Long goalId, String groupName) {
        if(isExistGroup(userId,groupName)&&!isExistRelation(userId,goalId,groupName)){
            return collectMapper.insert(new Collect(userId, goalId, groupName, LocalDateTime.now())) > 0;
        }
        return false;
    }

    @Override
    public boolean remove(Long userId, Long goalId, String groupName) {
        if(isExistGroup(userId,groupName)&&isExistRelation(userId,goalId,groupName))
            return collectMapper.delete(new LambdaQueryWrapper<Collect>()
                    .eq(Collect::getUserId,userId)
                    .eq(Collect::getPostId,goalId)
                    .eq(Collect::getGroup,groupName)
            )>0;
        return false;
    }

    @Override
    public boolean moveTo(Long userId, Long goalId, String oldGroupName, String newGroupName) {
        if(isExistGroup(userId,oldGroupName)&&isExistRelation(userId,goalId,oldGroupName)&&isExistGroup(userId,newGroupName)&&!isExistRelation(userId,goalId,newGroupName))
            return collectMapper.update(null,new LambdaUpdateWrapper<Collect>()
                    .eq(Collect::getUserId,userId)
                    .eq(Collect::getPostId,goalId)
                    .eq(Collect::getGroup,oldGroupName)
                    .set(Collect::getGroup,newGroupName)
            )>0;
        return false;
    }

    @Override
    public IPage<Collect> getGroupContent(Long userId, String groupName, Long startNum, Long pageSize) {
        Page<Collect> page = new Page<>(startNum, pageSize);
        //查询结果时间降序排序
        LambdaQueryWrapper<Collect> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Collect::getTime);
        return collectMapper.selectPage(page, queryWrapper);
    }

    @Override
    public List<Object> shareGroup(Long useId, String groupName) {
        return collectMapper.shareGroup(useId,groupName);
    }

    public List<PostDTO> getCollectPagesOrderByTime(Long userId, String groupName, Long startNum, Long pageSize){
        return collectMapper.getCollectPages(userId,groupName,startNum-1,pageSize);
    }

}
