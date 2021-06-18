package com.rsradjakhospital.monitoring;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.rsradjakhospital.monitoring.Adapter.RuanganAdapter;
import com.rsradjakhospital.monitoring.Helper.Helper;
import com.rsradjakhospital.monitoring.Model.ResponseData;
import com.rsradjakhospital.monitoring.Model.ResponseEntityRoom;
import com.rsradjakhospital.monitoring.Service.MyService;
import com.rsradjakhospital.monitoring.api.ApiService;
import com.rsradjakhospital.monitoring.api.Server;
import com.rsradjakhospital.monitoring.sessionManager.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rsradjakhospital.monitoring.DetailRoom.TAG_SELESAI_SIMPAN;
import static com.rsradjakhospital.monitoring.DetailRoom.TAG_SELESAI_TRANSFER;
import static com.rsradjakhospital.monitoring.MainActivity.TAG_USER;
import static com.rsradjakhospital.monitoring.UbahStatusBed.TAG_SELESAI_UBAH_BED;

public class listRuangan extends AppCompatActivity {


    ProgressDialog pDialog;

    private ApiService API;
    private RecyclerView rvRoom;



    private Context mContext;
    private List<ResponseEntityRoom> AllPaymentList = new ArrayList<>();
    private RuanganAdapter Adapter;

    SwipeRefreshLayout swLayout;




    public  static String TAG_CATEGORY = "EMERALD" ;
    public  static String TAG_KELAS = "" ;
    public  static String TAG_FLOOR = "FLOOR 1" ;
    public static int TAG_COUNT_KOSONG = 0;
    public static int TAG_COUNT_RUSAK = 0;
    public static int TAG_COUNT_ISI = 0;
    public static int TAG_COUNT_TITIPAN = 0;
    public static int TAG_COUNT_RENCANA = 0;
    public static int TAG_COUNT_BOOKING = 0;

    TextView countKosong , toolbar_title_room ;

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_ruangan);

        session = new SessionManager(getApplicationContext());

        // Inisialisasi SwipeRefreshLayout
        swLayout = (SwipeRefreshLayout) findViewById(R.id.swlayout);

        mContext = this ;
        API = Server.getAPIService();
        rvRoom = findViewById(R.id.rvRoom);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvRoom.setLayoutManager(mLayoutManager);
        rvRoom.setItemAnimator(new DefaultItemAnimator());

        toolbar_title_room = findViewById(R.id.toolbar_title_room) ;

        toolbar_title_room.setText("LIST "+TAG_CATEGORY+" ROOM");

        countKosong = findViewById(R.id.countKosong);


        Adapter = new RuanganAdapter(this, AllPaymentList);

        getListRuangan(TAG_CATEGORY,"ALLROOM",TAG_KELAS) ;


        // Mengeset properti warna yang berputar pada SwipeRefreshLayout
        swLayout.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimary);

        // Mengeset listener yang akan dijalankan saat layar di refresh/swipe
        swLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                // Handler untuk menjalankan jeda selama 5 detik
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {

                        // Berhenti berputar/refreshing
                        swLayout.setRefreshing(false);

                        AllPaymentList.clear();
                        Adapter.notifyDataSetChanged();
                        getListRuangan(TAG_CATEGORY,"ALLROOM",TAG_KELAS) ;


                    }
                }, 1000);
            }
        });


    }



    private void getListRuangan(String category, String request , String Kelas){

        pDialog = new ProgressDialog(listRuangan.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Memuat...");
        pDialog.show();


        Call<ResponseData> call = API.FetchRoom(category,request ,Kelas);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData>  response) {
                if(response.isSuccessful()) {
                    if (response.body().getDataRoom() != null) {


                        if(!response.body().getDataRoom().isEmpty()){

                            pDialog.cancel();
                            AllPaymentList.addAll(response.body().getDataRoom());
                            rvRoom.setAdapter(new RuanganAdapter(mContext, AllPaymentList));
                            Adapter.notifyDataSetChanged();

                            for (ResponseEntityRoom kosong : AllPaymentList){


                                if (kosong.getPatientStatus().equals("KOSONG")){
                                    TAG_COUNT_KOSONG = TAG_COUNT_KOSONG + 1 ;
                                }

                                if (kosong.getPatientStatus().equals("RUSAK")){
                                    TAG_COUNT_RUSAK = TAG_COUNT_RUSAK + 1 ;
                                }

                                if (kosong.getPatientStatus().equals("ISI")){
                                    TAG_COUNT_ISI = TAG_COUNT_ISI + 1 ;
                                }

                                if (kosong.getPatientStatus().equals("TITIPAN")){
                                    TAG_COUNT_TITIPAN = TAG_COUNT_TITIPAN + 1 ;
                                }

                                if (kosong.getPatientStatus().equals("RENCANA PULANG")){
                                    TAG_COUNT_RENCANA = TAG_COUNT_RENCANA + 1 ;
                                }

                                if (kosong.getPatientStatus().equals("BOOKING")){
                                    TAG_COUNT_BOOKING= TAG_COUNT_BOOKING + 1 ;
                                }



                            }

                            countKosong.setText("ISI : "+TAG_COUNT_ISI+" KOSONG : "+TAG_COUNT_KOSONG+" TITIPAN : "+TAG_COUNT_TITIPAN+" RUSAK : "+TAG_COUNT_RUSAK+" RNCNA PLG : "+TAG_COUNT_RENCANA+" BOOKING : "+TAG_COUNT_BOOKING);
                            TAG_COUNT_KOSONG = 0 ;
                            TAG_COUNT_ISI = 0 ;
                            TAG_COUNT_TITIPAN = 0 ;
                            TAG_COUNT_RUSAK = 0 ;
                            TAG_COUNT_RENCANA= 0 ;
                            TAG_COUNT_BOOKING= 0 ;




                        }else{
                            pDialog.cancel();
                            finish();
                            Toast.makeText(mContext, "Room Tidak Ditemukan", Toast.LENGTH_LONG).show();
                            finish();
                        }



                    }else{
                        pDialog.cancel();
                        finish();
                        Toast.makeText(mContext, "Error Response Data", Toast.LENGTH_LONG).show();
                    }

                }else{
                    pDialog.cancel();
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

    @Override
    protected void onResume() {

        if (!TAG_SELESAI_UBAH_BED.equals("")){
            TAG_SELESAI_UBAH_BED = "" ;
            AllPaymentList.clear();
            Adapter.notifyDataSetChanged();
            getListRuangan(TAG_CATEGORY,"ALLROOM",TAG_KELAS) ;

        }


        if (!TAG_SELESAI_SIMPAN.equals("")){
            TAG_SELESAI_SIMPAN = "" ;
            AllPaymentList.clear();
            Adapter.notifyDataSetChanged();
            getListRuangan(TAG_CATEGORY,"ALLROOM",TAG_KELAS) ;

        }

        if (!TAG_SELESAI_TRANSFER.equals("")){
            TAG_SELESAI_TRANSFER = "" ;
            AllPaymentList.clear();
            Adapter.notifyDataSetChanged();
            getListRuangan(TAG_CATEGORY,"ALLROOM",TAG_KELAS) ;

        }

            if(session.getUsername() != null){
                TAG_USER = session.getUsername();
            }


        Helper.Notification_(session.getNotifID(),"Count", listRuangan.this);




        super.onResume();
    }
}
