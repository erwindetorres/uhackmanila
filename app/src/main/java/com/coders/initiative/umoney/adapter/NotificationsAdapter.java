package com.coders.initiative.umoney.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coders.initiative.umoney.R;
import com.coders.initiative.umoney.model.PaymentResponseModel;

import java.util.List;

/**
 * Created by Kira on 8/28/2016.
 */
public class NotificationsAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<PaymentResponseModel> notificationList;

    public NotificationsAdapter(Context context, List<PaymentResponseModel> notificationList){
        this.context = context;
        this.notificationList = notificationList;
    }

    @Override
    public int getGroupCount() {
        return notificationList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return notificationList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_notification_parent, null);
        }
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTransID);
        TextView tvContents = (TextView)convertView.findViewById(R.id.tvMessage);
        tvTitle.setText(String.valueOf(notificationList.get(groupPosition).getTransactionid()));
        tvContents.setText(String.valueOf(notificationList.get(groupPosition).getConfirmationno()));

        ImageView ivIndicator = (ImageView)convertView.findViewById(R.id.iv_indicator);
        LinearLayout holder = (LinearLayout)convertView.findViewById(R.id.holder);
        if(isExpanded){
            holder.setBackgroundColor(Color.parseColor("#FF5722"));
            tvTitle.setTextColor(Color.parseColor("#FFFFFF"));
            tvContents.setTextColor(Color.parseColor("#FFFFFF"));
            ivIndicator.setImageResource(R.mipmap.ic_remove);
        } else {
            holder.setBackgroundColor(Color.parseColor("#FFFFFF"));
            tvTitle.setTextColor(Color.parseColor("#000000"));
            tvContents.setTextColor(Color.parseColor("#000000"));
            ivIndicator.setImageResource(R.mipmap.ic_add);

        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_notification_child, null);
        }
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
        tvTitle.setText(String.valueOf(notificationList.get(childPosition).getConfirmationno()));
        TextView tvContents = (TextView)convertView.findViewById(R.id.tv_contents);
        tvContents.setText(String.valueOf(notificationList.get(childPosition).getMessage()));
        LinearLayout holder = (LinearLayout)convertView.findViewById(R.id.holder);
        holder.setBackgroundColor(Color.parseColor("#FFFFFF"));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

}
