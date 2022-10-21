package com.snapmint.merchantsdk.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.snapmint.merchantsdk.constants.SnapmintConstants;
import com.snapmint.merchantsdk.constants.SnapmintConfiguration;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiBuilder {
    private static Retrofit retrofit = null;
    private final static int TIMEOUT_IN_SECONDS = 60;
    private static final String HEADER_CACHE_CONTROL = "Cache-Control";
    private static final String HEADER_PRAGMA = "Pragma";
    private static int cacheSize = 10 * 1024 * 1024; // 10 MB

    public static <T> T create(final Class<T> serviceInterface) {
        String baseUrl;
        SnapmintConfiguration snapmintConfiguration = new SnapmintConfiguration();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().
                addInterceptor(interceptor)
                .connectTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS).build();

        Gson gson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SnapmintConstants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(serviceInterface);
    }

}
