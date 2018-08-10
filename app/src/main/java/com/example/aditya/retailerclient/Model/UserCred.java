package com.example.aditya.retailerclient.Model;

/**
 * Created by Aditya on 3/27/2018.
 */

public class UserCred {


    public UserCred(String u_name, String u_password, int first_time,int status) {
        U_name = u_name;
        U_password = u_password;
        First_time = first_time;
        Status = status;
    }

    String U_name;
    int Status;

    public void setU_name(String u_name) {
        U_name = u_name;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public void setU_password(String u_password) {
        U_password = u_password;
    }

    public void setFirst_time(int first_time) {
        First_time = first_time;
    }

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
