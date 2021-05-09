package com.karayadar.app;

import com.karayadar.app.api.APIService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {


    private static final String BASE_URL = "http://appkarayadar.000webhostapp.com/public/";
    //private static final String BASE_URL = "http://192.168.10.4/RetrofitKarayaDar/public/";
    public static final String Image_url="http://appkarayadar.000webhostapp.com/ImagesUploadApi/uploads/";
    //public static final String Image_url="http://192.168.10.4/ImagesUploadApi/uploads/";
    private static RetrofitClient mInstance;
    private Retrofit retrofit;

    private RetrofitClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    public APIService getApi(){
        return retrofit.create(APIService.class);
    }
}