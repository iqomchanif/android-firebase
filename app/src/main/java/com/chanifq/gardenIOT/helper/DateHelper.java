package com.chanifq.gardenIOT.helper;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateHelper {
    public DateHelper(){

    }
    public String getDateTomorrow(){
        int batas;
        Calendar c = Calendar.getInstance();
        int day=c.get(Calendar.DAY_OF_MONTH)+1;
        int month=c.get(Calendar.MONTH); //mulai dari 0
        int year=c.get(Calendar.YEAR);
        year=year-1900;
        if(month==1){
            if(year%400==0||(year%4==0&&year%100!=0)){ //kabisat
                batas=29;
            } else{ batas=28; }
        }
        else if(month<=6){
            if(month%2==1){batas=30;}
            else{batas=31;}
        }
        else{
            if(month%2==1){batas=31;}
            else{batas=30;}
        }

        Log.d("input_helper",day+","+month+","+year);

        if(day>batas){
            day=1;
            month=month+1;
            if(month>12){
                month=1;
                year=year+1;
            }
        }
        Log.d("input_helper2",day+","+month+","+year);
        Date d= new Date(year,month,day);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDateToday = df.format(d);
        return(formattedDateToday);

    }

    public String getDateToday(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDateToday = df.format(c);
        return(formattedDateToday);

    }

    public boolean isThisMonth(String tgl){
        Calendar c = Calendar.getInstance();
        int month=c.get(Calendar.MONTH)+1;
        String month_ = (tgl.split(" ")[0]).split("-")[1];
        Log.d("date_isThisMonth",Integer.parseInt(month_) + "vs" +month);
        if(month== Integer.parseInt(month_)){
            return(true);
        }
        else{
            return(false);
        }
    }

    public boolean isToday(String tgl){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDateToday = df.format(c);
        tgl=tgl.split(" ")[0];
        if(tgl.equals(formattedDateToday)){
            return(true);
        }
        else{
            return(false);
        }
    }
    public boolean isTomorrow(String tgl){
        String formattedDate =getDateTomorrow();
        tgl=tgl.split(" ")[0];
        if(tgl.equals(formattedDate)){
            return(true);
        }
        else{
            return(false);
        }
    }

    public String getDDMMYYYformat(String YYYMMDD){
        String date[]= YYYMMDD.split("-");
        String hasil= date[2] + "-" + date[1] +"-"+date[0];
        return(hasil);
    }
    public String getIndoFormat(String tgl){
        String date[]=tgl.split("-");
        String bulan="";
        switch(Integer.parseInt(date[1])){
            case 1: bulan= "jan"; break;
            case 2: bulan= "feb"; break;
            case 3: bulan= "mar"; break;
            case 4: bulan= "apr"; break;
            case 5: bulan= "mei"; break;
            case 6: bulan= "jun"; break;
            case 7: bulan= "jul"; break;
            case 8: bulan= "agu"; break;
            case 9: bulan= "sep"; break;
            case 10: bulan= "okt"; break;
            case 11: bulan= "nov"; break;
            case 12: bulan= "des"; break;
        }
        String hasil= date[2] + " " + bulan +" "+date[0];
        return(hasil);
    }


    public String getStringHari(int index){
        String hari="senin";
        switch(index){
            case 0: hari="senin"; break;
            case 1: hari="selasa"; break;
            case 2: hari="rabu"; break;
            case 3: hari="kamis"; break;
            case 4: hari="jumat"; break;
            case 5: hari="sabtu"; break;
            case 6: hari="minggu"; break;
        }
        return(hari);
    }
    public int isBiggerDate(String a, String b){
        String year1=a.split("-")[2];
        String year2=b.split("-")[2];
        if(Integer.parseInt(year1)>Integer.parseInt(year2))
            return(1);
        else if(Integer.parseInt(year1)<Integer.parseInt(year2))
            return(0);
        String month1=a.split("-")[2];
        String month2=b.split("-")[2];
        if(Integer.parseInt(month1)>Integer.parseInt(month2))
            return(1);
        else if(Integer.parseInt(month1)<Integer.parseInt(month2))
            return(0);
        String tgl1= a.split("-")[0];
        String tgl2=a.split("-")[0];
        if(Integer.parseInt(tgl1)>Integer.parseInt(tgl2))
            return(1);
        else if(Integer.parseInt(tgl1)<Integer.parseInt(tgl2))
            return(0);

        return(1);
    }
    public int isLowerDate(String a, String b){
        String year1=a.split("-")[2];
        String year2=b.split("-")[2];
        if(Integer.parseInt(year1)>Integer.parseInt(year2))
            return(0);
        else if(Integer.parseInt(year1)<Integer.parseInt(year2))
            return(1);
        String month1=a.split("-")[2];
        String month2=b.split("-")[2];
        if(Integer.parseInt(month1)>Integer.parseInt(month2))
            return(0);
        else if(Integer.parseInt(month1)<Integer.parseInt(month2))
            return(1);
        String tgl1= a.split("-")[0];
        String tgl2=a.split("-")[0];
        if(Integer.parseInt(tgl1)>Integer.parseInt(tgl2))
            return(0);
        else if(Integer.parseInt(tgl1)<Integer.parseInt(tgl2))
            return(1);

        return(1);
    }
    public String getDateYesterday(){
        Calendar c = Calendar.getInstance();
        int day=c.get(Calendar.DAY_OF_MONTH)-1;
        int month=c.get(Calendar.MONTH); //mulai dari 0
        int year=c.get(Calendar.YEAR); //ofset 1900
        year=year-1900;

        if(day<=0){
            month=month-1;
            if(month<=0){
                month=12;
                year=year-1;
            }
            if(month==1){
                if(year%400==0||(year%4==0&&year%100!=0)){ //kabisat
                    day=29;
                } else{ day=28; }
            }
            else if(month<=6){
                if(month%2==1){day=30;}
                else{day=31;}
            }
            else{
                if(month%2==1){day=31;}
                else{day=30;}
            }
        }


        Date d= new Date(year,month,day);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDateToday = df.format(d);
        return(formattedDateToday);

    }



}
