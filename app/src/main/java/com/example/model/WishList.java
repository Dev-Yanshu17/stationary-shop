package com.example.model;

public class WishList {
    private String id;
    private String pimage;
    private String pname;
    private String pprice;


    public WishList() {
    }

    public WishList(String id, String pimage, String pname, String pprice) {
        this.id = id;
        this.pimage = pimage;
        this.pname = pname;
        this.pprice = pprice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPimage() {
        return pimage;
    }

    public void setPimage(String pimage) {
        this.pimage = pimage;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPprice() {
        return pprice;
    }

    public void setPprice(String pprice) {
        this.pprice = pprice;
    }
}
