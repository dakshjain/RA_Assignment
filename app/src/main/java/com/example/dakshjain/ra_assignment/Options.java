package com.example.dakshjain.ra_assignment;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Options extends RealmObject {

    public Options() {
    }

    public Options(int id, String name, String icon) {
        this.id = id;
        this.name = name;
        this.icon = icon;
    }

    @PrimaryKey
    private int id;

    private String name;

    private String icon;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
