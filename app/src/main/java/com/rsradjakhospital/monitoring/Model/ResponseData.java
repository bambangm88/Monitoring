package com.rsradjakhospital.monitoring.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ResponseData {



    @SerializedName("dataRoom")
    @Expose
    private List<ResponseEntityRoom> dataRoom = null;

    private List<ResponseEntityRoom> pasienPulang = null;

    public List<ResponseEntityRoom> getPasienPulang() {
        return pasienPulang;
    }

    public void setPasienPulang(List<ResponseEntityRoom> pasienPulang) {
        this.pasienPulang = pasienPulang;
    }



    public List<ResponseEntityRoom> getDataRoom() {
        return dataRoom;
    }



    public void setDataRoom(List<ResponseEntityRoom> dataRoom) {
        this.dataRoom = dataRoom;
    }



    @SerializedName("dataCountRoom")
    @Expose
    private List<ResponseEntityCountRoom> dataCountRoom = null;

    public List<ResponseEntityCountRoom> getDataCountRoom() {
        return dataCountRoom;
    }

    public void setDataCountRoom(List<ResponseEntityCountRoom> dataCountRoom) {
        this.dataCountRoom = dataCountRoom;
    }


    @SerializedName("dataCountByCategory")
    @Expose
    private List<ResponseEntityCountRoomByCategory> dataCountByCategory = null;


    public List<ResponseEntityCountRoomByCategory> getDataCountByCategory() {
        return dataCountByCategory;
    }

    public void setDataCountByCategory(List<ResponseEntityCountRoomByCategory> dataCountByCategory) {
        this.dataCountByCategory = dataCountByCategory;
    }





    @SerializedName("dataLogin")
    @Expose
    private List<ResponseEntityLogin> dataLogin = null;

    public List<ResponseEntityLogin> getDataLogin() {
        return dataLogin;
    }

    public void setDataLogin(List<ResponseEntityLogin> dataLogin) {
        this.dataLogin = dataLogin;
    }


    @SerializedName("dataDoctor")
    @Expose
    private List<ResponseEntityDoctor> dataDoctor = null;

    public List<ResponseEntityDoctor> getDataDoctor() {
        return dataDoctor;
    }

    public void setDataDoctor(List<ResponseEntityDoctor> dataDoctor) {
        this.dataDoctor = dataDoctor;
    }





    @SerializedName("data")
    @Expose
    private List<ResponseMonitor> data = null;

    public List<ResponseMonitor> getData() {
        return data;
    }

    public void setData(List<ResponseMonitor> data) {
        this.data = data;
    }



    @SerializedName("dataFloor")
    @Expose
    private List<ResponseFloor> dataFloor = null;


    public List<ResponseFloor> getDataFloor() {
        return dataFloor;
    }

    public void setDataFloor(List<ResponseFloor> dataFloor) {
        this.dataFloor = dataFloor;
    }



    @SerializedName("dataKelas")
    @Expose
    private List<ResponseKelas> dataKelas = null;


    public List<ResponseKelas> getDataKelas() {
        return dataKelas;
    }

    public void setDataKelas(List<ResponseKelas> dataKelas) {
        this.dataKelas = dataKelas;
    }


    @SerializedName("dataDetail")
    @Expose
    private List<ResponseEntityRoom> dataDetail = null;


    public List<ResponseEntityRoom> getDataDetail() {
        return dataDetail;
    }

    public void setDataDetail(List<ResponseEntityRoom> dataDetail) {
        this.dataDetail = dataDetail;
    }



    @SerializedName("dataCaraPulang")
    @Expose
    private List<ResponseEntityCaraPulang> dataCaraPulang = null;


    public List<ResponseEntityCaraPulang> getDataCaraPulang() {
        return dataCaraPulang;
    }

    public void setDataCaraPulang(List<ResponseEntityCaraPulang> dataCaraPulang) {
        this.dataCaraPulang = dataCaraPulang;
    }


    @SerializedName("dataNotification")
    @Expose
    private List<ResponseEntityNotification> dataNotification = null;


    public List<ResponseEntityNotification> getDataNotification() {
        return dataNotification;
    }

    public void setDataNotification(List<ResponseEntityNotification> dataNotification) {
        this.dataNotification = dataNotification;
    }



    @SerializedName("dataListRoom")
    @Expose
    private List<ResponseEntityListRoom> dataListRoom = null;


    public List<ResponseEntityListRoom> getDataListRoom() {
        return dataListRoom;
    }

    public void setDataListRoom(List<ResponseEntityListRoom> dataListRoom) {
        this.dataListRoom = dataListRoom;
    }


    @SerializedName("dataVersion")
    @Expose
    private List<ResponseEntityVersion> dataVersion = null;

    public List<ResponseEntityVersion> getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(List<ResponseEntityVersion> dataVersion) {
        this.dataVersion = dataVersion;
    }








}






