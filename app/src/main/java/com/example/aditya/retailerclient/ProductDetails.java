package com.example.aditya.retailerclient;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.aditya.retailerclient.Adapters.ProductRecyclerAdapter;
import com.example.aditya.retailerclient.Model.ProductDisplay;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductDetails extends AppCompatActivity {
    Button btnPurchase;
    MaterialEditText quant;
    ImageView backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        getIncomingIntent();


        btnPurchase = (Button)findViewById(R.id.addToCart);
        quant = (MaterialEditText)findViewById(R.id.quantity);
        backButton = (ImageView)findViewById(R.id.backProductDetails);
        quant.setText("1");

        btnPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quant.getText().toString().isEmpty() || quant.getText().toString().length() >10000
                        || quant.getText().toString().equals("0") )
                {
                    Toast.makeText(ProductDetails.this, "Value should be between 0 and 9999", Toast.LENGTH_SHORT).show();

                }else{
                    addToCartEvent();
                }


            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDetails.this,Dashboard.class);
                startActivity(intent);
            }
        });

    }





    private void addToCartEvent() {
            int quantity = Integer.parseInt(quant.getText().toString());
            int prodId = getIntent().getIntExtra("prodId",0);
            Double TotalPrice = quantity * Double.parseDouble(getIntent().getStringExtra("prod_price"));

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ConstValues.link+API.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            API api = retrofit.create(API.class);
            Call<Void> call = api.addtoCart(prefs.getString("username",""),prodId,quantity,TotalPrice);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {

                    Toast.makeText(ProductDetails.this,"Added To Cart",Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(ProductDetails.this,"Added To Cart",Toast.LENGTH_LONG).show();
                }
            });





    }


    private void getIncomingIntent(){
        if(getIntent().hasExtra("image_url")&&getIntent().hasExtra("image_name")){
            String imageUrl = getIntent().getStringExtra("image_url");
            String imagename = getIntent().getStringExtra("image_name");
            String imageSize = getIntent().getStringExtra("image_size");
            String prodPrice = getIntent().getStringExtra("prod_price");
            setImage(imageUrl,imagename,imageSize,prodPrice);
        }
    }


    private void setImage(String imageUrl,String imageName,String imageSize,String prodPrice){
        TextView name = findViewById(R.id.imagedesc);
        name.setText(imageName);
        TextView size = findViewById(R.id.imageSize);
        size.setText(imageSize);
        TextView price = findViewById(R.id.prodPrice);
        price.setText(prodPrice);
        ImageView image =findViewById(R.id.image);
        Log.d("ABCDEFGH",imageUrl);
        Glide.with(ProductDetails.this).load(ConstValues.Prodimagelink + imageUrl).into(image);
    }
}
