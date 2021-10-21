package com.example.furniturevision;

import java.io.Serializable;

public class ProductsModel implements Serializable {

    private  String product_img;
    private String product_title;
    private int product_price;
    private String product_desc;


    public ProductsModel(String product_img, String product_title, int product_price, String product_desc) {
        this.product_img = product_img;
        this.product_title = product_title;
        this.product_price = product_price;
        this.product_desc = product_desc;
    }

    public String getProduct_img() {
        return product_img;
    }

    public void setProduct_img(String product_img) {
        this.product_img = product_img;
    }

    public String getProduct_title() {
        return product_title;
    }

    public void setProduct_title(String product_title) {
        this.product_title = product_title;
    }

    public int getProduct_price() {
        return product_price;
    }

    public void setProduct_price(int product_price) {
        this.product_price = product_price;
    }

    public String getProduct_desc() {
        return product_desc;
    }

    public void setProduct_desc(String product_desc) {
        this.product_desc = product_desc;
    }
}
