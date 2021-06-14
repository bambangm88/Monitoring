package com.rsradjakhospital.monitoring;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.rsradjakhospital.monitoring.Model.ResponseData;
import com.rsradjakhospital.monitoring.api.ApiService;
import com.rsradjakhospital.monitoring.api.Server;
import com.rsradjakhospital.monitoring.paint.PaintView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rsradjakhospital.monitoring.DetailRoom.TAG_CASES_ID;
import static com.rsradjakhospital.monitoring.DetailRoom.TAG_DOCTOR_ID;
import static com.rsradjakhospital.monitoring.DetailRoom.TAG_SELESAI_TULIS_RESEP;
import static com.rsradjakhospital.monitoring.MainActivity.TAG_USER;

public class signature extends AppCompatActivity {

    private PaintView paintView;
    private int defaultColor;
    private int STORAGE_PERMISSION_CODE = 1;

    public static String TAG_IMAGE_STRING = "";

    ProgressDialog pDialog;

    private FloatingActionButton fab_main, fab_save, fab_undo , fab_redo;
    private Animation fab_open, fab_close, fab_clock, fab_anticlock;
    TextView textview_save, textview_undo , textview_redo;

    Boolean isOpen = false;

    ApiService API;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Button button;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);


        paintView = findViewById(R.id.paintView);

        DisplayMetrics displayMetrics = new DisplayMetrics();



        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        paintView.initialise(displayMetrics);

        API = Server.getAPIService();

        fab_main = findViewById(R.id.fab);
        fab_save = findViewById(R.id.fabSave);
        fab_undo = findViewById(R.id.fabUndo);
        fab_redo = findViewById(R.id.fabRedo);

        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_clock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_clock);
        fab_anticlock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_anticlock);

        textview_save = (TextView) findViewById(R.id.textview_save);
        textview_undo = (TextView) findViewById(R.id.textview_undo);
        textview_redo = (TextView) findViewById(R.id.textview_redo);



        fab_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isOpen) {

                    textview_save.setVisibility(View.INVISIBLE);
                    textview_undo.setVisibility(View.INVISIBLE);
                    textview_redo.setVisibility(View.INVISIBLE);
                    fab_save.startAnimation(fab_close);
                    fab_undo.startAnimation(fab_close);
                    fab_redo.startAnimation(fab_close);
                    fab_main.startAnimation(fab_anticlock);
                    fab_save.setClickable(false);
                    fab_undo.setClickable(false);
                    fab_redo.setClickable(false);
                    isOpen = false;
                } else {
                    textview_save.setVisibility(View.VISIBLE);
                    textview_undo.setVisibility(View.VISIBLE);
                    textview_redo.setVisibility(View.VISIBLE);
                    fab_save.startAnimation(fab_open);
                    fab_undo.startAnimation(fab_open);
                    fab_redo.startAnimation(fab_open);
                    fab_main.startAnimation(fab_clock);
                    fab_save.setClickable(true);
                    fab_undo.setClickable(true);
                    fab_redo.setClickable(true);
                    isOpen = true;
                }

            }
        });


        fab_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                paintView.saveImage();
                saveResep(TAG_IMAGE_STRING, signature.this);

            }
        });

        fab_undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paintView.undo();
                Toast.makeText(getApplicationContext(), "Undo", Toast.LENGTH_SHORT).show();

            }
        });

        fab_redo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paintView.redo();
                Toast.makeText(getApplicationContext(), "Redo", Toast.LENGTH_SHORT).show();

            }
        });







    }


    private void requestStoragePermission () {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("Needed to save image")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            ActivityCompat.requestPermissions(signature.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();

                        }

                    })
                    .create().show();

        } else {

            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == STORAGE_PERMISSION_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "Access Diizinkan", Toast.LENGTH_LONG).show();

            } else {

                Toast.makeText(this, "Access Ditolak", Toast.LENGTH_LONG).show();

            }

        }

    }



    public void saveResep(String foto_resep, Context context){

        pDialog = new ProgressDialog(context);
        pDialog.setCancelable(false);
        pDialog.setMessage("Memuat...");
        pDialog.show();


        Call<ResponseData> call = API.saveResep(TAG_CASES_ID,foto_resep,TAG_DOCTOR_ID,TAG_USER);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if(response.isSuccessful()) {

                    pDialog.cancel();
                    Toast.makeText(context, "Success", Toast.LENGTH_LONG).show();
                    TAG_SELESAI_TULIS_RESEP = "selesai" ;
                    finish();


                }else{
                    pDialog.cancel();
                    Toast.makeText(context, "Error Response Data", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

                pDialog.cancel();

                Toast.makeText(context, "Internal server error / check your connection", Toast.LENGTH_SHORT).show();
                Log.e("Error", "onFailure: "+t.getMessage() );
            }
        });
    }

















}