package com.demo.takeoutapp.ui.store;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.demo.takeoutapp.R;
import com.demo.takeoutapp.data.TakeUserinfo;
import com.demo.takeoutapp.http.Apis;
import com.demo.takeoutapp.http.HttpCallBack;
import com.demo.takeoutapp.http.MyHttpUtil;
import com.demo.takeoutapp.ui.BaseActivity;
import com.demo.takeoutapp.ui.person.LoginActivity;
import com.demo.takeoutapp.util.Constant;
import com.kproduce.roundcorners.RoundButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;

import static com.demo.takeoutapp.util.ToastUtil.toast;

public class AddTypeActivity extends BaseActivity {

    private LinearLayout headGroup;
    private ImageView headLefticon;
    private TextView headText;
    private ImageView headRighticon;
    private EditText typename;
    private RoundButton next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_type);
        initView();
    }

    private void initView() {
        headGroup = (LinearLayout) findViewById(R.id.head_group);
        headLefticon = (ImageView) findViewById(R.id.head_lefticon);
        headText = (TextView) findViewById(R.id.head_text);
        headRighticon = (ImageView) findViewById(R.id.head_righticon);
        typename = (EditText) findViewById(R.id.typename);
        next = (RoundButton) findViewById(R.id.next);

        headLefticon.setImageResource(R.mipmap.arrowleft_back);
        headLefticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        headText.setText("添加分类");

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(typename.getText())) {
                    toast("信息不能为空");
                    return;
                }
                add();
            }
        });
    }

    /**
     * 添加分类
     */
    private void add() {
        show();
        JSONObject req = new JSONObject();
        try {
            req.put("stroeid", MyStoreActivity.storeid);
            req.put("typename", typename.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MyHttpUtil.getInstance().POST(Apis.addtype, req.toString(), new HttpCallBack() {
            @Override
            public void Error(Call call, IOException e) {
                dismiss();
            }

            @Override
            public void Success(Call call, String res) throws Exception {
                dismiss();
                JSONObject rep = new JSONObject(res);
                if (rep.getInt("code") == 200) {
                    finish();
                }
                toast(rep.getString("message"), AddTypeActivity.this);
            }
        });
    }
}