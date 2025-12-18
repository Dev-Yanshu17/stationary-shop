package com.example.model;

import java.io.Serializable;

public class UserOrder implements Serializable {
    int id;
    public String firstname;
    public String lastname;
    public String mobile;
    public String zipCode;
    public String address;
    public String landmark;
    public String productImage;
    public String productName;
    public String productPrice;
    public String productQuantity;
    public String productTotalPrice;

    public UserOrder(){

    }

    public int getId(){return id;}
    public void setId(int id){this.id=id;}

    public UserOrder(int id, String firstname, String lastname, String mobile, String zipCode, String address, String landmark, String productImage, String productName, String productPrice, String productQuantity, String productTotalPrice) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.mobile = mobile;
        this.zipCode = zipCode;
        this.address = address;
        this.landmark = landmark;
        this.productImage = productImage;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productTotalPrice = productTotalPrice;
    }

    public UserOrder(String firstname, String lastname, String mobile, String zipCode, String address, String landmark, String productImage, String productName, String productPrice, String productQuantity, String productTotalPrice) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.mobile = mobile;
        this.zipCode = zipCode;
        this.address = address;
        this.landmark = landmark;
        this.productImage = productImage;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productTotalPrice = productTotalPrice;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(String productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }
}
