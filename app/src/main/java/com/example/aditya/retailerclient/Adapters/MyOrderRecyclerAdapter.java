package com.example.aditya.retailerclient.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.aditya.retailerclient.API;
import com.example.aditya.retailerclient.ConstValues;
import com.example.aditya.retailerclient.Model.MyOrder;
import com.example.aditya.retailerclient.Model.ProductDisplay;
import com.example.aditya.retailerclient.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.media.CamcorderProfile.get;

/**
 * Created by Aditya on 5/9/2018.
 */

public class MyOrderRecyclerAdapter extends RecyclerView.Adapter<MyOrderRecyclerAdapter.MyViewHolder> {

    Context mContext;
    List<MyOrder> mData;
    Dialog myDialog;

    public MyOrderRecyclerAdapter(Context mContext, List<MyOrder> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyOrderRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_myorder,parent,false);
        MyViewHolder vholder = new MyViewHolder(v);
        myDialog = new Dialog(mContext);
        myDialog.setContentView(R.layout.bill_image);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return vholder;
    }

    @Override
    public void onBindViewHolder(final MyOrderRecyclerAdapter.MyViewHolder holder, final int position) {

        holder.myOrderPname.setText(mData.get(position).getP_name());
        holder.myOrderPquant.setText(String.valueOf(mData.get(position).getQuantity()));
        holder.myOrderPprice.setText(" "+String.valueOf(mData.get(position).getTotal_Price()));
        holder.myOrderPstatus.setText(mData.get(position).getSTATE());
        Glide.with(mContext).load(ConstValues.Billimagelink + mData.get(position).getFinal_bill()).into(holder.myOrderImage);

        //event for opening details of product from myorders
        holder.myOrderImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Toast.makeText(mContext, "Details Page of Product will appear", Toast.LENGTH_SHORT).show();
            }
        });

        //event for cacneling an order
        holder.cancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(mContext, "Cancelled the order", Toast.LENGTH_SHORT).show();
                cancel_the_order(mData.get(position).getT_id(),position);


            }
        });

    }

    private void showBillImage(MyViewHolder holder) {
        ImageView imageViewBill = myDialog.findViewById(R.id.billImageView);
        Glide.with(mContext).load(ConstValues.Billimagelink + mData.get(holder.getAdapterPosition()).getFinal_bill()).into(imageViewBill);
        myDialog.show();
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView myOrderPname;
        private TextView myOrderPquant;
        private TextView myOrderPprice;
        private TextView myOrderPstatus;
        private ImageView myOrderImage;
        private Button cancelOrder;


        public MyViewHolder(View itemView) {
            super(itemView);

            myOrderPname = itemView.findViewById(R.id.myorderpname);
            myOrderPprice = itemView.findViewById(R.id.myorderprice);
            myOrderPquant = itemView.findViewById(R.id.myorderpquant);
            myOrderPstatus = itemView.findViewById(R.id.myorderstatus);
            myOrderImage = itemView.findViewById(R.id.myorderpimage);
            cancelOrder = itemView.findViewById(R.id.myordercancel);

        }
    }

    //when cancel order button is clicked
    private void cancel_the_order(int T_id,final int position) {

        if(mData.get(position).getSTATE().equals("DISPATCHED")){
            Toast.makeText(mContext, "Package has been sent. Can't Cancel Now", Toast.LENGTH_SHORT).show();

        }else{
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ConstValues.link+ API.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            API api = retrofit.create(API.class);
            Call<Void> call = api.cancelMyOrder(T_id);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Toast.makeText(mContext, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                    mData.remove(position);
                    //this.notify();
                    notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(mContext, "Can't Delete Right Now", Toast.LENGTH_SHORT).show();
                }
            });

         //   load_data_from_server();
        }






    }

    private void load_data_from_server() {
        mData.clear();
        API api;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstValues.link + prefs.getString("ip","")+API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(API.class);
        Call<List<MyOrder>> call = api.getMyOrders(prefs.getString("username",""));
        call.enqueue(new Callback<List<MyOrder>>() {
            @Override
            public void onResponse(Call<List<MyOrder>> call, Response<List<MyOrder>> response) {
                List<MyOrder> myorder = response.body();
                mData.addAll(myorder);
                notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<MyOrder>> call, Throwable t) {

            }
        });
    }



}
