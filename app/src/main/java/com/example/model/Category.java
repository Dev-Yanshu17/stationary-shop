package com.example.model;

import java.io.Serializable;

public class Category implements Serializable {
    public String cimage;
    public String cname;

    public Category(String cimage, String cname) {
        this.cimage = cimage;
        this.cname = cname;
    }

    public Category() {

    }

    public String getCategoryName() {
        return cname;
    }

    public void setCategoryName(String cname) {
        this.cname = cname;
    }

    public String getCategoryImage() {
        return cimage;
    }

    public void setCategoryImage(String cimage) {
        this.cimage = cimage;
    }
}
