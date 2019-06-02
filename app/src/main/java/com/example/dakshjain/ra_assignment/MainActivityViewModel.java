package com.example.dakshjain.ra_assignment;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.ArrayList;

public class MainActivityViewModel extends AndroidViewModel {

    MutableLiveData<String> facility_name = new MutableLiveData<>();
    MutableLiveData<ArrayList<Facility>> facilityMutableLiveData = new MutableLiveData<>();

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    void getData() {

        JSONParser.getDataFromWeb();

    }

    void loadData() {
        RealmController realmController = new RealmController();

        facilityMutableLiveData.postValue(realmController.getFacility());

    }

}