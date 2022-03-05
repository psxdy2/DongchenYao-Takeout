package com.demo.takeoutapp.ui.store;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.demo.takeoutapp.R;
import com.demo.takeoutapp.http.Apis;
import com.demo.takeoutapp.http.HttpCallBack;
import com.demo.takeoutapp.http.MyHttpUtil;
import com.demo.takeoutapp.ui.BaseActivity;
import com.demo.takeoutapp.util.Constant;
import com.demo.takeoutapp.util.NavigationManager;
import com.kproduce.roundcorners.RoundButton;
import com.kproduce.roundcorners.RoundImageView;
import com.kproduce.roundcorners.RoundLinearLayout;
import com.kproduce.roundcorners.RoundTextView;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;

public class MyStoreActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout headGroup;
    private ImageView headLefticon;
    private TextView headText;
    private ImageView headRighticon;
    private RoundImageView storeImage;
    private RoundTextView storeInfo;
    private FrameLayout load;
    private RoundButton createStore;
    private RoundLinearLayout settingtype;
    private RoundLinearLayout salesStatistics;

    //店铺id
    public static String storeid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavigationManager.setStatusBarColor(this);
        NavigationManager.setBottomNavigationColor(this);
        setContentView(R.layout.activity_my_store);
        initView();
        checkIsStore();
    }

    private void initView() {
        headGroup = (LinearLayout) findViewById(R.id.head_group);
        headLefticon = (ImageView) findViewById(R.id.head_lefticon);
        headText = (TextView) findViewById(R.id.head_text);
        headRighticon = (ImageView) findViewById(R.id.head_righticon);
        storeImage = (RoundImageView) findViewById(R.id.store_image);
        storeInfo = (RoundTextView) findViewById(R.id.store_info);
        settingtype = (RoundLinearLayout) findViewById(R.id.settingtype);
        salesStatistics = (RoundLinearLayout) findViewById(R.id.sales_statistics);
        load = (FrameLayout) findViewById(R.id.load);
        createStore = (RoundButton) findViewById(R.id.create_store);

        headLefticon.setImageResource(R.mipmap.arrowleft_back);
        headLefticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        headText.setText("我的店铺");

        createStore.setOnClickListener(this);
        settingtype.setOnClickListener(this);
        salesStatistics.setOnClickListener(this);
    }

    /**
     * 验证当前账号是否创建了店铺
     */
    private void checkIsStore() {
        show();
        MyHttpUtil.getInstance().GET(Apis.SearchStore + "?id=" + Constant.userinfo.getId(), new HttpCallBack() {
            @Override
            public void Error(Call call, IOException e) {
                dismiss();
            }

            @Override
            public void Success(Call call, String res) throws Exception {
                dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(res);
                            if (jsonObject.getInt("code") == 200) {
                                load.setVisibility(View.GONE);
                                storeid = String.valueOf(jsonObject.getJSONObject("data").getInt("id"));
                                String storeimage = jsonObject.getJSONObject("data").getString("storeimage");
                                String storename = jsonObject.getJSONObject("data").getString("storename");
                                String storeinfo = jsonObject.getJSONObject("data").getString("storeinfo");
                                //加载图片
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Glide.with(MyStoreActivity.this).load(storeimage).into(storeImage);
                                        storeInfo.setText(storename + "\n" + storeinfo);
                                    }
                                });
                            } else {
                                load.setVisibility(View.VISIBLE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_store:
                startActivity(new Intent(MyStoreActivity.this, CreateStoreActivity.class));
                break;
            case R.id.settingtype:
                startActivity(new Intent(MyStoreActivity.this, TypeActivity.class));
                break;
            case R.id.sales_statistics:
                startActivity(new Intent(MyStoreActivity.this, SalesActivity.class));
                break;
        }
    }
}