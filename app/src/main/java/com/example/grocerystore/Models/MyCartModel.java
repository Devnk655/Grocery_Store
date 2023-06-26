package com.example.grocerystore.Models;

public class MyCartModel {
    String currentDate;
    String currentTime;
    String productName;
    String productPrice;
    String totalQuaninty ;
    int totalPrice;
    public MyCartModel() {
    }
    public MyCartModel(String currentDate, String currentTime, String productName, String productPrice, String totalQuaninty, int totalPrice) {
        this.currentDate = currentDate;
        this.currentTime = currentTime;
        this.productName = productName;
        this.productPrice = productPrice;
        this.totalQuaninty = totalQuaninty;
        this.totalPrice = totalPrice;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
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

    public String getTotalQuaninty() {
        return totalQuaninty;
    }

    public void setTotalQuaninty(String totalQuaninty) {
        this.totalQuaninty = totalQuaninty;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
