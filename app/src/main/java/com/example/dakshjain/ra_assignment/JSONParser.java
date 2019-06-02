package com.example.dakshjain.ra_assignment;

import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

class JSONParser {

    private static final String MAIN_URL = "https://my-json-server.typicode.com/iranjith4/ad-assignment/db";

    static void getDataFromWeb() {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS).build();

        final Request request = new Request.Builder()
                .url(MAIN_URL)
                .build();

        final JSONObject jsonObject;

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                if (!response.isSuccessful()) {
                    Log.d("OKHTTP", "response unsuccessfull");
                    throw new IOException("Unexpected code " + response);
                } else {
                    // do something wih the result
                    Log.d("OKHTTP", "response successfull");
                    String result = response.body().string();
                    try {
                        jsonObjecttoFacilityRealm(result);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        jsonObjectToExclusionRealm(result);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                Log.d("OKHTTP", "onFailure");
            }
        });
    }

    private static void jsonObjectToExclusionRealm(String response) throws JSONException {
        ArrayList<String> stringArrayList = new ArrayList<>();
        RealmController realmController = new RealmController();
        JSONObject jsonObject = new JSONObject(response);
        JSONArray jsonArray = jsonObject.getJSONArray("exclusions");

        if(jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONArray innerJsonArray = jsonArray.getJSONArray(i);
                stringArrayList.clear();
                for (int j = 0; j < innerJsonArray.length(); j++) {
                    JSONObject innerJSONObject = innerJsonArray.getJSONObject(j);
                    String facility_id = innerJSONObject.getString("facility_id");
                    String option_id = innerJSONObject.getString("options_id");

                    if(facility_id != null && option_id != null) {
                        ArrayList<Facility> facilityArrayList = realmController.getFacilitybyId(facility_id);
                        for (Facility facility : facilityArrayList) {
                            if (facility.getOptionsRealmList().getId().equals(option_id)) {
                                stringArrayList.add(facility.getOptionsRealmList().getName());
                            }
                        }
                    }
                }

                ExclusionList exclusion = new ExclusionList(stringArrayList.get(0), stringArrayList.get(1));
                realmController.insertOrUpdate(exclusion);
            }
        }


    }

    private static void jsonObjecttoFacilityRealm(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);

        JSONArray jsonArray = jsonObject.getJSONArray("facilities");
        if(jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject facility = jsonArray.getJSONObject(i);
                String facility_id = facility.getString("facility_id");
                String name = facility.getString("name");

                JSONArray optionJSONArray = facility.getJSONArray("options");
                for (int j = 0; j < optionJSONArray.length(); j++) {
                    JSONObject optionJsonObject = optionJSONArray.getJSONObject(j);
                    String option_id = optionJsonObject.getString("id");
                    String option_name = optionJsonObject.getString("name");
                    String option_icon = optionJsonObject.getString("icon");

                    Options options = new Options(option_id, option_name, option_icon);

                    Facility facility_ = new Facility(facility_id, name, options);
                    RealmController realmController = new RealmController();
                    realmController.insertOrupdate(facility_);
                }
            }
        }
    }
}