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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rsradjakhospital.monitoring.Model.ResponseData;
import com.rsradjakhospital.monitoring.api.ApiService;
import com.rsradjakhospital.monitoring.api.Server;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rsradjakhospital.monitoring.DetailRoom.TAG_CASES_ID;
import static com.rsradjakhospital.monitoring.DetailRoom.TAG_NAMA_BED;
import static com.rsradjakhospital.monitoring.DetailRoom.TAG_SELESAI_TRANSFER;
import static com.rsradjakhospital.monitoring.MainActivity.TAG_USER;

public class TransferActivity extends AppCompatActivity {


    EditText et_transfer_origin , et_transfer_destination ;
    Button btn_trf ;

    ProgressDialog pDialog;

    ApiService API;



    public static String TAG_TRF_DST = "" ;

    Context mContext ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        et_transfer_origin = findViewById(R.id.trf_ori);
        et_transfer_destination = findViewById(R.id.trf_dst);
        btn_trf = findViewById(R.id.btn_trf) ;



        mContext = this ;

        API = Server.getAPIService();

        et_transfer_origin.setText(TAG_NAMA_BED);


        et_transfer_destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TransferActivity.this,TransferCariRoomActivity.class);
                startActivity(intent);


            }
        });


        btn_trf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                show_dialog_trf(TransferActivity.this);

            }
        });


    }


    @Override
    protected void onResume() {

        if (TAG_TRF_DST != null || !TAG_TRF_DST.equals("")){
            et_transfer_destination.setText(TAG_TRF_DST);
            TAG_TRF_DST = "" ;
        }



        super.onResume();
    }




    public void show_dialog_trf(Context context){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage("Room Transfer Sudah Benar ?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        if (et_transfer_destination.getText().toString().equals("")){
                            Toast.makeText(mContext, "Silahkan pilih room tujuan", Toast.LENGTH_LONG).show();
                        }else{
                            transferPasien(et_transfer_destination.getText().toString(),TAG_CASES_ID,et_transfer_origin.getText().toString());
                        }



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


    private void transferPasien(String dest, String caseID, String origin  ){

        pDialog = new ProgressDialog(TransferActivity.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Memuat...");
        pDialog.show();


        Call<ResponseData> call = API.transferPasien(dest,caseID,origin,TAG_USER);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if(response.isSuccessful()) {

                    pDialog.cancel();
                    TAG_SELESAI_TRANSFER = "selesai" ;
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



}
