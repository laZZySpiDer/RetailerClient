package com.example.aditya.retailerclient.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aditya.retailerclient.Model.MyOrder;
import com.example.aditya.retailerclient.Model.ProductParent;
import com.example.aditya.retailerclient.ParentWiseProducts;
import com.example.aditya.retailerclient.R;

import java.util.List;

/**
 * Created by Aditya on 5/7/2018.
 */

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.MyViewHolder> {

    Context mContext;
    List<ProductParent> mData;

    public CategoryRecyclerAdapter(Context mContext, List<ProductParent> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_category,parent,false);
        MyViewHolder vholder = new MyViewHolder(v);
        return vholder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.parentName.setText(mData.get(position).getPP_name());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ParentWiseProducts.class);
                intent.putExtra("PP_id_FK",mData.get(position).getPP_id());
                mContext.startActivity(intent);

                //Toast.makeText(mContext, "Item Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView parentName;

        public MyViewHolder(View itemView) {
            super(itemView);
            parentName = itemView.findViewById(R.id.parentName);
        }
    }
}
