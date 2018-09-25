package com.blub.amazontest.retro;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit INSTANCE;
    private static final String BASE_URL = "https://s3.amazonaws.com";

    public static Retrofit getInstance() {
        if (INSTANCE == null) {
            return new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return INSTANCE;
    }

    public static CallService getCallService() {
        return getInstance().create(CallService.class);
    }
}
