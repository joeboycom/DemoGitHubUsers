package com.example.githubusers.API;

import com.example.githubusers.API.ApiResponse.GetUserDetailApiResponse;
import com.example.githubusers.BuildConfig;
import com.example.githubusers.Model.UserList;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;


/**
 * Created by joewu on 2018/8/7.
 */

public interface APIUtility
{
    String BASE_URL         = "https://api.github.com/";
    String CLIENT_ID        = "baf44fa09d5c2bda1871";
    String CLIENT_SECRET    = "d649cb8a14b4ccc7185f31d6f132494745bbce12";

    OkHttpClient.Builder httpClient = BuildConfig.DEBUG ?
            new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            :
            new OkHttpClient.Builder();

    Retrofit API_BUILDER = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build();

// ==================================================================================
// users
// ==================================================================================
    @GET("users")
    Call<List<UserList>> getUserListAPI(@Query("since") String since, @Query("per_page") String per_page, @Query("client_id") String client_id, @Query("client_secret") String client_secret);

    @GET
    Call<List<UserList>> getUserListAPI(@Url String url, @Query("client_id") String client_id, @Query("client_secret") String client_secret);

    @GET("users/{login}")
    Call<GetUserDetailApiResponse> getUserDetailAPI(@Path("login") String login, @Query("client_id") String client_id, @Query("client_secret") String client_secret);
}
