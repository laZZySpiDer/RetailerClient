package com.example.aditya.retailerclient;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.aditya.retailerclient.Adapters.DashboardViewPageAdapter;
import com.example.aditya.retailerclient.FragmentsDashboard.FragmentCategory;
import com.example.aditya.retailerclient.FragmentsDashboard.FragmentMyOrder;
import com.example.aditya.retailerclient.FragmentsDashboard.FragmentProduct;

public class Dashboard extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageButton goToProfile,goToCart;
    private DashboardViewPageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);




        tabLayout = (TabLayout)findViewById(R.id.tablayout_id);
        viewPager = (ViewPager)findViewById(R.id.viewpager_id);
        adapter = new DashboardViewPageAdapter(getSupportFragmentManager(),this);
        goToProfile = (ImageButton)findViewById(R.id.goToProfile);
        goToCart = (ImageButton)findViewById(R.id.goToCart);


        //add the fragments here
        //adapter.AddFragment(new FragmentMyOrder(),"Notifications");
        //adapter.AddFragment(new FragmentCart(),"Cart");
        adapter.AddFragment(new FragmentProduct(),"Products");

        adapter.AddFragment(new FragmentCategory(),"Category");
        adapter.AddFragment(new FragmentMyOrder(),"My Orders");



        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(
                ContextCompat.getColor(this,R.color.white),
                ContextCompat.getColor(this,R.color.colorAccent)
        );
       //tabLayout.getTabAt(0).setIcon(R.drawable.ic_notifications_black_24dp);
       // tabLayout.getTabAt(1).setIcon(R.drawable.ic_shopping_cart_black_24dp);
//        tabLayout.getTabAt(0).setIcon(R.drawable.ic_business_center_black_24dp);
//        tabLayout.getTabAt(1).setIcon(R.drawable.ic_account_balance_black_24dp);
//        tabLayout.getTabAt(2).setIcon(R.drawable.ic_person_black_24dp);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
           //     Toast.makeText(Dashboard.this, "Tab Changed 0", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPageSelected(int position) {
            //   Toast.makeText(Dashboard.this, "Tab Changed 1", Toast.LENGTH_SHORT).show();
               adapter.notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            //    Toast.makeText(Dashboard.this, "Tab Changed 2", Toast.LENGTH_SHORT).show();
            }
        });
       // viewPager.canScrollHorizontally(4);


        //when cart is clicked
        goToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this,CartActivity.class);
                startActivity(intent);
            }
        });


        //when profile is clicked
        goToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this,ProfileActivity.class);
                startActivity(intent);
            }
        });

    }
}
