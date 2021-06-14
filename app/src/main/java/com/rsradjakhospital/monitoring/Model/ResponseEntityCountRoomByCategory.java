package com.rsradjakhospital.monitoring.Model;

import com.google.gson.annotations.SerializedName;

public class ResponseEntityCountRoomByCategory {



    @SerializedName("Category")
    private String Category;

    @SerializedName("jumlah")
    private String jumlah;


    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }










}
