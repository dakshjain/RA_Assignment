package com.example.dakshjain.ra_assignment;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Exclusions extends RealmObject {
    private RealmList<Exclusion> exclusionRealmList = new RealmList<Exclusion>();

    public Exclusions() {
    }

    public Exclusions(RealmList<Exclusion> exclusionRealmList) {
        this.exclusionRealmList = exclusionRealmList ;
    }

    public RealmList<Exclusion> getExclusionRealmList() {
        return exclusionRealmList;
    }

    public void setExclusionRealmList(RealmList<Exclusion> exclusionRealmList) {
        this.exclusionRealmList = exclusionRealmList;
    }
}

