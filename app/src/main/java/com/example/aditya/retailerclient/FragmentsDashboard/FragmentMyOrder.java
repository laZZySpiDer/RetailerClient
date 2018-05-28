package com.example.aditya.retailerclient.FragmentsDashboard;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.aditya.retailerclient.API;
import com.example.aditya.retailerclient.Adapters.MyOrderRecyclerAdapter;
import com.example.aditya.retailerclient.ConstValues;
import com.example.aditya.retailerclient.Model.MyOrder;
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

public class FragmentMyOrder extends Fragment {
    View v;
    SwipeRefreshLayout swipeRefreshLayout;
    MyOrderRecyclerAdapter adapter;
    private RecyclerView myrecyclerview;
    private List<MyOrder> lstMyOrder;
    LinearLayoutManager linearLayoutManager;
    API api;

    public FragmentMyOrder() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.myorder_fragment,container,false);
        myrecyclerview = v.findViewById(R.id.myorder_recyclerview);
        swipeRefreshLayout = v.findViewById(R.id.swipemyorder);

        adapter = new MyOrderRecyclerAdapter(getContext(),lstMyOrder);
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lstMyOrder = new ArrayList<>();
        load_data_from_server();
        getFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }


    private void load_data_from_server() {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstValues.link +API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(API.class);
        Call<List<MyOrder>> call = api.getMyOrders(prefs.getString("username",""));
        call.enqueue(new Callback<List<MyOrder>>() {
            @Override
            public void onResponse(Call<List<MyOrder>> call, Response<List<MyOrder>> response) {
                List<MyOrder> myorder = response.body();
                    lstMyOrder.clear();
                    lstMyOrder.addAll(myorder);
                    adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<MyOrder>> call, Throwable t) {

            }
        });
    }
}
