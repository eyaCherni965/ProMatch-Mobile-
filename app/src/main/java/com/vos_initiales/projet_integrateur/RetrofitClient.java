package com.vos_initiales.projet_integrateur;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import com.google.gson.GsonBuilder;

public class RetrofitClient {
    public static final String HOST = "10.0.2.2";
    public static final String PORT = "3000";
    public static final String BASE_URL = "http://" + HOST + ":" + PORT + "/";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create()) // <- Simple et propre
                    .build();

        }
        return retrofit;
    }
}
