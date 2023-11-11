package com.html.nds.entity;

import lombok.Data;
@Data
public class UserDTO {

    private Integer userId;
    private String userName;
    private String userProfile;
    public UserDTO(Integer userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }
}
