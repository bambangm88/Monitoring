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
import android.widget.TextView;
import android.widget.Toast;

import com.rsradjakhospital.monitoring.Adapter.CountRoomAdapter;
import com.rsradjakhospital.monitoring.Model.ResponseData;
import com.rsradjakhospital.monitoring.Model.ResponseEntityCountRoomByCategory;
import com.rsradjakhospital.monitoring.api.ApiService;
import com.rsradjakhospital.monitoring.api.Server;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountRoomActivity extends AppCompatActivity {



    ProgressDialog pDialog;
    ApiService API;
    private Spinner spinnerMonitor;


    private RecyclerView rvDoctor;
    private List<ResponseEntityCountRoomByCategory> AllRoomList = new ArrayList<>();
    private CountRoomAdapter Adapter;

    private Context mContext;

    public static String TAG_STATUS_CATEGORY = "";
    public static String TAG_STATUS_CATEGORY_COUNT = "";

    TextView tvJumlahTotal ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_room);



        mContext = this ;
        API = Server.getAPIService();

        rvDoctor = findViewById(R.id.rvRoom_count_room);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvDoctor.setLayoutManager(mLayoutManager);
        rvDoctor.setItemAnimator(new DefaultItemAnimator());

        tvJumlahTotal = findViewById(R.id.total_room);

        Adapter = new CountRoomAdapter(this, AllRoomList);

        pDialog = new ProgressDialog(CountRoomActivity.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Memuat...");

        getCountRoom(TAG_STATUS_CATEGORY);

        tvJumlahTotal.setText("TOTAL ROOM "+TAG_STATUS_CATEGORY+" : "+TAG_STATUS_CATEGORY_COUNT);


    }



    private void getCountRoom(String status){


        pDialog.show();


        Call<ResponseData> call = API.fetchCountAllRoomByCategory(status);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if(response.isSuccessful()) {
                    if (response.body().getDataCountByCategory() != null) {


                        if(!response.body().getDataCountByCategory().isEmpty()){

                            pDialog.cancel();
                            AllRoomList.addAll(response.body().getDataCountByCategory());
                            rvDoctor.setAdapter(new CountRoomAdapter(mContext, AllRoomList));
                            Adapter.notifyDataSetChanged();

                        }else{

                            pDialog.cancel();
                            finish();
                            Toast.makeText(mContext, "Room Tidak Ditemukan", Toast.LENGTH_LONG).show();

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
