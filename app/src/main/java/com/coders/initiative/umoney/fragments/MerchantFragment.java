package com.coders.initiative.umoney.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coders.initiative.umoney.R;

/**
 * Created by Kira on 8/27/2016.
 */
public class MerchantFragment extends Fragment {

    public MerchantFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;

    }
}
