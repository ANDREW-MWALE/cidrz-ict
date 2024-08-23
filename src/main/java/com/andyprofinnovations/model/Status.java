package com.andyprofinnovations.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class Status {
    private int id;
    private String name;


    public Status() {
    }

    public Status(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
