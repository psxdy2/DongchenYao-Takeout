package com.demo.takeoutapp.ui.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.demo.takeoutapp.R;
import com.demo.takeoutapp.adapter.DeliveryManagerAdapter;
import com.demo.takeoutapp.adapter.UserManagerAdapter;
import com.demo.takeoutapp.data.TakeUserinfo;
import com.demo.takeoutapp.http.Apis;
import com.demo.takeoutapp.http.HttpCallBack;
import com.demo.takeoutapp.http.MyHttpUtil;
import com.demo.takeoutapp.ui.BaseActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class DeliveryManagerActivity extends BaseActivity {

    private LinearLayout headGroup;
    private ImageView headLefticon;
    private TextView headText;
    private ImageView headRighticon;
    private ListView deliverymanagerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_manager);
        initView();
    }

    private void initView() {
        headGroup = (LinearLayout) findViewById(R.id.head_group);
        headLefticon = (ImageView) findViewById(R.id.head_lefticon);
        headText = (TextView) findViewById(R.id.head_text);
        headRighticon = (ImageView) findViewById(R.id.head_righticon);
        deliverymanagerList = (ListView) findViewById(R.id.deliverymanager_list);


        headLefticon.setImageResource(R.mipmap.arrowleft_back);
        headLefticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        headText.setText("骑手管理");
    }


    @Override
    protected void onResume() {
        super.onResume();
        getAlDelivery();
    }

    /**
     * 请求所有数据
     */
    private void getAlDelivery() {
        show();
        MyHttpUtil.getInstance().GET(Apis.getAllDelivery, new HttpCallBack() {
            @Override
            public void Error(Call call, IOException e) {

            }

            @Override
            public void Success(Call call, String res) throws Exception {
                dismiss();
                JSONObject jsonObject = new JSONObject(res);
                if (jsonObject.getInt("code") == 200) {
                    List<TakeUserinfo> list = new ArrayList<>();
                    JSONArray array = jsonObject.getJSONArray("data");
                    for (int i = 0; i < array.length(); i++) {
                        TakeUserinfo takeUserinfo = new TakeUserinfo();
                        takeUserinfo.setId(array.getJSONObject(i).getInt("id"));
                        takeUserinfo.setPassword(array.getJSONObject(i).getString("account"));
                        takeUserinfo.setUsername(array.getJSONObject(i).getString("username"));
                        list.add(takeUserinfo);
                    }
                    initAadapter(list);
                }
            }
        });
    }

    private void initAadapter(List<TakeUserinfo> list) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DeliveryManagerAdapter adapter = new DeliveryManagerAdapter(DeliveryManagerActivity.this, list);
                adapter.setCallback(new DeliveryManagerAdapter.OperationCallback() {
                    @Override
                    public void updateNicename(TakeUserinfo takeUserinfo) {
                        //删除骑手
                        delete(takeUserinfo.getId());
                    }

                    @Override
                    public void updatePassword(TakeUserinfo takeUserinfo) {

                    }
                });
                deliverymanagerList.setAdapter(adapter);
            }
        });
    }

    /**
     * 删除
     */
    private void delete(int id) {
        //show显示等待dialog
        show();
        MyHttpUtil.getInstance().GET(Apis.removeDelivery + "?userid=" + id, new HttpCallBack() {
            @Override
            public void Error(Call call, IOException e) {
                dismiss();
            }

            @Override
            public void Success(Call call, String res) throws Exception {
                //dismiss取消
                dismiss();
                JSONObject jsonObject = new JSONObject(res);
                if (jsonObject.getInt("code") == 200) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getAlDelivery();
                        }
                    });
                }
            }
        });
    }
}