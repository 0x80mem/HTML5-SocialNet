package com.jlusw.html.common;

import com.jlusw.html.entity.User;
import com.jlusw.html.entity.UserDTO;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

import java.util.ArrayList;
import java.util.List;


public class UserUtil {

    public static MapperFactory getMyMapper(){
        //获取MapperFactory
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        //MapperFactory设置映射
        mapperFactory.classMap(User.class,UserDTO.class)
                .field("id","userId")//名称不同时映射
                .field("name","userName")
                .byDefault()//默认
                .register();
        return mapperFactory;
    }
    /** User 转 UserDTO */
    public static UserDTO toUserDTO(User user){
        return getMyMapper().getMapperFacade().map(user,UserDTO.class);
    }
    /**Users 转 UserDTOs */
    public static List<UserDTO> toUserDTOs(List<User> users){
        return getMyMapper().getMapperFacade().mapAsList(users,UserDTO.class);
    }
}
