package com.rsradjakhospital.monitoring.Model;

import com.google.gson.annotations.SerializedName;

public class ResponseEntityLogin {


    @SerializedName("aUserID")
    private String UserName;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }



    @SerializedName("unit")
    private String unit;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }


    @SerializedName("NotifID")
    private String NotifID;

    public String getNotifID() {
        return NotifID;
    }

    public void setNotifID(String notifID) {
        NotifID = notifID;
    }
















}
