package com.example.stationaryshop;

public class DbModel {
    String email;
    String name;
    String password;
    String conformpasswod;
    String mno;

    public DbModel(String email, String name, String password, String conformpasswod, String mno) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.conformpasswod = conformpasswod;
        this.mno = mno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConformpasswod() {
        return conformpasswod;
    }

    public void setConformpasswod(String conformpasswod) {
        this.conformpasswod = conformpasswod;
    }

    public String getMno() {
        return mno;
    }

    public void setMno(String mno) {
        this.mno = mno;
    }
}
