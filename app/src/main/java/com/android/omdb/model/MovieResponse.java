package com.android.omdb.model;

import com.google.gson.annotations.SerializedName;


public class MovieResponse {
    @SerializedName("Error")
    private String error;
    private String Released;
    private String Response;
    private String Poster;
    private String Title;
    private String imdbRating;
    private String Year;
    private String Actors;
    private String Plot;
    private String Awards;
    private String Production;
    private String Genre;
    private String Director;
    private String Writer;
private String Language;

    public String getLanguage() {
        return Language;
    }

    public String getWriter() {
        return Writer;
    }

    public String getDirector() {
        return Director;
    }

    public String getGenre() {
        return Genre;
    }

    public String getProduction() {
        return Production;
    }

    public String getAwards() {
        return Awards;
    }

    public String getReleased() {
        return Released;
    }

    public String getPoster() {
        return Poster;
    }

    public String getTitle() {
        return Title;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public String getPlot() {
        return Plot;
    }

    public String getActors() {
        return Actors;
    }

    public String getYear() {
        return Year;
    }

    public String getResponse() {
        return Response;
    }

    public String getError() {
        return error;
    }
}