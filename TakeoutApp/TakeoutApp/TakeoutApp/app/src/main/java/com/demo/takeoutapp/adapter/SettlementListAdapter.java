package com.demo.takeoutapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.takeoutapp.R;
import com.demo.takeoutapp.data.GWCbean;
import com.demo.takeoutapp.data.TakeFoods;
import com.demo.takeoutapp.data.TakeFoodtype;
import com.demo.takeoutapp.ui.store.FoodActivity;

import java.util.List;

public class SettlementListAdapter extends BaseAdapter {
    private List<GWCbean> list = null;
    private Context mContext;
    private OperationCallback callback;
    public interface OperationCallback{
        void call(GWCbean gwCbean);
    }

    public void setCallback(OperationCallback callback) {
        this.callback = callback;
    }

    public SettlementListAdapter(Context mContext, List<GWCbean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    public int getCount() {
        return this.list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder;
        final GWCbean data = list.get(position);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_settlement, null);
            viewHolder.image = (ImageView) view.findViewById(R.id.image);
            viewHolder.name = (TextView) view.findViewById(R.id.name);
            viewHolder.money = (TextView) view.findViewById(R.id.money);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.name.setText(data.getTakeFoods().getFoodname() + " x" +data.getNum());
        viewHolder.money.setText(String.valueOf((double) (data.getNum() * Double.parseDouble(data.getTakeFoods().getFoodprice()))));
        return view;
    }



    final static class ViewHolder {
        ImageView image;
        TextView name,money;
    }

}