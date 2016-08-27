package com.coders.initiative.umoney.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.coders.initiative.umoney.R;
import com.coders.initiative.umoney.dummies.DummyContent;
import com.coders.initiative.umoney.model.NavModel;

import java.util.List;

/**
 * Created by Kira on 8/27/2016.
 */
public class DrawerAdapter extends BaseAdapter{

    private Context mContext;
    private List<NavModel> mDrawerItems;
    private LayoutInflater mInflater;

    public DrawerAdapter(Context context) {
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mDrawerItems = DummyContent.getDrawerListIcons();
        mContext = context;
    }

    @Override
    public int getCount() {
        return mDrawerItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mDrawerItems.get(position);
    }

    public String getTitle(int position){
        return  mDrawerItems.get(position).getText();
    }

    @Override
    public long getItemId(int position) {
        return mDrawerItems.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(
                    R.layout.nav_row_item, parent,
                    false);
            holder = new ViewHolder();

            holder.icon = (ImageView) convertView
                    .findViewById(R.id.nav_row_item_icon);
            holder.title = (TextView) convertView.findViewById(R.id.nav_row_item_title);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        NavModel item = mDrawerItems.get(position);
        holder.icon.setImageDrawable(DummyContent.getNavDrawerDrawable(mContext,position));
        holder.title.setText(item.getText());
        return convertView;
    }

    private static class ViewHolder {
        public ImageView icon;
        public TextView title;
    }
}
