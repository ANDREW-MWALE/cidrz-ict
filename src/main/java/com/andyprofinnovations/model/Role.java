package com.andyprofinnovations.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Role {
 private int role_id;
 private String name;
 private String description;

    public Role() {
        this.role_id = role_id;
        this.name = name;
        this.description = description;
    }
}
