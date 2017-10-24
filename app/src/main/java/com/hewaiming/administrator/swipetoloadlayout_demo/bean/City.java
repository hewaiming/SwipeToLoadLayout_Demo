package com.hewaiming.administrator.swipetoloadlayout_demo.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/19 0019.
 */

public class City implements Serializable {
    private String Id;
    private String Name;

    public City(String id, String name) {
        Id = id;
        Name = name;
    }

    public City() {
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public String toString() {
        return "City{" +
                "Id='" + Id + '\'' +
                ", Name='" + Name + '\'' +
                '}';
    }
}
