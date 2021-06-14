package com.rsradjakhospital.monitoring.api;


import static com.rsradjakhospital.monitoring.util.Utility.BASE_URL_API;

public class Server {
    public static ApiService getAPIService() {

        return Client.getClient(BASE_URL_API).create(ApiService.class);
    }

}
