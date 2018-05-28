package com.example.aditya.retailerclient.FragmentsDashboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aditya.retailerclient.R;

/**
 * Created by Aditya on 4/14/2018.
 */

public class FragmentProfile extends Fragment {
    View v;

    public FragmentProfile() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.profile_fragment,container,false);
        return v;
    }
}
