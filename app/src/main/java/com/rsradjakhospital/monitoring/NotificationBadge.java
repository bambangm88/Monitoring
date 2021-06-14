package com.rsradjakhospital.monitoring;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.rsradjakhospital.monitoring.Adapter.NotificationAdapter;
import com.rsradjakhospital.monitoring.Model.ResponseData;
import com.rsradjakhospital.monitoring.Model.ResponseEntityNotification;
import com.rsradjakhospital.monitoring.api.ApiService;
import com.rsradjakhospital.monitoring.api.Server;
import com.rsradjakhospital.monitoring.sessionManager.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationBadge extends AppCompatActivity {

    private RecyclerView rvRiwayat;

    private NotificationAdapter Adapter;

    private Context mContext;

    public List<ResponseEntityNotification> AllEntityNotification = new ArrayList<>();

    public List<ResponseEntityNotification> AllEntityNotificationUpdate = new ArrayList<>();

    ApiService API;

    ProgressDialog pDialog;

    SessionManager sessionManager ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        mContext = this ;
        API = Server.getAPIService();

        pDialog = new ProgressDialog(NotificationBadge.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Memuat...");

        sessionManager = new SessionManager(mContext);


        rvRiwayat = findViewById(R.id.rvRoom_notification);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvRiwayat.setLayoutManager(mLayoutManager);
        rvRiwayat.setItemAnimator(new DefaultItemAnimator());


        Notification(sessionManager.getNotifID() , "GET");








    }



    private void Notification( String notifID , String request){

        AllEntityNotification.clear();

        pDialog.show();
        Call<ResponseData> call = API.notifikasi( notifID , request);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if(response.isSuccessful()) {
                    if (response.body().getDataNotification() != null) {


                        if(response.body().getDataNotification().isEmpty()){

                            pDialog.cancel();
                            Toast.makeText(mContext, "Tidak Ada Notifikasi", Toast.LENGTH_LONG).show();
                            finish();

                        }else{
                            pDialog.cancel();

                            AllEntityNotification.addAll(response.body().getDataNotification()) ;

                            Adapter = new NotificationAdapter(NotificationBadge.this, AllEntityNotification);

                            rvRiwayat.setAdapter(new NotificationAdapter(mContext, AllEntityNotification));
                            Adapter.notifyDataSetChanged();


                            UpdateNotification(sessionManager.getNotifID());



                        }



                    }else{
                        pDialog.cancel();
                        finish();
                        Toast.makeText(mContext, "Error Response Data", Toast.LENGTH_LONG).show();
                    }

                }else{
                    pDialog.cancel();
                    finish();

                    Toast.makeText(mContext, "Error Response Data", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

                pDialog.cancel();

                Toast.makeText(mContext, "Internal server error / check your connection", Toast.LENGTH_SHORT).show();
                Log.e("Error", "onFailure: "+t.getMessage() );
            }
        });
    }



    private void UpdateNotification( String notifID){

        AllEntityNotificationUpdate.clear();

        Call<ResponseData> call = API.UpdateNotifikasi(notifID);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if(response.isSuccessful()) {
                    if (response.body().getDataNotification() != null) {


                        if(response.body().getDataNotification().isEmpty()){


                           //Toast.makeText(mContext, "Tidak Ada Notifikasi", Toast.LENGTH_LONG).show();


                            //Notification(sessionManager.getNotifID() , "GET");


                        }else{


                            AllEntityNotificationUpdate.addAll(response.body().getDataNotification()) ;

                            //showNotification(MainActivity.this, "ts", "tes") ;



                        }

                    }else{

                        Toast.makeText(mContext, "Error Response Data", Toast.LENGTH_LONG).show();


                    }

                }else{


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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        UpdateNotification(sessionManager.getNotifID());
    }


}