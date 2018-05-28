package com.example.aditya.retailerclient.Model;

/**
 * Created by Aditya on 3/27/2018.
 */

public class UserCred {


    public UserCred(String u_name, String u_password, int first_time) {
        U_name = u_name;
        U_password = u_password;
        First_time = first_time;
    }

    String U_name;

    public String getU_name() {
        return U_name;
    }

    public String getU_password() {
        return U_password;
    }

    public int getFirst_time() {
        return First_time;
    }

    String U_password;
    int First_time;


}
