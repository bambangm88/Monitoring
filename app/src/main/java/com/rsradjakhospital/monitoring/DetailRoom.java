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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rsradjakhospital.monitoring.Model.ResponseData;
import com.rsradjakhospital.monitoring.Model.ResponseEntityListRoom;
import com.rsradjakhospital.monitoring.Model.ResponseEntityRoom;
import com.rsradjakhospital.monitoring.api.ApiService;
import com.rsradjakhospital.monitoring.api.Server;
import com.rsradjakhospital.monitoring.sessionManager.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.layout.simple_spinner_item;
import static com.rsradjakhospital.monitoring.MainActivity.TAG_USER;

public class DetailRoom extends AppCompatActivity {


    Button btn_tranfer ,  btn_pulang , btn_tulis_resep , btn_update , btn_berkas;

    ProgressDialog pDialog;
    ApiService API;


    EditText  tv_kunjungan , tvNoMr , tvKelas , tv_tglMasuk , tvPenjamin , tvDiagnosaAwal , dpjp , namaPasien ;
    TextView tv_title , textKet ;
    Spinner statusRuangan ;
    public static String  TAG_NAMA_BED= "" ;
    public static String  TAG_NAMA_PASIEN= "" ;
    public static String  TAG_SELESAI_SIMPAN= "" ;
    public static String  TAG_SELESAI_TRANSFER= "" ;

    public static String  TAG_KUNJUNGAN = "" ;
    public static String  TAG_NOMR= "" ;
    public static String  TAG_ROOM= "" ;
    public static String  TAG_ROOM_HEADER= "" ;
    public static String  TAG_TANGGAL_MASUK = "" ;
    public static String  TAG_PENJAMIN = "" ;
    public static String  TAG_DIAGNOSA_AWAL = "" ;
    public static String  TAG_DPJP = "" ;
    public static String  TAG_STATUS_RUANGAN= "" ;
    public static String  TAG_STATUS_TEXT= "" ;
    public static String  TAG_STATUS_KELAS= "" ;
    public static String  TAG_CASES_ID= "" ;

    public static String TAG_DOCTOR = "" ;

    public static String TAG_DOCTOR_ID = "" ;

    public static String TAG_SELESAI_TULIS_RESEP = "";

    SessionManager session;


    private ArrayList<String> arrayStatusRuangan = new ArrayList<String>();
    private List<ResponseEntityListRoom> AllStatusRoomList = new ArrayList<>();
    private List<ResponseEntityRoom> AllEntityList = new ArrayList<>();


