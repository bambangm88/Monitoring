package com.rsradjakhospital.monitoring;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.rsradjakhospital.monitoring.Adapter.SearchTransferRuanganAdapter;
import com.rsradjakhospital.monitoring.Model.ResponseData;
import com.rsradjakhospital.monitoring.Model.ResponseEntityRoom;
import com.rsradjakhospital.monitoring.Model.ResponseMonitor;
import com.rsradjakhospital.monitoring.api.ApiService;
import com.rsradjakhospital.monitoring.api.Server;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.layout.simple_spinner_item;
import static com.rsradjakhospital.monitoring.DetailRoom.TAG_ROOM_HEADER;

public class TransferCariRoomActivity extends AppCompatActivity {


    ProgressDialog pDialog;
    ApiService API;
    private Spinner spinnerMonitor;


    private RecyclerView rvRoom;

    private Context mContext;
    private List<ResponseEntityRoom> AllPaymentList = new ArrayList<>();
    private SearchTransferRuanganAdapter Adapter;


    private ArrayList<String> arrayMonitor = new ArrayList<String>();
    private List<ResponseMonitor> AllMonitorList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_cari_room);


        mContext = this ;
        API = Server.getAPIService();
        spinnerMonitor = findViewById(R.id.spMonitor);


        rvRoom = findViewById(R.id.rvRoom_search);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvRoom.setLayoutManager(mLayoutManager);
        rvRoom.setItemAnimator(new DefaultItemAnimator());

        Adapter = new SearchTransferRuanganAdapter(this, AllPaymentList);

        pDialog = new ProgressDialog(TransferCariRoomActivity.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Memuat...");


        //fetchMonitor();



//        spinnerMonitor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                String category = ""+spinnerMonitor.getSelectedItem() ;
////                String floor = ""+spinnerFloor.getSelectedItem() ;
////                String kelas = ""+spinnerKelas.getSelectedItem() ;
//
//
//                if(category.equals("-- Category --") ) {
//
//                    Toast.makeText(TransferCariRoomActivity.this,"Please Select Category", Toast.LENGTH_SHORT).show();
//                }else{
//                    AllPaymentList.clear();
//                    Adapter.notifyDataSetChanged();
//
//                    getListRuangan(arrayMonitor.get(position));
//                    Toast.makeText(TransferCariRoomActivity.this, arrayMonitor.get(position), Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });



        getListRuangan(TAG_ROOM_HEADER);





    }


    private void fetchMonitor() {

        pDialog.show();

        API.fetchMonitor().enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {

                if (response.isSuccessful()){


                    pDialog.cancel();

                    AllMonitorList.addAll(response.body().getData());

                    arrayMonitor.add("-- Category --") ;

                    for(ResponseMonitor model : AllMonitorList) {

                        arrayMonitor.add(model.getMonitor()) ;
                    }

                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(TransferCariRoomActivity.this, simple_spinner_item,arrayMonitor );
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                    spinnerMonitor.setAdapter(spinnerArrayAdapter);


                }else {
                    pDialog.cancel();
                    Toast.makeText(TransferCariRoomActivity.this, "Cek koneksi internet anda", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                pDialog.cancel();
                Toast.makeText(TransferCariRoomActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }




    private void getListRuangan(String category){



        String A = "" ;
        String B = "" ;

        pDialog.show();


        Call<ResponseData> call = API.FetchRoom(category,A,B);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData>  response) {
                if(response.isSuccessful()) {
                    if (response.body().getDataRoom() != null) {


                        if(!response.body().getDataRoom().isEmpty()){

                            pDialog.cancel();
                            AllPaymentList.addAll(response.body().getDataRoom());
                            rvRoom.setAdapter(new SearchTransferRuanganAdapter(mContext, AllPaymentList));
                            Adapter.notifyDataSetChanged();

                        }else{

                            pDialog.cancel();
                            finish();
                            Toast.makeText(mContext, "Room Kosong Tidak Ditemukan", Toast.LENGTH_LONG).show();

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
