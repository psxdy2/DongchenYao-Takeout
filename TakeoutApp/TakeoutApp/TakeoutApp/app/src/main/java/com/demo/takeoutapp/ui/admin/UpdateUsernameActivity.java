package com.demo.takeoutapp.ui.admin;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.takeoutapp.R;
import com.demo.takeoutapp.app.MyApplication;
import com.demo.takeoutapp.data.TakeUserinfo;
import com.demo.takeoutapp.http.Apis;
import com.demo.takeoutapp.http.HttpCallBack;
import com.demo.takeoutapp.http.MyHttpUtil;
import com.demo.takeoutapp.ui.BaseActivity;
import com.demo.takeoutapp.util.Constant;
import com.kproduce.roundcorners.RoundButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

import static com.demo.takeoutapp.util.ToastUtil.toast;

public class UpdateUsernameActivity extends BaseActivity {

    private LinearLayout headGroup;
    private ImageView headLefticon;
    private TextView headText;
    private ImageView headRighticon;
    private EditText username;
    private RoundButton next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_username);
        initView();
    }

    private void initView() {
        headGroup = (LinearLayout) findViewById(R.id.head_group);
        headLefticon = (ImageView) findViewById(R.id.head_lefticon);
        headText = (TextView) findViewById(R.id.head_text);
        headRighticon = (ImageView) findViewById(R.id.head_righticon);
        username = (EditText) findViewById(R.id.username);
        next = (RoundButton) findViewById(R.id.next);

        headLefticon.setImageResource(R.mipmap.arrowleft_back);
        headLefticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        headText.setText("修改昵称");

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(username.getText())) {
                    toast("新昵称不能为空");
                    return;
                }
                updateInfo();
            }
        });
    }


    /**
     * 修改个人信息
     */
    private void updateInfo() {
        show();
        JSONObject req = new JSONObject();
        try {
            req.put("id", getIntent().getStringExtra("id"));
            req.put("username", username.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MyHttpUtil.getInstance().POST(Apis.UpdateUserInfo, req.toString(), new HttpCallBack() {
            @Override
            public void Error(Call call, IOException e) {
                dismiss();
            }

            @Override
            public void Success(Call call, String res) throws Exception {
                dismiss();
                JSONObject rep = new JSONObject(res);
                if (rep.getInt("code") == 200) {
                    Constant.userinfo.setUsername(username.getText().toString());
                    List<TakeUserinfo> userinfos = MyApplication.getMyApplication().getDaoSession().getTakeUserinfoDao().loadAll();
                    if (userinfos.size() != 0) {
                        MyApplication.getMyApplication().getDaoSession().getTakeUserinfoDao().deleteAll();
                    }
                    MyApplication.getMyApplication().getDaoSession().getTakeUserinfoDao().save(Constant.userinfo);
                    finish();
                }
                toast(rep.getString("message"), UpdateUsernameActivity.this);
            }
        });
    }
}