package com.example.aditya.retailerclient.Model;

/**
 * Created by Aditya on 6/22/2018.
 */

public class Offers {

    private int Offer_Id;
    private double Offer_Min_Amount;
    private double Offer_Max_Amount;
    private String Valid;
    private String Description;
    private String Offer_Title;
    private double Discount;

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getOffer_Title() {
        return Offer_Title;
    }

    public void setOffer_Title(String offer_Title) {
        Offer_Title = offer_Title;
    }

    public int getOffer_Id() {
        return Offer_Id;
    }

    public void setOffer_Id(int offer_Id) {
        Offer_Id = offer_Id;
    }

    public double getOffer_Min_Amount() {
        return Offer_Min_Amount;
    }

    public void setOffer_Min_Amount(double offer_Min_Amount) {
        Offer_Min_Amount = offer_Min_Amount;
    }

    public double getOffer_Max_Amount() {
        return Offer_Max_Amount;
    }

    public void setOffer_Max_Amount(double offer_Max_Amount) {
        Offer_Max_Amount = offer_Max_Amount;
    }

    public String getValid() {
        return Valid;
    }

    public void setValid(String valid) {
        Valid = valid;
    }

    public double getDiscount() {
        return Discount;
    }

    public void setDiscount(double discount) {
        Discount = discount;
    }

    public Offers(int offer_Id, double offer_Min_Amount, double offer_Max_Amount, String valid, double discount, String description, String offer_title) {
        Offer_Id = offer_Id;
        Offer_Min_Amount = offer_Min_Amount;
        Offer_Max_Amount = offer_Max_Amount;
        Valid = valid;
        Discount = discount;
        Description = description;
        Offer_Title = offer_title;
    }
}
