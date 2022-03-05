package com.demo.takeoutapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.demo.takeoutapp.R;
import com.demo.takeoutapp.data.CollectStore;
import com.demo.takeoutapp.data.TakeCollect;
import com.demo.takeoutapp.data.TakeFoodtype;
import com.demo.takeoutapp.ui.home.FoodDefailsActivity;
import com.demo.takeoutapp.ui.store.FoodActivity;

import java.util.List;

public class CollectionAdapter extends BaseAdapter {
    private List<CollectStore> list = null;
    private Context mContext;
    private OperationCallback callback;
    public interface OperationCallback{
        void call(CollectStore collectStore);
    }

    public void setCallback(OperationCallback callback) {
        this.callback = callback;
    }

    public CollectionAdapter(Context mContext, List<CollectStore> list) {
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
        final CollectStore data = list.get(position);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_collection, null);
            viewHolder.name = (TextView) view.findViewById(R.id.name);
            viewHolder.group = (FrameLayout) view.findViewById(R.id.group);
            viewHolder.back = (ImageView) view.findViewById(R.id.back);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Glide.with(mContext).load(data.getStoreimage()).into(viewHolder.back);
        viewHolder.name.setText(data.getStorename());
        viewHolder.group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, FoodDefailsActivity.class);
                intent.putExtra("id",data.getStoreid().toString());
                intent.putExtra("storename",data.getStorename());
                intent.putExtra("storeimage",data.getStoreimage());
                mContext.startActivity(intent);
            }
        });
        return view;
    }



    final static class ViewHolder {
        FrameLayout group;
        ImageView back;
        TextView name;
    }

}