package com.example.aditya.retailerclient.Model;

import com.example.aditya.retailerclient.ConstValues;

/**
 * Created by Aditya on 4/10/2018.
 */

public class ProductDisplay {
    int P_id;
    String P_name;
    int P_stock;
    String P_size;
    int PP_id_FK;
    String P_image;
    Double Price;
    Double P_Gst;
    String P_desc;
    int P_Ipq;

    public ProductDisplay(int p_id_FK, String p_name, int p_stock, String p_size, int PP_id_FK, String p_image, Double price, Double p_gst,String p_desc,int p_ipq) {
        P_id = p_id_FK;
        P_name = p_name;
        P_stock = p_stock;
        P_size = p_size;
        this.PP_id_FK = PP_id_FK;
        P_image =  p_image;
        Price = price;
        P_Gst = p_gst;
        P_desc = p_desc;
        P_Ipq = p_ipq;
    }

    public int getP_id() {
        return P_id;
    }

    public void setP_id(int p_id) {
        P_id = p_id;
    }

    public Double getP_Gst() {
        return P_Gst;
    }

    public void setP_Gst(Double p_Gst) {
        P_Gst = p_Gst;
    }

    public String getP_desc() {
        return P_desc;
    }

    public void setP_desc(String p_desc) {
        P_desc = p_desc;
    }

    public int getP_Ipq() {
        return P_Ipq;
    }

    public void setP_Ipq(int p_Ipq) {
        P_Ipq = p_Ipq;
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

    public String getP_size() {
        return P_size;
    }

    public void setP_size(String p_size) {
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
