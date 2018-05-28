package com.example.aditya.retailerclient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aditya.retailerclient.Adapters.WalkThroughSliderAdapter;

public class WalkThrough extends AppCompatActivity {

    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;
    private Button mNextButton;
    private Button mPrevButton;
    private int mCurrentPage;
    SharedPreferences prefs;
    private TextView[] mDots;
    private WalkThroughSliderAdapter sliderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(prefs.getBoolean("firstTime",false)){
            Intent intent = new Intent(WalkThrough.this,LoginPage.class);
            startActivity(intent);
            finish();
        }

        setContentView(R.layout.activity_walk_through);

        mSlideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        mDotLayout = (LinearLayout)findViewById(R.id.dotsLayout);
        mNextButton = (Button)findViewById(R.id.nextButton);
        mPrevButton = (Button)findViewById(R.id.prevButton);
        sliderAdapter = new WalkThroughSliderAdapter(this);
        mSlideViewPager.setAdapter(sliderAdapter);
        addDotsIndicator(0);

        mSlideViewPager.addOnPageChangeListener(viewListener);

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mNextButton.getText().toString().equals("Finish") || mNextButton.getText().toString().equals("FINISH")){
                    Intent intent = new Intent(WalkThrough.this,LoginPage.class);
                    prefs.edit().putBoolean("firstTime",true).apply();

                    startActivity(intent);
                    finish();
                }
                else{
                    mSlideViewPager.setCurrentItem(mCurrentPage +1);
                }

            }
        });
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSlideViewPager.setCurrentItem(mCurrentPage - 1);
            }
        });


    }

    public void addDotsIndicator(int position){
        mDots = new TextView[3];
        mDotLayout.removeAllViews();

        for(int i=0;i<mDots.length;i++){
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));

            mDotLayout.addView(mDots[i]);
        }

        if(mDots.length > 0){
            mDots[position].setTextColor(getResources().getColor(R.color.white));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
                addDotsIndicator(position);
                mCurrentPage = position;
            if (position == 0) {

                mNextButton.setEnabled(true);
                mPrevButton.setEnabled(false);
                mPrevButton.setVisibility(View.INVISIBLE);
                mNextButton.setText("Next");
                mPrevButton.setText("");
            } else if (position == mDots.length - 1) {
                mNextButton.setEnabled(true);
                mPrevButton.setEnabled(true);
                mPrevButton.setVisibility(View.VISIBLE);
                mNextButton.setText("Finish");
                mPrevButton.setText("Back");
            } else {
                mNextButton.setEnabled(true);
                mPrevButton.setEnabled(true);
                mPrevButton.setVisibility(View.VISIBLE);
                mNextButton.setText("Next");
                mPrevButton.setText("Back");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}
