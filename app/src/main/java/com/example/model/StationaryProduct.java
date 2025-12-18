package com.example.model;

import java.io.Serializable;

public class StationaryProduct implements Serializable {
    public String pimage;
    public String pname;
    public String pprice;
    public String pdescription;

    public StationaryProduct(){

    }

    public StationaryProduct(String pimage, String pname, String pprice, String pdescription) {
        this.pimage = pimage;
        this.pname = pname;
        this.pprice = pprice;
        this.pdescription = pdescription;
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

    public String getPdescription() {
        return pdescription;
    }

    public void setPdescription(String pdescription) {
        this.pdescription = pdescription;
    }


}
