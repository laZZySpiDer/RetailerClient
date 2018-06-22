package com.example.aditya.retailerclient;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.example.aditya.retailerclient.ConstValues;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aditya.retailerclient.Adapters.CartRecyclerAdapter;
import com.example.aditya.retailerclient.Model.CartDisplay;
import com.example.aditya.retailerclient.Model.Offers;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CartActivity extends AppCompatActivity {
    Button purchaseAll,removeAll;
    TextView grandTotalLayout;
    ImageView back;
    CartRecyclerAdapter adapter;
    private RecyclerView myrecyclerview;
    private List<CartDisplay> lstCart;
    private List<Offers> lstOffer;
    private List<Double> grandTotalList;
    LinearLayoutManager linearLayoutManager;
    API api;
    SwipeRefreshLayout swipedown;
    Double reducedNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        //initializing necessary things here
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        swipedown = findViewById(R.id.swipeCartActivity);
        purchaseAll = findViewById(R.id.purchaseCartBtn);
        removeAll = findViewById(R.id.emptyCartBtn);
        back = findViewById(R.id.backButton);
        grandTotalLayout = findViewById(R.id.grandTotal);
        grandTotalList = new ArrayList<>();
        lstOffer = new ArrayList<>();
        //load and initialize the recyclerview, also setting the adapter of the recycler view
        loadCartIntent();


        adapter.notifyDataSetChanged();




        // on clicking the icon  of Profile
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(CartActivity.this,ProfileActivity.class);
               startActivity(intent);
            }
        });


        //whenever the intent is swiped down
        swipedown.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipedown.setRefreshing(false);
                adapter = new CartRecyclerAdapter(CartActivity.this,lstCart,prefs.getString("username",""));
                lstCart.clear();
                load_data_from_server();
                myrecyclerview.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this,Dashboard.class);
                startActivity(intent);
            }
        });
        purchaseAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lstCart.isEmpty()){
                    Toast.makeText(CartActivity.this, "Cart is Empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    getAllOffers();
                    purchaseCartContent();
                }

            }
        });


        removeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(lstCart.isEmpty()){

               }else{
                   removeCartContent();
               }
            }
        });

    }

    private void grandTotal(){

       Double totalPriceInCart = 0.0;
       for(Double indiPrice : grandTotalList){
           totalPriceInCart +=indiPrice;
       }
        grandTotalList.clear();
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        reducedNumber = Double.valueOf(numberFormat.format(totalPriceInCart));
        grandTotalLayout.setText(String.valueOf(reducedNumber));
    }


    // a function to get all the offers and check whether it is applicable or not
    private void getAllOffers() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstValues.link+API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(API.class);

        Call<List<Offers>> call = api.getAllOffers();
        call.enqueue(new Callback<List<Offers>>() {
            @Override
            public void onResponse(Call<List<Offers>> call, Response<List<Offers>> response) {
                List<Offers> newOffers = response.body();
                lstOffer.clear();
                for(Offers offers : newOffers){
                    if(reducedNumber >= offers.getOffer_Min_Amount() && reducedNumber <= offers.getOffer_Max_Amount() && offers.getValid().equals("ENABLE")){

                    }else{

                    }
                    lstOffer.add(offers);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Offers>> call, Throwable t) {
                //  Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
            }
        });

    }






    private void loadCartIntent(){
        myrecyclerview = (RecyclerView)findViewById(R.id.cart_recyclerview);
        String usernameAuto;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        usernameAuto = prefs.getString("username","");
        lstCart = new ArrayList<>();
        adapter = new CartRecyclerAdapter(this,lstCart,usernameAuto);
        linearLayoutManager = new LinearLayoutManager(this);
        myrecyclerview.setLayoutManager(linearLayoutManager);
        myrecyclerview.setAdapter(adapter);
        load_data_from_server();

    }

    //function to load all the data from server and populate the cart activity
    private void load_data_from_server(){

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstValues.link+API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(API.class);
        String usernameAuto = prefs.getString("username","");
        Call<List<CartDisplay>> call = api.getCartContent(usernameAuto);
        call.enqueue(new Callback<List<CartDisplay>>() {
            @Override
            public void onResponse(Call<List<CartDisplay>> call, Response<List<CartDisplay>> response) {
                List<CartDisplay> cart = response.body();
                lstCart.clear();
                for(CartDisplay cartContent : cart){
                    grandTotalList.add(cartContent.getPrice());
                    lstCart.add(cartContent);
                }
                //function call to set Total Amount of the contents in cart
                grandTotal();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<CartDisplay>> call, Throwable t) {
                //  Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
            }
        });

    }


    //function to purchsae all the cart content
    private void purchaseCartContent(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstValues.link +API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API api = retrofit.create(API.class);
        Call<List<CartDisplay>> call = api.purchaseCart(prefs.getString("username",""));
        call.enqueue(new Callback<List<CartDisplay>>() {
            @Override
            public void onResponse(Call<List<CartDisplay>> call, Response<List<CartDisplay>> response) {
                Toast.makeText(CartActivity.this, "Item Purchased", Toast.LENGTH_SHORT).show();
                lstCart.clear();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<CartDisplay>> call, Throwable t) {
                Toast.makeText(CartActivity.this, "Item Purchased", Toast.LENGTH_SHORT).show();
                lstCart.clear();
                adapter.notifyDataSetChanged();
            }
        });




    }


    //function to remove cart Content
    private void removeCartContent(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstValues.link +API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API api = retrofit.create(API.class);
        Call<List<Void>> call = api.emptyCart(prefs.getString("username",""));
        call.enqueue(new Callback<List<Void>>() {
            @Override
            public void onResponse(Call<List<Void>> call, Response<List<Void>> response) {
                Toast.makeText(CartActivity.this, "Cart Emptied", Toast.LENGTH_SHORT).show();
                lstCart.clear();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Void>> call, Throwable t) {
                Toast.makeText(CartActivity.this, "Cart Emptied", Toast.LENGTH_SHORT).show();
                lstCart.clear();
                adapter.notifyDataSetChanged();
            }
        });

    }

}
