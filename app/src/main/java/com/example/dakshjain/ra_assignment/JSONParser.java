package com.example.dakshjain.ra_assignment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.realm.RealmList;

public class JSONParser {

    private static final String MAIN_URL = "https://my-json-server.typicode.com/iranjith4/ad-assignment/db";

    public static final String TAG = "TAG";

    private static final String KEY_USER_ID = "user_id";

    private static Response response;

    public static void getDataFromWeb() {
        final Handler mHandler = new Handler(Looper.getMainLooper());
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(MAIN_URL)
                .build();

        final JSONObject jsonObject;
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
                Log.d("OKHTTP", "onFailure");
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Log.d("OKHTTP", "response unsuccessfull");
                    throw new IOException("Unexpected code " + response);
                } else {
                    // do something wih the result
                    Log.d("OKHTTP", "response successfull");
                    try {
                        jsonObjecttoRepo(response.body().string());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    private static void jsonObjecttoRepo(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);

        JSONArray jsonArray = jsonObject.getJSONArray("facilities");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject facility = jsonArray.getJSONObject(i);
            String facility_id = facility.getString("facility_id");
            String name = facility.getString("name");

            RealmList<Options> optionsArrayList = new RealmList<>();
            JSONArray optionJSONArray = facility.getJSONArray("options");
            for (int j = 0; j < optionJSONArray.length(); j++) {
                JSONObject optionJsonObject = optionJSONArray.getJSONObject(j);
                String option_id = optionJsonObject.getString("id");
                String option_name = optionJsonObject.getString("name");
                String option_icon = optionJsonObject.getString("icon");

                Options options = new Options(Integer.valueOf(option_id), option_name, option_icon);
                optionsArrayList.add(options);
            }

            Facility facility_ = new Facility(Integer.valueOf(facility_id), name, optionsArrayList);
            RealmController realmController = new RealmController();
            realmController.insertOrupdate(facility_);
        }
    }
}