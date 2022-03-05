package com.demo.takeoutapp.ui.store;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.demo.takeoutapp.R;
import com.demo.takeoutapp.adapter.TypeAdapter;
import com.demo.takeoutapp.data.TakeFoodtype;
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

public class TypeActivity extends BaseActivity {

    private LinearLayout headGroup;
    private ImageView headLefticon;
    private TextView headText;
    private ImageView headRighticon;
    private ListView addtypeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getTypes();
    }

    private void initView() {
        headGroup = (LinearLayout) findViewById(R.id.head_group);
        headLefticon = (ImageView) findViewById(R.id.head_lefticon);
        headText = (TextView) findViewById(R.id.head_text);
        headRighticon = (ImageView) findViewById(R.id.head_righticon);
        addtypeList = (ListView) findViewById(R.id.addtype_list);

        headLefticon.setImageResource(R.mipmap.arrowleft_back);
        headLefticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        headText.setText("分类管理");
        headRighticon.setImageResource(R.mipmap.add);
        headRighticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TypeActivity.this, AddTypeActivity.class));
            }
        });
    }

    /**
     * 查找类别信息
     */
    private void getTypes() {
        show();
        MyHttpUtil.getInstance().GET(Apis.SearchFoodTypes + "?id=" + MyStoreActivity.storeid, new HttpCallBack() {
            @Override
            public void Error(Call call, IOException e) {
                dismiss();
            }

            @Override
            public void Success(Call call, String res) throws Exception {
                dismiss();
                JSONObject jsonObject = new JSONObject(res);
                if (jsonObject.getInt("code") == 200) {
                    List<TakeFoodtype> list = new ArrayList<>();
                    JSONArray array = jsonObject.getJSONArray("data");
                    for (int i = 0; i < array.length(); i++) {
                        TakeFoodtype takeFoodtype = new TakeFoodtype();
                        takeFoodtype.setId(array.getJSONObject(i).getInt("id"));
                        takeFoodtype.setStoreid(array.getJSONObject(i).getInt("storeid"));
                        takeFoodtype.setTypename(array.getJSONObject(i).getString("typename"));
                        list.add(takeFoodtype);
                    }
                    initAadapter(list);
                }
            }
        });
    }

    private void initAadapter(List<TakeFoodtype> list) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TypeAdapter adapter = new TypeAdapter(TypeActivity.this, list);
                adapter.setCallback(new TypeAdapter.OperationCallback() {
                    @Override
                    public void call(TakeFoodtype takeFoodtype) {
                        delete(takeFoodtype.getId());
                    }
                });
                addtypeList.setAdapter(adapter);
            }
        });
    }

    /**
     * 删除
     */
    private void delete(int id) {
        show();
        MyHttpUtil.getInstance().GET(Apis.deleteFoodTypes + "?id=" + id, new HttpCallBack() {
            @Override
            public void Error(Call call, IOException e) {
                dismiss();
            }

            @Override
            public void Success(Call call, String res) throws Exception {
                dismiss();
                JSONObject jsonObject = new JSONObject(res);
                if (jsonObject.getInt("code") == 200) {
                    getTypes();
                }
            }
        });
    }
}