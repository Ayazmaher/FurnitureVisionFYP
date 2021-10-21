package com.example.furniturevision;

public class Myorder_model  {

    private  String myorder_product_img;
    private String myorder_date;
    private String myorder_product_title;
    private int myorder_product_price;
    private String myorder_product_desc;
    private int myorder_qty;
    String UUID;


    public Myorder_model(String myorder_product_img, String myorder_date, String myorder_product_title, int myorder_product_price, String myorder_product_desc, int myorder_qty,String UUID) {
        this.myorder_product_img = myorder_product_img;
        this.myorder_date = myorder_date;
        this.myorder_product_title = myorder_product_title;
        this.myorder_product_price = myorder_product_price;
        this.myorder_product_desc = myorder_product_desc;
        this.myorder_qty = myorder_qty;
        this.UUID = UUID;
    }

    public String getUUID() {
        return UUID;
    }

    public String getMyorder_product_img() {
        return myorder_product_img;
    }

    public void setMyorder_product_img(String myorder_product_img) {
        this.myorder_product_img = myorder_product_img;
    }

    public String getMyorder_date() {
        return myorder_date;
    }

    public void setMyorder_date(String myorder_date) {
        this.myorder_date = myorder_date;
    }

    public String getMyorder_product_title() {
        return myorder_product_title;
    }

    public void setMyorder_product_title(String myorder_product_title) {
        this.myorder_product_title = myorder_product_title;
    }

    public int getMyorder_product_price() {
        return myorder_product_price;
    }

    public void setMyorder_product_price(int myorder_product_price) {
        this.myorder_product_price = myorder_product_price;
    }

    public String getMyorder_product_desc() {
        return myorder_product_desc;
    }

    public void setMyorder_product_desc(String myorder_product_desc) {
        this.myorder_product_desc = myorder_product_desc;
    }

    public int getMyorder_qty() {
        return myorder_qty;
    }

    public void setMyorder_qty(int myorder_qty) {
        this.myorder_qty = myorder_qty;
    }
}
