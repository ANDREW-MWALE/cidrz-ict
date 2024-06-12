package com.andyprofinnovations.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Users {
    private int user_id;
    private String name;
    private String position;
    private String email;
    private int role_id;
    private String password;

    public Users(int user_id, String name, String position, String email, int role_id, String password) {
        this.user_id = user_id;
        this.name = name;
        this.position = position;
        this.email = email;
        this.role_id = role_id;
        this.password = password;
    }
}
