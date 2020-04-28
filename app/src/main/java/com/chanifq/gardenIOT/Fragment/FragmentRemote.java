package com.chanifq.gardenIOT.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chanifq.gardenIOT.MainActivity;
import com.chanifq.gardenIOT.R;
import com.chanifq.gardenIOT.model.GlobalClass;
import com.chanifq.gardenIOT.model.ListPlant;
import com.chanifq.gardenIOT.model.Setting;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class FragmentRemote extends Fragment {
    ToggleButton btnSemprot, btnLampu,btnKipas,btnMode;
    MainActivity mainActivity;
    Spinner spinJenisTanaman;
    GlobalClass globalClass;
    Button btnUpdateSetting;
    TextView tvSettingSuhu, tvSettingKelembapan,tvSettingCahaya,tvSettingKelembapanTanah;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View orderView = inflater.inflate(R.layout.fragment_remote, container, false);
        btnLampu=orderView.findViewById(R.id.btnLampu);
        btnSemprot=orderView.findViewById(R.id.btnSemprot);
        mainActivity= (MainActivity) getActivity();
        btnKipas= orderView.findViewById(R.id.btnKipas);
        globalClass= (GlobalClass) getActivity().getApplicationContext();
        spinJenisTanaman= orderView.findViewById(R.id.spinner);
        tvSettingSuhu= orderView.findViewById(R.id.tvSettingSuhu);
        tvSettingKelembapan= orderView.findViewById(R.id.tvSettingKelembapan);
        tvSettingCahaya= orderView.findViewById(R.id.tvSettingCahaya);
        tvSettingKelembapanTanah= orderView.findViewById(R.id.tvSettingKelembapanTanah);
        btnUpdateSetting= orderView.findViewById(R.id.btnUpdateSetting);
        btnMode=orderView.findViewById(R.id.btnMode);
        btnMode.setTextOn("Manual");
        btnMode.setTextOff("Otomatis");
        // Spinner click listener
        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        for(ListPlant list:globalClass.getListPlants()){
            categories.add(list.getName());

        }


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinJenisTanaman.setAdapter(dataAdapter);
        btnSemprot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("spiin",spinJenisTanaman.getSelectedItem().toString());

                if(btnSemprot.isChecked()){
                    mainActivity.setUpdateData(spinJenisTanaman.getSelectedItem().toString(),"semprot","1");
                }
                else{
                    mainActivity.setUpdateData(spinJenisTanaman.getSelectedItem().toString(),"semprot","0");

                }
            }
        });
        btnLampu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnLampu.isChecked()){
                    mainActivity.setUpdateData(spinJenisTanaman.getSelectedItem().toString(),"led","1");
                }
                else{
                    mainActivity.setUpdateData(spinJenisTanaman.getSelectedItem().toString(),"led","0");

                }
            }
        });
        btnKipas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnKipas.isChecked()){
                    mainActivity.setUpdateData(spinJenisTanaman.getSelectedItem().toString(),"kipas","1");
                }
                else{
                    mainActivity.setUpdateData(spinJenisTanaman.getSelectedItem().toString(),"kipas","0");

                }
            }
        });
        btnMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnMode.isChecked()){
                    mainActivity.setUpdateData(spinJenisTanaman.getSelectedItem().toString(),"mode","1");
                    btnKipas.setEnabled(true);
                    btnLampu.setEnabled(true);
                    btnSemprot.setEnabled(true);
                }
                else{
                    mainActivity.setUpdateData(spinJenisTanaman.getSelectedItem().toString(),"mode","0");
                    btnKipas.setEnabled(false);
                    btnLampu.setEnabled(false);
                    btnSemprot.setEnabled(false);

                }
            }
        });
        updateData(globalClass.getSettings());

        btnUpdateSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUpdateSetting();
            }
        });
        return orderView;


    }

    public void updateData(ArrayList<Setting> setting){
        for(Setting data:setting){
            Log.d("getSetting",data.getName());
            Log.d("getSetting","kipas"+data.getKipas());

            if(data.getName().equals(spinJenisTanaman.getSelectedItem().toString())){
                btnSemprot.setChecked(data.getSemprot().equals("1")?true:false);
                btnLampu.setChecked(data.getled().equals("1")?true:false);
                btnKipas.setChecked(data.getKipas().equals("1")?true:false);
                btnMode.setChecked(data.getMode().equals("1")?true:false);
                if(btnMode.isChecked()){
                    btnKipas.setEnabled(true);
                    btnLampu.setEnabled(true);
                    btnSemprot.setEnabled(true);
                }
                else{
                    btnKipas.setEnabled(false);
                    btnLampu.setEnabled(false);
                    btnSemprot.setEnabled(false);
                }
                Log.d("data-value",data.getValueSettingHThL());
                updateStringSetting(data.getValueSettingHThL());

            }
        }

    }

    public void updateStringSetting(String value){
        String dataVal[]= value.split("-");
        tvSettingKelembapanTanah.setText("Kelembapan Tanah : "+ dataVal[0]);
        tvSettingKelembapan.setText("Kelembapan : "+ dataVal[2]);
        tvSettingSuhu.setText("Suhu: "+ dataVal[1]);
        tvSettingCahaya.setText("Cahaya : "+ dataVal[3]);
    }

    public void DialogUpdateSetting() {
        android.app.AlertDialog.Builder dialog;
        LayoutInflater inflater;
        View dialogView;
        final EditText txt_suhu, txt_kelembapan,txt_cahaya,txt_kelembapanTanah;

        dialog = new android.app.AlertDialog.Builder(getActivity());
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.mipmap.ic_launcher);
        txt_suhu = dialogView.findViewById(R.id.txt_suhu);
        txt_kelembapan = dialogView.findViewById(R.id.txt_kelembapan);
        txt_cahaya = dialogView.findViewById(R.id.txt_cahaya);
        txt_kelembapanTanah = dialogView.findViewById(R.id.txt_kelembapanTanah);
        dialog.setTitle("Update Data Setting");

        dialog.setPositiveButton("oke", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String suhu = txt_suhu.getText().toString().trim();
                String kelembapan = txt_kelembapan.getText().toString().trim();
                String kelembapanTanah = txt_kelembapanTanah.getText().toString().trim();
                String cahaya = txt_cahaya.getText().toString().trim();
                String value=kelembapanTanah+"-"+suhu+"-"+kelembapan+"-"+cahaya;
                mainActivity.setUpdateData(spinJenisTanaman.getSelectedItem().toString()
                        ,"valueSettingHThL", value);
                updateStringSetting(value);
                dialog.dismiss();
            }
        });
        dialog.setNegativeButton("batal", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
