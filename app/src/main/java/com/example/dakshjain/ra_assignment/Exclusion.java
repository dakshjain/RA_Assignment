package com.example.dakshjain.ra_assignment;

import io.realm.RealmObject;

public class Exclusion extends RealmObject {

    public Exclusion() {
    }

    public Exclusion(int facilityId, int optionId) {
        this.facilityId = facilityId;
        this.optionId = optionId;
    }

    private int facilityId;
    private int optionId;

    public int getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(int facilityId) {
        this.facilityId = facilityId;
    }

    public int getOptionId() {
        return optionId;
    }

    public void setOptionId(int optionId) {
        this.optionId = optionId;
    }
}
