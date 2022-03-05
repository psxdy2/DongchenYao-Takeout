package com.demo.takeoutapp.ui.person;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.demo.takeoutapp.R;
import com.demo.takeoutapp.adapter.CollectionAdapter;
import com.demo.takeoutapp.app.MyApplication;
import com.demo.takeoutapp.data.CollectStore;
import com.demo.takeoutapp.data.TakeUserinfo;
import com.demo.takeoutapp.http.Apis;
import com.demo.takeoutapp.http.HttpCallBack;
import com.demo.takeoutapp.http.MyHttpUtil;
import com.demo.takeoutapp.ui.store.MyStoreActivity;
import com.demo.takeoutapp.ui.web.X5WebViewActivity;
import com.demo.takeoutapp.ui.widget.HorizontalListView;
import com.demo.takeoutapp.util.Constant;
import com.kproduce.roundcorners.RoundImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

import static com.demo.takeoutapp.util.ToastUtil.toast;

public class FragmentPerson extends Fragment implements View.OnClickListener {
    private View view;
    private LinearLayout headGroup;
    private ImageView headLefticon;
    private TextView headText;
    private ImageView headRighticon;
    private RoundImageView portrait;
    private TextView nickname;
    private HorizontalListView collection;
    private LinearLayout mystore;
    private LinearLayout aboutmy;
    private LinearLayout addressmanager;
    private RoundImageView advertisement;
    private LinearLayout delivery;
    private LinearLayout weather;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_person, container, false);
        initView();
        return view;
    }

    private void initView() {
        headGroup = (LinearLayout) view.findViewById(R.id.head_group);
        headLefticon = (ImageView) view.findViewById(R.id.head_lefticon);
        headText = (TextView) view.findViewById(R.id.head_text);
        headRighticon = (ImageView) view.findViewById(R.id.head_righticon);
        portrait = (RoundImageView) view.findViewById(R.id.portrait);
        nickname = (TextView) view.findViewById(R.id.nickname);
        collection = (HorizontalListView) view.findViewById(R.id.collection);
        mystore = (LinearLayout) view.findViewById(R.id.mystore);
        aboutmy = (LinearLayout) view.findViewById(R.id.aboutmy);
        addressmanager = (LinearLayout) view.findViewById(R.id.addressmanager);
        advertisement = (RoundImageView) view.findViewById(R.id.advertisement);
        delivery = (LinearLayout) view.findViewById(R.id.delivery);
        weather = (LinearLayout) view.findViewById(R.id.weather);

        nickname.setOnClickListener(this);
        mystore.setOnClickListener(this);
        aboutmy.setOnClickListener(this);
        addressmanager.setOnClickListener(this);
        delivery.setOnClickListener(this);
        weather.setOnClickListener(this);

        initData();
    }

    private void initData() {
        if (Constant.userinfo == null) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
            return;
        }
        Glide.with(getActivity()).load(Constant.userinfo.getPortrait()).into(portrait);
        nickname.setText(Constant.userinfo.getUsername());

        Glide.with(getActivity())
                .load("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1453131484,2894628883&fm=26&gp=0.jpg")
                .into(advertisement);

        getCollection();
    }

    @Override
    public void onResume() {
        super.onResume();
        List<TakeUserinfo> userinfos = MyApplication.getMyApplication().getDaoSession().getTakeUserinfoDao().loadAll();
        if (userinfos.size() == 1) {
            Constant.userinfo = userinfos.get(0);
        } else {
            MyApplication.getMyApplication().getDaoSession().getTakeUserinfoDao().deleteAll();
        }
        if (Constant.userinfo == null) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
            toast("请先登陆");
            return;
        }
        initData();
    }

    /**
     * 获取我的收藏店铺
     */
    private void getCollection() {
        MyHttpUtil.getInstance().GET(Apis.getCollection + "?id=" + Constant.userinfo.getId(), new HttpCallBack() {
            @Override
            public void Error(Call call, IOException e) {

            }

            @Override
            public void Success(Call call, String res) throws Exception {
                JSONObject jsonObject = new JSONObject(res);
                if (jsonObject.getInt("code") == 200) {
                    List<CollectStore> list = new ArrayList<>();
                    JSONArray array = jsonObject.getJSONArray("data");
                    for (int i = 0; i < array.length(); i++) {
                        CollectStore collectStore = new CollectStore();
                        collectStore.setStoreid(array.getJSONObject(i).getInt("storeid"));
                        collectStore.setCollectid(array.getJSONObject(i).getInt("collectid"));
                        collectStore.setStorename(array.getJSONObject(i).getString("storename"));
                        collectStore.setStoreinfo(array.getJSONObject(i).getString("storeinfo"));
                        collectStore.setStoreimage(array.getJSONObject(i).getString("storeimage"));
                        list.add(collectStore);
                    }
                    initAadapter(list);
                }
            }
        });
    }

    private void initAadapter(List<CollectStore> list) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CollectionAdapter adapter = new CollectionAdapter(getContext(), list);
                collection.setAdapter(adapter);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nickname:
                startActivity(new Intent(getActivity(), PersonInfoActivity.class));
                break;
            case R.id.mystore:
                //我的店铺
                startActivity(new Intent(getActivity(), MyStoreActivity.class));
                break;
            case R.id.aboutmy:
                //关于我们
                Intent intent = new Intent(getContext(), AboutActivity.class);
                intent.putExtra("content", "外卖人是一款专门服务于广西科技大学校园食堂与学生之间的外卖软件，大家通过这款app就可以在寝室里，大礼堂附近享用到食堂的美食。快来试试吧！");
                intent.putExtra("title", "公司简介");
                startActivity(intent);
                break;
            case R.id.addressmanager:
                //地址管理
                startActivity(new Intent(getActivity(), AddressManagerActivity.class));
                break;
            case R.id.delivery:
                //申请成功骑手
                getOneDelivery();
                break;
            case R.id.weather:
                intent = new Intent(getContext(), X5WebViewActivity.class);
                intent.putExtra("url", "https://m.tianqi.com/liuzhou/");
                intent.putExtra("title", "当日天气");
                startActivity(intent);
                break;
        }
    }

    /**
     * 申请骑手
     */
    private void getOneDelivery() {
        show();
        MyHttpUtil.getInstance().GET(Apis.queryDelivery + "?userid=" + Constant.userinfo.getId(), new HttpCallBack() {
            @Override
            public void Error(Call call, IOException e) {
                dismiss();
            }

            @Override
            public void Success(Call call, String res) throws Exception {
                dismiss();
                JSONObject rep = new JSONObject(res);
                if (rep.getInt("code") == 200) {
                    startActivity(new Intent(getActivity(), GrantDeliveryActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), DeliveryDataActivity.class));
                }
            }
        });
    }

    private ProgressDialog mProgressDialog;

    public void show() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getContext());
            mProgressDialog.setCancelable(false);
            mProgressDialog.setTitle("提示");
            mProgressDialog.setMessage("加载中...");
            mProgressDialog.show();
        } else {
            mProgressDialog.show();
        }
    }

    public void dismiss() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

}
