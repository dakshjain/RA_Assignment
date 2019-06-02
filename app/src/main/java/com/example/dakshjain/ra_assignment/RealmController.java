package com.example.dakshjain.ra_assignment;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmController {
    Realm realm = Realm.getDefaultInstance();

    RealmController() {
    }

    public void insertOrupdate(final Facility facility) {
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                    realm.insertOrUpdate(facility); // could be copyToRealmOrUpdate
                }
            });
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }

    public void insertOrUpdate(final Exclusions exclusions){
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                    realm.insertOrUpdate(exclusions); // could be copyToRealmOrUpdate
                }
            });
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }

    public ArrayList<Facility> getFacility() {
        Realm realm = null;
        final ArrayList<Facility> facilityArrayList = new ArrayList<>();
        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {

                @Override
                public void execute(Realm realm) {

                    RealmResults<Facility> results = realm.where(Facility.class).findAll();
                    for (int i = 0; i < results.size(); i++) {
                        facilityArrayList.add(results.get(i));
                    }
                }
            });
        } finally {
            if (realm != null) {
                realm.close();
            }
        }

        return facilityArrayList;
    }

    public ArrayList<Exclusions> getExclusions() {
        Realm realm = null;
        final ArrayList<Exclusions> exclusionsArrayList = new ArrayList<>();
        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {

                @Override
                public void execute(Realm realm) {

                    RealmResults<Exclusions> results = realm.where(Exclusions.class).findAll();
                    for (int i = 0; i < results.size(); i++) {
                        exclusionsArrayList.add(results.get(i));
                    }
                }
            });
        } finally {
            if (realm != null) {
                realm.close();
            }
        }

        return exclusionsArrayList;
    }

}
