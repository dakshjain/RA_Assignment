package com.example.dakshjain.ra_assignment;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.ArrayList;

public class MainActivityViewModel extends AndroidViewModel {

    MutableLiveData<ArrayList<Facility>> facilityMutableLiveData = new MutableLiveData<>();
    MutableLiveData<ArrayList<ExclusionList>> exclusionsMutableLiveData = new MutableLiveData<>();

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    void getData(Apiresponse apiresponse) {

        JSONParser.getDataFromWeb(apiresponse);

    }

    void loadData() {
        RealmController realmController = new RealmController();

        facilityMutableLiveData.postValue(realmController.getFacilityByIdDistinct());
        exclusionsMutableLiveData.postValue(realmController.getExclusions());

    }

    ArrayList<Facility> getOptionArrayList(String facilityId) {
        RealmController realmController = new RealmController();
        return realmController.getFacilitybyId(facilityId);
    }


}