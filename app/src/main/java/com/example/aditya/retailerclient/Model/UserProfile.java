package com.example.aditya.retailerclient.Model;

/**
 * Created by Aditya on 5/9/2018.
 */

public class UserProfile {

    public String U_name_FK;
    public String U_email;
    public String U_whatsapp;
    public String U_adhar;
    public String U_Pan;

    public UserProfile(String u_name_FK, String u_email, String u_whatsapp, String u_adhar, String u_Pan) {
        U_name_FK = u_name_FK;
        U_email = u_email;
        U_whatsapp = u_whatsapp;
        U_adhar = u_adhar;
        U_Pan = u_Pan;
    }

    public String getU_name_FK() {
        return U_name_FK;
    }

    public void setU_name_FK(String u_name_FK) {
        U_name_FK = u_name_FK;
    }

    public String getU_email() {
        return U_email;
    }

    public void setU_email(String u_email) {
        U_email = u_email;
    }

    public String getU_whatsapp() {
        return U_whatsapp;
    }

    public void setU_whatsapp(String u_whatsapp) {
        U_whatsapp = u_whatsapp;
    }

    public String getU_adhar() {
        return U_adhar;
    }

    public void setU_adhar(String u_adhar) {
        U_adhar = u_adhar;
    }

    public String getU_Pan() {
        return U_Pan;
    }

    public void setU_Pan(String u_Pan) {
        U_Pan = u_Pan;
    }
}
