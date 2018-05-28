package com.example.aditya.retailerclient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.aditya.retailerclient.Model.UserProfile;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileActivity extends AppCompatActivity {

    private EditText email,mbnumber,adhar,pan;
    Button btnEdit;
    Button btnUpdate;
    Button logOut;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        ///initializing the button and edit text
        btnEdit = findViewById(R.id.btnEditProfile);
        btnUpdate = findViewById(R.id.btnUpdateProfile);
        email = (EditText) findViewById(R.id.email);
        mbnumber = (EditText) findViewById(R.id.mbnumber);
        adhar = (EditText) findViewById(R.id.AdhCard);
        pan = (EditText) findViewById(R.id.pancard);
        back = findViewById(R.id.backButton);
        logOut = findViewById(R.id.logout);

        //call intent initializer to initialize some effects
        updateIntent();

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnUpdate.setEnabled(true);
                editProfile();
            }
        });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfileDb();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnUpdate.isEnabled()){
                    Toast.makeText(ProfileActivity.this, "First Save the changes using update button",
                            Toast.LENGTH_SHORT).show();

                }else{
                    Intent intent = new Intent(ProfileActivity.this,Dashboard.class);
                    startActivity(intent);
                    finish();
                }

            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnUpdate.isEnabled()){
                    Toast.makeText(ProfileActivity.this, "First Save the changes using update button",
                            Toast.LENGTH_SHORT).show();

                }else{
                    logOutUser();
                }

            }
        });

    }


    //function to logout the user
    private void logOutUser() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ProfileActivity.this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("username");
        editor.apply();
        Intent intent = new Intent(ProfileActivity.this,LoginPage.class);
        startActivity(intent);
        finish();
        finishAffinity();
        finishAndRemoveTask();
    }


    //profile to update the data to database
    private void updateProfileDb() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        String Email = email.getText().toString();
        String Mb = mbnumber.getText().toString();
        String Adharcard = adhar.getText().toString();
        String Pancard = pan.getText().toString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstValues.link+API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API api = retrofit.create(API.class);
        Call<List<UserProfile>> call = api.updateProfile(prefs.getString("username",""),Email,Mb,Adharcard,Pancard);
        call.enqueue(new Callback<List<UserProfile>>() {
            @Override
            public void onResponse(Call<List<UserProfile>> call, Response<List<UserProfile>> response) {
                Toast.makeText(getApplicationContext(), "Saved Successfully", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<List<UserProfile>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Saved Successfully", Toast.LENGTH_SHORT).show();

            }
        });

        btnEdit.setEnabled(true);
        btnUpdate.setEnabled(false);
        email.setEnabled(false);
        mbnumber.setEnabled(false);
        pan.setEnabled(false);
        adhar.setEnabled(false);

    }


    //to allow editig the fields of personal info
    private void editProfile() {

        email.setEnabled(true);
        mbnumber.setEnabled(true);
        pan.setEnabled(true);
        adhar.setEnabled(true);
        btnEdit.setEnabled(false);

    }


    //update intent with the data from database
    private void updateIntent() {
        btnUpdate.setTextColor(getResources().getColor(R.color.grey));
        btnUpdate.setEnabled(false);

        email.setEnabled(false);
        mbnumber.setEnabled(false);
        pan.setEnabled(false);
        adhar.setEnabled(false);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstValues.link+API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API api = retrofit.create(API.class);
        Call<List<UserProfile>> call = api.getprofiledata(prefs.getString("username",""));
        call.enqueue(new Callback<List<UserProfile>>() {
            @Override
            public void onResponse(Call<List<UserProfile>> call, Response<List<UserProfile>> response) {
                List<UserProfile> profile = response.body();
                for (UserProfile prof : profile) {

                    email.setText(prof.getU_email());
                    mbnumber.setText(prof.getU_whatsapp());
                    adhar.setText(prof.getU_adhar());
                    pan.setText(prof.getU_Pan());
                }
            }
            @Override
            public void onFailure(Call<List<UserProfile>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "profile data not found", Toast.LENGTH_SHORT).show();
            }
        });



    }
}
