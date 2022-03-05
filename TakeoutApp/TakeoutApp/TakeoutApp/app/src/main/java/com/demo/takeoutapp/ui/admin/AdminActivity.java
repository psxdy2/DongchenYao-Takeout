package com.demo.takeoutapp.ui.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.takeoutapp.R;
import com.demo.takeoutapp.ui.BaseActivity;
import com.kproduce.roundcorners.RoundLinearLayout;

/**
 * 管理员管理界面
 */
public class AdminActivity extends BaseActivity {

    /**
     * 组件
     * ui组件
     * button 按钮 imageview 图片控件
     * ps:html5 button input p 这些都可以理解成一个ui组件
     * <p>
     * java面向对象
     */
    private LinearLayout headGroup;
    private ImageView headLefticon;
    private TextView headText;
    private ImageView headRighticon;
    private TextView currentTime;
    private RoundLinearLayout goUsermanager;
    private RoundLinearLayout goStoremanager;
    private RoundLinearLayout goDeliverymanager;
    private RoundLinearLayout goDataglance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        initView();
    }

    private void initView() {
        headGroup = (LinearLayout) findViewById(R.id.head_group);
        headLefticon = (ImageView) findViewById(R.id.head_lefticon);
        headText = (TextView) findViewById(R.id.head_text);
        headRighticon = (ImageView) findViewById(R.id.head_righticon);
        currentTime = (TextView) findViewById(R.id.current_time);
        //寻找指定都id对象
        goUsermanager = (RoundLinearLayout) findViewById(R.id.go_usermanager);
        goStoremanager = (RoundLinearLayout) findViewById(R.id.go_storemanager);
        goDeliverymanager = (RoundLinearLayout) findViewById(R.id.go_deliverymanager);
        goDataglance = (RoundLinearLayout) findViewById(R.id.go_dataglance);

        headLefticon.setImageResource(R.mipmap.arrowleft_back);
        headLefticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        headText.setText("管理员");

        //设置了事件
        //点击事件
        //单击事件 长按事件 双击事件 等等
        goUsermanager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果我们点击了以后在这里是可以接收到我们的一个回调的
                //开始做我们单击事件的处理
                //Intent 这个对象表示意图
                //意图 ： 希望它去帮我们实现什么操作
                //比如打开相册
                //比如说打开文件夹
                //比如说跳转新的activity
                //startActivity 发送一个intent对象给系统 告诉它我们的意图然后系统去执行这个意图完毕
                //所以说之后你看到了这个intent对象基本的操作都是执行打开新活动
                startActivity(new Intent(AdminActivity.this, UserManagerActivity.class));
            }
        });
        goStoremanager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, StoreManagerActivity.class));
            }
        });
        goDeliverymanager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, DeliveryManagerActivity.class));
            }
        });
        goDataglance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, DataGlanceActivity.class));
            }
        });

    }

}