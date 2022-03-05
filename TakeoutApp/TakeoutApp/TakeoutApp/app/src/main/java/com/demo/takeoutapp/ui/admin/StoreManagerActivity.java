package com.demo.takeoutapp.ui.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.demo.takeoutapp.R;
import com.demo.takeoutapp.adapter.StoreManagerAdapter;
import com.demo.takeoutapp.adapter.UserManagerAdapter;
import com.demo.takeoutapp.data.TakeStore;
import com.demo.takeoutapp.data.TakeUserinfo;
import com.demo.takeoutapp.http.Apis;
import com.demo.takeoutapp.http.HttpCallBack;
import com.demo.takeoutapp.http.MyHttpUtil;
import com.demo.takeoutapp.ui.BaseActivity;
import com.demo.takeoutapp.util.Constant;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 店铺管理活动
 *
 */
public class StoreManagerActivity extends BaseActivity {

    /**
     * 组件
     */
    private LinearLayout headGroup;
    private ImageView headLefticon;
    private TextView headText;
    private ImageView headRighticon;
    // !!!!!
    private ListView storemanagerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_manager);
        initView();
    }

    private void initView() {
        headGroup = (LinearLayout) findViewById(R.id.head_group);
        headLefticon = (ImageView) findViewById(R.id.head_lefticon);
        headText = (TextView) findViewById(R.id.head_text);
        headRighticon = (ImageView) findViewById(R.id.head_righticon);
        storemanagerList = (ListView) findViewById(R.id.storeanager_list);

        //把标题栏都左侧都imageview设置一个返回左箭头图标
        headLefticon.setImageResource(R.mipmap.arrowleft_back);
        //必须给这个左箭头图标设置一个单击事件
        headLefticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //结束当前活动
                //StoreManagerActivity结束它
                finish();
            }
        });
        //设置我们的标题
        headText.setText("店铺管理");
    }

    // onResume 这里注意
    // activity的生命周期
    @Override
    protected void onResume() {
        super.onResume();
        getAllStore();
    }

    /**
     * 请求所有店铺数据
     */
    private void getAllStore() {
        //MyHttpUtil 一个http工具类
        //Get
        MyHttpUtil.getInstance().GET(Apis.SearchAllStore, new HttpCallBack() {
            @Override
            public void Error(Call call, IOException e) {

            }

            @Override
            public void Success(Call call, String res) throws Exception {
                //
                JSONObject jsonObject = new JSONObject(res);
                if (jsonObject.getInt("code") == 200) {
                    List<TakeStore> list = new ArrayList<>();
                    JSONArray array = jsonObject.getJSONArray("data");
                    for (int i = 0; i < array.length(); i++) {
                        TakeStore takeStore = new TakeStore();
                        takeStore.setId(array.getJSONObject(i).getInt("id"));
                        takeStore.setStorename(array.getJSONObject(i).getString("storename"));
                        takeStore.setStoreimage(array.getJSONObject(i).getString("storeimage"));
                        list.add(takeStore);
                    }
                    //启动适配器
                    initAadapter(list);
                }
            }
        });
    }

    private void initAadapter(List<TakeStore> list) {
        //runOnUiThread 开启一个ui线程
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //StoreManagerAdapter 适配器
                StoreManagerAdapter adapter = new StoreManagerAdapter(StoreManagerActivity.this, list);
                //设置点击事件
                adapter.setCallback(new StoreManagerAdapter.OperationCallback() {
                    @Override
                    public void delete(TakeStore takeStore) {
                        showNormalDialog(takeStore.getId());
                    }
                });
                storemanagerList.setAdapter(adapter);
            }
        });
    }

    /**
     * 显示弹窗
     * 防止误触
     * @param id
     */
    private void showNormalDialog(int id){
        AlertDialog.Builder normalDialog = new AlertDialog.Builder(StoreManagerActivity.this);
        normalDialog.setTitle("系统提示");
        normalDialog.setMessage("你确定删除此店铺嘛?");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete(id);
                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        normalDialog.show();
    }

    /**
     * 删除
     */
    private void delete(int id) {
        //show显示等待dialog
        show();
        MyHttpUtil.getInstance().GET(Apis.removeStore + "?id=" + id, new HttpCallBack() {
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
                    getAllStore();
                }
            }
        });
    }
}