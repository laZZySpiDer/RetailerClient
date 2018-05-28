package com.example.aditya.retailerclient.Model;

import com.example.aditya.retailerclient.ConstValues;

/**
 * Created by Aditya on 4/10/2018.
 */

public class ProductDisplay {
    int P_id;
    String P_name;
    int P_stock;
    Double P_size;
    int PP_id_FK;
    String P_image;
    Double Price;

    public ProductDisplay(int p_id_FK, String p_name, int p_stock, Double p_size, int PP_id_FK, String p_image, Double price) {
        P_id = p_id_FK;
        P_name = p_name;
        P_stock = p_stock;
        P_size = p_size;
        this.PP_id_FK = PP_id_FK;
        P_image =  p_image;
        Price = price;
    }

    public int getP_id_FK() {
        return P_id;
    }

    public void setP_id_FK(int p_id_FK) {
        P_id = p_id_FK;
    }

    public String getP_name() {
        return P_name;
    }

    public void setP_name(String p_name) {
        P_name = p_name;
    }

    public int getP_stock() {
        return P_stock;
    }

    public void setP_stock(int p_stock) {
        P_stock = p_stock;
    }

    public Double getP_size() {
        return P_size;
    }

    public void setP_size(Double p_size) {
        P_size = p_size;
    }

    public int getPP_id_FK() {
        return PP_id_FK;
    }

    public void setPP_id_FK(int PP_id_FK) {
        this.PP_id_FK = PP_id_FK;
    }

    public String getP_image() {
        return P_image;
    }

    public void setP_image(String p_image) {
        P_image = p_image;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }
}
