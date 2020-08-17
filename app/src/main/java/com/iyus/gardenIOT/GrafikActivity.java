package com.iyus.gardenIOT;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.iyus.gardenIOT.helper.DateHelper;
import com.iyus.gardenIOT.model.DataPlant;
import com.iyus.gardenIOT.model.GlobalClass;
import com.iyus.gardenIOT.model.ListPlant;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class GrafikActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference databaseReference;
    DatabaseReference databaseReferenceHistoryGarden;
    Button btnGarden, btnPlant,btnKey1;
    EditText editText;
    String alamat;
    Button btnFilter, btnFilter2, btnGOFilter;
    LineChart chartTemp, chartHumi, chartLight, chartHumiTanah;
    LineData lineData;
    ArrayList<String> dataFirebase;
    GlobalClass globalClass;
    ArrayList<Entry> valuesHumi;
    ArrayList<Entry> valuesTemp;
    ArrayList<Entry> valuesLight;
    ArrayList<Entry> valuesHumiTanah;
    ArrayList<ListPlant> listPlants;
    ArrayList<String> valuesClocks;
    Calendar myCalendar;
    TextView tvTanggalAwal, tvTanggalAkhir;
    Boolean filterStatus;
    String tglAwal, tglAkhir;
    ScrollView sv;
    String lastCreated;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafik);
        database = FirebaseDatabase.getInstance();
        dataFirebase = new ArrayList<String>();
        databaseReference = database.getReference().child("gardeniot-c4aed").child("Plants");
        chartHumi = findViewById(R.id.chartHumidity);
        chartHumiTanah = findViewById(R.id.chartHumidityTanah);
        chartTemp = findViewById(R.id.chartTemperature);
        chartLight = findViewById(R.id.chartLight);
        btnFilter = findViewById(R.id.btnfilter);
        btnFilter2 = findViewById(R.id.btnfilter2);
        myCalendar = Calendar.getInstance();
        sv= findViewById(R.id.sv);
        tvTanggalAkhir = findViewById(R.id.tvTanggalAkhir);
        tvTanggalAwal = findViewById(R.id.tvTanggalAwal);
        DateHelper dateHelper= new DateHelper();
//        btnKey1=findViewById(R.id.btnKey1);
        tglAwal=dateHelper.getDDMMYYYformat( dateHelper.getDateToday());
        tglAkhir=dateHelper.getDDMMYYYformat( dateHelper.getDateToday());


        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(GrafikActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, month);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String formatTanggal = "dd-MM-yyyy";
                        SimpleDateFormat sdf = new SimpleDateFormat(formatTanggal);
                        Log.d("filter", sdf.format(myCalendar.getTime()));
                        tvTanggalAwal.setText(sdf.format(myCalendar.getTime()));
                        tglAwal=sdf.format(myCalendar.getTime());

                    }
                },
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                updateGrafik();

            }
        });

        btnFilter2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(GrafikActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, month);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String formatTanggal = "dd-MM-yyyy";
                        SimpleDateFormat sdf = new SimpleDateFormat(formatTanggal);
                        Log.d("filter", sdf.format(myCalendar.getTime()));
                        tvTanggalAkhir.setText(sdf.format(myCalendar.getTime()));
                        tglAkhir=sdf.format(myCalendar.getTime());
                    }
                },
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                updateGrafik();
            }
        });
        filterStatus = false;
        btnGOFilter = findViewById(R.id.btnGOFilter);
        btnGOFilter.setText("filter state: off");
        btnGOFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filterStatus) {
                    filterStatus = false;
                    btnGOFilter.setText("filter state: off");
                    tvTanggalAkhir.setText("-");
                    tvTanggalAwal.setText("-");
                } else {
                    filterStatus = true;
                    btnGOFilter.setText("filter state: on");

                    tvTanggalAwal.setText(tglAwal);
                    tvTanggalAkhir.setText(tglAkhir);
                }

                updateGrafik();
            }
        });
        chartHumi.setTouchEnabled(true);
        chartHumiTanah.setTouchEnabled(true);
        chartTemp.setTouchEnabled(true);
        chartLight.setTouchEnabled(true);

        chartHumi.setPinchZoom(true);
        chartHumiTanah.setPinchZoom(true);
        chartTemp.setPinchZoom(true);
        chartLight.setPinchZoom(true);

        globalClass = (GlobalClass) getApplicationContext();

        valuesHumi = new ArrayList<>();
        valuesLight = new ArrayList<>();
        valuesTemp = new ArrayList<>();
        valuesHumiTanah= new ArrayList<>();
        valuesClocks= new ArrayList<>();
        listPlants = new ArrayList<>();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(globalClass.getModeNamePlant());
        toolbar.setSubtitle("last upload on ");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //        MyMarkerView mv = new MyMarkerView(getApplicationContext(), R.layout.custom_marker_view);
        //        mv.setChartView(mChart);
        //        mChart.setMarker(mv);
        updateGrafik();
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String created_at = null;

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
                        Log.d("plant_value",plant.toString());
