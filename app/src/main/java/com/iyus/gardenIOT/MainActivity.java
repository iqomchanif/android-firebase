package com.iyus.gardenIOT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;

import com.iyus.gardenIOT.Adapter.ItemAdapter;
import com.iyus.gardenIOT.Fragment.FragmentList;
import com.iyus.gardenIOT.Fragment.FragmentRemote;
import com.iyus.gardenIOT.model.DataPlant;
import com.iyus.gardenIOT.model.GlobalClass;
import com.iyus.gardenIOT.model.ListPlant;
import com.iyus.gardenIOT.model.Setting;
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
    long timeConnect;
    int iUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = FirebaseDatabase.getInstance();
        databaseReferencePlant = database.getReference().child("gardeniot-c4aed").child("Plants");
        databaseReferenceSetting = database.getReference().child("gardeniot-c4aed").child("Setting");
        iUpdate=0;
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
                            Handler handler= new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    fragmentRemote.updateData(listSetting);
                                }
                            }, 100);
                            globalClass.setState("remote");
                        }
                        return true;
                    }
                }
        );
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("My Garden IOT");
        timeConnect= (savedInstanceState == null)?0:Integer.valueOf(savedInstanceState.getString("timeConnect"));
        final Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(System.currentTimeMillis()-timeConnect>20000){
                    toolbar.setBackgroundResource(R.color.red);
                }
                else{
                    toolbar.setBackgroundResource(R.color.yellowbackgrounddarker);

                }
                handler.postDelayed(this, 2000);
            }
        },2000);
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReferencePlant.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("addEventListenOy", "masuk");
                listPlants.clear();
                ArrayList<DataPlant> arrayPlant = new ArrayList<>();
                int iPlant = 0;
                for (DataSnapshot plantSnapshot : dataSnapshot.getChildren()) {
                    Log.d("firebase_plants", plantSnapshot.toString());
                    Log.d("firebase_plants",plantSnapshot.getKey());
                    String name = plantSnapshot.getKey();
                    arrayPlant.clear();
                    int error = 0;
                    int i = 0;
                    for (DataSnapshot plantDetail : plantSnapshot.getChildren()) {

//                        try {
                            DataPlant plant = plantDetail.getValue(DataPlant.class);
                            //                        Log.d("firebase_plants_value","humi:"+ plant.getHumidity());
                            //                        Log.d("firebase_plants_value","temp:"+ plant.getTemperature());
                            //                        Log.d("firebase_plants_value","light:"+plant.getLight());
                            String temp = plant.getTemperature();
                            String humi = plant.getHumidity();
                            String light = plant.getLight();
                            String created = plant.getCreated_at();
                            String humiTanah = plant.getHumidityTanah();
                            String clockAt=plant.getClock_at();

                            DataPlant itemPlant = new DataPlant(String.valueOf(i), name, humiTanah, humi, temp, light, created,clockAt);
                            i = i + 1;
                            arrayPlant.add(itemPlant);

//                        } catch (Exception e) {
//                            error += 1;
//                            Log.d("datasnapshot","error"+error);
//                            Log.d("snapplantdetail", plantDetail.toString());
//                        }
                    }
//                    Toast.makeText(MainActivity.this, "get " + error + " data error", Toast.LENGTH_SHORT).show();
                    iPlant++;
                    ListPlant list = new ListPlant(iPlant, name, arrayPlant);
                    listPlants.add(list);
                }
                GlobalClass globalClass = (GlobalClass) getApplicationContext();
                globalClass.setListPlants(listPlants);
                if (globalClass.getState() == "list")
                    fragmentList.setRvItem(listPlants);
                if (globalClass.getState() == "remote")
                    fragmentRemote.updateData(listSetting);
                if(iUpdate>=3)
                    timeConnect=System.currentTimeMillis();
                iUpdate++;
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
                Log.d("datachange","plants");
                for (DataSnapshot plantSnapshot : dataSnapshot.getChildren()) {
                    String name = plantSnapshot.getKey();
                    String getID = "", kipas = "", light = "", semprot = "", valueSetting = "", mode = "";

                    Log.d("setting", "getgetget");
                    for (DataSnapshot data : plantSnapshot.getChildren()) {
                        Log.d("firebase_setting", name);
                        Log.d("firebase_setting", data.toString());
                        getID = data.getKey();
                        Setting datasetting = data.getValue(Setting.class);
                        kipas = datasetting.getKipas();
                        light = datasetting.getled();
                        semprot = datasetting.getSemprot();
                        valueSetting = datasetting.getValueSettingHThL();
                        mode = datasetting.getMode();
                        Setting setting = new Setting(name, getID, semprot, light, kipas, valueSetting, mode);
                        listSetting.add(setting);
                        for (ListPlant list : listPlants) {
                            if (list.getName().equals(name)) {
                                list.setDataSetting(setting);
                                Log.d("firebase_setting", "update-listplant-setting");
                            }
                        }

                    }

                }
                globalClass.setSettings(listSetting);
                for (Setting setting : listSetting) {
                    Log.d("firebase_setting", setting.getName());
                    Log.d("firebase_setting", setting.getIDsetting());

                }
                if (globalClass.getState() == "remote")
                    fragmentRemote.updateData(listSetting);
                if (globalClass.getState() == "list")
                    fragmentList.setRvItem(listPlants);
                if(iUpdate>=5)
                    timeConnect=System.currentTimeMillis();
                iUpdate++;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putString("timeConnect", String.valueOf(timeConnect));
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
        DatabaseReference databaseReference = database.getReference().child("gardeniot-c4aed")
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

