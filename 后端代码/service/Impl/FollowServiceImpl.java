package com.jlusw.html.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jlusw.html.entity.Follow;
import com.jlusw.html.entity.PostDTO;
import com.jlusw.html.mapper.FollowMapper;
import com.jlusw.html.service.IFollowService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlusw.html.service.IGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements IFollowService, IGroupService<Follow> {
    @Autowired
    FollowMapper followMapper;

    @Override
    public boolean createGroup(Long userId, String groupName) {
        if (isExistGroup(userId, groupName))
            return false;
        return followMapper.insert(new Follow(userId, userId, LocalDateTime.now(), groupName)) > 0;
    }

    @Override
    public boolean deleteGroup(Long userId, String groupName) {
        return followMapper.delete(new LambdaQueryWrapper<Follow>()
                .eq(Follow::getFollowerId, userId)
                .eq(Follow::getGroup, groupName)
        ) > 0;
    }

    @Override
    public boolean changeGroupName(Long userId, String oldGroupName, String newGroupName) {
        if (!isExistGroup(userId, oldGroupName))
            return false;
        return followMapper.update(null, new LambdaUpdateWrapper<Follow>()
                .eq(Follow::getFollowerId, userId)
                .eq(Follow::getGroup, oldGroupName)
                .set(Follow::getGroup, newGroupName)
        ) > 0;
    }

    @Override
    public boolean isExistGroup(Long userId, String groupName) {
        return followMapper.selectOne(new LambdaQueryWrapper<Follow>()
                .eq(Follow::getFollowerId, userId)
                .eq(Follow::getUserId, userId)
                .eq(Follow::getGroup, groupName)
        ) != null;
    }

    @Override
    public List<String> getGroupNames(Long userId) {
        return followMapper.getGroupNames(userId);
    }

    public boolean isExistRelation(Long userId, Long followerId, String groupName) {
        return followMapper.selectOne(new LambdaQueryWrapper<Follow>()
                .eq(Follow::getUserId, userId)
                .eq(Follow::getFollowerId, followerId)
                .eq(Follow::getGroup, groupName)
        ) != null;
    }

    @Override
    public boolean insert(Long userId, Long goalId, String groupName) {
        if (isExistRelation(goalId, userId, groupName))
            return false;
        return followMapper.insert(new Follow(goalId, userId, LocalDateTime.now(), groupName)) > 0;
    }

    @Override
    public boolean remove(Long userId, Long goalId, String groupName) {
        if (!isExistRelation(goalId, userId, groupName))
            return false;
        return followMapper.delete(new LambdaQueryWrapper<Follow>()
                .eq(Follow::getUserId, goalId)
                .eq(Follow::getFollowerId, userId)
                .eq(Follow::getGroup, groupName)
        ) > 0;
    }

    @Override
    public boolean moveTo(Long userId, Long goalId, String oldGroupName, String newGroupName) {
        if (isExistGroup(userId, oldGroupName) && isExistRelation(goalId, userId, oldGroupName) && isExistGroup(userId, newGroupName) && !isExistRelation(goalId, userId, newGroupName))
            return followMapper.update(null, new LambdaUpdateWrapper<Follow>()
                    .eq(Follow::getUserId, goalId)
                    .eq(Follow::getFollowerId, userId)
                    .eq(Follow::getGroup, oldGroupName)
                    .set(Follow::getGroup, newGroupName)
            ) > 0;
        return false;

    }
    @Override
    public IPage<Follow> getGroupContent(Long userId, String groupName, Long startNum, Long pageSize) {
        return null;
    }

    @Override
    public List<Object> shareGroup(Long useId, String groupName) {
        return followMapper.shareGroup(useId,groupName);
    }

    public List<PostDTO> getFollowPagesOrderByTime(Long userId, String groupName, Long startNum, Long pageSize){
        return followMapper.getFollowPages(userId,groupName,startNum-1,pageSize);
    }
}
