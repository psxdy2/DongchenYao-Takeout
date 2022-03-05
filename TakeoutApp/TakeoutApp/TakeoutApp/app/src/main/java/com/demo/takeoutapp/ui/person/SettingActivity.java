package com.demo.takeoutapp.ui.person;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.demo.takeoutapp.R;
import com.demo.takeoutapp.app.MyApplication;
import com.demo.takeoutapp.ui.BaseActivity;
import com.demo.takeoutapp.ui.admin.AdminActivity;
import com.demo.takeoutapp.ui.admin.UserManagerActivity;
import com.demo.takeoutapp.util.Constant;
import com.kproduce.roundcorners.RoundButton;
import com.kproduce.roundcorners.RoundLinearLayout;

import static com.demo.takeoutapp.util.ToastUtil.toast;

public class SettingActivity extends BaseActivity {

    private LinearLayout headGroup;
    private ImageView headLefticon;
    private TextView headText;
    private ImageView headRighticon;
    private RoundButton next;
    private RoundLinearLayout goAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
    }

    private void initView() {
        headGroup = (LinearLayout) findViewById(R.id.head_group);
        headLefticon = (ImageView) findViewById(R.id.head_lefticon);
        headText = (TextView) findViewById(R.id.head_text);
        headRighticon = (ImageView) findViewById(R.id.head_righticon);
        goAdmin = (RoundLinearLayout) findViewById(R.id.go_admin);
        next = (RoundButton) findViewById(R.id.next);

        headLefticon.setImageResource(R.mipmap.arrowleft_back);
        headLefticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        headText.setText("系统设置");

        goAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //退出登陆
                Constant.userinfo = null;
                MyApplication.getMyApplication().getDaoSession().getTakeUserinfoDao().deleteAll();
                startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    private void showInputDialog() {
        final EditText editText = new EditText(SettingActivity.this);
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(SettingActivity.this);
        inputDialog.setTitle("请输入admin管理员密码").setView(editText);
        inputDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (editText.getText().toString().equals("admin")){
                            startActivity(new Intent(SettingActivity.this, AdminActivity.class));
                        }else{
                            toast("密码错误",SettingActivity.this);
                        }
                    }
                }).show();
    }

}