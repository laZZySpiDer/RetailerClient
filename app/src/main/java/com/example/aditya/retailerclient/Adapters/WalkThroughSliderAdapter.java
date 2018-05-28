package com.example.aditya.retailerclient.Adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.aditya.retailerclient.R;

/**
 * Created by Aditya on 5/27/2018.
 */

public class WalkThroughSliderAdapter extends PagerAdapter{

    Context context;
    LayoutInflater layoutInflater;

    public WalkThroughSliderAdapter(Context context) {
        this.context = context;
    }

    //Arrays
    public int[] slide_images = {
            R.drawable.widerange,
            R.drawable.buy,
            R.drawable.doorstep
    };

    public String[] slide_headings = {
           "Widerange Products",
            "Add to Cart",
            "Doorstep Delivery"
    };

    public String[] slide_desc = {
            "We have wide range of products of all household necessary things. We also have them in almost all sizes available in market which are being sold at a much cheaper" +
                    " rate than others.",
            "Whatever you wish to buy, just add it to your cart. Add products to cart from our large inventory and you are just one click away from buying it." +
                    "If you wish to drop an item from cart, be our guest, we provide that too.",
            "Oh wait!! We provide Doortstep delivery to all our retailers. Just place an order and we will reach out to you and deliver the products on time." +
                    "Thank you."
    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (RelativeLayout)object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout,container,false);

        ImageView slideImageVIew = (ImageView)view.findViewById(R.id.imageSample);
        TextView slideHeading = (TextView)view.findViewById(R.id.heading);
        TextView slidedesc = (TextView)view.findViewById(R.id.caption);

        slideImageVIew.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slidedesc.setText(slide_desc[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }
}
