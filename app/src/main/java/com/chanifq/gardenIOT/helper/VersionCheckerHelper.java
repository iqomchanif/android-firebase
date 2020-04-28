//package com.chanifq.gardenIOT.helper;
//
//import android.app.AlertDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.util.Log;
//
//import com.smartcanteenindonesia.beepartner.models.GlobalClass;
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//import java.io.IOException;
//
//public class VersionCheckerHelper extends AsyncTask<Void, String, String> {
//
//    Context context;
//    String currentVersion;
//    Handler mHandler;
//
//    public VersionCheckerHelper(Context context, Handler mHandler) {
//        this.context = context;
//        this.mHandler=mHandler;
//    }
//
//    @Override
//
//    protected String doInBackground(Void... voids) {
//
//        String newVersion = null;
//        try {
//
//            Document document = Jsoup.connect("https://play.google.com/store/apps/details?id=" + context.getPackageName() + "&hl=en")
//                    .timeout(30000)
//                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
//                    .referrer("http://www.google.com")
//                    .get();
//            if (document != null) {
//                Elements element = document.getElementsContainingOwnText("Current Version");
//                for (Element ele : element) {
//                    if (ele.siblingElements() != null) {
//                        Elements sibElemets = ele.siblingElements();
//                        for (Element sibElemet : sibElemets) {
//                            newVersion = sibElemet.text();
//                        }
//                    }
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return newVersion;
//
//    }
//
//
//    @Override
//
//    protected void onPostExecute(String onlineVersion) {
//        super.onPostExecute(onlineVersion);
//        GlobalClass globalClass= (GlobalClass) context.getApplicationContext();
//        if (onlineVersion != null && !onlineVersion.isEmpty()) {
//            if(!currentVersion.equals(onlineVersion)){
//                Log.d("update_version?","need update!!");
//                globalClass.setVersionIsUpdated("0");
//                alert();
//            }
//            else{
//                Log.d("update_version?","already updated!!");
//                globalClass.setVersionIsUpdated("1");
//                kirimPesan("sudah");
//            }
//        }
//        Log.d("update_version?", "Current version " + currentVersion + "playstore version " + onlineVersion);
//    }
//
//    void kirimPesan(String pesan){
//        GlobalClass globalClass=(GlobalClass) context.getApplicationContext();
//        Message msg = mHandler.obtainMessage();
//        Bundle bundle = new Bundle();
//        bundle.putString("r_data","version_checker");
//        bundle.putString("r_msg",pesan);
//        msg.setData(bundle);
//        mHandler.sendMessage(msg);
//    }
//
//    public void setCurrentVersion(String version){
//        currentVersion=version;
//    }
//    private void alert(){
//        new AlertDialog.Builder(context)
//                .setTitle("Versi telah usang")
//                .setMessage("Update aplikasi sekarang?")
//                .setCancelable(false)
//                .setNegativeButton("nanti", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        kirimPesan("nanti");
//                    }
//                })
//                .setPositiveButton("update sekarang", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface arg0, int arg1) {
//                       // package name of the app
////                        try {
////                            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
////                        } catch (android.content.ActivityNotFoundException anfe) {
////                            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
////                        }
//                        kirimPesan("sekarang");
//
////                        kirimPesan();
//                    }
//                }).create().show();
//    }
//
//
//}
//
