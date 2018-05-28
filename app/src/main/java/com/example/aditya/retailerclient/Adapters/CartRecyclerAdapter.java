package com.example.aditya.retailerclient.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.aditya.retailerclient.API;
import com.example.aditya.retailerclient.ConstValues;
import com.example.aditya.retailerclient.FragmentsDashboard.FragmentCart;
import com.example.aditya.retailerclient.Model.CartDisplay;
import com.example.aditya.retailerclient.ProductDetails;
import com.example.aditya.retailerclient.R;

import org.w3c.dom.Text;

import java.util.List;

import javax.microedition.khronos.opengles.GL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Aditya on 4/15/2018.
 */

public class CartRecyclerAdapter extends RecyclerView.Adapter<CartRecyclerAdapter.CartViewHolder> {

    Context mContext;
    List<CartDisplay> mData;
    String username;
    Dialog myDialog;

    public CartRecyclerAdapter(Context mContext, List<CartDisplay> mData, String username) {
        this.mContext = mContext;
        this.mData = mData;
        this.username = username;
       // notifyDataSetChanged();
    }

    public void refreshEvents(List<CartDisplay> data){
        this.mData.clear();
        this.mData.addAll(data);
        notifyDataSetChanged();
    }


    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_cart,parent,false);
        myDialog = new Dialog(mContext);
        CartViewHolder viewHolder = new CartViewHolder(v);
       // notifyDataSetChanged();

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, final int position) {

        holder.cart_P_name.setText(mData.get(position).getP_name());
        holder.cart_P_quantity.setText("Quantity : "+String.valueOf(mData.get(position).getQuantity()));
        holder.cart_P_price.setText("INR."+String.valueOf(mData.get(position).getPrice()));
        Glide.with(mContext).load(ConstValues.Prodimagelink + mData.get(position).getP_image()).into(holder.p_image);


        holder.cart_Remove_from_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoveFromCart(position);
                mData.remove(position);
                notifyDataSetChanged();

            }
        });


        holder.p_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProductDetails();
            }
        });

    }

    private void openProductDetails() {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstValues.link+API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API api = retrofit.create(API.class);




    }

    private void RemoveFromCart(int position){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstValues.link+API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API api = retrofit.create(API.class);
        Call<List<CartDisplay>> call = api.removeFromCart(username,mData.get(position).getProduct_id(),
                mData.get(position).getPrice());
        call.enqueue(new Callback<List<CartDisplay>>() {
            @Override
            public void onResponse(Call<List<CartDisplay>> call, Response<List<CartDisplay>> response) {
                Toast.makeText(mContext, "Product Removed From cart", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<CartDisplay>> call, Throwable t) {
                Toast.makeText(mContext, "Product Removed From cart", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {

        private ImageView p_image;
        private TextView cart_P_name;
        private TextView cart_P_price;
        private TextView cart_P_quantity;
        private ImageButton cart_Remove_from_cart;

        public CartViewHolder(View itemView) {
            super(itemView);
            p_image = (ImageView)itemView.findViewById(R.id.cart_image_product);
            cart_P_name = (TextView)itemView.findViewById(R.id.cart_product_name);
            cart_P_price = (TextView)itemView.findViewById(R.id.cart_product_price);
            cart_P_quantity = (TextView)itemView.findViewById(R.id.cart_product_quantity);
            cart_Remove_from_cart = (ImageButton) itemView.findViewById(R.id.cart_product_remove);
        }
    }


}
