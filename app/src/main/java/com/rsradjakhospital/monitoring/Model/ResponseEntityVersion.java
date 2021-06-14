package com.rsradjakhospital.monitoring.Model;

import com.google.gson.annotations.SerializedName;

public class ResponseEntityVersion {


    @SerializedName("version")
    private String version;


    @SerializedName("versionCode")
    private String versionCode;

    @SerializedName("AppIdentity")
    private String AppIdentity;

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAppIdentity() {
        return AppIdentity;
    }

    public void setAppIdentity(String appIdentity) {
        AppIdentity = appIdentity;
    }





















}
