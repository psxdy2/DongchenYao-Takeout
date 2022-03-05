package com.demo.takeoutapp.ui.home;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.demo.takeoutapp.R;
import com.demo.takeoutapp.ui.BaseActivity;
import com.demo.takeoutapp.util.AppManager;
import com.kproduce.roundcorners.RoundButton;
import com.kproduce.roundcorners.RoundImageView;

import cn.bmob.v3.util.V;

public class DeliveryActivity extends BaseActivity {

    private LinearLayout headGroup;
    private ImageView headLefticon;
    private TextView headText;
    private ImageView headRighticon;
    private RoundImageView tx;
    private TextView info;
    private RoundButton complete;
    private RoundImageView advertisement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        initView();
    }

    private void initView() {
        headGroup = (LinearLayout) findViewById(R.id.head_group);
        headLefticon = (ImageView) findViewById(R.id.head_lefticon);
        headText = (TextView) findViewById(R.id.head_text);
        headRighticon = (ImageView) findViewById(R.id.head_righticon);
        tx = (RoundImageView) findViewById(R.id.tx);
        info = (TextView) findViewById(R.id.info);
        complete = (RoundButton) findViewById(R.id.complete);
        advertisement = (RoundImageView) findViewById(R.id.advertisement);

        headText.setText("等待送达");
        Glide.with(DeliveryActivity.this)
                .load(SettlementActivity.takeDelivery.getPortrait())
                .into(tx);
        info.setText("配送员\n"+SettlementActivity.takeDelivery.getUsername());
        Glide.with(DeliveryActivity.this)
                .load("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1453131484,2894628883&fm=26&gp=0.jpg")
                .into(advertisement);
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.getAppManager().finishAllActivity();
            }
        });
    }
}