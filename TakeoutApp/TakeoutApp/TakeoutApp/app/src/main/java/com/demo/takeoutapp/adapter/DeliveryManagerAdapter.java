package com.demo.takeoutapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.demo.takeoutapp.R;
import com.demo.takeoutapp.data.TakeUserinfo;

import java.util.List;

public class DeliveryManagerAdapter extends BaseAdapter {
    private List<TakeUserinfo> list = null;
    private Context mContext;
    private OperationCallback callback;

    public interface OperationCallback {
        void updateNicename(TakeUserinfo takeUserinfo);

        void updatePassword(TakeUserinfo takeUserinfo);
    }

    public void setCallback(OperationCallback callback) {
        this.callback = callback;
    }

    public DeliveryManagerAdapter(Context mContext, List<TakeUserinfo> list) {
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
        final TakeUserinfo data = list.get(position);
        ViewHolder viewHolder = new ViewHolder();
        view = LayoutInflater.from(mContext).inflate(R.layout.item_usermanager, null);
        viewHolder.name = (TextView) view.findViewById(R.id.name);
        viewHolder.updatePassword = (TextView) view.findViewById(R.id.update_password);
        viewHolder.updateNicename = (TextView) view.findViewById(R.id.update_nickname);
        view.setTag(viewHolder);

        try {
            viewHolder.name.append("昵称:" + data.getUsername()
                    + "\n手机号:" + data.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }

        viewHolder.updateNicename.setText("删除骑手");
        viewHolder.updatePassword.setVisibility(View.GONE);
        viewHolder.updateNicename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.updateNicename(data);
                }
            }
        });
        return view;
    }

    final static class ViewHolder {
        TextView name, updatePassword, updateNicename;
    }

}