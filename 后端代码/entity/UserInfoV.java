package com.html.nds.entity;

import lombok.Data;

@Data
public class UserInfoV {
    private Integer id;
    private String bio;
    private String username;
    private String avatar;

    public UserInfoV(Integer id, String bio, String username, String avatar) {
        this.id = id;
        this.bio = bio;
        this.username = username;
        this.avatar = avatar;
    }
}
