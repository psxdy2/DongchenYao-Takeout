package com.demo.takeoutapp.ui.store;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.demo.takeoutapp.R;
import com.demo.takeoutapp.adapter.FoodAdapter;
import com.demo.takeoutapp.adapter.TypeAdapter;
import com.demo.takeoutapp.data.TakeFoods;
import com.demo.takeoutapp.data.TakeFoodtype;
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

public class FoodActivity extends BaseActivity {

    private LinearLayout headGroup;
    private ImageView headLefticon;
    private TextView headText;
    private ImageView headRighticon;
    private ListView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getFoods();
    }

    private void initView() {
        headGroup = (LinearLayout) findViewById(R.id.head_group);
        headLefticon = (ImageView) findViewById(R.id.head_lefticon);
        headText = (TextView) findViewById(R.id.head_text);
        headRighticon = (ImageView) findViewById(R.id.head_righticon);
        content = (ListView) findViewById(R.id.list);

        headLefticon.setImageResource(R.mipmap.arrowleft_back);
        headLefticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        headText.setText("菜品管理");
        headRighticon.setImageResource(R.mipmap.add);
        headRighticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodActivity.this, AddFoodActivity.class);
                intent.putExtra("id",getIntent().getStringExtra("id"));
                startActivity(intent);
            }
        });
    }

    /**
     * 查找菜品信息
     */
    private void getFoods() {
        show();
        MyHttpUtil.getInstance().GET(Apis.SearchFoods + "?id=" + getIntent().getStringExtra("id"), new HttpCallBack() {
            @Override
            public void Error(Call call, IOException e) {
                dismiss();
            }

            @Override
            public void Success(Call call, String res) throws Exception {
                dismiss();
                JSONObject jsonObject = new JSONObject(res);
                if (jsonObject.getInt("code") == 200) {
                    List<TakeFoods> list = new ArrayList<>();
                    JSONArray array = jsonObject.getJSONArray("data");
                    for (int i = 0; i < array.length(); i++) {
                        TakeFoods takeFoodtype = new TakeFoods();
                        takeFoodtype.setId(array.getJSONObject(i).getInt("id"));
                        takeFoodtype.setFoodname(array.getJSONObject(i).getString("foodname"));
                        takeFoodtype.setFoodinfo(array.getJSONObject(i).getString("foodinfo"));
                        takeFoodtype.setFoodprice(array.getJSONObject(i).getString("foodprice"));
                        list.add(takeFoodtype);
                    }
                    initAadapter(list);
                }
            }
        });
    }

    private void initAadapter(List<TakeFoods> list) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                FoodAdapter adapter = new FoodAdapter(FoodActivity.this, list);
                adapter.setCallback(new FoodAdapter.OperationCallback() {
                    @Override
                    public void call(TakeFoods takeFoods) {
                        delete(takeFoods.getId());
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
        MyHttpUtil.getInstance().GET(Apis.deleteFoods + "?id=" + id, new HttpCallBack() {
            @Override
            public void Error(Call call, IOException e) {
                dismiss();
            }

            @Override
            public void Success(Call call, String res) throws Exception {
                dismiss();
                JSONObject jsonObject = new JSONObject(res);
                if (jsonObject.getInt("code") == 200) {
                    getFoods();
                }
            }
        });
    }
}