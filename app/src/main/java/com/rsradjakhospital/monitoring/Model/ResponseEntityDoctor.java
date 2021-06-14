package com.rsradjakhospital.monitoring.Model;

import com.google.gson.annotations.SerializedName;

public class ResponseEntityDoctor {


    @SerializedName("FirstName")
    private String FirstName;


    @SerializedName("DoctorID")
    private String DoctorID;


    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getDoctorID() {
        return DoctorID;
    }

    public void setDoctorID(String doctorID) {
        DoctorID = doctorID;
    }
















}
