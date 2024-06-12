package com.andyprofinnovations.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Location {
    private int location_id;
    private String name;
    private String address;

    public Location(int location_id, String name, String address) {
        this.location_id = location_id;
        this.name = name;
        this.address = address;
    }

    public Location() {
    }

    //    public Location() {
//        this.location_id = location_id;
//        this.name = name;
//        this.address = address;
//    }
}
