package com.rsradjakhospital.monitoring;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.rsradjakhospital.monitoring.api.ApiService;
import com.rsradjakhospital.monitoring.api.Server;
import com.rsradjakhospital.monitoring.sessionManager.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VersionActivity extends AppCompatActivity {

    SessionManager session;
    RelativeLayout rlProgress;
    ApiService API;
    TextView btnCekVersi, namaApp;

    SwipeRefreshLayout refresh;

    private void findElement(){
        API = Server.getAPIService();
        session = new SessionManager(getApplicationContext());
        rlProgress = findViewById(R.id.rlprogress);

        btnCekVersi = findViewById(R.id.btnCekVersi);
        namaApp = findViewById(R.id.namaApp);

        Toolbar toolbar = findViewById(R.id.toolbar_pay);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int versionCode = BuildConfig.VERSION_CODE;
        String versionName = BuildConfig.VERSION_NAME;

        namaApp.setText("v"+versionName);

        Check_version();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
      //  startActivity(new Intent(VersionActivity.this, Pengaturan.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version);
        findElement();
        setListener();
    }


    private void setListener() {

        btnCekVersi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Check_version();
            }
        });
    }

    public void Check_version() {
        VersionChecker versionChecker = new VersionChecker(this);
        versionChecker.execute();
    }



    public void showProgress(boolean bool) {
        if (bool) {
            rlProgress.setVisibility(View.VISIBLE);
        } else {
            rlProgress.setVisibility(View.GONE);
        }
    }

    //homeback
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                //Write your logic here

                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }






}