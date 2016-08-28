package com.coders.initiative.umoney.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.coders.initiative.umoney.MainActivity;
import com.coders.initiative.umoney.R;
import com.coders.initiative.umoney.adapter.NotificationsAdapter;
import com.coders.initiative.umoney.model.PaymentResponseModel;

import java.util.List;

/**
 * Created by Kira on 8/27/2016.
 */
public class TransactionsFragment extends Fragment {

    /*public TransactionsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_transactions, container, false);
        List<PaymentResponseModel> history = PaymentResponseModel.listAll(PaymentResponseModel.class);
        for(int i=0;i<history.size();i++){
            System.out.println("DATA: " + history.get(i));
        }
        return view;

    }*/

    ExpandableListView notificationList;
    NotificationsAdapter notificationAdapter;

    public TransactionsFragment () {
        // Required empty public constructor
    }


    @Override
    public View onCreateView ( LayoutInflater inflater, ViewGroup container,
                               Bundle savedInstanceState ) {

        View view =  inflater.inflate ( R.layout.fragment_transactions, container, false );

        List<PaymentResponseModel> prmList = PaymentResponseModel.listAll(PaymentResponseModel.class);
        notificationList = (ExpandableListView)view.findViewById(R.id.notification_list);
        notificationAdapter = new NotificationsAdapter(MainActivity.getInstance(), prmList);
        notificationList.setAdapter(notificationAdapter);

        return view;
    }
}
