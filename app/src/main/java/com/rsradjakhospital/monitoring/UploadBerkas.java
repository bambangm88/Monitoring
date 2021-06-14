package com.rsradjakhospital.monitoring;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.rsradjakhospital.monitoring.Model.ResponseData;
import com.rsradjakhospital.monitoring.api.ApiService;
import com.rsradjakhospital.monitoring.api.Server;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rsradjakhospital.monitoring.DetailRoom.TAG_CASES_ID;
import static com.rsradjakhospital.monitoring.MainActivity.TAG_USER;

public class UploadBerkas extends AppCompatActivity {


    ImageView berkas1 , berkas2 , berkas3 , berkas4 , berkas5 ;

    int bitmap_size = 60; // range 1 - 100
    Bitmap bitmap, decoded;
    ProgressDialog pDialog;
    private int GALLERY = 1, CAMERA = 2;

    Button btn_upload ;

    ApiService API;

    public static String chooseUpload = "";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_berkas);

        API = Server.getAPIService();


        berkas1 = findViewById(R.id.berkas1);
        berkas2 = findViewById(R.id.berkas2);
        berkas3 = findViewById(R.id.berkas3);
        berkas4 = findViewById(R.id.berkas4);
        berkas5 = findViewById(R.id.berkas5);
        btn_upload = findViewById(R.id.btn_upload);

        requestMultiplePermissions();


        berkas1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                berkas1.setTag("ada");
                chooseUpload = "1" ;
                showPictureDialog();
            }
        });

        berkas2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                berkas2.setTag("ada");
                chooseUpload = "2" ;
                showPictureDialog();
            }
        });

        berkas3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                berkas3.setTag("ada");
                chooseUpload = "3" ;
                showPictureDialog();
            }
        });


        berkas4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                berkas4.setTag("ada");
                chooseUpload = "4" ;
                showPictureDialog();
            }
        });


        berkas5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                berkas5.setTag("ada");
                chooseUpload = "5" ;
                showPictureDialog();
            }
        });

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String img_berkas1 = getStringImage(imageView2Bitmap(berkas1)) ;
                String img_berkas2 = getStringImage(imageView2Bitmap(berkas2)) ;
                String img_berkas3 = getStringImage(imageView2Bitmap(berkas3)) ;
                String img_berkas4 = getStringImage(imageView2Bitmap(berkas4)) ;
                String img_berkas5 = getStringImage(imageView2Bitmap(berkas5)) ;


                if (!berkas1.getTag().toString().equals("ada")){
                    img_berkas1 = "" ;
                }

                if (!berkas2.getTag().toString().equals("ada")){
                    img_berkas2 = "" ;
                }

                if (!berkas3.getTag().toString().equals("ada")){
                    img_berkas3 = "" ;
                }

                if (!berkas4.getTag().toString().equals("ada")){
                    img_berkas4 = "" ;
                }

                if (!berkas5.getTag().toString().equals("ada")){
                    img_berkas5 = "" ;
                }


                saveRBerkas(img_berkas1,img_berkas2,img_berkas3,img_berkas4,img_berkas5,UploadBerkas.this);




            }
        });



    }





    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select berkas from gallery",
                "Capture berkas from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }


    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {

                    if(chooseUpload.equals("1")){
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                        berkas1.setImageBitmap(bitmap);
                    }

                    if(chooseUpload.equals("2")){
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                        berkas2.setImageBitmap(bitmap);
                    }

                    if(chooseUpload.equals("3")){
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                        berkas3.setImageBitmap(bitmap);
                    }

                    if(chooseUpload.equals("4")){
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                        berkas4.setImageBitmap(bitmap);
                    }

                    if(chooseUpload.equals("5")){
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                        berkas5.setImageBitmap(bitmap);
                    }



                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(UploadBerkas.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {


            if(chooseUpload.equals("1")){
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                berkas1.setImageBitmap(thumbnail);
            }

            if(chooseUpload.equals("2")){
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                berkas2.setImageBitmap(thumbnail);
            }

            if(chooseUpload.equals("3")){
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                berkas3.setImageBitmap(thumbnail);
            }

            if(chooseUpload.equals("4")){
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                berkas4.setImageBitmap(thumbnail);
            }

            if(chooseUpload.equals("5")){
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                berkas5.setImageBitmap(thumbnail);
            }

           // imageView2Bitmap(berkas1);

           // Toast.makeText(UploadBerkas.this, "encode"+getStringImage(imageView2Bitmap(berkas1)), Toast.LENGTH_SHORT).show();
        }
    }





    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    private void  requestMultiplePermissions(){
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            //openSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<com.karumi.dexter.listener.PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }


                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    private Bitmap imageView2Bitmap(ImageView view){
        Bitmap bitmap = ((BitmapDrawable)view.getDrawable()).getBitmap();
        return bitmap;
    }





    public void saveRBerkas(String berkas1,String berkas2, String berkas3, String berkas4, String berkas5,  Context context){

        pDialog = new ProgressDialog(context);
        pDialog.setCancelable(false);
        pDialog.setMessage("Memuat...");
        pDialog.show();


        Call<ResponseData> call = API.saveBerkas(TAG_CASES_ID,berkas1,berkas2,berkas3,berkas4,berkas5,TAG_USER);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if(response.isSuccessful()) {

                    pDialog.cancel();
                    Toast.makeText(context, "Success", Toast.LENGTH_LONG).show();
                    //TAG_SELESAI_TULIS_RESEP = "selesai" ;
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
