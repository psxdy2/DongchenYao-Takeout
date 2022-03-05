package com.demo.takeoutapp.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.demo.takeoutapp.R;
import com.demo.takeoutapp.adapter.HomeAdapter;
import com.demo.takeoutapp.data.TakeStore;
import com.demo.takeoutapp.http.Apis;
import com.demo.takeoutapp.http.HttpCallBack;
import com.demo.takeoutapp.http.MyHttpUtil;
import com.demo.takeoutapp.util.Constant;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.constants.IndicatorGravity;
import com.zhpan.bannerview.constants.IndicatorSlideMode;
import com.zhpan.bannerview.holder.HolderCreator;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

import static com.demo.takeoutapp.util.ToastUtil.toast;
import static com.zhpan.bannerview.constants.PageStyle.MULTI_PAGE_SCALE;

public class FragmentHome extends Fragment {
    private View view;

    private BannerViewPager bannerview;
    private ListView content;
    private EditText search;
    private ImageView searchButton;

    private List<String> bannerlist = new ArrayList<>();
    private List<TakeStore> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_home, container, false);
        initView();
        initData();
        initBanner();
        return view;
    }

    private void initView() {
        bannerview = (BannerViewPager) view.findViewById(R.id.bannerview);
        content = (ListView) view.findViewById(R.id.content);
        search = (EditText) view.findViewById(R.id.search);
        searchButton = (ImageView) view.findViewById(R.id.search_button);

        //搜索按钮点击事件
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Search(search.getText().toString());
            }
        });
    }

    /**
     * 搜索
     */
    private void Search(String tag) {
        if (TextUtils.isEmpty(tag)) {
            initAadapter(list);
            return;
        }
        List<TakeStore> templist = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getStorename().indexOf(tag) != -1) {
                templist.add(list.get(i));
            }
        }
        initAadapter(templist);
    }

    private void initData() {
        SearchAllStore();

        bannerlist.add("https://dss2.bdstatic.com/8_V1bjqh_Q23odCf/pacific/1990667051.png");
        bannerlist.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2848120264,2778704476&fm=26&gp=0.jpg");
    }

    private void initBanner() {
        bannerview.showIndicator(true)
                .setInterval(3000)
                .setCanLoop(false)
                .setAutoPlay(true)
                .setIndicatorSlideMode(IndicatorSlideMode.WORM)
                .setRoundCorner(2)
                //.setIndicatorStyle(ROUND_RECT)
                .setIndicatorColor(Color.parseColor("#8C6C6D72"), Color.parseColor("#F9BD00"))
                .setIndicatorGravity(IndicatorGravity.CENTER)
                .setScrollDuration(1000)
                .setPageStyle(MULTI_PAGE_SCALE)
                .setPageMargin(getContext().getResources().getDimensionPixelOffset(R.dimen.dp_15))
                .setRevealWidth(getContext().getResources().getDimensionPixelOffset(R.dimen.dp_15))
                .setHolderCreator(new HolderCreator<NetViewHolder>() {
                    @Override
                    public NetViewHolder createViewHolder() {
                        return new NetViewHolder();
                    }
                })
                .setOnPageClickListener(new BannerViewPager.OnPageClickListener() {
                    @Override
                    public void onPageClick(int position) {

                    }
                }).create(bannerlist);
    }

    /**
     * 查询所有的外卖店铺
     */
    private void SearchAllStore() {
        MyHttpUtil.getInstance().GET(Apis.SearchAllStore, new HttpCallBack() {
            @Override
            public void Error(Call call, IOException e) {

            }

            @Override
            public void Success(Call call, String res) throws Exception {
                JSONObject rep = new JSONObject(res);
                if (rep.getInt("code") == 200) {
                    list.clear();
                    JSONArray array = rep.getJSONArray("data");
                    for (int i = 0; i < array.length(); i++) {
                        TakeStore takeStore = new TakeStore();
                        takeStore.setId(array.getJSONObject(i).getInt("id"));
                        takeStore.setUserid(array.getJSONObject(i).getInt("userid"));
                        takeStore.setStorename(array.getJSONObject(i).getString("storename"));
                        takeStore.setStoreinfo(array.getJSONObject(i).getString("storeinfo"));
                        takeStore.setStoreimage(array.getJSONObject(i).getString("storeimage"));
                        list.add(takeStore);
                    }
                    initAadapter(list);
                }
            }
        });
    }

    private void initAadapter(List<TakeStore> templist) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                HomeAdapter adapter = new HomeAdapter(getContext(), templist);
                adapter.setCallback(new HomeAdapter.OperationCallback() {
                    @Override
                    public void call(TakeStore takeStore) {
                        if (Constant.userinfo == null){
                            toast("请先登陆",getActivity());
                            return;
                        }
                        Intent intent = new Intent(getContext(), FoodDefailsActivity.class);
                        intent.putExtra("id", takeStore.getId().toString());
                        intent.putExtra("userid", takeStore.getUserid().toString());
                        intent.putExtra("storename", takeStore.getStorename());
                        intent.putExtra("storeimage", takeStore.getStoreimage());
                        startActivity(intent);
                    }
                });
                content.setAdapter(adapter);
            }
        });
    }


}
