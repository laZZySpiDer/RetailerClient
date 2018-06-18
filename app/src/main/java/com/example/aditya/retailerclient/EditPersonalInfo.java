package com.example.aditya.retailerclient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aditya.retailerclient.Model.UserProfile;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditPersonalInfo extends AppCompatActivity {

    Button saveNExit,exitWithoutSaving;
    EditText editedName,editedPhone,editedEmail,editedAddress;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_personal_info);

        //initialize textviews
        editedName = (EditText) findViewById(R.id.editedName);
        editedPhone = (EditText) findViewById(R.id.editedPhoneNumber);
        editedEmail = (EditText) findViewById(R.id.editedEmail);
        editedAddress = (EditText) findViewById(R.id.editedAddress);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        //initialize buttons
        saveNExit = (Button)findViewById(R.id.exitwithsaving);
        exitWithoutSaving = (Button)findViewById(R.id.exitwithoutsaving);

        editedName.setEnabled(false);

        //load the details of particular user
        editedName.setText(prefs.getString("username",""));
        editedAddress.setText(getIntent().getStringExtra("address"));
        editedPhone.setText(getIntent().getStringExtra("phone"));
        editedEmail.setText(getIntent().getStringExtra("email"));

        //listener events
        saveNExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTheUpdatedData();
            }
        });

        exitWithoutSaving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditPersonalInfo.this,ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    void addTheUpdatedData(){

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        String Email = editedEmail.getText().toString();
        String phoneNumber = editedPhone.getText().toString();
        String address = editedAddress.getText().toString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstValues.link+API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API api = retrofit.create(API.class);
        Call<List<UserProfile>> call = api.updateProfilePersonal(prefs.getString("username",""),Email,phoneNumber,address);
        call.enqueue(new Callback<List<UserProfile>>() {
            @Override
            public void onResponse(Call<List<UserProfile>> call, Response<List<UserProfile>> response) {
                Toast.makeText(getApplicationContext(), "Saved Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditPersonalInfo.this,ProfileActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<List<UserProfile>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Saved Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditPersonalInfo.this,ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }


    @Override
    public void onBackPressed() {
        return ;
    }
}
