package com.demo.takeoutapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.demo.takeoutapp.R;
import com.demo.takeoutapp.data.TakeAddress;
import com.demo.takeoutapp.data.TakeStore;

import java.util.List;

public class HomeAdapter extends BaseAdapter {
    private List<TakeStore> list = null;
    private Context mContext;
    private OperationCallback callback;
    public interface OperationCallback{
        void call(TakeStore takeStore);
    }

    public void setCallback(OperationCallback callback) {
        this.callback = callback;
    }

    public HomeAdapter(Context mContext, List<TakeStore> list) {
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
        final TakeStore data = list.get(position);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_home, null);
            viewHolder.name = (TextView) view.findViewById(R.id.name);
            viewHolder.group = (LinearLayout) view.findViewById(R.id.group);
            viewHolder.image = (ImageView) view.findViewById(R.id.image);
            viewHolder.delete = (TextView) view.findViewById(R.id.delect);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.name.setText(data.getStorename()
                + "\n" + data.getStoreinfo());
        Glide.with(mContext).load(data.getStoreimage()).into(viewHolder.image);
        viewHolder.group.setOnClickListener(new View.OnClickListener() {
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
        ImageView image;
    }

}