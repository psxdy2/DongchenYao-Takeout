package com.demo.takeoutapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.demo.takeoutapp.R;
import com.demo.takeoutapp.data.TakeFoodtype;
import com.demo.takeoutapp.data.TakeOrder;
import com.demo.takeoutapp.ui.home.NetViewHolder;
import com.demo.takeoutapp.ui.store.FoodActivity;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.constants.IndicatorGravity;
import com.zhpan.bannerview.constants.IndicatorSlideMode;
import com.zhpan.bannerview.holder.HolderCreator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.zhpan.bannerview.constants.IndicatorStyle.ROUND_RECT;
import static com.zhpan.bannerview.constants.PageStyle.MULTI_PAGE_SCALE;

public class OrderAdapter extends BaseAdapter {
    private List<TakeOrder> list = null;
    private Context mContext;
    private OperationCallback callback;

    public interface OperationCallback {
        void confirm(TakeOrder takeOrder);
        void comment(TakeOrder takeOrder);
        void cancel(TakeOrder takeOrder);
    }

    public void setCallback(OperationCallback callback) {
        this.callback = callback;
    }

    public OrderAdapter(Context mContext, List<TakeOrder> list) {
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
        final TakeOrder data = list.get(position);
        ViewHolder viewHolder = new ViewHolder();
        view = LayoutInflater.from(mContext).inflate(R.layout.item_order, null);
        viewHolder.name = (TextView) view.findViewById(R.id.name);
        viewHolder.confirm = (TextView) view.findViewById(R.id.confirm);
        viewHolder.comment = (TextView) view.findViewById(R.id.comment);
        viewHolder.cancel = (TextView) view.findViewById(R.id.cancel);
        viewHolder.image = (com.zhpan.bannerview.BannerViewPager) view.findViewById(R.id.image);
        viewHolder.sendtime = (TextView) view.findViewById(R.id.sendtime);
        viewHolder.delivery_tx = (ImageView) view.findViewById(R.id.delivery_tx);
        viewHolder.delivery_info = (TextView) view.findViewById(R.id.delivery_info);
        view.setTag(viewHolder);

        try {
            /**
             * 待收货
             * 待评价
             * 已完成
             */
            List<String> images = new ArrayList<>();
            viewHolder.name.append(data.getStatus() + "  订单时间" +data.getCreatedtime().replace("T"," ")+ "\n");
            viewHolder.name.append("总金额:"+data.getPrice() + "\n");
            JSONArray array = new JSONArray(data.getFoods());
            for (int i = 0; i < array.length(); i++) {
                images.add(array.getJSONObject(i).getJSONObject("takeFoods").getString("foodimage"));
                viewHolder.name.append(
                        array.getJSONObject(i).getJSONObject("takeFoods").getString("foodname")
                                + " *" + array.getJSONObject(i).getInt("num") + "\n");
            }
            viewHolder.image.showIndicator(true)
                    .setInterval(1500)
                    .setCanLoop(false)
                    .setAutoPlay(true)
                    .setIndicatorSlideMode(IndicatorSlideMode.WORM)
                    .setRoundCorner(2)
                    .setIndicatorStyle(ROUND_RECT)
                    .setIndicatorColor(Color.parseColor("#8C6C6D72"), Color.parseColor("#F9BD00"))
                    .setIndicatorGravity(IndicatorGravity.CENTER)
                    .setScrollDuration(1000)
                    .setPageStyle(MULTI_PAGE_SCALE)
                    .setHolderCreator(new HolderCreator<NetViewHolder>() {
                        @Override
                        public NetViewHolder createViewHolder() {
                            return new NetViewHolder();
                        }
                    }).create(images);
            viewHolder.sendtime.setText("预计到达时间:"+data.getSendtime());
            if (data.getStatus().equals("待收货")) {
                viewHolder.comment.setVisibility(View.GONE);
            } else if (data.getStatus().equals("待评价")) {
                viewHolder.sendtime.setText("送达时间:"+data.getSendtime());
                viewHolder.confirm.setVisibility(View.GONE);
                viewHolder.cancel.setVisibility(View.GONE);
            } else if (data.getStatus().equals("已完成")) {
                viewHolder.sendtime.setText("送达时间:"+data.getSendtime());
                viewHolder.confirm.setVisibility(View.GONE);
                viewHolder.comment.setVisibility(View.GONE);
                viewHolder.cancel.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(data.getDeliveryinfo())){
                JSONObject info = new JSONObject(data.getDeliveryinfo());
                Glide.with(mContext).load(info.getString("portrait")).into(viewHolder.delivery_tx);
                viewHolder.delivery_info.setText("本次配送由配送员 "+info.getString("username")+" 为你服务\n联系电话:"+info.getString("account"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        viewHolder.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null){
                    callback.confirm(data);
                }
            }
        });
        viewHolder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null){
                    callback.comment(data);
                }
            }
        });
        viewHolder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null){
                    callback.cancel(data);
                }
            }
        });
        return view;
    }

    final static class ViewHolder {
        TextView name, confirm, comment, cancel;
        com.zhpan.bannerview.BannerViewPager image;
        TextView sendtime;
        ImageView delivery_tx;
        TextView delivery_info;
    }

}