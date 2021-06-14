package com.rsradjakhospital.monitoring.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.view.View;
import android.widget.Toast;

import com.rsradjakhospital.monitoring.Helper.Helper;
import com.rsradjakhospital.monitoring.MainActivity;
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

public class MyService extends Service {


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate() {

        SessionManager sessionManager =  new SessionManager(MyService.this) ;

        Helper.Notification_(sessionManager.getNotifID(),"Count",MyService.this);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

            SessionManager sessionManager =  new SessionManager(MyService.this) ;

        Helper.Notification_(sessionManager.getNotifID(),"Count",MyService.this);

        return START_STICKY;
    }















}
