package com.example.bumbee.activities.Reading;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RetrofitAPi{
    @GET
    Call<NewsModel> getAllNews(@Url String url);

    @GET
    Call<NewsModel>getNewsByCategory(@Url String url);

}
