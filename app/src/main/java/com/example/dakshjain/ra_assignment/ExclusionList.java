package com.example.dakshjain.ra_assignment;

import io.realm.RealmObject;

public class ExclusionList extends RealmObject {
    private String exclusion1;
    private String exclusion2;

    public ExclusionList() {
    }

    ExclusionList(String exclusion1, String exclusion2) {
        this.exclusion1 = exclusion1;
        this.exclusion2 = exclusion2;
    }

    public String getExclusion1() {
        return exclusion1;
    }

    public void setExclusion1(String exclusion1) {
        this.exclusion1 = exclusion1;
    }

    public String getExclusion2() {
        return exclusion2;
    }

    public void setExclusion2(String exclusion2) {
        this.exclusion2 = exclusion2;
    }
}

