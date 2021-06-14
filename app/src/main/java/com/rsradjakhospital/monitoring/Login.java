package com.rsradjakhospital.monitoring;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rsradjakhospital.monitoring.Helper.Helper;
import com.rsradjakhospital.monitoring.Model.ResponseData;
import com.rsradjakhospital.monitoring.api.ApiService;
import com.rsradjakhospital.monitoring.api.Server;
import com.rsradjakhospital.monitoring.sessionManager.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rsradjakhospital.monitoring.MainActivity.TAG_USER;

public class Login extends AppCompatActivity {

    Button btn_login ;

    ProgressDialog pDialog;

    EditText username , password ;


    private Context mContext;
    private ApiService API;

    SessionManager session;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new SessionManager(getApplicationContext());


        if (session.isLoggedIn()) {

            TAG_USER = session.getUsername();

            Intent i = new Intent(Login.this, MainActivity.class);

            startActivity(i);
            finish();
        }



        btn_login = findViewById(R.id.btn_login);

        mContext = this ;
        API = Server.getAPIService();

        pDialog = new ProgressDialog(Login.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Memuat...");



        username = findViewById(R.id.et_username) ;
        password = findViewById(R.id.et_password);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (username.getText().toString().equals("") || password.getText().toString().equals("")){

                    Toast.makeText(mContext, "Silahkan Diisi", Toast.LENGTH_LONG).show();

                }else{

                    Login(username.getText().toString(),password.getText().toString());


                }



            }
        });


        Helper.checkVersionUpdate(this);


    }








    private void Login(String username, String password){


        pDialog.show();


        Call<ResponseData> call = API.Login(username,password);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if(response.isSuccessful()) {
                    if (response.body().getDataLogin() != null) {


                        if(response.body().getDataLogin().isEmpty()){

                            pDialog.cancel();

                            Toast.makeText(mContext, "Username / Password salah", Toast.LENGTH_LONG).show();



                        }else{


                            String unit = response.body().getDataLogin().get(0).getUnit();
                            String notifID = response.body().getDataLogin().get(0).getNotifID();
;
                            session.createLoginSession(username,unit,notifID);
                            TAG_USER = username ;
                            Intent intent = new Intent(Login.this,MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(mContext, "Login Berhasil", Toast.LENGTH_LONG).show();
                            finish();
                        }



                    }else{
                        pDialog.cancel();

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

                Toast.makeText(mContext, "Internal server error / check your connection", Toast.LENGTH_SHORT).show();
                Log.e("Error", "onFailure: "+t.getMessage() );
            }
        });
    }





}
