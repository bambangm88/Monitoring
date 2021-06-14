package com.rsradjakhospital.monitoring.Model;

import com.google.gson.annotations.SerializedName;

public class ResponseEntityNotification {


    @SerializedName("MessageOrigin")
    private String MessageOrigin;

    @SerializedName("MessageDate")
    private String MessageDate;



    @SerializedName("C_MessageDate")
    private String C_MessageDate;


    @SerializedName("Param1")
    private String Param1;

    @SerializedName("Param2")
    private String Param2;

    @SerializedName("MessageBody")
    private String MessageBody;

    @SerializedName("callback")
    private String callback;

    @SerializedName("Param3")
    private String Param3;

    @SerializedName("PatientCode")
    private String PatientCode;

    @SerializedName("FirstName")
    private String FirstName;

    @SerializedName("Status")
    private String Status;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }



    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }



    public String getParam3() {
        return Param3;
    }

    public void setParam3(String param3) {
        Param3 = param3;
    }





    public String getPatientCode() {
        return PatientCode;
    }

    public void setPatientCode(String patientCode) {
        PatientCode = patientCode;
    }



    public String getMessageOrigin() {
        return MessageOrigin;
    }

    public void setMessageOrigin(String messageOrigin) {
        MessageOrigin = messageOrigin;
    }

    public String getMessageDate() {
        return MessageDate;
    }

    public void setMessageDate(String messageDate) {
        MessageDate = messageDate;
    }

    public String getParam1() {
        return Param1;
    }

    public void setParam1(String param1) {
        Param1 = param1;
    }

    public String getParam2() {
        return Param2;
    }

    public void setParam2(String param2) {
        Param2 = param2;
    }

    public String getMessageBody() {
        return MessageBody;
    }

    public void setMessageBody(String messageBody) {
        MessageBody = messageBody;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public String getC_MessageDate() {
        return C_MessageDate;
    }

    public void setC_MessageDate(String c_MessageDate) {
        C_MessageDate = c_MessageDate;
    }




























}
