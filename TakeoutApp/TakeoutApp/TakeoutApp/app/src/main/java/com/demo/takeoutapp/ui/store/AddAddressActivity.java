package com.demo.takeoutapp.ui.store;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.demo.takeoutapp.R;
import com.demo.takeoutapp.http.Apis;
import com.demo.takeoutapp.http.HttpCallBack;
import com.demo.takeoutapp.http.MyHttpUtil;
import com.demo.takeoutapp.ui.BaseActivity;
import com.demo.takeoutapp.util.Constant;
import com.demo.takeoutapp.util.FileUtil;
import com.demo.takeoutapp.util.GlideEngine;
import com.kproduce.roundcorners.RoundButton;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadFileListener;
import okhttp3.Call;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static com.demo.takeoutapp.util.ToastUtil.toast;

public class AddAddressActivity extends BaseActivity {

    private LinearLayout headGroup;
    private ImageView headLefticon;
    private TextView headText;
    private ImageView headRighticon;
    private EditText name;
    private EditText phone;
    private EditText address;
    private RoundButton next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        initView();
    }

    private void initView() {
        headGroup = (LinearLayout) findViewById(R.id.head_group);
        headLefticon = (ImageView) findViewById(R.id.head_lefticon);
        headText = (TextView) findViewById(R.id.head_text);
        headRighticon = (ImageView) findViewById(R.id.head_righticon);
        name = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.phone);
        address = (EditText) findViewById(R.id.address);
        next = (RoundButton) findViewById(R.id.next);

        headLefticon.setImageResource(R.mipmap.arrowleft_back);
        headLefticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        headText.setText("添加地址");

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(name.getText())) {
                    toast("姓名不能为空");
                    return;
                }
                if (TextUtils.isEmpty(phone.getText())) {
                    toast("电话不能为空");
                    return;
                }
                if (TextUtils.isEmpty(address.getText())) {
                    toast("地址不能为空");
                    return;
                }
                add();
            }
        });
    }

    /**
     * 添加地址
     */
    private void add() {
        show();
        JSONObject req = new JSONObject();
        try {
            req.put("userid", Constant.userinfo.getId());
            req.put("name", name.getText().toString());
            req.put("phone", phone.getText().toString());
            req.put("address", address.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MyHttpUtil.getInstance().POST(Apis.addaddress, req.toString(), new HttpCallBack() {
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
                toast(rep.getString("message"), AddAddressActivity.this);
            }
        });
    }
}