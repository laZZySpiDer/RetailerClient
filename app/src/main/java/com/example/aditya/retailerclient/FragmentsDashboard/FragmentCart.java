package com.example.aditya.retailerclient.FragmentsDashboard;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.aditya.retailerclient.API;
import com.example.aditya.retailerclient.Adapters.CartRecyclerAdapter;
import com.example.aditya.retailerclient.Model.CartDisplay;
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

public class FragmentCart extends Fragment {

    View v;
    CartRecyclerAdapter adapter;
    private RecyclerView myrecyclerview;
    private List<CartDisplay> lstCart;
    LinearLayoutManager linearLayoutManager;
    API api;
    public FragmentCart() {

    }

    @Override
    public void onStart() {
        super.onStart();
        load_data_from_server();
        adapter.refreshEvents(lstCart);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        load_data_from_server();
        adapter.refreshEvents(lstCart);
        adapter.notifyDataSetChanged();
    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        load_data_from_server();
//        adapter.refreshEvents(lstCart);
//        adapter.notifyDataSetChanged();
//    }

    //    @Override
//    public void onStop() {
//        super.onStop();
//        load_data_from_server();
//        adapter.refreshEvents(lstCart);
//        adapter.notifyDataSetChanged();
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.cart_fragment,container,false);
        myrecyclerview = (RecyclerView)v.findViewById(R.id.cart_recyclerview);
        String usernameAuto;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        usernameAuto = prefs.getString("username","");
        adapter = new CartRecyclerAdapter(getContext(),lstCart,usernameAuto);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        myrecyclerview.setLayoutManager(linearLayoutManager);
        myrecyclerview.setAdapter(adapter);
        return v;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lstCart = new ArrayList<>();
        load_data_from_server();
    }


    private void load_data_from_server(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(API.class);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String usernameAuto = prefs.getString("username","");
        Call<List<CartDisplay>> call = api.getCartContent(usernameAuto);
        call.enqueue(new Callback<List<CartDisplay>>() {
            @Override
            public void onResponse(Call<List<CartDisplay>> call, Response<List<CartDisplay>> response) {
                List<CartDisplay> cart = response.body();
                for(CartDisplay cartContent : cart){
                    lstCart.add(cartContent);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<CartDisplay>> call, Throwable t) {
              //  Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
