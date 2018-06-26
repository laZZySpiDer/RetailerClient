package com.example.aditya.retailerclient.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aditya.retailerclient.Model.Offers;
import com.example.aditya.retailerclient.R;

import java.util.List;

/**
 * Created by Aditya on 6/26/2018.
 */

public class OfferRecyclerAdapter extends RecyclerView.Adapter<OfferRecyclerAdapter.MyViewHolder> {

    Context mContext;
    List<Offers> mData;

    public OfferRecyclerAdapter(Context mContext, List<Offers> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public OfferRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_offer,parent,false);
        final MyViewHolder vHolder = new MyViewHolder(v);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(OfferRecyclerAdapter.MyViewHolder holder, int position) {
        holder.offerTitle.setText(mData.get(position).getOffer_Title());
        holder.offerDescription.setText(mData.get(position).getDescription());
        holder.offerDiscount.setText(String.valueOf(mData.get(position).getDiscount()) + " %  Discount");
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView offerTitle;
        private TextView offerDescription;
        private TextView offerDiscount;


        public MyViewHolder(View itemView) {
            super(itemView);
            offerTitle = (TextView)itemView.findViewById(R.id.offerTitle);
            offerDescription = (TextView)itemView.findViewById(R.id.offerDescription);
            offerDiscount = (TextView)itemView.findViewById(R.id.offerDiscount);
        }
    }
}
