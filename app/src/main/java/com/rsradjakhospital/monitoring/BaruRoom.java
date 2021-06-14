package com.rsradjakhospital.monitoring;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rsradjakhospital.monitoring.Model.ResponseData;
import com.rsradjakhospital.monitoring.Model.ResponseKelas;
import com.rsradjakhospital.monitoring.api.ApiService;
import com.rsradjakhospital.monitoring.api.Server;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.layout.simple_spinner_item;
import static com.rsradjakhospital.monitoring.DetailRoom.TAG_ROOM;

public class BaruRoom extends AppCompatActivity {


    ProgressDialog pDialog;
    ApiService API;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    EditText tgl_msk ;


    private Spinner spinnerKelas;

    private TextView title ;

    private ArrayList<String> arrayKelas = new ArrayList<String>();
    private List<ResponseKelas> AllKelasList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baru_room);

        tgl_msk = findViewById(R.id.tgl_msk);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        spinnerKelas = findViewById(R.id.spMonitor);
        title = findViewById(R.id.toolbar_title);

        API = Server.getAPIService();

        pDialog = new ProgressDialog(BaruRoom.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Memuat...");

        title.setText("Baru - "+TAG_ROOM);

        fetchKelas() ;

        tgl_msk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
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

                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(BaruRoom.this, simple_spinner_item,arrayKelas );
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                    spinnerKelas.setAdapter(spinnerArrayAdapter);


                }else {
                    pDialog.cancel();
                    Toast.makeText(BaruRoom.this, "Cek koneksi internet anda", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                pDialog.cancel();
                Toast.makeText(BaruRoom.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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

                tgl_msk.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }



}
