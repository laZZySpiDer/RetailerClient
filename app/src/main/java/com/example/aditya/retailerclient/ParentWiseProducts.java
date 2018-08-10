package com.example.aditya.retailerclient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.aditya.retailerclient.Adapters.ProductRecyclerAdapter;
import com.example.aditya.retailerclient.Model.ProductDisplay;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ParentWiseProducts extends AppCompatActivity {

    ProductRecyclerAdapter adapter;
    private RecyclerView myrecyclerview;
    private List<ProductDisplay> lstProduct;
    LinearLayoutManager linearLayoutManager;
    API api;
    private ImageButton goToProfile,goToCart;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_wise_products);

        myrecyclerview = (RecyclerView)findViewById(R.id.parentwiseproduct);
        goToProfile = (ImageButton)findViewById(R.id.goToProfileParentWise);
        goToCart = (ImageButton)findViewById(R.id.goToCartParentWise);
        backButton = (ImageView)findViewById(R.id.backButtonParentWise);

        String usernameAuto;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this) ;
        usernameAuto = prefs.getString("username","");

        linearLayoutManager = new LinearLayoutManager(this);
        myrecyclerview.setLayoutManager(linearLayoutManager);

        lstProduct = new ArrayList<>();
        load_data_from_server();
        adapter = new ProductRecyclerAdapter(this,lstProduct,usernameAuto);
        myrecyclerview.setAdapter(adapter);

        goToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParentWiseProducts.this,CartActivity.class);
                startActivity(intent);
                finish();
            }
        });


        //when profile is clicked
        goToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParentWiseProducts.this,ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParentWiseProducts.this,Dashboard.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void load_data_from_server() {
        int PP_id_FK= getIntent().getIntExtra("PP_id_FK",0);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstValues.link+API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(API.class);
        String usernameAuto;
        usernameAuto = prefs.getString("username","");
        Call<List<ProductDisplay>> call = api.getParentWiseProducts(usernameAuto,PP_id_FK);
        call.enqueue(new Callback<List<ProductDisplay>>() {
            @Override
            public void onResponse(Call<List<ProductDisplay>> call, Response<List<ProductDisplay>> response) {

                List<ProductDisplay> prod = response.body();
                // data_list.addAll(prod);
                try{
                    lstProduct.addAll(prod);
                    adapter.notifyDataSetChanged();
                }catch (NullPointerException ex){
                    Toast.makeText(ParentWiseProducts.this, "No items Found", Toast.LENGTH_SHORT).show();
                }catch (Exception ex){
                    Toast.makeText(ParentWiseProducts.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }

//                for(ProductDisplay product : prod ){
//                    lstProduct.add(product);
//                }


            }

            @Override
            public void onFailure(Call<List<ProductDisplay>> call, Throwable t) {

                Toast.makeText(ParentWiseProducts.this, "Items not Found", Toast.LENGTH_SHORT).show();
            }
        });




    }


}
