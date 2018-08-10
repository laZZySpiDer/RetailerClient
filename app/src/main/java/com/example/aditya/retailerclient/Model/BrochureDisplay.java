package com.example.aditya.retailerclient.Model;

/**
 * Created by Aditya on 8/10/2018.
 */

public class BrochureDisplay {
    int P_ID;
    String P_Title;
    String P_Image;
    String P_Status;

    public int getP_ID() {
        return P_ID;
    }

    public void setP_ID(int p_ID) {
        P_ID = p_ID;
    }

    public String getP_Title() {
        return P_Title;
    }

    public void setP_Title(String p_Title) {
        P_Title = p_Title;
    }

    public String getP_Image() {
        return P_Image;
    }

    public void setP_Image(String p_Image) {
        P_Image = p_Image;
    }

    public String getP_Status() {
        return P_Status;
    }

    public void setP_Status(String p_Status) {
        P_Status = p_Status;
    }

    public BrochureDisplay(int p_ID, String p_Title, String p_Image, String p_Status) {
        P_ID = p_ID;
        P_Title = p_Title;
        P_Image = p_Image;
        P_Status = p_Status;
    }
}
