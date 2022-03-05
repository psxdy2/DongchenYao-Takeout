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
import com.demo.takeoutapp.data.GWCbean;
import com.demo.takeoutapp.data.TakeFoods;
import com.demo.takeoutapp.ui.home.FoodDefailsActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFoodAdapter extends BaseAdapter {
    private List<TakeFoods> list = null;
    private FoodDefailsActivity activity;
    private OperationCallback callback;

    public interface OperationCallback {
        void call(TakeFoods takeFoods);
    }

    public void setCallback(OperationCallback callback) {
        this.callback = callback;
    }

    public HomeFoodAdapter(FoodDefailsActivity activity, List<TakeFoods> list) {
        this.activity = activity;
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
        final TakeFoods data = list.get(position);

        ViewHolder viewHolder = new ViewHolder();
        view = LayoutInflater.from(activity).inflate(R.layout.item_home_food, null);
        viewHolder.group = (LinearLayout) view.findViewById(R.id.group);
        viewHolder.info = (TextView) view.findViewById(R.id.info);
        viewHolder.num = (TextView) view.findViewById(R.id.num);
        viewHolder.image = (ImageView) view.findViewById(R.id.image);
        viewHolder.jian = (ImageView) view.findViewById(R.id.jian);
        viewHolder.jia = (ImageView) view.findViewById(R.id.jia);
        viewHolder.stock = (TextView) view.findViewById(R.id.stock);
        view.setTag(viewHolder);

        if (activity.caches.get(data.getId()) != null) {
            viewHolder.num.setText(activity.caches.get(data.getId()));
        }

        Glide.with(activity).load(data.getFoodimage()).into(viewHolder.image);
        viewHolder.info.setText(data.getFoodname()
                + "\n描述:" + data.getFoodinfo()
                + "\n价格:" + data.getFoodprice());
        viewHolder.stock.setText("库存:"+data.getFoodnum());
        viewHolder.jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.valueOf(viewHolder.num.getText().toString());
                if (num > 0) {
                    num--;
                } else {
                    return;
                }
                viewHolder.num.setText(String.valueOf(num));
                activity.caches.put(data.getId(), String.valueOf(num));
                activity.SumMoney = activity.SumMoney - Double.parseDouble(data.getFoodprice());
                activity.reshMoney();
                addGWC(data,num);
            }
        });
        viewHolder.jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.valueOf(viewHolder.num.getText().toString());
                if (num >= data.getFoodnum()){
                    return;
                }
                num++;
                viewHolder.num.setText(String.valueOf(num));
                activity.caches.put(data.getId(), String.valueOf(num));
                activity.SumMoney = activity.SumMoney + Double.parseDouble(data.getFoodprice());
                activity.reshMoney();
                addGWC(data,num);
            }
        });
        return view;
    }

    /**
     * 添加购物车
     */
    private void addGWC(TakeFoods data, int num) {
        //检查集合中是否已经存在相同菜品
        GWCbean gwCbean = EqFoot(data.getId());
        if (gwCbean == null) {
            gwCbean = new GWCbean();
            gwCbean.setId(data.getId());
            gwCbean.setNum(num);
            gwCbean.setTakeFoods(data);
            FoodDefailsActivity.gwCbeans.add(gwCbean);
        } else {
            gwCbean.setNum(num);
        }
    }

    private GWCbean EqFoot(int id) {
        for (int i = 0; i < FoodDefailsActivity.gwCbeans.size(); i++) {
            if (FoodDefailsActivity.gwCbeans.get(i).getId() == id) {
                return FoodDefailsActivity.gwCbeans.get(i);
            }
        }
        return null;
    }

    final static class ViewHolder {
        LinearLayout group;
        ImageView image, jia, jian;
        TextView info, num, stock;
    }

}