package com.demo.takeoutapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.demo.takeoutapp.R;
import com.demo.takeoutapp.data.TakeFoodtype;
import com.demo.takeoutapp.data.UserinfoComment;
import com.demo.takeoutapp.ui.store.FoodActivity;

import java.util.List;

public class HomeCommentAdapter extends BaseAdapter {
    private List<UserinfoComment> list = null;
    private Context mContext;
    private OperationCallback callback;
    public interface OperationCallback{
        void call(UserinfoComment userinfoComment);
    }

    public void setCallback(OperationCallback callback) {
        this.callback = callback;
    }

    public HomeCommentAdapter(Context mContext, List<UserinfoComment> list) {
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
        final UserinfoComment data = list.get(position);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_comment, null);
            viewHolder.image = (ImageView) view.findViewById(R.id.image);
            viewHolder.name = (TextView) view.findViewById(R.id.name);
            viewHolder.info = (TextView) view.findViewById(R.id.info);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Glide.with(mContext).load(data.getPortrait()).into(viewHolder.image);
        viewHolder.name.setText(data.getUsername());
        viewHolder.info.setText(data.getComment());
        return view;
    }



    final static class ViewHolder {
        ImageView image;
        TextView name,info;
    }

}