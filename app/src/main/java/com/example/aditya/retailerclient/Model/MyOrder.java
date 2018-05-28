package com.example.aditya.retailerclient.Model;

import com.example.aditya.retailerclient.ConstValues;

/**
 * Created by Aditya on 5/9/2018.
 */

public class MyOrder {

    String P_name;
    String P_image;
    int Quantity;
    String STATE;
    Double Total_Price;
    int T_id;
    String Final_bill;

    public MyOrder(String p_name, String p_image, int quantity, String STATE, Double total_Price,int t_id,String final_bill) {
        P_name = p_name;
        P_image = p_image;
        Quantity = quantity;
        this.STATE = STATE;
        Total_Price = total_Price;
        T_id = t_id;
        Final_bill = ConstValues.Billimagelink + final_bill;
    }

    public String getFinal_bill() {
        return Final_bill;
    }

    public void setFinal_bill(String final_bill) {
        Final_bill = final_bill;
    }

    public int getT_id() {
        return T_id;
    }

    public void setT_id(int t_id) {
        T_id = t_id;
    }

    public String getP_name() {
        return P_name;
    }

    public void setP_name(String p_name) {
        P_name = p_name;
    }

    public String getP_image() {
        return P_image;
    }

    public void setP_image(String p_image) {
        P_image = p_image;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public String getSTATE() {
        return STATE;
    }

    public void setSTATE(String STATE) {
        this.STATE = STATE;
    }

    public Double getTotal_Price() {
        return Total_Price;
    }

    public void setTotal_Price(Double total_Price) {
        Total_Price = total_Price;
    }
}
