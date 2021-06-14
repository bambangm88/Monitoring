package com.rsradjakhospital.monitoring.Model;

import com.google.gson.annotations.SerializedName;

public class ResponseKelas {




    @SerializedName("Kelas")
    private String Kelas;


    public String getKelas() {
        return Kelas;
    }

    public void setKelas(String kelas) {
        Kelas = kelas;
    }



}
