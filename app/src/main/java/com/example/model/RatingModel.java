package com.example.model;

public class RatingModel {
    private String username;
    private String rating;

    public RatingModel() {
        // Required empty public constructor for Firebase
    }

    public RatingModel(String username, String rating) {
        this.username = username;
        this.rating = rating;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
