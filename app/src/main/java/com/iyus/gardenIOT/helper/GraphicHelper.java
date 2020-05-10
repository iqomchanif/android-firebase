package com.iyus.gardenIOT.helper;//package com.smartcanteenindonesia.beepartner.helper;
//
//import android.graphics.Color;
//import android.graphics.DashPathEffect;
//
//import com.github.mikephil.charting.charts.BarChart;
//import com.github.mikephil.charting.charts.LineChart;
//import com.github.mikephil.charting.components.LimitLine;
//import com.github.mikephil.charting.components.XAxis;
//import com.github.mikephil.charting.components.YAxis;
//import com.github.mikephil.charting.data.Entry;
//import com.github.mikephil.charting.data.LineData;
//import com.github.mikephil.charting.data.LineDataSet;
//import com.github.mikephil.charting.formatter.ValueFormatter;
//import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
//import com.smartcanteenindonesia.beepartner.R;
//import com.smartcanteenindonesia.beepartner.fragments.GraphicFragment;
//
//import java.util.ArrayList;
//
//public class GraphicHelper {
//    int kind;
//    BarChart bChart;
//    LineChart lChart;
//    public GraphicHelper(BarChart barChart) {
//        kind=1;
//        bChart=barChart;
//        barChart.setTouchEnabled(true);
//        barChart.setPinchZoom(true);
//    }
//    public GraphicHelper(LineChart lineChart) {
//        kind=2;
//        lChart=lineChart;
//        lineChart.setTouchEnabled(true);
//        lineChart.setPinchZoom(true);
//    }
//
//
//
//
//
//    public void renderData() {
//        LimitLine llXAxis = new LimitLine(10f, "Index 10");
//        llXAxis.setLineWidth(4f);
//        llXAxis.enableDashedLine(10f, 10f, 0f);
//        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
//        llXAxis.setTextSize(10f);
//
//        ValueFormatter xAxisFormatter = new GraphicFragment.DayAxisValueFormatter(lChart);
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
//        setData();
//    }
//
//public class DayAxisValueFormatter extends ValueFormatter {
//    private final LineChart chart;
//    public DayAxisValueFormatter(LineChart chart) {
//        this.chart = chart;
//    }
//    @Override
//    public String getFormattedValue(float value) {
//        int intValue= (int) value;
//        String bulan="";
//        switch(intValue){
//            case 1:
//                bulan=("jan");
//                break;
//            case 2:
//                bulan=("feb");
//                break;
//            case 3:
//                bulan=("mar");
//                break;
//
//        }
//        return(bulan);
//    }
//}
//    private void setData() {
//
//
//
//        LineDataSet set1;
//        if (mChart.getData() != null &&
//                mChart.getData().getDataSetCount() > 0) {
//            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
//            set1.setValues(getValue());
//            mChart.getData().notifyDataChanged();
//            mChart.notifyDataSetChanged();
//        } else {
//            set1 = new LineDataSet(getValue(), "Bakso");
//            set1.setDrawIcons(false);
////            set1.enableDashedLine(10f, 5f, 0f);
////            set1.enableDashedHighlightLine(10f, 5f, 0f);
//            set1.setColor(Color.DKGRAY);
//            set1.setCircleColor(Color.DKGRAY);
//            set1.setLineWidth(1f);
//            set1.setCircleRadius(3f);
//            set1.setDrawCircleHole(false);
//            set1.setValueTextSize(9f);
//            set1.setDrawFilled(false);
//            set1.setFormLineWidth(1f);
//            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
//            set1.setFormSize(15.f);
//            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
//
//
//            dataSets.add(set1);
//            set1 = new LineDataSet(getValue(), "Soto");
//            set1.setDrawIcons(false);
////            set1.enableDashedLine(10f, 5f, 0f);
////            set1.enableDashedHighlightLine(10f, 5f, 0f);
//            set1.setColor(Color.RED);
//            set1.setCircleColor(Color.RED);
//            set1.setLineWidth(1f);
//            set1.setCircleRadius(3f);
//            set1.setDrawCircleHole(false);
//            set1.setValueTextSize(9f);
//            set1.setDrawFilled(false);
//            set1.setFormLineWidth(1f);
//            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
//            set1.setFormSize(15.f);
//            dataSets.add(set1);
////            if (Utils.getSDKInt() >= 18) {
////                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_blue);
////                set1.setFillDrawable(drawable);
////            } else {
////                set1.setFillColor(Color.DKGRAY);
////            }
//
//            ArrayList<String> xAxisName= new ArrayList<>();
//            xAxisName.add("januari");
//            xAxisName.add("februari");
//            xAxisName.add("maret");
//            xAxisName.add("april");
//            xAxisName.add("mei");
//
//            LineData data = new LineData(dataSets);
//            mChart.setData(data);
//        }
//    }
//
//    private ArrayList<Entry> getValue(){
//        ArrayList<Entry> values = new ArrayList<>();
//        values.add(new Entry(1,(int)(Math.random()*100)));
//        values.add(new Entry(2,(int)(Math.random()*100)));
//        values.add(new Entry(3,(int)(Math.random()*100)));
//        values.add(new Entry(4,(int)(Math.random()*100)));
//        values.add(new Entry(5,(int)(Math.random()*100)));
//        values.add(new Entry(6,(int)(Math.random()*100)));
//        values.add(new Entry(7,(int)(Math.random()*100)));
//        values.add(new Entry(8,(int)(Math.random()*100)));
//        values.add(new Entry(9,(int)(Math.random()*100)));
//        values.add(new Entry(10,(int)(Math.random()*100)));
//
//        return(values);
//
//    }
//}
