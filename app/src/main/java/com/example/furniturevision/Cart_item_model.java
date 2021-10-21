package com.example.furniturevision;


import java.io.Serializable;

public class Cart_item_model implements Serializable {



    public String cart_Image ;
    private String cart_product_title;
    private int cart_product_price;
    private int cart_product_qty;
    private String cart_product_desc;
    String UUID;




    public Cart_item_model(String cart_Image, String cart_product_title, int cart_product_price, int cart_product_qty, String cart_product_desc ,String UUID) {
        this.cart_Image = cart_Image;
        this.cart_product_title = cart_product_title;
        this.cart_product_price = cart_product_price;
        this.cart_product_qty = cart_product_qty;
        this.cart_product_desc = cart_product_desc;
        this.UUID = UUID;
    }



    public String getUUID() {
        return UUID;
    }

    public Object setUUID(String UUID) {
        this.UUID = UUID;
        return UUID;
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

    public int setCart_product_qty(int cart_product_qty) {
        return cart_product_qty;

    }

    public String getCart_product_desc() {
        return cart_product_desc;
    }

    public void setCart_product_desc(String cart_product_desc) {
        this.cart_product_desc = cart_product_desc;
    }


}
