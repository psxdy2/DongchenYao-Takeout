package com.demo.takeoutapp.ui.home;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import com.demo.takeoutapp.R;
import com.zhpan.bannerview.holder.ViewHolder;

/**
 * 其实是一个工具类
 * 一个适配器
 * 扶着加载我们的轮播图
 */
public class NetViewHolder implements ViewHolder<String> {

    @Override
    public int getLayoutId() {
        return R.layout.item_net;
    }

    @Override
    public void onBind(View itemView, String data, int position, int size) {
        ImageView imageView = itemView.findViewById(R.id.banner_image);
        Glide.with(imageView.getContext()).load(data).into(imageView);
    }

}