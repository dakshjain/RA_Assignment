package com.example.dakshjain.ra_assignment;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Facility extends RealmObject {

    public Facility() {
    }

    Facility(int id, String name, RealmList<Options> optionsRealmList) {
        this.id = id;
        this.name = name;
        this.optionsRealmList = optionsRealmList;
    }

    @PrimaryKey
    private int id;

    private String name;

    private RealmList<Options> optionsRealmList = new RealmList<Options>();

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

    public RealmList<Options> getOptionsRealmList() {
        return optionsRealmList;
    }

    public void setOptionsRealmList(RealmList<Options> optionsRealmList) {
        this.optionsRealmList = optionsRealmList;
    }
}
