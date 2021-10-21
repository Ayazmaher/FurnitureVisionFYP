package com.example.furniturevision;

import java.io.Serializable;

public class FinalConfirmationModel implements Serializable {


    private String cart_Image ;
    private String cart_product_title;
    private int cart_product_price;
    private int cart_product_qty;
    private String cart_product_desc;
    String UUId;
    String name ;
    String email;
    String address;
    String city;
    String payment_type;
    int number;




    public FinalConfirmationModel(String cart_Image, String cart_product_title, int cart_product_price, int cart_product_qty, String cart_product_desc,String UUid,String name, String email, String address, int number,String payment_type,String city ) {
        this.cart_Image = cart_Image;
        this.cart_product_title = cart_product_title;
        this.cart_product_price = cart_product_price;
        this.cart_product_qty = cart_product_qty;
        this.cart_product_desc = cart_product_desc;
        this.UUId=UUid;
        this.email = email;
        this.name = name;
        this.address = address;
        this.number = number;
        this.city = city;
        this.payment_type=payment_type;
    }


    public String getCity() {
        return city;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public String getUUId() {
        return UUId;
    }

    public void setUUId(String UUId) {
        this.UUId = UUId;
    }

    public String getCart_Image() {
        return cart_Image;
    }

    public void setCart_Image(String cart_Image) {
        this.cart_Image = cart_Image;
    }

    public String getCart_product_title() {
        return cart_product_title;
    }

    public void setCart_product_title(String cart_product_title) {
        this.cart_product_title = cart_product_title;
    }

    public int getCart_product_price() {
        return cart_product_price;
    }

    public void setCart_product_price(int cart_product_price) {
        this.cart_product_price = cart_product_price;
    }

    public int getCart_product_qty() {
        return cart_product_qty;
    }

    public void setCart_product_qty(int cart_product_qty) {
        this.cart_product_qty = cart_product_qty;
    }

    public String getCart_product_desc() {
        return cart_product_desc;
    }

    public void setCart_product_desc(String cart_product_desc) {
        this.cart_product_desc = cart_product_desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
