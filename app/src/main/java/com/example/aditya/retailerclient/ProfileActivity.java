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

    TextView logOut;
    ImageView back,personalInfo,otherInfo;
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
        personalInfo = findViewById(R.id.editPersonalInfo);
        otherInfo = findViewById(R.id.editOtherInfo);

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

        personalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this,EditPersonalInfo.class);
                intent.putExtra("name",uname.getText().toString());
                intent.putExtra("phone",mbnumber.getText().toString());
                intent.putExtra("email",email.getText().toString());
                intent.putExtra("address",uaddress.getText().toString());
                startActivity(intent);
            }
        });

        otherInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this,EditOtherInfo.class);
                intent.putExtra("adhar",adhar.getText().toString());
                intent.putExtra("gst",gst.getText().toString());
                intent.putExtra("pan",pan.getText().toString());
                startActivity(intent);
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

                    email.setText(prof.getU_email());
                    mbnumber.setText(prof.getU_whatsapp());
                    adhar.setText(prof.getU_adhar());
                    pan.setText(prof.getU_Pan());
                    uaddress.setText(prof.getU_add());
                    gst.setText(prof.getU_GST());
                    uname.setText(prefs.getString("username",""));
                }
            }
            @Override
            public void onFailure(Call<List<UserProfile>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "profile data not found", Toast.LENGTH_SHORT).show();
            }
        });



    }
}
