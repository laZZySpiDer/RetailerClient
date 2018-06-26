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
import com.example.aditya.retailerclient.Adapters.OfferRecyclerAdapter;
import com.example.aditya.retailerclient.ConstValues;
import com.example.aditya.retailerclient.Model.Offers;
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

public class FragmentOffers extends Fragment {
    View v;
    OfferRecyclerAdapter adapter;
    private RecyclerView myrecyclerview;
    private List<Offers> lstOffer;
    LinearLayoutManager linearLayoutManager;

    API api;
    public FragmentOffers() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.offer_fragment,container,false);
        myrecyclerview = (RecyclerView)v.findViewById(R.id.offer_recyclerview);

        String usernameAuto;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext()) ;
        usernameAuto = prefs.getString("username","");
        adapter = new OfferRecyclerAdapter(getContext(),lstOffer);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        myrecyclerview.setLayoutManager(linearLayoutManager);
        myrecyclerview.setAdapter(adapter);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lstOffer = new ArrayList<>();
        load_data_from_server();
    }


    private void load_data_from_server() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstValues.link+API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(API.class);

        Call<List<Offers>> call = api.getAllOffers();
        call.enqueue(new Callback<List<Offers>>() {
            @Override
            public void onResponse(Call<List<Offers>> call, Response<List<Offers>> response) {

                List<Offers> offers = response.body();
                // data_list.addAll(prod);
                try{
                    lstOffer.addAll(offers);
                    adapter.notifyDataSetChanged();
                }catch(NullPointerException e){
                    Toast.makeText(getContext(), "Check Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Offers>> call, Throwable t) {

                Toast.makeText(getContext(), "Offers not Found", Toast.LENGTH_SHORT).show();
            }
        });




    }



}
