package com.demo.takeoutapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.demo.takeoutapp.R;
import com.demo.takeoutapp.data.TakeStore;
import com.demo.takeoutapp.data.TakeUserinfo;

import java.util.List;

/**
 * 简单理解适配器都作用作用就是把我们的集合里面都数据装到我们指定控件去 比如listview
 */
public class StoreManagerAdapter extends BaseAdapter {
    private List<TakeStore> list = null;
    private Context mContext;
    private OperationCallback callback;

    public interface OperationCallback {
        void delete(TakeStore takeStore);
    }

    public void setCallback(OperationCallback callback) {
        this.callback = callback;
    }

    public StoreManagerAdapter(Context mContext, List<TakeStore> list) {
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
        final TakeStore data = list.get(position);
        ViewHolder viewHolder = new ViewHolder();
        view = LayoutInflater.from(mContext).inflate(R.layout.item_storemanager, null);
        viewHolder.name = (TextView) view.findViewById(R.id.name);
        viewHolder.delete = (TextView) view.findViewById(R.id.delete);
        viewHolder.image = (ImageView) view.findViewById(R.id.storemanager_image);
        view.setTag(viewHolder);

        try {
            Glide.with(mContext).load(data.getStoreimage()).into(viewHolder.image);
            viewHolder.name.append("店铺名称:" + data.getStorename());
        } catch (Exception e) {
            e.printStackTrace();
        }

        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.delete(data);
                }
            }
        });
        return view;
    }

    final static class ViewHolder {
        TextView name, delete;
        ImageView image;
    }

}