package com.rsradjakhospital.monitoring;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rsradjakhospital.monitoring.Helper.Helper;
import com.rsradjakhospital.monitoring.Model.ResponseData;
import com.rsradjakhospital.monitoring.Model.ResponseEntityCountRoom;
import com.rsradjakhospital.monitoring.Model.ResponseEntityNotification;
import com.rsradjakhospital.monitoring.Model.ResponseKelas;
import com.rsradjakhospital.monitoring.Model.ResponseMonitor;
import com.rsradjakhospital.monitoring.Service.MyService;
import com.rsradjakhospital.monitoring.api.ApiService;
import com.rsradjakhospital.monitoring.api.Server;
import com.rsradjakhospital.monitoring.sessionManager.SessionManager;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.layout.simple_spinner_item;
import static com.rsradjakhospital.monitoring.CountRoomActivity.TAG_STATUS_CATEGORY;
import static com.rsradjakhospital.monitoring.CountRoomActivity.TAG_STATUS_CATEGORY_COUNT;
import static com.rsradjakhospital.monitoring.listRuangan.TAG_CATEGORY;

public class MainActivity extends AppCompatActivity {

    ProgressDialog pDialog;
    ApiService API;
    private Spinner spinnerMonitor;
    private Spinner spinnerFloor;
    private Spinner spinnerKelas;
    RelativeLayout btn_notification;

    Toolbar toolbar ;

    SessionManager session;
    public static String  TAG_USER = "" ;

    Button btn_cari ;



    SwipeRefreshLayout swLayout;

    Handler mHandler ;


    public List<ResponseEntityNotification> AllEntityNotification = new ArrayList<>();

    private ArrayList<String> arrayMonitor = new ArrayList<String>();
    private ArrayList<String> arrayFloor = new ArrayList<String>();
    private List<ResponseMonitor> AllMonitorList = new ArrayList<>();

    private List<ResponseEntityCountRoom> AllCountRoom = new ArrayList<>();

    private ArrayList<String> arrayKelas = new ArrayList<String>();
    private List<ResponseKelas> AllKelasList = new ArrayList<>();

    TextView tvKosong, tvIsi , tvRusak , tvTitipan , tvRencana , tvProfile , tvBooking ,tvClosed ;

    LinearLayout Kosong, Isi , Rusak , Titipan , Rencana ,  Booking  , closed;

    TextView badge ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_cari = findViewById(R.id.btn_cari);
        session = new SessionManager(getApplicationContext());

        API = Server.getAPIService();
        spinnerMonitor = findViewById(R.id.spMonitor);
        spinnerFloor = findViewById(R.id.spFloor);
        spinnerKelas = findViewById(R.id.spKelas);
        btn_notification = findViewById(R.id.btn_notification);

        tvIsi = findViewById(R.id.isi);
        tvKosong = findViewById(R.id.kosong);
        tvRusak = findViewById(R.id.rusak);
        tvTitipan = findViewById(R.id.titipan);
        tvRencana = findViewById(R.id.rencana_pulang);
        tvProfile = findViewById(R.id.profile);
        tvBooking = findViewById(R.id.booking);
        tvClosed = findViewById(R.id.closed);



        Isi = findViewById(R.id.btn_isi);
        Kosong = findViewById(R.id.btn_kosong);
        Rusak = findViewById(R.id.btn_rusak);
        Titipan = findViewById(R.id.btn_titipan);
        Rencana = findViewById(R.id.btn_rencana_pulang);
        Booking = findViewById(R.id.btn_booking);
        closed = findViewById(R.id.btn_closed);



        // Inisialisasi SwipeRefreshLayout
        swLayout = (SwipeRefreshLayout) findViewById(R.id.swlayout);


        toolbar = (Toolbar) findViewById(R.id.toolbar_pay);
        setSupportActionBar(toolbar);

        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Memuat...");

        badge = findViewById(R.id.badge);

        fetchMonitor();

