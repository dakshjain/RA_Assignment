package com.example.dakshjain.ra_assignment;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity {

    MainActivityViewModel mainActivityViewModel;
    Context context;
    ArrayList<String> radioButtonCheckedList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        if(isOneDay()) {
            SharedPreferences.Editor editor = getSharedPreferences("TIME-STAMP", MODE_PRIVATE).edit();
            editor.putLong("timeStamp", System.currentTimeMillis()/1000);
            editor.apply();

            mainActivityViewModel.getData();
        }
        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name("tasky.realm")
                .schemaVersion(0)
                .build();
        Realm.setDefaultConfiguration(realmConfig);

        mainActivityViewModel.loadData();


        mainActivityViewModel.facilityMutableLiveData.observe(this, new Observer<ArrayList<Facility>>() {
            @Override
            public void onChanged(@Nullable final ArrayList<Facility> facilities) {

                mainActivityViewModel.exclusionsMutableLiveData.observe((LifecycleOwner)context, new Observer<ArrayList<ExclusionList>>() {
                    @Override
                    public void onChanged(@Nullable ArrayList<ExclusionList> exclusions) {
                        RecyclerView recyclerView = findViewById(R.id.rv_facility);
                        FacilityAdapter adapter = new FacilityAdapter(facilities, context);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView.setLayoutManager(mLayoutManager);

                        recyclerView.setAdapter(adapter);

                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });



    }

    boolean isOneDay(){
        SharedPreferences prefs = getSharedPreferences("TIME-STAMP", MODE_PRIVATE);
        Long restoredText = prefs.getLong("timeStamp", 0);
        Long timestamp = System.currentTimeMillis()/1000;
        return restoredText == 0 || (timestamp - restoredText) > 86400;
    }


    class FacilityAdapter extends RecyclerView.Adapter<FacilityAdapter.ViewHolder> {
        ArrayList<Facility> facilityArrayList;
        Context context;

        FacilityAdapter(ArrayList<Facility> facilityArrayList, Context context) {
            this.facilityArrayList = facilityArrayList;
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_facility, parent, false);

            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            final Facility facility = facilityArrayList.get(i);
            viewHolder.facilityName.setText(facility.getName());
            for (final Facility facility1 : mainActivityViewModel.getOptionArrayList(facility.getId())) {

                RadioButton radioButton = new RadioButton(context);

                radioButton.setLayoutParams(new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                radioButton.setPadding((int) getResources().getDimension(R.dimen.padding30), (int) getResources().getDimension(R.dimen.paddin15), 0, 0);

                setBackground(facility1.getOptionsRealmList().getName() , radioButton);
                radioButton.setBackground(getResources().getDrawable(R.drawable.checkbox_background, null));
                radioButton.setTextColor(getResources().getColor(R.color.darkText));
                radioButton.setText(facility1.getOptionsRealmList().getName());

                radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(final CompoundButton buttonView, boolean isChecked) {
                        RealmController realmController = new RealmController();

                        if (isChecked) {
                            if (radioButtonCheckedList.size() > 0) {
                                for (String selectedOptions : radioButtonCheckedList) {
                                    if (realmController.isExclusion(selectedOptions, facility1.getOptionsRealmList().getName())) {
                                        buttonView.setChecked(false);
                                        Toast.makeText(context, "Not Allowed", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                                radioButtonCheckedList.add(facility1.getOptionsRealmList().getName());
                                return;
                            } else {
                                radioButtonCheckedList.add(facility1.getOptionsRealmList().getName());
                            }
                        } else {
                            radioButtonCheckedList.remove(facility1.getOptionsRealmList().getName());
                        }
                    }
                });
                viewHolder.optionRadioGroup.addView(radioButton);
            }


        }

        @Override
        public int getItemCount() {
            return facilityArrayList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView facilityName;
            RadioGroup optionRadioGroup;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                facilityName = itemView.findViewById(R.id.tv_facility_name);
                optionRadioGroup = itemView.findViewById(R.id.rg_option);
            }
        }


        private void setBackground(String name , RadioButton radioButton) {
            switch (name){
                case "Apartment":
                    radioButton.setButtonDrawable(R.drawable.apartment_2x);
                    break;
                case "Condo":
                    radioButton.setButtonDrawable(R.drawable.condo_2x);
                    break;
                case "Boat House":
                    radioButton.setButtonDrawable(R.drawable.condo_2x);
                    break;
                case "Land":
                    radioButton.setButtonDrawable(R.drawable.land_2x);
                    break;
                case "1 to 3 Rooms":
                    radioButton.setButtonDrawable(R.drawable.rooms_2x);
                    break;
                case "No Rooms":
                    radioButton.setButtonDrawable(R.drawable.no_room_2x);
                    break;
                case "Swimming Pool":
                    radioButton.setButtonDrawable(R.drawable.swimming_2x);
                    break;
                case "Garden Area":
                    radioButton.setButtonDrawable(R.drawable.garden_2x);
                    break;
                case "Garage":
                    radioButton.setButtonDrawable(R.drawable.garage_2x);
                    break;
            }
        }
    }
}
