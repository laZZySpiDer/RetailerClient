package com.example.aditya.retailerclient.FragmentsDashboard;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.aditya.retailerclient.API;
import com.example.aditya.retailerclient.Adapters.CategoryRecyclerAdapter;
import com.example.aditya.retailerclient.Adapters.ProductRecyclerAdapter;
import com.example.aditya.retailerclient.ConstValues;
import com.example.aditya.retailerclient.Model.ProductDisplay;
import com.example.aditya.retailerclient.Model.ProductParent;
import com.example.aditya.retailerclient.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Aditya on 4/14/2018.
 */

public class FragmentCategory extends Fragment {

    View v;
    CategoryRecyclerAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView myrecyclerview;
    private List<ProductParent> lstMyParent;
    LinearLayoutManager linearLayoutManager;
    API api;

    public FragmentCategory() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.category_fragment,container,false);
        myrecyclerview = v.findViewById(R.id.category_recyclerview);
        swipeRefreshLayout = v.findViewById(R.id.swipecategory);

        adapter = new CategoryRecyclerAdapter(getContext(),lstMyParent);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        myrecyclerview.setLayoutManager(linearLayoutManager);
        myrecyclerview.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                load_data_from_server();
            }
        });
        return v;
    }

    private void load_data_from_server() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstValues.link +API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(API.class);
        Call<List<ProductParent>> call = api.getParents();
        call.enqueue(new Callback<List<ProductParent>>() {
            @Override
            public void onResponse(Call<List<ProductParent>> call, Response<List<ProductParent>> response) {
                List<ProductParent> myorder = response.body();
                lstMyParent.clear();
                try{
                    lstMyParent.addAll(myorder);
                    adapter.notifyDataSetChanged();
                }catch (NullPointerException ex){
                    Toast.makeText(getActivity(), "No Categories", Toast.LENGTH_SHORT).show();
                }catch (Exception ex){
                    Toast.makeText(getActivity(), "Check Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<ProductParent>> call, Throwable t) {
                Toast.makeText(getActivity(), "Check Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lstMyParent = new ArrayList<>();
        load_data_from_server();

    }
}
