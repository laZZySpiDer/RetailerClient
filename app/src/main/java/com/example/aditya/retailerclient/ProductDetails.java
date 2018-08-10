package com.example.aditya.retailerclient;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductDetails extends AppCompatActivity {
    Button btnPurchase,btnIncrease,btnDecrease;
    TextView dynamicPrice;
    MaterialEditText quant;
    ImageView backButton;
    Double finalPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        dynamicPrice = (TextView)findViewById(R.id.dynamicprice);
        getIncomingIntent();


        btnPurchase = (Button)findViewById(R.id.addToCart);
        quant = (MaterialEditText)findViewById(R.id.quantity);
        backButton = (ImageView)findViewById(R.id.backProductDetails);
        btnDecrease = (Button)findViewById(R.id.btnDecrease);
        btnIncrease = (Button)findViewById(R.id.btnIncrease);

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

        btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseQty();
            }
        });


        btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               increaseQty();
            }
        });

        quant.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    if(Integer.parseInt(quant.getText().toString()) == 0){
                        dynamicPrice.setText("0");
                    }else{
                        dynamicPrice.setText(String.valueOf(Integer.parseInt(quant.getText().toString())*finalPrice));
                    }
                }catch (NumberFormatException  e){
                    dynamicPrice.setText("0");
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void decreaseQty(){
        if(Integer.parseInt(quant.getText().toString())<=0){
            Toast.makeText(this, "Cannot Decrease more Value", Toast.LENGTH_SHORT).show();
        }
        else{
            int qty = Integer.parseInt(quant.getText().toString()) - 1;
            quant.setText(String.valueOf(qty));
        }

    }

    private void increaseQty(){
        if(Integer.parseInt(quant.getText().toString())>=9999){
            Toast.makeText(this, "Cannot Increase more Value", Toast.LENGTH_SHORT).show();
        }
        else{
            int qty = Integer.parseInt(quant.getText().toString()) + 1;
            quant.setText(String.valueOf(qty));
        }
    }



    private void addToCartEvent() {
            int quantity = Integer.parseInt(quant.getText().toString());
            int prodId = getIntent().getIntExtra("prodId",0);
            Double TotalPrice = quantity * finalPrice;

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
            String gst = getIntent().getStringExtra("gst");
            String ipq = getIntent().getStringExtra("ipq");
            String desc = getIntent().getStringExtra("desc");
            setProductDetails(imageUrl,imagename,imageSize,prodPrice,gst,ipq,desc);
        }
    }


    private void setProductDetails(String imageUrl,String imageName,String imageSize,String prodPrice,String gst,String ipq,String desc){

        //set the product name
        TextView name = findViewById(R.id.imagedesc);
        name.setText(imageName);

        //set the size of the product
        TextView size = findViewById(R.id.imageSize);
        size.setText(imageSize);

        //set the ipqdetails of the product
        TextView ipqDetails = findViewById(R.id.ipq);
        ipqDetails.setText(ipq);

        //set the image descriotion of the product
        TextView imageDescription = findViewById(R.id.imgDescription);
        imageDescription.setText(desc);




        //calculate GST and show GST Price
        TextView price = findViewById(R.id.prodPrice);
        Double priceInitial = Double.parseDouble(prodPrice);
        Double gstFinal = Double.parseDouble(gst);
        finalPrice = priceInitial * (gstFinal /100);
        finalPrice += priceInitial;

        DecimalFormat numberFormat = new DecimalFormat("#.00");
        Double temp  = Double.valueOf(numberFormat.format(finalPrice));
        finalPrice = temp;
        price.setText("\u20B9"+" "+Double.toString(temp)+" (with GST)");
        dynamicPrice.setText(Double.toString(temp));

        ImageView image =findViewById(R.id.image);
        Log.d("ABCDEFGH",imageUrl);
        Glide.with(ProductDetails.this).load(ConstValues.Prodimagelink + imageUrl).into(image);
    }
}
