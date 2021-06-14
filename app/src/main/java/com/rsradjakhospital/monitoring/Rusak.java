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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.rsradjakhospital.monitoring.Model.ResponseData;
import com.rsradjakhospital.monitoring.api.ApiService;
import com.rsradjakhospital.monitoring.api.Server;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.layout.simple_spinner_item;
import static com.rsradjakhospital.monitoring.Booking.TAG_SELESAI_BOOKING;
import static com.rsradjakhospital.monitoring.MainActivity.TAG_USER;
import static com.rsradjakhospital.monitoring.UbahStatusBed.TAG_OFFICE_NAME;
import static com.rsradjakhospital.monitoring.UbahStatusBed.TAG_SELESAI_UBAH_BED;

public class Rusak extends AppCompatActivity {

    private Spinner spinnerMonitor;
    private ArrayList<String> arrayKesulitan= new ArrayList<String>();
    private Context mContext;
    ProgressDialog pDialog;
    private ApiService API;
    private EditText et_ket ;
    private Button btn_apply ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rusak);



        mContext = this ;
        API = Server.getAPIService();

        spinnerMonitor = findViewById(R.id.statusKesulitan);
        et_ket = findViewById(R.id.text_ket);
        btn_apply = findViewById(R.id.btn_apply);


        arrayKesulitan.add("-- Tingkat Kesulitan --") ;
        arrayKesulitan.add("RINGAN") ;
        arrayKesulitan.add("SEDANG") ;
        arrayKesulitan.add("PARAH") ;

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(mContext, simple_spinner_item,arrayKesulitan);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinnerMonitor.setAdapter(spinnerArrayAdapter);



        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (spinnerMonitor.getSelectedItem().equals("-- Tingkat Kesulitan --")) {
                    Toast.makeText(mContext, "Pilih Status Kesulitan", Toast.LENGTH_LONG).show();
                }else if(et_ket.getText().toString().equals("")){
                    Toast.makeText(mContext, "Silahkan Isi Keterangan", Toast.LENGTH_LONG).show();
                }else{

                    // UbahStatusBed(TAG_OFFICE_NAME, spinnerMonitor.getSelectedItem().toString() ); ;

                    String ket =  et_ket.getText().toString();
                    String status =  spinnerMonitor.getSelectedItem().toString();

                    String tempNote = ket + "/" + status ;

                    show_dialog_rusak(mContext,TAG_OFFICE_NAME,"RUSAK",tempNote); ;
                    //show_dialog_rusak();


                }

            }
        });


    }



    private void UbahStatusRusak(String office,  String status , String TempNote){

        pDialog = new ProgressDialog(mContext);
        pDialog.setCancelable(false);
        pDialog.setMessage("Memuat...");
        pDialog.show();


        Call<ResponseData> call = API.updateStatusRoom(office,status,TempNote,TAG_USER);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if(response.isSuccessful()) {

                    TAG_SELESAI_BOOKING = "selesai";

                    pDialog.cancel();

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


    public void show_dialog_rusak(Context context, String office , String statusBed, String tempNote){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage("Ubah Status Bed Menjadi "+statusBed+" ?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        UbahStatusRusak(office,statusBed,tempNote); ;
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

}
