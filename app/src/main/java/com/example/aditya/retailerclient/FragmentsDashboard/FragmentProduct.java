package com.example.aditya.retailerclient.FragmentsDashboard;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.aditya.retailerclient.API;
import com.example.aditya.retailerclient.Adapters.ProductRecyclerAdapter;
import com.example.aditya.retailerclient.ConstValues;
import com.example.aditya.retailerclient.LoginPage;
import com.example.aditya.retailerclient.Model.ProductDisplay;
import com.example.aditya.retailerclient.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Aditya on 4/14/2018.
 */

public class FragmentProduct extends Fragment {

    View v;
    ProductRecyclerAdapter adapter;
    private RecyclerView myrecyclerview;
    private List<ProductDisplay> lstProduct;
    LinearLayoutManager linearLayoutManager;

    API api;
    public FragmentProduct(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.product_fragment,container,false);
        myrecyclerview = (RecyclerView)v.findViewById(R.id.product_recyclerview);

        String usernameAuto;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext()) ;
        usernameAuto = prefs.getString("username","");
        adapter = new ProductRecyclerAdapter(getContext(),lstProduct,usernameAuto);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        myrecyclerview.setLayoutManager(linearLayoutManager);
        myrecyclerview.setAdapter(adapter);


        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lstProduct = new ArrayList<>();
        load_data_from_server();
    }

    private void load_data_from_server() {
       SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        Retrofit  retrofit = new Retrofit.Builder()
                .baseUrl(ConstValues.link+API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(API.class);
        String usernameAuto;
        usernameAuto = prefs.getString("username","");
        Call<List<ProductDisplay>> call = api.getProductsPrice(usernameAuto);
        call.enqueue(new Callback<List<ProductDisplay>>() {
            @Override
            public void onResponse(Call<List<ProductDisplay>> call, Response<List<ProductDisplay>> response) {

                List<ProductDisplay> prod = response.body();
                // data_list.addAll(prod);
                try{
                    lstProduct.addAll(prod);
                    adapter.notifyDataSetChanged();
                }catch(NullPointerException e){
                    Toast.makeText(getActivity(), "Check Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ProductDisplay>> call, Throwable t) {
                Toast.makeText(getActivity(), "Items not Found", Toast.LENGTH_SHORT).show();
            }
        });




    }


}
