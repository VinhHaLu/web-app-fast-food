package com.example.fastfood.OpenrationRetrofitApi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String BASE_URL = "http://192.168.31.170/RestApi/";

    public static final String BASE_URL1 = "http://446ee7a0ef37.ngrok.io";

    public static Retrofit retrofit = null;

    public static Retrofit getApiClient(){

        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


}
