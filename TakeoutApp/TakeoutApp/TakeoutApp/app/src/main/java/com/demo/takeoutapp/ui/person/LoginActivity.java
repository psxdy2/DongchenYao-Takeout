package com.demo.takeoutapp.ui.person;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.demo.takeoutapp.R;
import com.demo.takeoutapp.app.MyApplication;
import com.demo.takeoutapp.data.TakeUserinfo;
import com.demo.takeoutapp.http.Apis;
import com.demo.takeoutapp.http.HttpCallBack;
import com.demo.takeoutapp.http.MyHttpUtil;
import com.demo.takeoutapp.ui.BaseActivity;
import com.demo.takeoutapp.util.Constant;
import com.dhh.websocket.RxWebSocket;
import com.kproduce.roundcorners.RoundButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.WebSocket;

import static com.demo.takeoutapp.util.ToastUtil.toast;

public class LoginActivity extends BaseActivity {

    private LinearLayout headGroup;
    private ImageView headLefticon;
    private TextView headText;
    private ImageView headRighticon;
    private EditText storename;
    private EditText password;
    private RoundButton next;
    private TextView goRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        headGroup = (LinearLayout) findViewById(R.id.head_group);
        headLefticon = (ImageView) findViewById(R.id.head_lefticon);
        headText = (TextView) findViewById(R.id.head_text);
        headRighticon = (ImageView) findViewById(R.id.head_righticon);
        storename = (EditText) findViewById(R.id.storename);
        password = (EditText) findViewById(R.id.password);
        next = (RoundButton) findViewById(R.id.next);
        goRegister = (TextView) findViewById(R.id.go_register);

        headLefticon.setImageResource(R.mipmap.arrowleft_back);
        headLefticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        headText.setText("登陆");

        goRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(storename.getText())) {
                    toast("账号不能为空");
                    return;
                }
                if (TextUtils.isEmpty(password.getText())) {
                    toast("密码不能为空");
                    return;
                }
                login();
            }
        });
    }

    private void login() {
        show();
        JSONObject req = new JSONObject();
        try {
            req.put("account", storename.getText().toString());
            req.put("password", password.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MyHttpUtil.getInstance().POST(Apis.login, req.toString(), new HttpCallBack() {
            @Override
            public void Error(Call call, IOException e) {
                dismiss();
            }

            @Override
            public void Success(Call call, String res) throws Exception {
                dismiss();
                JSONObject rep = new JSONObject(res);
                if (rep.getInt("code") == 200) {
                    JSONObject data = rep.getJSONObject("data");
                    TakeUserinfo userinfo = new TakeUserinfo();
                    userinfo.setId(data.getInt("id"));
                    userinfo.setAccount(data.getString("account"));
                    userinfo.setUsername(data.getString("username"));
                    userinfo.setPassword(data.getString("password"));
                    userinfo.setPortrait(data.getString("portrait"));
                    userinfo.setSex(data.getString("sex"));
                    userinfo.setBirthday(data.getString("birthday"));
                    Constant.userinfo = userinfo;
                    //登陆成功先清除临时保存信息
                    List<TakeUserinfo> userinfos = MyApplication.getMyApplication().getDaoSession().getTakeUserinfoDao().loadAll();
                    if (userinfos.size() != 0) {
                        MyApplication.getMyApplication().getDaoSession().getTakeUserinfoDao().deleteAll();
                    }
                    MyApplication.getMyApplication().getDaoSession().getTakeUserinfoDao().save(userinfo);
                    finish();
                }
                toast(rep.getString("message"), LoginActivity.this);
            }
        });
    }

}