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
   public TextView grandTotalLayout;
    ImageView back;
    CartRecyclerAdapter adapter;
    private RecyclerView myrecyclerview;
    private List<CartDisplay> lstCart;
    private List<Offers> lstOffer;
    private List<Double> grandTotalList;
    LinearLayoutManager linearLayoutManager;
    API api;
    SwipeRefreshLayout swipedown;
    Double grandTotalNoDiscount;


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
                grandTotal(lstCart);
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
                    //to get and check whether offer is available or not



                     purchaseCartContent();
                     getAllOffers();
                     grandTotalLayout.setText("0");
                }

            }
        });


        removeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(lstCart.isEmpty()){
                   Toast.makeText(CartActivity.this, "Cart is Already Empty", Toast.LENGTH_SHORT).show();
               }else{
                   removeCartContent();
                   grandTotalLayout.setText("0");
               }
            }
        });

    }

    public void grandTotal(List<CartDisplay> lstCartTotal){

        grandTotalLayout = findViewById(R.id.grandTotal);
        grandTotalList = new ArrayList<>();
        if(lstCartTotal.isEmpty()){
            grandTotalLayout.setText("0");
        }else{

            for(CartDisplay cartContent : lstCartTotal){
                grandTotalList.add(cartContent.getPrice());
            }
            Double totalPriceInCart = 0.0;
            for(Double indiPrice : grandTotalList){
                totalPriceInCart +=indiPrice;
            }
            grandTotalList.clear();
            DecimalFormat numberFormat = new DecimalFormat("#.00");
            grandTotalNoDiscount = Double.valueOf(numberFormat.format(totalPriceInCart));
            grandTotalLayout.setText(String.valueOf(grandTotalNoDiscount));
        }

    }


    // a function to get all the offers and check whether it is applicable or not
    private void getAllOffers() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstValues.link+API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(API.class);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Call<List<Offers>> call = api.getAllOffers(prefs.getString("username",""));
        call.enqueue(new Callback<List<Offers>>() {
            @Override
            public void onResponse(Call<List<Offers>> call, Response<List<Offers>> response) {
                List<Offers> newOffers = response.body();
                lstOffer.clear();
                for(Offers offers : newOffers){
                    if(grandTotalNoDiscount >= offers.getOffer_Min_Amount() && grandTotalNoDiscount <= offers.getOffer_Max_Amount() && offers.getValid().equals("ENABLE")){
                        Toast.makeText(CartActivity.this, "Offer Available", Toast.LENGTH_SHORT).show();
                        addToBill(grandTotalNoDiscount,offers.getDiscount());

                    }else{
                        //Toast.makeText(CartActivity.this, "No Offer Available", Toast.LENGTH_SHORT).show();
                        addToBill(grandTotalNoDiscount,0);
                    }
                    lstOffer.add(offers);
                }

            }

            @Override
            public void onFailure(Call<List<Offers>> call, Throwable t) {
                //  Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
            }
        });

    }


    //function to add data and discount to the bill
    private void addToBill(Double grandTotalNoDiscount, double discount) {
        //resume from here.
        //have to send total amount,final amount,discount,username(user_id) to the table
        //and then the purchasing normal process should be resumed
        Double grandTotalWithDiscount;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstValues.link+API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        if(discount >0){
            //if discount is applied then we find the final amount that is to applied to the total amount
            grandTotalWithDiscount = grandTotalNoDiscount - (grandTotalNoDiscount * (discount/100));
        }else{
            //if no discount is found than will keep both initial and final value equal
            grandTotalWithDiscount = grandTotalNoDiscount ;
        }
        api = retrofit.create(API.class);
        String usernameAuto = prefs.getString("username","");
        Call<List<Void>> call = api.addBillTableData(usernameAuto,grandTotalNoDiscount,grandTotalWithDiscount,discount);
        call.enqueue(new Callback<List<Void>>() {
            @Override
            public void onResponse(Call<List<Void>> call, Response<List<Void>> response) {
                Toast.makeText(CartActivity.this, "Changes are made to bill table", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<List<Void>> call, Throwable t) {
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
                  lstCart.addAll(cart);
//                for(CartDisplay cartContent : cart){
////                    grandTotalList.add(cartContent.getPrice());
//                    lstCart.add(cartContent);
//                }
                //function call to set Total Amount of the contents in cart
                adapter.notifyDataSetChanged();
                grandTotal(lstCart);

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
                grandTotalLayout.setText("0.00");
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
