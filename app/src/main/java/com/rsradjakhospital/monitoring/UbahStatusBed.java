package com.rsradjakhospital.monitoring;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.rsradjakhospital.monitoring.Model.ResponseData;
import com.rsradjakhospital.monitoring.Model.ResponseEntityListRoom;
import com.rsradjakhospital.monitoring.Model.ResponseMonitor;
import com.rsradjakhospital.monitoring.api.ApiService;
import com.rsradjakhospital.monitoring.api.Server;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.layout.simple_spinner_item;
import static com.rsradjakhospital.monitoring.Booking.TAG_SELESAI_BOOKING;
import static com.rsradjakhospital.monitoring.MainActivity.TAG_USER;
import static com.rsradjakhospital.monitoring.listRuangan.TAG_CATEGORY;

public class UbahStatusBed extends AppCompatActivity {

    private Spinner spinnerMonitor;
    private ArrayList<String> arrayMonitor = new ArrayList<String>();
    private List<ResponseEntityListRoom> AllStatusRoomList = new ArrayList<>();

    private Context mContext;
    ProgressDialog pDialog;
    private ApiService API;

    Button btn_apply;

    public static String  TAG_OFFICE_NAME= "" ;

    public static String  TAG_ROOM_STATUS= "" ;

    public static String  TAG_SELESAI_UBAH_BED= "" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_status_bed);

        mContext = this ;
        API = Server.getAPIService();

        spinnerMonitor = findViewById(R.id.spMonitor);

        btn_apply = findViewById(R.id.btn_apply);

      /*  if (TAG_USER.equals("Rois") || TAG_USER.equals("admission")){

            arrayMonitor.add("-- Status Bed --") ;
            arrayMonitor.add("KOSONG") ;
            arrayMonitor.add("TITIPAN") ;
            arrayMonitor.add("RUSAK") ;
            arrayMonitor.add("BOOKING") ;

        }else{

            arrayMonitor.add("-- Status Bed --") ;
            arrayMonitor.add("KOSONG") ;
            arrayMonitor.add("TITIPAN") ;
            arrayMonitor.add("RUSAK") ;

        }*/




        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(UbahStatusBed.this, simple_spinner_item,arrayMonitor );
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinnerMonitor.setAdapter(spinnerArrayAdapter);




        spinnerMonitor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String status = ""+spinnerMonitor.getSelectedItem() ;


                if (status.equals("BOOKING")){
                    startActivity(new Intent(UbahStatusBed.this,Booking.class));
                }

                if (status.equals("RUSAK")){
                    startActivity(new Intent(UbahStatusBed.this,Rusak.class));
                }



            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (spinnerMonitor.getSelectedItem().equals("-- Status Bed --")) {
                    Toast.makeText(mContext, "Pilih Status Bed", Toast.LENGTH_LONG).show();
                }
                else if (spinnerMonitor.getSelectedItem().equals("RUSAK")) {
                    startActivity(new Intent(UbahStatusBed.this,Rusak.class));
                }else if (spinnerMonitor.getSelectedItem().equals("BOOKING")) {
                    startActivity(new Intent(UbahStatusBed.this,Booking.class));
                }else{

                   // UbahStatusBed(TAG_OFFICE_NAME, spinnerMonitor.getSelectedItem().toString() ); ;

                    show_dialog_ubah(UbahStatusBed.this,TAG_OFFICE_NAME, spinnerMonitor.getSelectedItem().toString() );
                }

            }
        });


        fetchListRoom(TAG_ROOM_STATUS);


    }



    private void UbahStatusBed(String office,  String status){

        pDialog = new ProgressDialog(UbahStatusBed.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Memuat...");
        pDialog.show();


        Call<ResponseData> call = API.updateStatusRoom(office,status,"",TAG_USER);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if(response.isSuccessful()) {

                    TAG_SELESAI_UBAH_BED = "SELESAI" ;

                    pDialog.cancel();
//                            AllPaymentList.addAll(response.body().getDataRoom());
//                            rvRoom.setAdapter(new RuanganAdapter(mContext, AllPaymentList));
//                            Adapter.notifyDataSetChanged();
                    Toast.makeText(mContext, "Success", Toast.LENGTH_LONG).show();
                    finish();



                }else{
                    pDialog.cancel();
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




    private void fetchListRoom(String status) {


        arrayMonitor.removeAll(arrayMonitor);
        arrayMonitor.clear();

        //pDialog.show();

        API.getListRoom(status).enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {

                if (response.isSuccessful()){

                    // pDialog.cancel();
                    AllStatusRoomList.addAll(response.body().getDataListRoom());

                    arrayMonitor.add("-- Status Bed --") ;

                    for(ResponseEntityListRoom model : AllStatusRoomList) {

                        if(!arrayMonitor.contains(model.getPatientStatus())){

                            arrayMonitor.add(model.getPatientStatus()) ;

                        }

                    }

                   // Log.e("TAG", "TAG_USER"+ TAG_USER);

                    if (TAG_USER.equals("Rois") || TAG_USER.equals("rois") || TAG_USER.equals("Admission") ||TAG_USER.equals("admission")){



                    }else{

                        arrayMonitor.remove("BOOKING");

                       // Log.e("TAG", "TAG_USER"+ TAG_USER);
                    }


                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(mContext, simple_spinner_item,arrayMonitor );
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                    spinnerMonitor.setAdapter(spinnerArrayAdapter);



                }else {
                    //pDialog.cancel();
                    Toast.makeText(mContext, "Cek koneksi internet anda", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                //pDialog.cancel();
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }




    public void show_dialog_ubah(Context context, String office , String statusBed){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage("Ubah Status Bed Menjadi "+statusBed+" ?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        UbahStatusBed(office, statusBed ); ;
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }


    @Override
    protected void onResume() {
        if (TAG_SELESAI_BOOKING == null || TAG_SELESAI_BOOKING.equals("") ){

        }else{
            TAG_SELESAI_UBAH_BED = "SELESAI" ;
            TAG_SELESAI_BOOKING = "";
            finish();
        }
        super.onResume();
    }
}
