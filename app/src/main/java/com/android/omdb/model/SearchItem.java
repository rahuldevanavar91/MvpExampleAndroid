package com.android.omdb.model;

import com.google.gson.annotations.SerializedName;


public class SearchItem {
    private int viewType;
    @SerializedName("Title")
    private String title;
    @SerializedName("Year")
    private String year;

    @SerializedName("imdbID")
    private String imdbId;
    @SerializedName("Type")
    private String type;
    @SerializedName("Poster")
    private String poster;

    public String getTitle() {
        return title;
    }

    public String getImdbId() {
        return imdbId;
    }

    public String getPoster() {
        return poster;
    }

    public String getType() {
        return type;
    }

    public String getYear() {
        return year;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
