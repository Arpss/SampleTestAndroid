package com.example.myapplication.apiservices;

import com.example.myapplication.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class BaseOkHttpBuilder {
    /**
     * builds the OkHttpClient to consume the REST API
     *
     * @return {@link OkHttpClient}
     */
    public OkHttpClient build() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }

        OkHttpClient client = builder.build();
        return client;

    }
}
