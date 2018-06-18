package com.example.aditya.retailerclient;

import android.app.AlertDialog;
import com.example.aditya.retailerclient.ConstValues;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.transition.Explode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aditya.retailerclient.Model.UserCred;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginPage extends AppCompatActivity {
    private MaterialEditText user,passwd;
    private Button lginBtn;

    private TextView frgtpwd;
    SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        //function to check whether IP address is stored in sharedpreferences
        //if it is not stored than popup will be seen and thus can be added
        //from there
        //checkIpAddress();


        //obtain username from sharedpreferences..If username found no need to login again
        //directly send the user to DASHBOARD
        prefs = PreferenceManager.getDefaultSharedPreferences(LoginPage.this) ;
        String usernameAuto = prefs.getString("username","");
        if(!usernameAuto.isEmpty()){

            Intent intent = new Intent(LoginPage.this,Dashboard.class);
            finish();
            startActivity(intent);
        }


        //initialize the parameters used on login page intent
        user = findViewById(R.id.username);
        passwd = findViewById(R.id.passwd);
        lginBtn = findViewById(R.id.lgin);
        frgtpwd = findViewById(R.id.frgtpwd);



        //login button event
        lginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when login is clicked check user authenticty with DB
                checkUser();
            }
        });


        //when configure is clicked
//        configure.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showIpDialog();
//            }
//        });

        //when forgot password is clicked
        frgtpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showForgotIpDialog();
            }
        });

    }


    //forgot password
    private void showForgotIpDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View addIpLayout = inflater.inflate(R.layout.forgotpassword,null);
        final MaterialEditText mobNumber = addIpLayout.findViewById(R.id.mobnumber);

        dialog.setView(addIpLayout);

        dialog.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(TextUtils.isEmpty(mobNumber.getText().toString())){
                    Toast.makeText(LoginPage.this, "Enter proper Mobile Number", Toast.LENGTH_SHORT).show();
                }
                else{
                    dialog.dismiss();
                    resetPassword(mobNumber.getText().toString());
                    Toast.makeText(LoginPage.this, "New Password has sent to Registered Device"
                            , Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();


    }


    //when continue is clickd on the dialog box reset of password is done
    private void resetPassword(String mobNumber) {

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstValues.link+API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API api = retrofit.create(API.class);
        Call<List<UserCred>> call = api.getphonenumber(mobNumber);
        call.enqueue(new Callback<List<UserCred>>() {
            @Override
            public void onResponse(Call<List<UserCred>> call, Response<List<UserCred>> response) {
               // Toast.makeText(getApplicationContext(),"Password reset successfully",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<List<UserCred>> call, Throwable t) {
               // Toast.makeText(getApplicationContext(), "Password reset Successfully", Toast.LENGTH_LONG).show();

            }
        });

    }

    //to check whether the IP Address is set or not
    private void checkIpAddress(){
        prefs = PreferenceManager.getDefaultSharedPreferences(LoginPage.this);
        // if IP address is not set then user have to set it
        if(prefs.getString("ip","").isEmpty()){
            showIpDialog();
        }
    }


    //shows a dialog box which allows to add IP address and stores for the first time in
    //shared preferences.
    private void showIpDialog(){
     AlertDialog.Builder dialog = new AlertDialog.Builder(this);
     LayoutInflater inflater = LayoutInflater.from(this);
     View addIpLayout = inflater.inflate(R.layout.dialog_ip,null);
        final MaterialEditText ip1 = addIpLayout.findViewById(R.id.ip1);
        final MaterialEditText ip2 =  addIpLayout.findViewById(R.id.ip2);
        final MaterialEditText ip3 =  addIpLayout.findViewById(R.id.ip3);
        final MaterialEditText ip4 =  addIpLayout.findViewById(R.id.ip4);
        dialog.setView(addIpLayout);

        dialog.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(TextUtils.isEmpty(ip1.getText().toString()) || TextUtils.isEmpty(ip2.getText().toString())||
                        TextUtils.isEmpty(ip3.getText().toString())||TextUtils.isEmpty(ip4.getText().toString())){
                    Toast.makeText(LoginPage.this, "Enter all value", Toast.LENGTH_SHORT).show();
                }
                else{
                    dialog.dismiss();
                    String ip = ip1.getText().toString() + "." + ip2.getText().toString() + "." +
                            ip3.getText().toString() + "."+ip4.getText().toString();
                    prefs.edit().putString("ip",ip).apply();
                    Toast.makeText(LoginPage.this, "IP Address Added", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                   dialog.dismiss();
            }
        });
        dialog.show();
    }







    //function to check whether the user is authenticated or not
    private void checkUser(){

        String name  = user.getText().toString();
        prefs = PreferenceManager.getDefaultSharedPreferences(LoginPage.this);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstValues.link+API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API api = retrofit.create(API.class);
        Call<List<UserCred>> call = api.getUser(name);

        call.enqueue(new Callback<List<UserCred>>() {
            @Override
            public void onResponse(Call<List<UserCred>> call, Response<List<UserCred>> response) {

                List<UserCred> userCredData = response.body();
                // userCredData.clear();
                for(UserCred usr : userCredData){

                    Log.d("User Name : ",usr.getU_name());
                    Log.d("Password : ",usr.getU_password());
                    Log.d("First Time Login : ", String.valueOf(usr.getFirst_time()));
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(LoginPage.this) ;
                    prefs.edit().putString("username",usr.getU_name()).apply();
                    if(passwd.getText().toString().equals(usr.getU_password())){
                        if(usr.getFirst_time()==0){
                            //if not first time login
                            Intent intent = new Intent(LoginPage.this,Dashboard.class);
                            intent.putExtra("Username",usr.getU_name());//pass data from 1 intent to another                                startActivity(intent);
                            finish();
                            startActivity(intent);
                        }else{
                            //if first time login then force to change the password
                            Intent intent = new Intent(LoginPage.this,Dashboard.class);
                            intent.putExtra("Username",usr.getU_name());
                            finish();
                            startActivity(intent);
                        }
                    }else{
                        Toast.makeText(LoginPage.this,"Wrong Credentials",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<UserCred>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Not A user",Toast.LENGTH_LONG).show();
            }
        });

    }



}
