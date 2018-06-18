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
import android.widget.TextView;
import android.widget.Toast;

import com.example.aditya.retailerclient.Model.UserProfile;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileActivity extends AppCompatActivity {

    private TextView email,mbnumber,adhar,pan,uaddress,uname,gst;
    Button btnEdit;
    Button btnUpdate;
    TextView logOut;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        ///initializing the button and edit text

        uaddress = (TextView) findViewById(R.id.uaddress);
        uname = (TextView) findViewById(R.id.uname);
        email = (TextView) findViewById(R.id.email);
        gst = (TextView) findViewById(R.id.gst);
        mbnumber = (TextView) findViewById(R.id.mbnumber);
        adhar = (TextView) findViewById(R.id.AdhCard);
        pan = (TextView) findViewById(R.id.pancard);
        back = findViewById(R.id.backButton);
        logOut = findViewById(R.id.logout);

        //call intent initializer to initialize some effects
        updateIntent();





        //when back button is pressed go to Dashboard Activity
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(ProfileActivity.this,Dashboard.class);
                    startActivity(intent);
                    finish();
            }
        });

        //Logout the user from the app and then redirect to login page
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOutUser();
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

    }




    //update intent with the data from database
    private void updateIntent() {



        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
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

                    email.setText("EMAIL :  "+prof.getU_email());
                    mbnumber.setText("PHONE :  " +prof.getU_whatsapp());
                    adhar.setText("ADHAR :  " + prof.getU_adhar());
                    pan.setText("PAN :  "+ prof.getU_Pan());
                    uaddress.setText("ADDRESS :   "+prof.getU_add());
                    gst.setText("GST :   "+prof.getU_GST());
                    uname.setText("NAME :   "+ prefs.getString("username",""));
                }
            }
            @Override
            public void onFailure(Call<List<UserProfile>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "profile data not found", Toast.LENGTH_SHORT).show();
            }
        });



    }
}
