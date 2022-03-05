package com.demo.takeoutapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.demo.takeoutapp.R;
import com.demo.takeoutapp.data.TakeOrder;
import com.demo.takeoutapp.data.TakeUserinfo;
import com.demo.takeoutapp.ui.home.NetViewHolder;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.constants.IndicatorGravity;
import com.zhpan.bannerview.constants.IndicatorSlideMode;
import com.zhpan.bannerview.holder.HolderCreator;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import static com.zhpan.bannerview.constants.IndicatorStyle.ROUND_RECT;
import static com.zhpan.bannerview.constants.PageStyle.MULTI_PAGE_SCALE;

public class UserManagerAdapter extends BaseAdapter {
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

    public UserManagerAdapter(Context mContext, List<TakeUserinfo> list) {
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
                    + "\n密码:" + data.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }

        viewHolder.updatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.updatePassword(data);
                }
            }
        });
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