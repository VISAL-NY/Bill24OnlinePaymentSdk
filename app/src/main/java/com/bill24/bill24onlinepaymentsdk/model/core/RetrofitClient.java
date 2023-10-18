package com.bill24.bill24onlinepaymentsdk.model.core;

import com.bill24.bill24onlinepaymentsdk.model.conts.Constant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient instance=null;
    private ApiClient apiClient;

    private RetrofitClient(){
        Retrofit retrofit=new Retrofit.Builder().baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        apiClient=retrofit.create(ApiClient.class);
    }

    public static RetrofitClient getInstance(){
        if(instance==null){
            instance=new RetrofitClient();
        }
        return instance;
    }

    public ApiClient getApiClient(){
        return apiClient;
    }

}
