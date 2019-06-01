package com.example.dakshjain.ra_assignment;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.ArrayList;

public class MainActivityViewModel extends AndroidViewModel {

    MutableLiveData<String> facility_name = new MutableLiveData<>();

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public void getData() {

        JSONParser.getDataFromWeb();

    }

    public void loadData() {
        RealmController realmController = new RealmController();

        ArrayList<Facility> facilityArrayList = realmController.getFacility();
        String name = facilityArrayList.get(0).getName();
    }
}