package com.example.aditya.retailerclient.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.aditya.retailerclient.API;
import com.example.aditya.retailerclient.Model.ProductDisplay;
import com.example.aditya.retailerclient.ProductDetails;
import com.example.aditya.retailerclient.R;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.example.aditya.retailerclient.ConstValues;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Aditya on 4/15/2018.
 */

public class ProductRecyclerAdapter extends RecyclerView.Adapter<ProductRecyclerAdapter.MyViewHolder> {

    Context mContext;
    List<ProductDisplay> mData;
    String username;
    Dialog myDialog;

    public ProductRecyclerAdapter(Context mContext, List<ProductDisplay> mData, String username) {
        this.mContext = mContext;
        this.mData = mData;
        this.username = username;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_product,parent,false);
        myDialog = new Dialog(mContext);
        myDialog.setContentView(R.layout.dialog_add_to_cart);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        final MyViewHolder vHolder = new MyViewHolder(v);
        vHolder.item_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  OpenCart(vHolder);
               // Toast.makeText(mContext, "Item Clicked "+ String.valueOf(vHolder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
            }
        });
        return vHolder;
    }

    //function to show dialog box when cart is clicked.
    private void OpenCartDialogue(final MyViewHolder holder){

        TextView dialog_product_name = (TextView)myDialog.findViewById(R.id.dialog_product_name);
        TextView dialog_product_price = (TextView)myDialog.findViewById(R.id.dialog_product_price);
        final MaterialEditText dialog_quantity = (MaterialEditText)myDialog.findViewById(R.id.dialog_product_quantity);
        final Button dialog_add_to_cart = (Button)myDialog.findViewById(R.id.dialog_add_to_cart);
        ImageView dialog_image = (ImageView)myDialog.findViewById(R.id.dialog_image);
        //Glide.with(mContext).load(mData.get(position).getP_image()).into(holder.P_image);
        dialog_product_name.setText(mData.get(holder.getAdapterPosition()).getP_name());
        dialog_product_price.setText(String.valueOf(mData.get(holder.getAdapterPosition()).getPrice()));
        Glide.with(mContext).load(ConstValues.Prodimagelink + mData.get(holder.getAdapterPosition()).getP_image()).into(dialog_image);
        myDialog.show();
        dialog_quantity.setText("1");
        dialog_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(dialog_quantity.getText().toString().isEmpty() || dialog_quantity.getText().toString().length() >10000
                        || dialog_quantity.getText().toString().equals("0") )
                {
                    Toast.makeText(mContext, "Value should be between 0 and 9999", Toast.LENGTH_SHORT).show();
                    myDialog.dismiss();
                }else{
                    int quantity = Integer.parseInt(dialog_quantity.getText().toString());
                    addToCartDirectly(holder,quantity);
                    dialog_quantity.setText("");
                    myDialog.dismiss();
                    Toast.makeText(mContext, "Added To Cart", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    //dialogue box add to cart button event
    private void addToCartDirectly(final MyViewHolder holder,int quantity){
       int prodId =  mData.get(holder.getAdapterPosition()).getP_id_FK();

        //find GST price
        Double priceInitial = mData.get(holder.getAdapterPosition()).getPrice();
        Double gstFinal = mData.get(holder.getAdapterPosition()).getP_Gst();
        Double finalPrice = priceInitial * (gstFinal /100);
        finalPrice += priceInitial;

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);

       Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstValues.link+API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API api = retrofit.create(API.class);
        Call<Void> call = api.addtoCart(prefs.getString("username",""),prodId,quantity,(quantity*finalPrice));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                Toast.makeText(mContext,"Added To Cart",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(mContext,"Added To Cart",Toast.LENGTH_LONG).show();
            }
        });

    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        holder.P_name.setText(mData.get(position).getP_name());
        holder.P_price.setText("\u20B9"+" "+String.valueOf(mData.get(position).getPrice()));

        //find GST price
        Double priceInitial = mData.get(position).getPrice();
        Double gstFinal = mData.get(position).getP_Gst();
        Double finalPrice = priceInitial * (gstFinal /100);
        finalPrice += priceInitial;
        holder.P_gstprice.setText("\u20B9"+" "+Double.toString(finalPrice)+" (with GST)");
        holder.P_innerPackQuantity.setText("Inner Pack Quantity : " + String.valueOf(mData.get(position).getP_Ipq()));
        holder.P_gstValue.setText("GST  :  " + String.valueOf(mData.get(position).getP_Gst()));
        Glide.with(mContext).load(ConstValues.Prodimagelink +mData.get(position).getP_image()).into(holder.P_image);

        //image click listener
//        holder.P_image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext, ProductDetails.class);
//                intent.putExtra("image_url",mData.get(position).getP_image());
//                intent.putExtra("image_name",mData.get(position).getP_name());
//                intent.putExtra("prodId",mData.get(position).getP_id_FK());
//                intent.putExtra("image_size",Double.toString(mData.get(position).getP_size()));
//                intent.putExtra("prod_price",Double.toString(mData.get(position).getPrice()));
//
//                mContext.startActivity(intent);
//            }
//        });

        //itemview  click listner
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProductDetails.class);
                intent.putExtra("image_url",mData.get(position).getP_image());
                intent.putExtra("image_name",mData.get(position).getP_name());
                intent.putExtra("prodId",mData.get(position).getP_id_FK());
                intent.putExtra("image_size",Double.toString(mData.get(position).getP_size()));
                intent.putExtra("prod_price",Double.toString(mData.get(position).getPrice()));
                intent.putExtra("gst",Double.toString(mData.get(position).getP_Gst()));
                intent.putExtra("ipq",Integer.toString(mData.get(position).getP_Ipq()));
                intent.putExtra("desc",mData.get(position).getP_desc());
                mContext.startActivity(intent);
            }
        });

        //cart click listener
        holder.P_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               OpenCartDialogue(holder);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private CardView item_product;
        private TextView P_name;
        private TextView P_price;
        private ImageView P_image;
        private ImageView P_cart;
        private TextView P_gstprice;
        private TextView P_innerPackQuantity;
        private TextView P_gstValue;

        public MyViewHolder(View itemView) {
            super(itemView);
            item_product = (CardView)itemView.findViewById(R.id.product_item);
            P_name = (TextView)itemView.findViewById(R.id.product_name);
            P_price = (TextView)itemView.findViewById(R.id.product_price);
            P_image = (ImageView)itemView.findViewById(R.id.product_image);
            P_cart = (ImageView)itemView.findViewById(R.id.cartImage);
            P_gstprice = (TextView)itemView.findViewById(R.id.product_price_GST);
            P_gstValue = (TextView)itemView.findViewById(R.id.gstValue);

            P_innerPackQuantity = (TextView)itemView.findViewById(R.id.inner_pack_quantity);
        }
    }

}
