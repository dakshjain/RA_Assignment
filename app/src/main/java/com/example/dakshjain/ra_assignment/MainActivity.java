package com.example.dakshjain.ra_assignment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;

public class MainActivity extends AppCompatActivity {

    MainActivityViewModel mainActivityViewModel;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mainActivityViewModel.getData();
        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name("tasky.realm")
                .schemaVersion(0)
                .build();
        Realm.setDefaultConfiguration(realmConfig);

        mainActivityViewModel.loadData();


        mainActivityViewModel.facilityMutableLiveData.observe(this, new Observer<ArrayList<Facility>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Facility> facilities) {
                RecyclerView recyclerView = findViewById(R.id.rv_facility);
                FacilityAdapter adapter = new FacilityAdapter(facilities, context);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);

                recyclerView.setAdapter(adapter);

                adapter.notifyDataSetChanged();
            }
        });

        mainActivityViewModel.exclusionsMutableLiceData.observe(this, new Observer<ArrayList<Exclusions>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Exclusions> exclusions) {
                Toast.makeText(context, ""+exclusions.size(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    class FacilityAdapter extends RecyclerView.Adapter<FacilityAdapter.ViewHolder> {
        ArrayList<Facility> facilityArrayList = new ArrayList<>();
        Context context;

        public FacilityAdapter(ArrayList<Facility> facilityArrayList, Context context) {
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
            Facility facility = facilityArrayList.get(i);
            RealmList<Options> optionsArrayList = facility.getOptionsRealmList();
            viewHolder.facilityName.setText(facility.getName());

            for (Options options : optionsArrayList) {
                RadioButton radioButton = new RadioButton(context);
                radioButton.setLayoutParams(new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                radioButton.setPadding((int) getResources().getDimension(R.dimen.padding30), 0, 0, 0);
                radioButton.setButtonDrawable(R.drawable.swimming_2x);
                radioButton.setBackground(getResources().getDrawable(R.drawable.checkbox_background, null));
                radioButton.setTextColor(getResources().getColor(R.color.darkText));
                radioButton.setText(options.getName());

                viewHolder.optionRadioGroup.addView(radioButton);
            }

        }

        @Override
        public int getItemCount() {
            return facilityArrayList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            public TextView facilityName;
            public RadioGroup optionRadioGroup;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                facilityName = (TextView) itemView.findViewById(R.id.tv_facility_name);
                optionRadioGroup = (RadioGroup) itemView.findViewById(R.id.rg_option);
            }
        }
    }
}
