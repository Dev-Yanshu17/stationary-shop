package com.example.model;

public class User {
    String Id;
    String Email;
    String Username;
    String Mno;
    String password;
    String Conformpasswod;

    public User(){

    }

    public User(String id, String email, String username, String mno, String password, String conformpasswod) {
        Id = id;
        Email = email;
        Username = username;
        Mno = mno;
        this.password = password;
        Conformpasswod = conformpasswod;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getMno() {
        return Mno;
    }

    public void setMno(String mno) {
        Mno = mno;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConformpasswod() {
        return Conformpasswod;
    }

    public void setConformpasswod(String conformpasswod) {
        Conformpasswod = conformpasswod;
    }
}
