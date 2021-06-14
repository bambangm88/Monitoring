package com.rsradjakhospital.monitoring;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.rsradjakhospital.monitoring.Model.ResponseData;
import com.rsradjakhospital.monitoring.api.ApiService;
import com.rsradjakhospital.monitoring.api.Server;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rsradjakhospital.monitoring.MainActivity.TAG_USER;
import static com.rsradjakhospital.monitoring.UbahStatusBed.TAG_OFFICE_NAME;

public class Booking extends AppCompatActivity {


    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private TimePickerDialog mTimePicker;

    public static String  TAG_SELESAI_BOOKING = "" ;

    private Context mContext;
    ProgressDialog pDialog;
    private ApiService API;

    EditText tv_tgl , tv_jam , tv_nama , tv_telp;

    Button btn_simpan ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        mContext = this ;
        API = Server.getAPIService();

        dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);

        tv_tgl = findViewById(R.id.tv_tgl);
        tv_jam = findViewById(R.id.tv_jam);
        tv_nama = findViewById(R.id.tv_nama);
        tv_telp = findViewById(R.id.no_telp);
        btn_simpan = findViewById(R.id.btn_apply);

        tv_tgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDateDialog() ;

            }
        });


        tv_jam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showTimeDialog() ;

            }
        });

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String nama = tv_nama.getText().toString();
                String tgl = tv_tgl.getText().toString();
                String jam = tv_jam.getText().toString();
                String telp = tv_telp.getText().toString();

                if (nama.equals("") || tgl.equals("")  || jam.equals("") || telp.equals("")  ){

                    Toast.makeText(Booking.this,"Silahkan Isi",Toast.LENGTH_LONG).show();

                }else{

                    String tempNote = nama + "/" + tgl + "/" + jam + "/" + telp ;

                    show_dialog_booking(Booking.this,TAG_OFFICE_NAME,"BOOKING",tempNote) ;

                }





            }
        });


    }





    private void showDateDialog(){

        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                tv_tgl.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }


    private void showTimeDialog(){
        // TODO Auto-generated method stub
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        mTimePicker = new TimePickerDialog(Booking.this, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                tv_jam.setText(selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }



    private void DoBooking(String office,  String status , String TempNote){

        pDialog = new ProgressDialog(Booking.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Memuat...");
        pDialog.show();


        Call<ResponseData> call = API.updateStatusRoom(office,status,TempNote,TAG_USER);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if(response.isSuccessful()) {

                    TAG_SELESAI_BOOKING = "SELESAI" ;

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


    public void show_dialog_booking(Context context, String office , String statusBed, String tempNote){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage("Ubah Status Bed Menjadi "+statusBed+" ?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        DoBooking(office,statusBed,tempNote);
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
