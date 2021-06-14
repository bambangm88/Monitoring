package com.rsradjakhospital.monitoring;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.rsradjakhospital.monitoring.Model.ResponseData;
import com.rsradjakhospital.monitoring.Model.ResponseEntityCaraPulang;
import com.rsradjakhospital.monitoring.Model.ResponseMonitor;
import com.rsradjakhospital.monitoring.api.ApiService;
import com.rsradjakhospital.monitoring.api.Server;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.layout.simple_spinner_item;
import static com.rsradjakhospital.monitoring.DetailRoom.TAG_CASES_ID;
import static com.rsradjakhospital.monitoring.MainActivity.TAG_USER;

public class PasienPulangActivity extends AppCompatActivity {

    private Spinner spinnerMonitor;
    private ArrayList<String> arrayMonitor = new ArrayList<String>();
    private List<ResponseEntityCaraPulang> AllMonitorList = new ArrayList<>();



    private Context mContext;
    ProgressDialog pDialog;
    private ApiService API;

    Button btn_pulang ;
    EditText textPulang ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasien_pulang);

        mContext = this ;
        API = Server.getAPIService();

        spinnerMonitor = findViewById(R.id.spMonitor);
        btn_pulang = findViewById(R.id.btn_apply);
        textPulang = findViewById(R.id.textPulang);

        pDialog = new ProgressDialog(PasienPulangActivity.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Memuat...");




        fetchListCaraPulang() ;


        btn_pulang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (spinnerMonitor.getSelectedItem().equals("-- Cara Pulang --")) {
                    Toast.makeText(mContext, "Pilih Cara Pulang", Toast.LENGTH_LONG).show();
                }else if(textPulang.getText().toString().equals("")){
                    Toast.makeText(mContext, "Silahkan Isi Diagnosa Akhir", Toast.LENGTH_LONG).show();
                }else{

                    UpdatePasienPulang(TAG_CASES_ID, spinnerMonitor.getSelectedItem().toString() , textPulang.getText().toString()) ;
                }

            }
        });






    }



    private void fetchListCaraPulang() {


        arrayMonitor.removeAll(arrayMonitor);
        arrayMonitor.clear();

        pDialog.show();

        API.fetchListCaraPulang().enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {

                if (response.isSuccessful()){

                    pDialog.cancel();
                    AllMonitorList.addAll(response.body().getDataCaraPulang());



                    arrayMonitor.add("-- Cara Pulang --") ;

                    for(ResponseEntityCaraPulang model : AllMonitorList) {

                        if(!arrayMonitor.contains(model.getCara_pulang())){

                            arrayMonitor.add(model.getCara_pulang()) ;

                        }

                    }

                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(PasienPulangActivity.this, simple_spinner_item,arrayMonitor );
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                    spinnerMonitor.setAdapter(spinnerArrayAdapter);




                }else {
                    pDialog.cancel();
                    Toast.makeText(PasienPulangActivity.this, "Cek koneksi internet anda", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                pDialog.cancel();
                Toast.makeText(PasienPulangActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }




    private void UpdatePasienPulang(String caseID, String reason , String diagnosaakhir){

        pDialog = new ProgressDialog(PasienPulangActivity.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Memuat...");
        pDialog.show();


        Call<ResponseData> call = API.updatePasienPulang(caseID,reason,diagnosaakhir,TAG_USER);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if(response.isSuccessful()) {





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



    boolean check(){

         boolean connectStatus = true;
         ConnectivityManager ConnectionManager=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
         NetworkInfo networkInfo= ConnectionManager.getActiveNetworkInfo();
         if(networkInfo != null && networkInfo.isConnectedOrConnecting() ) {
             connectStatus = true;
         }
         else {
             connectStatus = false;
         }
         return connectStatus;

    }








}
