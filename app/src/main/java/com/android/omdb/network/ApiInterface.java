package com.android.omdb.network;


import com.android.omdb.model.MovieResponse;
import com.android.omdb.model.SearchResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiInterface {


    @GET("?")
    retrofit2.Call<SearchResponse> getSearchResult(@Query("s") String term, @Query("apikey") String apiKey, @Query("page") String page);

    @GET("?")
    retrofit2.Call<MovieResponse> getSearchResultDetail(@Query("i") String movieId, @Query("apikey") String apiKey);


}
