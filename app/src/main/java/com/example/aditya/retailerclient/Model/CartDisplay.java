package com.example.aditya.retailerclient.Model;

/**
 * Created by Aditya on 4/11/2018.
 */

public class CartDisplay {
    int User_id;
    String P_name;
    int Product_id;
    int Quantity;
    Double Price;
    String P_image;
    public CartDisplay(int user_id, String p_name, int product_id, int quantity, Double price, String status_Cart,String p_image) {
        User_id = user_id;
        P_name = p_name;
        Product_id = product_id;
        Quantity = quantity;
        Price = price;
        Status_Cart = status_Cart;
        P_image = p_image;
    }

    public String getP_image() {
        return P_image;
    }

    public void setP_image(String p_image) {
        P_image = p_image;
    }

    public int getUser_id() {
        return User_id;
    }

    public void setUser_id(int user_id) {
        User_id = user_id;
    }

    public String getP_name() {
        return P_name;
    }

    public void setP_name(String p_name) {
        P_name = p_name;
    }

    public int getProduct_id() {
        return Product_id;
    }

    public void setProduct_id(int product_id) {
        Product_id = product_id;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public String getStatus_Cart() {
        return Status_Cart;
    }

    public void setStatus_Cart(String status_Cart) {
        Status_Cart = status_Cart;
    }

    String Status_Cart;

}