        this.mHandler = new Handler();

        this.mHandler.postDelayed(m_Runnable,15000);

        startService(new Intent(getBaseContext(), MyService.class));



        tvProfile.setText("Login as : "+TAG_USER);

        btn_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NotificationBadge.class);
                startActivity(intent);
            }
        });


        Log.e("koneksi", "onCreate: internet"+haveNetworkConnection(this) );

        btn_cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String category = ""+spinnerMonitor.getSelectedItem() ;
//                String floor = ""+spinnerFloor.getSelectedItem() ;
//                String kelas = ""+spinnerKelas.getSelectedItem() ;


                if(category.equals("-- Category --") ) {
                    Toast.makeText(MainActivity.this, "Silahkan Pilih Category", Toast.LENGTH_SHORT).show();

                }
                else {
                    TAG_CATEGORY = "" + spinnerMonitor.getSelectedItem();
//                    TAG_FLOOR = "" + spinnerFloor.getSelectedItem();
//                    TAG_KELAS = "" + spinnerKelas.getSelectedItem();
                    Intent intent = new Intent(MainActivity.this, listRuangan.class);
                    startActivity(intent);
                }


            }
        });

        spinnerMonitor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String category = ""+spinnerMonitor.getSelectedItem() ;
//                String floor = ""+spinnerFloor.getSelectedItem() ;
//                String kelas = ""+spinnerKelas.getSelectedItem() ;


                if(category.equals("-- Category --") ) {
                    //Toast.makeText(MainActivity.this, "Silahkan Pilih Category", Toast.LENGTH_SHORT).show();

                }
                else {
                    TAG_CATEGORY = "" + spinnerMonitor.getSelectedItem();
//                    TAG_FLOOR = "" + spinnerFloor.getSelectedItem();
//                    TAG_KELAS = "" + spinnerKelas.getSelectedItem();

                    mHandler.removeCallbacks(m_Runnable);
                    Intent intent = new Intent(MainActivity.this, listRuangan.class);
                    startActivity(intent);
                }

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



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


                        arrayMonitor.removeAll(arrayMonitor);
                        arrayMonitor.clear();

                        if (arrayMonitor.isEmpty()){

                            fetchMonitor();
                            Log.e("sini", "run: empty" );

                        }else{

                            arrayMonitor.removeAll(arrayMonitor);
                            arrayMonitor.clear();
                            Log.e("sini", "run: tidak empty" );
                        }




                    }
                }, 1000);
            }
        });


        Helper.checkVersionUpdate(this);





    }



    private final Runnable m_Runnable = new Runnable()
    {
        public void run()

        {
            fetchCountAllRoom();
            Notification_(session.getNotifID(),"COUNT",MainActivity.this);

            MainActivity.this.mHandler.postDelayed(m_Runnable, 15000);

        }

    };//runnable


    @Override
    public void onBackPressed() {
        mHandler.removeCallbacks(m_Runnable);

        super.onBackPressed();
    }

    private void fetchMonitor() {


        arrayMonitor.removeAll(arrayMonitor);
        arrayMonitor.clear();

        //pDialog.show();

        API.fetchMonitor().enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {

                if (response.isSuccessful()){

                   // pDialog.cancel();
                    AllMonitorList.addAll(response.body().getData());



                    arrayMonitor.add("-- Category --") ;

                    for(ResponseMonitor model : AllMonitorList) {

                        if(!arrayMonitor.contains(model.getMonitor())){

                            arrayMonitor.add(model.getMonitor()) ;

                        }



                    }

                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(MainActivity.this, simple_spinner_item,arrayMonitor );
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                    spinnerMonitor.setAdapter(spinnerArrayAdapter);

                    fetchCountAllRoom();


                }else {
                    //pDialog.cancel();
                    Toast.makeText(MainActivity.this, "Cek koneksi internet anda", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                //pDialog.cancel();
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void fetchCountAllRoom() {

        //pDialog.cancel();

        API.fetchCountAllRoom().enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {

                if (response.isSuccessful()){

                    //pDialog.cancel();

                    AllCountRoom.addAll(response.body().getDataCountRoom());



                    for (ResponseEntityCountRoom count : AllCountRoom){

                        tvIsi.setText(""+count.getISI());
                        tvKosong.setText(""+count.getKOSONG());
                        tvRusak.setText(""+count.getRUSAK());
                        tvTitipan.setText(""+count.getTITIPAN());
                        tvRencana.setText(""+count.getRENCANA_PULANG());
                        tvBooking.setText(""+count.getBOOKING());
                        tvClosed.setText(""+count.getCLOSED());

                        if(Integer.parseInt(count.getISI()) > 0) {

                            Isi.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    TAG_STATUS_CATEGORY = "ISI";
                                    TAG_STATUS_CATEGORY_COUNT = "" + count.getISI();

                                    startActivity(new Intent(MainActivity.this, CountRoomActivity.class));
                                }
                            });

                        }


                        if(Integer.parseInt(count.getKOSONG()) > 0) {

                            Kosong.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    TAG_STATUS_CATEGORY = "KOSONG";
                                    TAG_STATUS_CATEGORY_COUNT = "" + count.getKOSONG();

                                    startActivity(new Intent(MainActivity.this, CountRoomActivity.class));
                                }
                            });

                        }

                        if(Integer.parseInt(count.getRUSAK()) > 0) {

                            Rusak.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    TAG_STATUS_CATEGORY = "RUSAK";
                                    TAG_STATUS_CATEGORY_COUNT = "" + count.getRUSAK();

                                    startActivity(new Intent(MainActivity.this, CountRoomActivity.class));
                                }
                            });

                        }

                        if(Integer.parseInt(count.getTITIPAN()) > 0) {

                            Titipan.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    TAG_STATUS_CATEGORY = "TITIPAN";
                                    TAG_STATUS_CATEGORY_COUNT = "" + count.getTITIPAN();

                                    startActivity(new Intent(MainActivity.this, CountRoomActivity.class));
                                }
                            });

                        }

                        if(Integer.parseInt(count.getRENCANA_PULANG()) > 0) {
                            Rencana.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    TAG_STATUS_CATEGORY = "RENCANA PULANG";
                                    TAG_STATUS_CATEGORY_COUNT = "" + count.getRENCANA_PULANG();

                                    startActivity(new Intent(MainActivity.this, CountRoomActivity.class));
                                }
                            });

                        }

                        if(Integer.parseInt(count.getBOOKING()) > 0) {
                            Booking.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    TAG_STATUS_CATEGORY = "BOOKING";
                                    TAG_STATUS_CATEGORY_COUNT = "" + count.getBOOKING();

                                    startActivity(new Intent(MainActivity.this, CountRoomActivity.class));
                                }
                            });

                        }


                        if(Integer.parseInt(count.getCLOSED()) > 0) {
                            closed.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    TAG_STATUS_CATEGORY = "CLOSED";
                                    TAG_STATUS_CATEGORY_COUNT = "" + count.getCLOSED();

                                    startActivity(new Intent(MainActivity.this, CountRoomActivity.class));
                                }
                            });

                        }


                    }


                    //fetchKelas();


                }else {
                   // pDialog.cancel();
                   // Toast.makeText(MainActivity.this, "Cek koneksi internet anda", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
               // pDialog.cancel();
               // Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void fetchKelas() {

        pDialog.show();

        API.fetchKelas().enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {

                if (response.isSuccessful()){
                    pDialog.cancel();

                    AllKelasList.addAll(response.body().getDataKelas());

                    arrayKelas.add("-- Kelas --") ;

                    for(ResponseKelas model : AllKelasList) {

                        arrayKelas.add(model.getKelas()) ;
                    }

                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(MainActivity.this, simple_spinner_item,arrayKelas );
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                    spinnerKelas.setAdapter(spinnerArrayAdapter);


                }else {
                    pDialog.cancel();
                    //Toast.makeText(MainActivity.this, "Cek koneksi internet anda", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                pDialog.cancel();
                //Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });








    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);





        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.logout) {

            show_dialog(MainActivity.this);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void show_dialog(Context context){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage("Logout ?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        session.logoutUser();
                        startActivity(new Intent(MainActivity.this,Login.class));
                        finish();


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





    public  void Notification_( String notifID , String request, Context context ){

        AllEntityNotification.clear();


        Call<ResponseData> call = API.notifikasi(notifID, request);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if(response.isSuccessful()) {
                    if (response.body().getDataNotification() != null) {


                        if(response.body().getDataNotification().isEmpty()){

                            // Toast.makeText(mContext, "Tidak Ada Riwayat", Toast.LENGTH_LONG).show();

                        }else{
                            AllEntityNotification.addAll(response.body().getDataNotification()) ;

                            String countBadge = AllEntityNotification.get(0).getCallback() ;

                            if (countBadge != null){

                                if (countBadge.equals("0")){

                                    badge.setVisibility(View.GONE);
                                }else{

                                    badge.setVisibility(View.VISIBLE);
                                    badge.setText(AllEntityNotification.get(0).getCallback());

                                    Toast.makeText(MainActivity.this, "NEW MESSAGE FROM MONITORING ", Toast.LENGTH_LONG).show();

                                    Notification_New( session.getNotifID(),"GETNEW",MainActivity.this);

                                    //Helper.showNotification(context, "MONITORING", "NEW MESSAGE") ;
                                }

                            }


                        }

                    }else{

                        //Toast.makeText(mContext, "Error Response Data", Toast.LENGTH_LONG).show();
                    }

                }else{

                    //Toast.makeText(mContext, "Error Response Data", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {



                //Toast.makeText(mContext, "Internal server error / check your connection", Toast.LENGTH_SHORT).show();
                // Log.e("Error", "onFailure: "+t.getMessage() );
            }
        });
    }




    public  void Notification_New( String notifID , String request, Context context ){

        AllEntityNotification.clear();


        Call<ResponseData> call = API.notifikasi(notifID, request);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if(response.isSuccessful()) {
                    if (response.body().getDataNotification() != null) {


                        if(response.body().getDataNotification().isEmpty()){

                            // Toast.makeText(mContext, "Tidak Ada Riwayat", Toast.LENGTH_LONG).show();

                        }else{
                            AllEntityNotification.addAll(response.body().getDataNotification()) ;

                            String countBadge = AllEntityNotification.get(0).getMessageBody();

                            if (countBadge.equals("")){


                            }else{

                                   String Message = AllEntityNotification.get(0).getMessageBody();
                                   String origin = AllEntityNotification.get(0).getMessageOrigin();
                                   String roomStatus = AllEntityNotification.get(0).getParam2();
                                   String room = AllEntityNotification.get(0).getParam3();

                                   Helper.showNotification(context, "MONITORING - "+ origin , Message + " " + roomStatus) ;
                            }
                        }

                    }else{

                        //Toast.makeText(mContext, "Error Response Data", Toast.LENGTH_LONG).show();
                    }

                }else{

                    //Toast.makeText(mContext, "Error Response Data", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {



                //Toast.makeText(mContext, "Internal server error / check your connection", Toast.LENGTH_SHORT).show();
                // Log.e("Error", "onFailure: "+t.getMessage() );
            }
        });
    }


    boolean isNetworkAvailable(Context context){

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


    private boolean haveNetworkConnection(Context context) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }







    @Override
    protected void onResume() {

        if(session.getUsername() != null){
            TAG_USER = session.getUsername();
            tvProfile.setText("Login as : "+TAG_USER);
            Notification_( session.getNotifID(),"COUNT",MainActivity.this);
        }

        mHandler.postDelayed(m_Runnable,15000);


        super.onResume();
    }
}
