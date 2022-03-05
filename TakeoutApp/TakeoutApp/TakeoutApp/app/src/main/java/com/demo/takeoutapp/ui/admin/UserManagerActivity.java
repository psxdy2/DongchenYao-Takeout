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
import com.demo.takeoutapp.adapter.OrderAdapter;
import com.demo.takeoutapp.adapter.UserManagerAdapter;
import com.demo.takeoutapp.data.TakeOrder;
import com.demo.takeoutapp.data.TakeUserinfo;
import com.demo.takeoutapp.http.Apis;
import com.demo.takeoutapp.http.HttpCallBack;
import com.demo.takeoutapp.http.MyHttpUtil;
import com.demo.takeoutapp.ui.BaseActivity;
import com.demo.takeoutapp.util.Constant;
import com.dhh.websocket.RxWebSocket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class UserManagerActivity extends BaseActivity {

    private LinearLayout headGroup;
    private ImageView headLefticon;
    private TextView headText;
    private ImageView headRighticon;
    private ListView usermanagerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manager);
        initView();
    }

    private void initView() {
        headGroup = (LinearLayout) findViewById(R.id.head_group);
        headLefticon = (ImageView) findViewById(R.id.head_lefticon);
        headText = (TextView) findViewById(R.id.head_text);
        headRighticon = (ImageView) findViewById(R.id.head_righticon);
        usermanagerList = (ListView) findViewById(R.id.usermanager_list);

        headLefticon.setImageResource(R.mipmap.arrowleft_back);
        headLefticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        headText.setText("用户管理");

    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllUser();
    }

    /**
     * 请求所有用户数据
     */
    private void getAllUser() {
        MyHttpUtil.getInstance().GET(Apis.SearchAllUser, new HttpCallBack() {
            @Override
            public void Error(Call call, IOException e) {

            }

            @Override
            public void Success(Call call, String res) throws Exception {
                JSONObject jsonObject = new JSONObject(res);
                if (jsonObject.getInt("code") == 200) {
                    List<TakeUserinfo> list = new ArrayList<>();
                    JSONArray array = jsonObject.getJSONArray("data");
                    for (int i = 0; i < array.length(); i++) {
                        TakeUserinfo takeUserinfo = new TakeUserinfo();
                        takeUserinfo.setId(array.getJSONObject(i).getInt("id"));
                        takeUserinfo.setPassword(array.getJSONObject(i).getString("password"));
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
                UserManagerAdapter adapter = new UserManagerAdapter(UserManagerActivity.this, list);
                adapter.setCallback(new UserManagerAdapter.OperationCallback() {
                    @Override
                    public void updateNicename(TakeUserinfo takeUserinfo) {
                        //Intent
                        Intent intent = new Intent(UserManagerActivity.this,UpdateUsernameActivity.class);
                        //intent.putExtra("id",takeUserinfo.getId().toString());
                        //携带参数跳转
                        intent.putExtra("id",takeUserinfo.getId().toString());
                        startActivity(intent);
                    }

                    @Override
                    public void updatePassword(TakeUserinfo takeUserinfo) {
                        Intent intent = new Intent(UserManagerActivity.this,UpdatePassActivity.class);
                        intent.putExtra("id",takeUserinfo.getId().toString());
                        startActivity(intent);
                    }
                });
                usermanagerList.setAdapter(adapter);
            }
        });
    }

}