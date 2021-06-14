package com.rsradjakhospital.monitoring;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Spinner;
import android.widget.Toast;

import com.rsradjakhospital.monitoring.Adapter.DoctorAdapter;
import com.rsradjakhospital.monitoring.Model.ResponseData;
import com.rsradjakhospital.monitoring.Model.ResponseEntityDoctor;
import com.rsradjakhospital.monitoring.api.ApiService;
import com.rsradjakhospital.monitoring.api.Server;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchDokter extends AppCompatActivity {



    ProgressDialog pDialog;
    ApiService API;
    private Spinner spinnerMonitor;


    private RecyclerView rvDoctor;
    private List<ResponseEntityDoctor> AllDoctorList = new ArrayList<>();
    private DoctorAdapter Adapter;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_dokter);

        mContext = this ;
        API = Server.getAPIService();

        rvDoctor = findViewById(R.id.rvRoom_search_doctor);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvDoctor.setLayoutManager(mLayoutManager);
        rvDoctor.setItemAnimator(new DefaultItemAnimator());

        Adapter = new DoctorAdapter(this, AllDoctorList);

        pDialog = new ProgressDialog(SearchDokter.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Memuat...");

        getDoctor();


    }



    private void getDoctor(){


        pDialog.show();


        Call<ResponseData> call = API.fetchDoctor();
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if(response.isSuccessful()) {
                    if (response.body().getDataDoctor() != null) {


                        if(!response.body().getDataDoctor().isEmpty()){

                            pDialog.cancel();
                            AllDoctorList.addAll(response.body().getDataDoctor());
                            rvDoctor.setAdapter(new DoctorAdapter(mContext, AllDoctorList));
                            Adapter.notifyDataSetChanged();

                        }else{

                            pDialog.cancel();
                            finish();
                            Toast.makeText(mContext, "Doctor Tidak Ditemukan", Toast.LENGTH_LONG).show();

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
                finish();
                Toast.makeText(mContext, "Internal server error / check your connection", Toast.LENGTH_SHORT).show();
                Log.e("Error", "onFailure: "+t.getMessage() );
            }
        });
    }




}
