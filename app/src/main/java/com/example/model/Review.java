package com.example.model;

import java.io.Serializable;

public class Review implements Serializable {
    public String pdname;
    public String pdreview;
    public String username;

    public Review(String pdname, String pdreview, String username) {
        this.pdname = pdname;
        this.pdreview = pdreview;
        this.username = username;
    }
    public Review(){

    }

    public String getPname() {
        return pdname;
    }

    public void setPname(String pname) {
        this.pdname = pname;
    }

    public String getPreview() {
        return pdreview;
    }

    public void setPreview(String preview) {
        this.pdreview = preview;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
