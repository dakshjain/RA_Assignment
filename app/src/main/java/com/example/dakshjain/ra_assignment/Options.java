package com.example.dakshjain.ra_assignment;

import io.realm.RealmObject;

public class Options extends RealmObject {

    public Options() {
    }

    public Options(String id, String name, String icon) {
        this.optionId = id;
        this.name = name;
        this.icon = icon;
    }

    private String optionId;

    private String name;

    private String icon;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        return optionId;
    }

    public void setId(String id) {
        this.optionId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
