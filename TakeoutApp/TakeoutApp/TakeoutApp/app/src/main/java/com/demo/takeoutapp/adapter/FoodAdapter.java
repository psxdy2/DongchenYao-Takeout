package com.demo.takeoutapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.takeoutapp.R;
import com.demo.takeoutapp.data.TakeFoods;

import java.util.List;

public class FoodAdapter extends BaseAdapter {
    private List<TakeFoods> list = null;
    private Context mContext;
    private OperationCallback callback;
    public interface OperationCallback{
        void call(TakeFoods takeFoods);
    }

    public void setCallback(OperationCallback callback) {
        this.callback = callback;
    }

    public FoodAdapter(Context mContext, List<TakeFoods> list) {
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
        final TakeFoods data = list.get(position);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_food, null);
            viewHolder.group = (LinearLayout) view.findViewById(R.id.group);
            viewHolder.name = (TextView) view.findViewById(R.id.name);
            viewHolder.delete = (TextView) view.findViewById(R.id.delect);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.name.setText(data.getFoodname()
                + "\n描述:" + data.getFoodinfo()
                + "\n价格:"+data.getFoodprice());
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null){
                    callback.call(data);
                }
            }
        });
        return view;
    }

    final static class ViewHolder {
        LinearLayout group;
        TextView delete;
        TextView name;
    }

}