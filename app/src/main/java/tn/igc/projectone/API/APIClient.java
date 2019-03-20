package tn.igc.projectone.API;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private static Retrofit retrofit = null;
    private static String API_BASE = "http://igc.tn:3005/api/";

    public static Retrofit getClient() {
        //creat logger
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //create okhttp client
        //addinterceptor pour enregistrer l'intercepteur
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        return getRetrofit(client);
    }

    public static Retrofit getClientWithToken(final String token) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        OkHttpClient client = new OkHttpClient.Builder().
            addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                        .header("Authorization", token)
                        .method(original.method(), original.body())
                        .build();
                    return chain.proceed(request);
                }
            }).addInterceptor(interceptor).build();
        return getRetrofit(client);
    }

    private static Retrofit getRetrofit(OkHttpClient client) {
        //creat logger
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        retrofit = new Retrofit.Builder()
            .baseUrl(API_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build();
        return retrofit;
    }
}
