package com.example.dakshjain.ra_assignment;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmController {
    Realm realm ;

    RealmController() {
        realm = Realm.getDefaultInstance();
    }

    void insertOrupdate(final Facility facility) {
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                    realm.insertOrUpdate(facility); // could be copyToRealmOrUpdate
                }
            });
        }
    }

    void insertOrUpdate(final ExclusionList exclusionList){
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                    realm.insertOrUpdate(exclusionList); // could be copyToRealmOrUpdate
                }
            });
        }
    }

    ArrayList<Facility> getFacilityByIdDistinct(){
        final ArrayList<Facility> facilityArrayList = new ArrayList<>();
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.executeTransaction(new Realm.Transaction() {

                @Override
                public void execute(Realm realm) {

                    RealmResults<Facility> results = realm.where(Facility.class).distinct("id");
                    facilityArrayList.addAll(results);
                }
            });
        }

        return facilityArrayList;
    }

    ArrayList<Facility> getFacilitybyId(final String facilityId){
        final ArrayList<Facility> facilityArrayList = new ArrayList<>();
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.executeTransaction(new Realm.Transaction() {

                @Override
                public void execute(Realm realm) {

                    RealmResults<Facility> results = realm.where(Facility.class).equalTo("id", facilityId).findAll();
                    facilityArrayList.addAll(results);
                }
            });
        }

        return facilityArrayList;
    }

    ArrayList<ExclusionList> getExclusions() {
        final ArrayList<ExclusionList> exclusionListArrayList = new ArrayList<>();
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.executeTransaction(new Realm.Transaction() {

                @Override
                public void execute(Realm realm) {

                    RealmResults<ExclusionList> results = realm.where(ExclusionList.class).findAll();
                    exclusionListArrayList.addAll(realm.copyFromRealm(results));
                }
            });
        }

        return exclusionListArrayList;
    }

    boolean isExclusion(final String selectedOption, final String option){
        final String[] name = new String[1];
        final ExclusionList[] results = new ExclusionList[1];
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.executeTransaction(new Realm.Transaction() {

                @Override
                public void execute(Realm realm) {

                    results[0] = realm.where(ExclusionList.class).equalTo("exclusion1", selectedOption).equalTo("exclusion2", option).findFirst();
                    if (results[0] == null) {
                        results[0] = realm.where(ExclusionList.class).equalTo("exclusion2", selectedOption).equalTo("exclusion1", option).findFirst();
                    }

                }
            });
        }
        return results[0] != null;
    }
}
