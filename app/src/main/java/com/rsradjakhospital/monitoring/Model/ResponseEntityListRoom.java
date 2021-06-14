package com.rsradjakhospital.monitoring.Model;

import com.google.gson.annotations.SerializedName;

public class ResponseEntityListRoom {


    @SerializedName("Status")
    private String Status;

    @SerializedName("PatientStatus")
    private String PatientStatus;

    @SerializedName("Condition")
    private String Condition;


    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getPatientStatus() {
        return PatientStatus;
    }

    public void setPatientStatus(String patientStatus) {
        PatientStatus = patientStatus;
    }

    public String getCondition() {
        return Condition;
    }

    public void setCondition(String condition) {
        Condition = condition;
    }









}
