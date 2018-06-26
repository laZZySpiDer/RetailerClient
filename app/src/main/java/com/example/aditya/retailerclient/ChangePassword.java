package com.example.aditya.retailerclient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aditya.retailerclient.Model.UserCred;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChangePassword extends AppCompatActivity {
    SharedPreferences prefs;
    API api;
    String oldPass;
    Button saveNExit,exitWithoutSaving;
    EditText oldPassChange,newPassChange,confPassChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);


        //initializing the components
        saveNExit = (Button)findViewById(R.id.exitwithsavingchange);
        exitWithoutSaving = (Button)findViewById(R.id.exitwithoutsavingchange);
        oldPassChange = (EditText)findViewById(R.id.oldpasschange);
        newPassChange = (EditText)findViewById(R.id.newpasschange);
        confPassChange = (EditText)findViewById(R.id.confirmpasschange);


        //get the old password of user for comparison
        getOldPassword();


        //event when save and exit button is pressed
        saveNExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkThePassword();
            }
        });

        //event when exit without saving button is pressed
        exitWithoutSaving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangePassword.this,ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void checkThePassword() {

            if(oldPass.equals(oldPassChange.getText().toString()) && newPassChange.getText().toString().equals(confPassChange.getText().toString())){
                changeThePassword();
                Intent intent = new Intent(ChangePassword.this,ProfileActivity.class);
                startActivity(intent);
                finish();
            }else if(!oldPass.equals(oldPassChange.getText().toString())){
                Toast.makeText(this, "Make sure the old Passsword is correct.", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "New Password and Confirm Password should match.", Toast.LENGTH_SHORT).show();
            }


    }

    private void changeThePassword() {
        //change the password here
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstValues.link+API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API api = retrofit.create(API.class);
        Call<List<Void>> call = api.changePassword(prefs.getString("username",""),newPassChange.getText().toString());

        call.enqueue(new Callback<List<Void>>() {
            @Override
            public void onResponse(Call<List<Void>> call, Response<List<Void>> response) {

            }
            @Override
            public void onFailure(Call<List<Void>> call, Throwable t) {

            }
        });

    }


    //function which will get the old password now and store it in oldpass string
    private void getOldPassword() {


        prefs = PreferenceManager.getDefaultSharedPreferences(ChangePassword.this);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstValues.link+API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API api = retrofit.create(API.class);
        Call<List<UserCred>> call = api.getUser(prefs.getString("username",""));

        call.enqueue(new Callback<List<UserCred>>() {
            @Override
            public void onResponse(Call<List<UserCred>> call, Response<List<UserCred>> response) {

                List<UserCred> userCredData = response.body();
                // userCredData.clear();
                for(UserCred usr : userCredData){
                    oldPass = usr.getU_password();
                }
            }

            @Override
            public void onFailure(Call<List<UserCred>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Service Not Available right now",Toast.LENGTH_LONG).show();
            }
        });

    }
}
