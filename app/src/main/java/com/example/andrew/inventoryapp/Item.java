package com.example.andrew.inventoryapp;

/**
 * Created by andrew on 10/24/2017.
 */

public class Item {
    public String productName;
    public String quantity;
    public String price;
    public String image;
    public String email;
    public Item() {
    }
    public Item(String productName, String quantity, String price, String image, String email) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.image = image;
        this.email = email;
    }
    public String setImage(String image) {
        this.image = image;
        return image;
    }
}
