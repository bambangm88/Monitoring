package com.rsradjakhospital.monitoring.Helper;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;


import com.rsradjakhospital.monitoring.BuildConfig;
import com.rsradjakhospital.monitoring.Model.ResponseData;
import com.rsradjakhospital.monitoring.Model.ResponseEntityNotification;
import com.rsradjakhospital.monitoring.NotificationBadge;
import com.rsradjakhospital.monitoring.R;
import com.rsradjakhospital.monitoring.Service.MyService;
import com.rsradjakhospital.monitoring.api.ApiService;
import com.rsradjakhospital.monitoring.api.Server;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.NOTIFICATION_SERVICE;
import static androidx.constraintlayout.widget.Constraints.TAG;
import static androidx.core.content.ContextCompat.getSystemService;

public class Helper {


    public static final String CHANNEL_1_ID = "channel1";
    public static NotificationManagerCompat notificationManager;


    public static boolean checkNullInputLogin(String username , String Password){

        Boolean check = true ;

        if(username.equals("") || Password.equals("")){

            check = false ;

        }

        return check ;


    }


    public static String chekJenisKelamin(String JenisKelamin){

        String jk = "" ;

        if (JenisKelamin.equals("1")){
            jk = "Perempuan";
        }else{
            jk = "Laki-Laki";
        }

        return jk ;

    }


    public static String convertStatus(String code){

        String status = "" ;

        if (code.equals("01")){
            status = "Terdaftar";
        }else if (code.equals("00")){
            status = "Selesai";
        }else{
            status = "Expired";
        }

        return status ;

    }


    public static String convertHariToDaySession(String day){

        String daySession = "" ;

        if (day.contains("Minggu")){
            daySession = "1";
        }

        if (day.contains("Senin")){
            daySession = "2";
        }

        if (day.contains("Selasa")){
            daySession = "3";
        }

        if (day.contains("Rabu")){
            daySession = "4";
        }

        if (day.contains("Kamis")){
            daySession = "5";
        }

        if (day.contains("Jumat")){
            daySession = "6";
        }

        if (day.contains("Sabtu")){
            daySession = "7";
        }


        return daySession ;

    }


    public static void showNotification(Context context, String title, String messageBody ) {
        Intent    intent = new Intent(context, NotificationBadge.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context,"channelID")
                .setSmallIcon(R.drawable.icon)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon))
                .setContentTitle(title)
                .setContentText(messageBody)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(new long[]{1000, 1000})
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        int notificationId = 1;
        createChannel(notificationManager);
        notificationManager.notify(notificationId, notificationBuilder.build());
    }

    public static void createChannel(NotificationManager notificationManager){
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }
        NotificationChannel channel = new NotificationChannel("channelID","name", NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription("Description");
        notificationManager.createNotificationChannel(channel);


    }






    public static void Notification_( String notifID , String request, Context context ){

        ApiService API = Server.getAPIService();

        List<ResponseEntityNotification> AllEntityNotification = new ArrayList<>();

        AllEntityNotification.clear();


        Call<ResponseData> call = API.notifikasi(notifID, request);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if(response.isSuccessful()) {
                    if (response.body().getDataNotification() != null) {


                        if(response.body().getDataNotification().isEmpty()){

                            // Toast.makeText(mContext, "Tidak Ada Riwayat", Toast.LENGTH_LONG).show();

                        }else{
                            AllEntityNotification.addAll(response.body().getDataNotification()) ;

                            String countBadge = AllEntityNotification.get(0).getCallback() ;

                            if (countBadge.equals("0")){


                            }else{


                                Toast.makeText(context, "NEW MESSAGE FROM MONITORING ", Toast.LENGTH_LONG).show();

                                Helper.showNotification(context, "MONITORING", "NEW MESSAGE") ;
                            }
                        }

                    }else{

                        //Toast.makeText(mContext, "Error Response Data", Toast.LENGTH_LONG).show();
                    }

                }else{

                    //Toast.makeText(mContext, "Error Response Data", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

                //Toast.makeText(mContext, "Internal server error / check your connection", Toast.LENGTH_SHORT).show();
                // Log.e("Error", "onFailure: "+t.getMessage() );
            }
        });
    }


    public static int checkVersion(){

        int versionCode = BuildConfig.VERSION_CODE;
        String versionName = BuildConfig.VERSION_NAME;

        return  versionCode ;

    }


    public static void checkVersionUpdate(Context mContext){

        ApiService API = Server.getAPIService();
        Call<ResponseData> call = API.CheckVersion( "MONITORING");
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {

                if(response.isSuccessful()) {


                    if (response.body().getDataVersion()!= null){

                        if(response.body().getDataVersion().isEmpty()){



                        }else{

                            String versionCodeUpdate = response.body().getDataVersion().get(0).getVersionCode() ;
                            int versionUpdate = Integer.parseInt(versionCodeUpdate);
                            int versionNow = checkVersion() ;


                            if (versionNow < versionUpdate){

                                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                                builder.setMessage("UPDATE APLIKASI VERSION v"+versionUpdate+" TERSEDIA")
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                //do things

                                                final String appPackageName = mContext.getPackageName(); // getPackageName() from Context or Activity object
                                                try {
                                                    mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                                } catch (android.content.ActivityNotFoundException anfe) {
                                                    mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                                }


                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();

                            }


                        }


                    }else{

                    }


                }else

                {
                    Toast.makeText(mContext, "Error Response Data", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

                Toast.makeText(mContext, "Internal server error / check your connection", Toast.LENGTH_SHORT).show();
                Log.e("Error", "onFailure: "+t.getMessage() );
            }
        });
    }




    public  static String dateTimeNow(){


        String time = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

        return time ;

    }
















}
