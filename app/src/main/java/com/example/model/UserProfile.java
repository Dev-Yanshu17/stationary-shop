package com.example.model;

public class UserProfile {
    private String username;
    private String email;
    private String mno;
    public UserProfile(){

    }

    public UserProfile(String username, String email, String mno) {
        this.username = username;
        this.email = email;
        this.mno = mno;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMno() {
        return mno;
    }

    public void setMno(String mno) {
        this.mno = mno;
    }
}
