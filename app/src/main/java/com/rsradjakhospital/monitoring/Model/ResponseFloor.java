package com.rsradjakhospital.monitoring.Model;

import com.google.gson.annotations.SerializedName;

public class ResponseFloor {


    @SerializedName("Floor")
    private String Floor;
    public String getFloor() {
        return Floor;
    }

    public void setFloor(String floor) {
        this.Floor = floor;
    }



}