//                        Log.d("firebase_plants_value","humi:"+ plant.getHumidity());
//                        Log.d("firebase_plants_value","temp:"+ plant.getTemperature());
//                        Log.d("firebase_plants_value","light:"+plant.getLight());

                        String temp = plant.getTemperature();
                        String humi = plant.getHumidity();
                        String light = plant.getLight();
                        String humiTanah= plant.getHumidityTanah();
                        String createdAt = plant.getCreated_at();
                        String clockAt=plant.getClock_at();
                        created_at= createdAt + " "+clockAt.split("\\.")[0];
                        DataPlant itemPlant = new DataPlant(String.valueOf(i), name, humiTanah, humi, temp, light, createdAt,clockAt);
                        i = i + 1;
                        arrayPlant.add(itemPlant);
                    }
                    iPlant++;
                    ListPlant list = new ListPlant(iPlant, name, arrayPlant);

                    listPlants.add(list);
                }
                lastCreated= created_at;
                toolbar.setSubtitle("last on "+ lastCreated );
                globalClass.setListPlants(listPlants);
                updateGrafik();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("firebase", "Failed to read value.", error.toException());
            }
        });

    }


    public void updateGrafik() {
        if (filterStatus) {
           Log.d("updategrafik",tglAwal+";"+tglAkhir);
            getValue(tglAwal, tglAkhir);
        }
        else {
            Log.d("updategrafik","malah nag kene");

            getValue();
        }
        Log.d("data-entry","UPDATEEE!!!!");
        for(Entry entry: valuesHumi){
            Log.d("data-entry",""+entry.getX()+","+entry.getY());
        }
        renderData(chartHumi, valuesHumi);
        renderData(chartTemp, valuesTemp);
        renderData(chartLight, valuesLight);
        renderData(chartHumiTanah,valuesHumiTanah);

    }


    public void renderData(LineChart lineChart, ArrayList<Entry> value) {
        LimitLine llXAxis = new LimitLine(10f, "Index 10");
        llXAxis.setLineWidth(4f);
        llXAxis.enableDashedLine(10f, 10f, 0f);
        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        llXAxis.setTextSize(10f);

//        LimitLine llXAxis = new LimitLine(10f, "Index 10");
//        llXAxis.setLineWidth(4f);
//        llXAxis.enableDashedLine(10f, 10f, 0f);
//        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
//        llXAxis.setTextSize(10f);
//
//        ValueFormatter xAxisFormatter = new  DayAxisValueFormatter(mChart);
//
//        XAxis xAxis = mChart.getXAxis();
//        xAxis.enableGridDashedLine(10, 10, 0);
//        xAxis.setAxisMaximum(10);
//        xAxis.setAxisMinimum(0);
//        xAxis.setDrawLimitLinesBehindData(true);
//        xAxis.setLabelCount(10);
//        xAxis.setValueFormatter(xAxisFormatter);
//        YAxis leftAxis = mChart.getAxisLeft();
//        leftAxis.removeAllLimitLines();
//        leftAxis.setAxisMaximum(100f);
//        leftAxis.setAxisMinimum(0f);
//        leftAxis.enableGridDashedLine(10f, 10f, 0f);
//        leftAxis.setDrawZeroLine(false);
//        leftAxis.setDrawLimitLinesBehindData(false);
//        mChart.getDescription().setText("Grafik Item Transaksi");
//
//        mChart.getAxisRight().setEnabled(false);
//        setData(mChart,value);

        ValueFormatter xAxisFormatter = new DayAxisValueFormatter(lineChart);
        float maxData = 0;
        for (Entry data : value) {
            if (data.getY() > maxData)
                maxData = data.getY();
        }
        float yAxisMax = (float) (maxData * 1.6);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setLabelRotationAngle(-45);
        xAxis.enableGridDashedLine(10, 10, 0);
        xAxis.setAxisMaximum(value.size());
        xAxis.setAxisMinimum(0);
        xAxis.setDrawLimitLinesBehindData(true);
        xAxis.setLabelCount(10);
        xAxis.setValueFormatter(xAxisFormatter);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.removeAllLimitLines();
        leftAxis.setAxisMaximum(yAxisMax);
        leftAxis.setAxisMinimum(0f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);
        leftAxis.setDrawLimitLinesBehindData(false);
//        lineChart.getDescription().setText("Grafik Item Transaksi");
        lineChart.getDescription().setEnabled(false);
        lineChart.getAxisRight().setEnabled(false);
        setData(lineChart, value);
        lineChart.notifyDataSetChanged();
        lineChart.invalidate();
    }

    public class DayAxisValueFormatter extends ValueFormatter {
        private final LineChart chart;

        public DayAxisValueFormatter(LineChart chart) {
            this.chart = chart;
        }

        @Override
        public String getFormattedValue(float value) {
            int intValue = (int) value;
            try {
                return valuesClocks.get((int) value); // xVal is a string array
            }
            catch (Exception e){
                return String.valueOf(value);
            }
        }
    }

    private void setData(LineChart lineChart, ArrayList<Entry> value) {


        LineDataSet set1;
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        set1 = new LineDataSet(value, "");
        set1.setDrawIcons(false);
//            set1.enableDashedLine(10f, 5f, 0f);
//            set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(Color.RED);
        set1.setCircleColor(Color.RED);
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(false);
        set1.setFormLineWidth(1f);
        set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        set1.setFormSize(15.f);
        dataSets.add(set1);

//            if (Utils.getSDKInt() >= 18) {
//                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_blue);
//                set1.setFillDrawable(drawable);
//            } else {
//                set1.setFillColor(Color.DKGRAY);
//            }

        ArrayList<String> xAxisName = new ArrayList<>();
        LineData data = new LineData(dataSets);
        lineChart.setData(data);

    }

    private void getValue() {

        int i = 0;
//        for(String data: dataFirebase){
//            values.add(new Entry(i, Float.parseFloat(data.split(";")[type])));
//            i=i+1;
//        }

        valuesTemp.clear();
        valuesLight.clear();
        valuesHumi.clear();
        valuesClocks.clear();
        valuesHumiTanah.clear();
        listPlants = globalClass.getListPlants();
        ArrayList<ListPlant> listPlant = globalClass.getListPlants();
        for (ListPlant list : listPlant) {
            if (list.getName().equals(globalClass.getModeNamePlant())) {
                for (DataPlant data : list.getPlants()) {
                    try{
                        String clock= data.getClock_at();
                        String hour=data.getCreated_at()+' '+clock.split(":")[0] +':'+clock.split(":")[1];
                        valuesTemp.add(new Entry(i, Integer.parseInt(data.getTemperature())));
                        valuesHumi.add(new Entry(i, Integer.parseInt(data.getHumidity())));
                        valuesLight.add(new Entry(i, Integer.parseInt(data.getLight())));
                        valuesHumiTanah.add(new Entry(i,Integer.parseInt(data.getHumidityTanah())));
                        valuesClocks.add(hour);
                        i = i + 1;
                        Log.d("value_axis_humi","" +Integer.parseInt(data.getHumidity()));
                    }
                    catch (NullPointerException e){

                    }

                }
                Log.d("value-axis","max="+i);
            }
        }


    }

    private void getValue(String tglawal, String tglakhir) {

        int i = 0;
//        for(String data: dataFirebase){
//            values.add(new Entry(i, Float.parseFloat(data.split(";")[type])));
//            i=i+1;
//        }

        valuesTemp.clear();
        valuesLight.clear();
        valuesHumi.clear();
        valuesHumiTanah.clear();
        valuesClocks.clear();
        listPlants = globalClass.getListPlants();
        ArrayList<ListPlant> listPlant = globalClass.getListPlants();
        for (ListPlant list : listPlant) {
            if (list.getName().equals(globalClass.getModeNamePlant())) {
                for (DataPlant data : list.getPlants()) {
                    try{
                        DateHelper dateHelper = new DateHelper();
                        String tgl = dateHelper.getDDMMYYYformat(data.getCreated_at());
                        Log.d("gettgl", tgl);
                        if (dateHelper.isBiggerDate(tgl, tglawal) == 1 && dateHelper.isLowerDate(tgl, tglakhir) == 1) {
                            Log.d("gettgl_banding_masuk", tgl + ";" + tglawal + ";" + tglakhir);

                            valuesTemp.add(new Entry(i, Integer.parseInt(data.getTemperature())));
                            valuesHumi.add(new Entry(i, Integer.parseInt(data.getHumidity())));
                            valuesLight.add(new Entry(i, Integer.parseInt(data.getLight())));
                            valuesHumiTanah.add(new Entry(i,Integer.parseInt(data.getHumidityTanah())));

                            i = i + 1;

                        }
                    }
                    catch(NullPointerException e){

                    }


                }
            }
        }


    }


}
