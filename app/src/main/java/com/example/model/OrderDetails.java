package com.example.model;

import java.util.Date;

public class OrderDetails {
    private String orderId;
    private String userId;
    private String userName;
    private Date dateTime;
    private String orderdatetime;
    private String address;
    private String city;
    private String postalCode;
    private String phone;
    private String productId;
    private String productImage;
    private String productName;
    private double productPrice;
    private double totalPrice;
    private String paymentMethod;

    public OrderDetails() {
    }

    public OrderDetails(String orderId, String userId, String userName, Date dateTime, String orderdatetime, String address, String city, String postalCode, String phone, String productId, String productImage, String productName, double productPrice, double totalPrice, String paymentMethod) {
        this.orderId = orderId;
        this.userId = userId;
        this.userName = userName;
        this.dateTime = dateTime;
        this.orderdatetime = orderdatetime;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.phone = phone;
        this.productId = productId;
        this.productImage = productImage;
        this.productName = productName;
        this.productPrice = productPrice;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getOrderdatetime() {
        return orderdatetime;
    }

    public void setOrderdatetime(String orderdatetime) {
        this.orderdatetime = orderdatetime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