    Context mContext ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_room);

        session = new SessionManager(getApplicationContext());


        btn_tranfer  =findViewById(R.id.btn_transfer);

        btn_pulang = findViewById(R.id.btn_pulang);

        btn_tulis_resep = findViewById(R.id.tulis_resep);


        btn_update = findViewById(R.id.btn_update);

        btn_berkas = findViewById(R.id.berkas);


        tv_title = findViewById(R.id.toolbar_title);


        tv_kunjungan = findViewById(R.id.idKunjungan);

        tvNoMr = findViewById(R.id.noMr);

        tvKelas = findViewById(R.id.kelas);

        tv_tglMasuk = findViewById(R.id.tglMasuk);

        tvPenjamin = findViewById(R.id.penjamin);

        tvDiagnosaAwal = findViewById(R.id.diagnosaAwal);

        dpjp = findViewById(R.id.dpjp);

        statusRuangan = findViewById(R.id.statusRuangan);

        textKet = findViewById(R.id.text_ket);

        namaPasien = findViewById(R.id.namaPasien);






        tv_kunjungan.setText(TAG_KUNJUNGAN);

        tvNoMr.setText(TAG_NOMR);

        tvKelas.setText(TAG_STATUS_KELAS);




        tv_tglMasuk.setText(TAG_TANGGAL_MASUK);

        tvPenjamin.setText(TAG_PENJAMIN);

        tvDiagnosaAwal.setText(TAG_DIAGNOSA_AWAL);

        namaPasien.setText(TAG_NAMA_PASIEN);

        dpjp.setText(TAG_DPJP);


        dpjp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DetailRoom.this,SearchDokter.class);
                startActivity(intent);


            }
        });




        tv_title.setText("Detail Room - " + TAG_ROOM);



        API = Server.getAPIService();

        pDialog = new ProgressDialog(DetailRoom.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Memuat...");




        mContext = this ;


        btn_tranfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailRoom.this, TransferActivity.class);
                startActivity(intent);
            }
        });


        btn_tulis_resep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailRoom.this, signature.class);
                startActivity(intent);
            }
        });

        btn_berkas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DetailRoom.this,UploadBerkas.class);
                startActivity(intent);

            }
        });


        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if(textKet.getText().toString().equals("") ){
////                    Toast.makeText(mContext, "Silahkan isi Keterangan", Toast.LENGTH_LONG).show();
////                }else

                if(statusRuangan.getSelectedItem().toString().equals("-- Status Ruangan --")){
                    Toast.makeText(mContext, "Silahkan pilih status ruangan", Toast.LENGTH_LONG).show();
                }else{
                    simpanDataPasien(TAG_CASES_ID,textKet.getText().toString(),TAG_NAMA_BED,TAG_DOCTOR_ID,statusRuangan.getSelectedItem().toString());

                }


            }
        });


        btn_pulang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailRoom.this, PasienPulangActivity.class);
                startActivity(intent);
            }
        });




        //arrayStatusRuangan.add("-- Status Ruangan --") ;
        //arrayStatusRuangan.add("ISI") ;
        //arrayStatusRuangan.add("RENCANA PULANG") ;
        //arrayStatusRuangan.add("RUSAK") ;
        //arrayStatusRuangan.add("TITIPAN") ;

        /*ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(DetailRoom.this, simple_spinner_item,arrayStatusRuangan);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        statusRuangan.setAdapter(spinnerArrayAdapter);

         int sp = 0 ;

        if(TAG_STATUS_RUANGAN.equals("ISI")){
            sp = 1 ;
        }

        if(TAG_STATUS_RUANGAN.equals("RENCANA PULANG")){
            sp = 2 ;
        }

        if(TAG_STATUS_RUANGAN.equals("RUSAK")){
            sp = 3 ;
        }

        if(TAG_STATUS_RUANGAN.equals("TITIPAN")){
            sp = 4 ;
        }

        statusRuangan.setSelection(sp);

        */


        fetchListRoom(TAG_STATUS_RUANGAN);

    }


    private void fetchListRoom(String status) {


        arrayStatusRuangan.removeAll(arrayStatusRuangan);
        arrayStatusRuangan.clear();

        //pDialog.show();

        API.getListRoom(status).enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {

                if (response.isSuccessful()){

                    // pDialog.cancel();
                    AllStatusRoomList.addAll(response.body().getDataListRoom());

                    arrayStatusRuangan.add("-- Status Ruangan --") ;

                    for(ResponseEntityListRoom model : AllStatusRoomList) {

                        if(!arrayStatusRuangan.contains(model.getPatientStatus())){

                            arrayStatusRuangan.add(model.getPatientStatus()) ;

                        }

                    }

                    if (!TAG_USER.equals("Rois") || !TAG_USER.equals("admission")){

                        arrayStatusRuangan.remove("BOOKING");

                    }


                    statusRuangan.setSelection(arrayStatusRuangan.indexOf(TAG_STATUS_RUANGAN));

                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(mContext, simple_spinner_item,arrayStatusRuangan );
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                    statusRuangan.setAdapter(spinnerArrayAdapter);



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


    public void show_dialog_kosong(Context context){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage("Kosongkan Room ini ?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
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



    private void simpanDataPasien(String caseID, String ket , String namaBed , String idDokter , String statusBed ){

        pDialog = new ProgressDialog(DetailRoom.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Memuat...");
        pDialog.show();


        Call<ResponseData> call = API.simpanDataPasien(caseID,statusBed,ket,idDokter,namaBed,TAG_USER);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if(response.isSuccessful()) {

                    pDialog.cancel();
                    TAG_SELESAI_SIMPAN= "selesai" ;
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




    @Override
    protected void onResume() {

        if (TAG_DOCTOR == null || TAG_DOCTOR.equals("") ){

        }else{
            dpjp.setText(TAG_DOCTOR);
            TAG_DOCTOR = "" ;
        }

        if (TAG_SELESAI_TULIS_RESEP == null || TAG_SELESAI_TULIS_RESEP.equals("") ){

        }else{
            finish();
        }

        if(session.getUsername() != null){
            TAG_USER = session.getUsername();
        }



        super.onResume();
    }







}
