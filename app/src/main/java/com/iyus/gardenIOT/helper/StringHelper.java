package com.iyus.gardenIOT.helper;

public class StringHelper {
    public StringHelper(){

    }

    public String strToRupiah(String nilai){
        char[] c = new char[nilai.length()];
        String rupiah;
        StringBuilder sb = new StringBuilder();
        sb.append("Rp ");
        int a=nilai.length();
        for(int i=0;i<nilai.length();i++){
            sb.append(nilai.charAt(i));
            a--;
            if(a==0){
                sb.append(",-");
            }
            else if(a%3==0){
                sb.append(".");
            }
        }
        String hasil= sb.toString();
        return(hasil);
    }

    public String strToAccount(String nilai){
        char[] c = new char[nilai.length()];
        String rupiah;
        StringBuilder sb = new StringBuilder();
        int a=nilai.length();
        for(int i=0;i<nilai.length();i++){
            sb.append(nilai.charAt(i));
            a--;
            if(a==0){
                sb.append(",-");
            }
            else if(a%3==0){
                sb.append(".");
            }
        }
        String hasil= sb.toString();
        return(hasil);
    }

    public String strLimit(String kataAwal, int awal, int akhir){
        StringBuilder sb= new StringBuilder();
        int flag=0;
        if((awal+akhir)>kataAwal.length()){
            return(kataAwal);
        }
        for(int i=0;i<kataAwal.length();i++){
            if(i<awal){
                sb.append(kataAwal.charAt(i));
                flag=0;
            }
            else if(i>kataAwal.length()-1-akhir){
                if(flag==0){
                    sb.append("...");
                }
                flag=1;
                sb.append(kataAwal.charAt(i));
            }
        }
        return(sb.toString());
    }
}