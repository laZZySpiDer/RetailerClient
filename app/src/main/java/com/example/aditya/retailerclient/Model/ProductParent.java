package com.example.aditya.retailerclient.Model;

/**
 * Created by Aditya on 5/7/2018.
 */

public class ProductParent {

     int PP_id;
     String PP_name;

    public ProductParent(int PP_id, String PP_name) {
        this.PP_id = PP_id;
        this.PP_name = PP_name;
    }

    public int getPP_id() {
        return PP_id;
    }

    public void setPP_id(int PP_id) {
        this.PP_id = PP_id;
    }

    public String getPP_name() {
        return PP_name;
    }

    public void setPP_name(String PP_name) {
        this.PP_name = PP_name;
    }
}
