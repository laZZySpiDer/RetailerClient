package com.example.aditya.retailerclient.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.aditya.retailerclient.Dashboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Aditya on 4/14/2018.
 */

public class DashboardViewPageAdapter extends FragmentPagerAdapter{

    private final List<Fragment> lstFragment = new ArrayList<>();
    private final List<String> lstTitles = new ArrayList<>();
    private FragmentManager fragmentManager;
    private Map<Integer,String> mFragmentTags;
    private Context mContext;
    public DashboardViewPageAdapter(FragmentManager fm, Context context) {
        super(fm);
        fragmentManager = fm;
        mFragmentTags = new HashMap<Integer, String>();
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        return lstFragment.get(position);
    }

    @Override
    public int getCount() {
        return lstTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return lstTitles.get(position);
    }

    public void AddFragment(Fragment fragment,String title){
        lstFragment.add(fragment);
        lstTitles.add(title);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
       Object obj =  super.instantiateItem(container, position);
        if(obj  instanceof Fragment ){
            Fragment f = (Fragment) obj;
            String tag = f.getTag();
            mFragmentTags.put(position,tag);
        }
        return obj;
    }

    public Fragment getFragment(int position){
        String tag = mFragmentTags.get(position);
        if(tag == null){
            return null;
        }
        return fragmentManager.findFragmentByTag(tag);
    }
}
