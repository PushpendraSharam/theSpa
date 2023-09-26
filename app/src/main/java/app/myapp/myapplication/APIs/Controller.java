package app.myapp.myapplication.APIs;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Controller {
    public static String apiUrl = "https://thespa.cloud/admin/public/api/";
    public static String baseUrl = "https://www.icakes.ca/";
    public static retrofit2.Retrofit retrofit;
    public static Controller client;


    public static ApiInterface getInstance() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(apiUrl).addConverterFactory(GsonConverterFactory.create()).client(client).build();
        ApiInterface api = retrofit.create(ApiInterface.class);

        return api;
    }
}
