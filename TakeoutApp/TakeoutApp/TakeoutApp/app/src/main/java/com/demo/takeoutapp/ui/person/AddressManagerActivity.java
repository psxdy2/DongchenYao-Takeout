package com.demo.takeoutapp.ui.person;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.demo.takeoutapp.R;
import com.demo.takeoutapp.adapter.AddressAdapter;
import com.demo.takeoutapp.adapter.TypeAdapter;
import com.demo.takeoutapp.data.TakeAddress;
import com.demo.takeoutapp.data.TakeFoodtype;
import com.demo.takeoutapp.http.Apis;
import com.demo.takeoutapp.http.HttpCallBack;
import com.demo.takeoutapp.http.MyHttpUtil;
import com.demo.takeoutapp.ui.BaseActivity;
import com.demo.takeoutapp.ui.store.AddAddressActivity;
import com.demo.takeoutapp.ui.store.AddTypeActivity;
import com.demo.takeoutapp.ui.store.MyStoreActivity;
import com.demo.takeoutapp.util.Constant;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class AddressManagerActivity extends BaseActivity {

    private LinearLayout headGroup;
    private ImageView headLefticon;
    private TextView headText;
    private ImageView headRighticon;
    private ListView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_manager);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAddress();
    }

    private void initView() {
        headGroup = (LinearLayout) findViewById(R.id.head_group);
        headLefticon = (ImageView) findViewById(R.id.head_lefticon);
        headText = (TextView) findViewById(R.id.head_text);
        headRighticon = (ImageView) findViewById(R.id.head_righticon);
        content = (ListView) findViewById(R.id.content);

        headLefticon.setImageResource(R.mipmap.arrowleft_back);
        headLefticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        headText.setText("地址管理");
        headRighticon.setImageResource(R.mipmap.add);
        headRighticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddressManagerActivity.this, AddAddressActivity.class));
            }
        });
    }

    /**
     * 查找地址信息
     */
    private void getAddress() {
        show();
        MyHttpUtil.getInstance().GET(Apis.SearchAddress + "?id=" + Constant.userinfo.getId(), new HttpCallBack() {
            @Override
            public void Error(Call call, IOException e) {
                dismiss();
            }

            @Override
            public void Success(Call call, String res) throws Exception {
                dismiss();
                JSONObject jsonObject = new JSONObject(res);
                if (jsonObject.getInt("code") == 200) {
                    List<TakeAddress> list = new ArrayList<>();
                    JSONArray array = jsonObject.getJSONArray("data");
                    for (int i = 0; i < array.length(); i++) {
                        TakeAddress takeAddress = new TakeAddress();
                        takeAddress.setId(array.getJSONObject(i).getInt("id"));
                        takeAddress.setName(array.getJSONObject(i).getString("name"));
                        takeAddress.setPhone(array.getJSONObject(i).getString("phone"));
                        takeAddress.setAddress(array.getJSONObject(i).getString("address"));
                        list.add(takeAddress);
                    }
                    initAadapter(list);
                }
            }
        });
    }

    private void initAadapter(List<TakeAddress> list) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AddressAdapter adapter = new AddressAdapter(AddressManagerActivity.this, list);
                adapter.setCallback(new AddressAdapter.OperationCallback() {
                    @Override
                    public void call(TakeAddress takeAddress) {
                        delete(takeAddress.getId());
                    }
                });
                content.setAdapter(adapter);
            }
        });
    }

    /**
     * 删除
     */
    private void delete(int id) {
        show();
        MyHttpUtil.getInstance().GET(Apis.deleteAddress + "?id=" + id, new HttpCallBack() {
            @Override
            public void Error(Call call, IOException e) {
                dismiss();
            }

            @Override
            public void Success(Call call, String res) throws Exception {
                dismiss();
                JSONObject jsonObject = new JSONObject(res);
                if (jsonObject.getInt("code") == 200) {
                    getAddress();
                }
            }
        });
    }
}