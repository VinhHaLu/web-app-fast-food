package com.example.fastfood.Models;

public class CartModel {
    public CartModel(){

    }
    String id_cart, img_cart, title_cart, check
            ;
    int number_cart, tv_price, totalPrice;

    public CartModel(String id_cart, String img_cart, String title_cart, String check, int number_cart, int tv_price, int totalPrice) {
        this.id_cart = id_cart;
        this.img_cart = img_cart;
        this.title_cart = title_cart;
        this.check = check;
        this.number_cart = number_cart;
        this.tv_price = tv_price;
        this.totalPrice = totalPrice;
    }

    public String getId_cart() {
        return id_cart;
    }

    public void setId_cart(String id_cart) {
        this.id_cart = id_cart;
    }

    public String getImg_cart() {
        return img_cart;
    }

    public void setImg_cart(String img_cart) {
        this.img_cart = img_cart;
    }

    public String getTitle_cart() {
        return title_cart;
    }

    public void setTitle_cart(String title_cart) {
        this.title_cart = title_cart;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public int getNumber_cart() {
        return number_cart;
    }

    public void setNumber_cart(int number_cart) {
        this.number_cart = number_cart;
    }

    public int getTv_price() {
        return tv_price;
    }

    public void setTv_price(int tv_price) {
        this.tv_price = tv_price;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
