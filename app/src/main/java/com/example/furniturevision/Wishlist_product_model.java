package com.example.furniturevision;

public class Wishlist_product_model {


    private  String wishlist_product_img;
    private String wishlist_product_title;
    private String wishlist_product_price;
    private String wishlist_product_desc;
    String UUId;

    public Wishlist_product_model(String wishlist_product_img, String wishlist_product_title, String wishlist_product_price, String wishlist_product_desc,String UUId) {
        this.wishlist_product_img = wishlist_product_img;
        this.wishlist_product_title = wishlist_product_title;
        this.wishlist_product_price = wishlist_product_price;
        this.wishlist_product_desc = wishlist_product_desc;
        this.UUId=UUId;
    }

    public String getUUId() {
        return UUId;
    }

    public void setUUId(String UUId) {
        this.UUId = UUId;
    }

    public void setWishlist_product_img(String wishlist_product_img) {
        this.wishlist_product_img = wishlist_product_img;
    }

    public String getWishlist_product_img() {
        return wishlist_product_img;
    }

    public String getWishlist_product_title() {
        return wishlist_product_title;
    }

    public void setWishlist_product_title(String wishlist_product_title) {
        this.wishlist_product_title = wishlist_product_title;
    }

    public String getWishlist_product_price() {
        return wishlist_product_price;
    }

    public void setWishlist_product_price(String wishlist_product_price) {
        this.wishlist_product_price = wishlist_product_price;
    }

    public String getWishlist_product_desc() {
        return wishlist_product_desc;
    }

    public void setWishlist_product_desc(String wishlist_product_desc) {
        this.wishlist_product_desc = wishlist_product_desc;
    }
}
