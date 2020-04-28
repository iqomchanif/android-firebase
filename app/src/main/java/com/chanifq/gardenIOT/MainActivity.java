package com.chanifq.gardenIOT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chanifq.gardenIOT.Adapter.ItemAdapter;
import com.chanifq.gardenIOT.Fragment.FragmentList;
import com.chanifq.gardenIOT.Fragment.FragmentRemote;
import com.chanifq.gardenIOT.model.DataPlant;
import com.chanifq.gardenIOT.model.Garden;
import com.chanifq.gardenIOT.model.GlobalClass;
import com.chanifq.gardenIOT.model.ListPlant;
import com.chanifq.gardenIOT.model.Setting;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference databaseReferencePlant;
    DatabaseReference databaseReferenceSetting;
    RecyclerView rvItem;
    ItemAdapter itemAdapter;
    FragmentList fragmentList;
    FragmentRemote fragmentRemote;
    ArrayList<ListPlant> listPlants;
    ArrayList<Setting> listSetting;
    BottomNavigationView nav;
    GlobalClass globalClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = FirebaseDatabase.getInstance();
        databaseReferencePlant = database.getReference().child("pythonfirebaseiot").child("Plants");
        databaseReferenceSetting = database.getReference().child("pythonfirebaseiot").child("Setting");
        initFragment();
        loadFragment(fragmentList);
        globalClass = (GlobalClass) getApplicationContext();
        globalClass.setState("list");
        globalClass.setModeNamePlant("Anggrek");
        listPlants = new ArrayList<ListPlant>();
        listSetting = new ArrayList<>();
        nav = findViewById(R.id.nav_view);
        nav.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Log.d("itemOnSelect", String.valueOf(item.getTitle()));
                        if (item.getTitle().equals("MyPlant")) {
                            loadFragment(fragmentList);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    fragmentList.setRvItem(listPlants);
                                }
                            }, 100);
                            globalClass.setState("list");
                        } else {
                            loadFragment(fragmentRemote);
                            globalClass.setState("remote");
                        }
                        return true;
                    }
                }
        );
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("My Garden IOT");
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReferencePlant.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("addEventListenOy", "masuk");
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
//                Log.d("firebase_plants",dataSnapshot.getChildren());
//
//                );
                listPlants.clear();
                ArrayList<DataPlant> arrayPlant = new ArrayList<>();
                int iPlant = 0;
                for (DataSnapshot plantSnapshot : dataSnapshot.getChildren()) {
                    Log.d("snapplantsnapshot", plantSnapshot.toString());
//                    Log.d("firebase_plants",plantSnapshot.getKey());
                    String name = plantSnapshot.getKey();
                    arrayPlant.clear();
                    int i = 0;
                    for (DataSnapshot plantDetail : plantSnapshot.getChildren()) {
                        Log.d("snapplantdetail", plantDetail.toString());
                        DataPlant plant = plantDetail.getValue(DataPlant.class);
//                        Log.d("firebase_plants_value","humi:"+ plant.getHumidity());
//                        Log.d("firebase_plants_value","temp:"+ plant.getTemperature());
//                        Log.d("firebase_plants_value","light:"+plant.getLight());
                        String temp = plant.getTemperature();
                        String humi = plant.getHumidity();
                        String light = plant.getLight();
                        String created = plant.getCreated_at();
                        String humiTanah = plant.getHumidityTanah();
                        DataPlant itemPlant = new DataPlant(String.valueOf(i), name, humiTanah, humi, temp, light, created);
                        i = i + 1;
                        arrayPlant.add(itemPlant);
                    }
                    iPlant++;
                    ListPlant list = new ListPlant(iPlant, name, arrayPlant);
                    listPlants.add(list);
                }
                GlobalClass globalClass = (GlobalClass) getApplicationContext();
                globalClass.setListPlants(listPlants);
                if(globalClass.getState()=="list")
                    fragmentList.setRvItem(listPlants);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("firebase", "Failed to read value.", error.toException());
            }
        });

        databaseReferenceSetting.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listSetting.clear();
                for (DataSnapshot plantSnapshot : dataSnapshot.getChildren()) {
                    String name = plantSnapshot.getKey();
                    String getID = "", kipas = "", light = "", semprot = "", valueSetting = "",mode="";

                    Log.d("setting", "getgetget");
                    for (DataSnapshot data : plantSnapshot.getChildren()) {
                        Log.d("firebase_setting", name );
                        Log.d("firebase_setting", data.toString());
                        getID = data.getKey();
                        Setting datasetting = data.getValue(Setting.class);
                        kipas = datasetting.getKipas();
                        light = datasetting.getled();
                        semprot = datasetting.getSemprot();
                        valueSetting = datasetting.getValueSettingHThL();
                        mode=datasetting.getMode();
                        Setting setting = new Setting(name,getID,semprot, light, kipas, valueSetting,mode);
                        listSetting.add(setting);
                        for(ListPlant list: listPlants){
                            if(list.getName().equals(name)){
                                list.setDataSetting(setting);
                            }
                        }

                    }

                }
                globalClass.setSettings(listSetting);
                for (Setting setting : listSetting) {
                    Log.d("firebase_setting", setting.getName());
                    Log.d("firebase_setting", setting.getIDsetting());

                }
                if(globalClass.getState()=="remote")
                    fragmentRemote.updateData(listSetting);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    public ArrayList<DataPlant> getDataPlantOf(String name) {
        ArrayList<DataPlant> buff = new ArrayList<DataPlant>();
        for (ListPlant list : listPlants) {
            if (list.getName().equals(name)) {
                buff = list.getPlants();
            }
        }

        for (DataPlant data : buff) {
            Log.d("buff", "a" + data.getLight());
        }
        Log.d("buff", buff.toString());
        return (buff);
    }

    private void addPlant(String name, String humi, String temp, String light) {
        // Write a message to the database
//        String id= databaseReferencePlant.push().getKey();
//        DataPlant plant= new DataPlant(id,name,humi,temp,light);
//        databaseReferencePlant.child(id).setValue(plant);
//        Toast.makeText(getApplicationContext(),"succes",Toast.LENGTH_SHORT).show();
    }

//    private void addHistoryGarden(String name, String humi, String temp, String light) {
//        String id= databaseReferenceHistoryGarden.push().getKey();
//        Garden garden= new Garden(id,name,humi,temp,light);
//        databaseReferenceHistoryGarden.child(id).setValue(garden);
//        Toast.makeText(getApplicationContext(),"succes",Toast.LENGTH_SHORT).show();
//
//    }

    private void getPlant() {

    }

    void initFragment() {
        fragmentList = new FragmentList();
        fragmentRemote = new FragmentRemote();


    }

    public boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    public void setUpdateData(String plantName, String device, String state) {
        DatabaseReference databaseReference = database.getReference().child("pythonfirebaseiot")
                .child("Setting").child(plantName);
        Log.d("firebase", "set Semprot");
        for (Setting setting : listSetting) {
            Log.d("firebase_setting", setting.getName());
            Log.d("firebase_setting", setting.getIDsetting());
            if (setting.getName().equals(plantName)) {
                String id = setting.getIDsetting();
                Log.d("key_semprot", id);

                databaseReference.child(id).child(device).setValue(state);
            }
        }

    }

}

