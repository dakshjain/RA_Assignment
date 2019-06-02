package com.example.dakshjain.ra_assignment;

import io.realm.RealmObject;

public class Facility extends RealmObject {

    public Facility() {
    }

    Facility(String id, String name, Options optionsRealmList) {
        this.id = id;
        this.name = name;
        this.optionsRealmList = optionsRealmList;
    }

    private String id;

    private String name;

    private Options optionsRealmList = new Options();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    Options getOptionsRealmList() {
        return optionsRealmList;
    }

    public void setOptionsRealmList(Options optionsRealmList) {
        this.optionsRealmList = optionsRealmList;
    }
}
