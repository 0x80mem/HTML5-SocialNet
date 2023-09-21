package com.jlusw.html.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jlusw.html.entity.User;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;

@Mapper
public interface UserMapper extends BaseMapper<User>{
    /**
     * 用户注销
     * @param id 用户id
     * @param name 用户名
     * @param time 注销时间
     * @return 注销结果
     */
    @Insert("insert into cancelled_user values(#{id},#{name},#{time})")
    boolean cancel(long id, String name, LocalDateTime time);
}
