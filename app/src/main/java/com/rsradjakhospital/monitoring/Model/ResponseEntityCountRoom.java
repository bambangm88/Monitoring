package com.rsradjakhospital.monitoring.Model;

import com.google.gson.annotations.SerializedName;

public class ResponseEntityCountRoom {


    @SerializedName("KOSONG")
    private String KOSONG;

    @SerializedName("ISI")
    private String ISI;

    @SerializedName("TITIPAN")
    private String TITIPAN;

    @SerializedName("RUSAK")
    private String RUSAK;

    @SerializedName("RENCANA_PULANG")
    private String RENCANA_PULANG;

    @SerializedName("BOOKING")
    private String BOOKING;

    @SerializedName("SELESAI_PERBAIKAN")
    private String SELESAI_PERBAIKAN;


    @SerializedName("CLOSED")
    private String CLOSED;

    public String getCLOSED() {
        return CLOSED;
    }

    public void setCLOSED(String CLOSED) {
        this.CLOSED = CLOSED;
    }


    public String getSELESAI_PERBAIKAN() {
        return SELESAI_PERBAIKAN;
    }

    public void setSELESAI_PERBAIKAN(String SELESAI_PERBAIKAN) {
        this.SELESAI_PERBAIKAN = SELESAI_PERBAIKAN;
    }

    public String getBOOKING() {
        return BOOKING;
    }

    public void setBOOKING(String BOOKING) {
        this.BOOKING = BOOKING;
    }



    public String getKOSONG() {
        return KOSONG;
    }

    public void setKOSONG(String KOSONG) {
        this.KOSONG = KOSONG;
    }

    public String getISI() {
        return ISI;
    }

    public void setISI(String ISI) {
        this.ISI = ISI;
    }

    public String getTITIPAN() {
        return TITIPAN;
    }

    public void setTITIPAN(String TITIPAN) {
        this.TITIPAN = TITIPAN;
    }

    public String getRUSAK() {
        return RUSAK;
    }

    public void setRUSAK(String RUSAK) {
        this.RUSAK = RUSAK;
    }

    public String getRENCANA_PULANG() {
        return RENCANA_PULANG;
    }

    public void setRENCANA_PULANG(String RENCANA_PULANG) {
        this.RENCANA_PULANG = RENCANA_PULANG;
    }






}
