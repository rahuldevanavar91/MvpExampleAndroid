package com.android.omdb.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SearchResponse {
    @SerializedName("Response")
    private String response;

    @SerializedName("totalResults")
    private String totalResults;

    @SerializedName("Error")
    private String error;

    @SerializedName("Search")
    ArrayList<SearchItem> search;

    public ArrayList<SearchItem> getSearch() {
        return search;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public String getResponse() {
        return response;
    }

    public String getError() {
        return error;
    }
}
