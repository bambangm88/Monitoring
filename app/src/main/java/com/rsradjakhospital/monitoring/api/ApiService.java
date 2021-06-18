package com.rsradjakhospital.monitoring.api;


import com.rsradjakhospital.monitoring.Model.ResponseData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;



public interface ApiService {

    @GET("menu/index_get")
    Call<ResponseData> fetchMonitor();

    @GET("floor/index_get")
    Call<ResponseData> fetchFloor();

    @GET("kelas/index_get")
    Call<ResponseData> fetchKelas();

    @FormUrlEncoded
    @POST("getRoom")
    Call<ResponseData> FetchRoom(@Field("category") String category,@Field("floor") String floor,@Field("kelas") String kelas);


    @FormUrlEncoded
    @POST("login")
    Call<ResponseData> Login(@Field("username") String username , @Field("password") String password);


    @FormUrlEncoded
    @POST("DetailRoom")
    Call<ResponseData> DetailRoom(@Field("username") String username );

    @FormUrlEncoded
    @POST("getListRoomStatus")
    Call<ResponseData> getListRoom(@Field("status") String status);



    @FormUrlEncoded
    @POST("pasienPulang")
    Call<ResponseData> updatePasienPulang(@Field("caseID") String caseID,@Field("reason") String reason,@Field("diagnosaAkhir") String diagnosaAkhir,@Field("sUser") String sUser);


    @FormUrlEncoded
    @POST("updateStatusRoom")
    Call<ResponseData> updateStatusRoom(@Field("office") String office,@Field("status") String status,@Field("tempNote") String tempNote,@Field("sUser") String sUser);

    @GET("getDoctor")
    Call<ResponseData> fetchDoctor();


    @FormUrlEncoded
    @POST("simpanDataPasien")
    Call<ResponseData> simpanDataPasien(@Field("CaseID") String CaseID,@Field("statusBed") String statusBed , @Field("Keterangan") String Keterangan,@Field("IDDokter") String IDDokter,@Field("NamaBed") String NamaBed,@Field("sUser") String sUser
            ,@Field("diagnosa") String diagnosa);


    @FormUrlEncoded
    @POST("transferPasien")
    Call<ResponseData> transferPasien(@Field("RuanganTujuan") String RuanganTujuan,@Field("CaseID") String CaseID , @Field("RuanganAsal") String RuanganAsal,@Field("sUser") String sUser);

    @FormUrlEncoded
    @POST("saveResep")
    Call<ResponseData> saveResep(@Field("case_ID") String case_ID ,@Field("Foto_Resep") String Foto_Resep, @Field("ID_Doctor") String ID_Doctor,@Field("sUser") String sUser);

    @GET("getCountAllRoom/index_get")
    Call<ResponseData> fetchCountAllRoom();

    @FormUrlEncoded
    @POST("GetCountAllRoomByCategory")
    Call<ResponseData> fetchCountAllRoomByCategory(@Field("status") String status);

    @FormUrlEncoded
    @POST("insertBerkas")
    Call<ResponseData> saveBerkas(@Field("case_ID") String case_ID ,
                                  @Field("berkas1") String berkas1 ,
                                  @Field("berkas2") String berkas2 ,
                                  @Field("berkas3") String berkas3 ,
                                  @Field("berkas4") String berkas4 ,
                                  @Field("berkas5") String berkas5 ,
                                  @Field("sUser") String sUser);

    @GET("getListCaraPulang")
    Call<ResponseData> fetchListCaraPulang();

    @FormUrlEncoded
    @POST("notification")
    Call<ResponseData> notifikasi(
                                  @Field("notifID") String notifID,
                                  @Field("request") String request);

    @FormUrlEncoded
    @POST("updateNotification")
    Call<ResponseData> UpdateNotifikasi(
                                  @Field("notifID") String notifID);

    @FormUrlEncoded
    @POST("checkVersion")
    Call<ResponseData> CheckVersion(@Field("app") String app);



}
