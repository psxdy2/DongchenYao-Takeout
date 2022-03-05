package com.demo.takeoutapp.ui.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.demo.takeoutapp.R;
import com.demo.takeoutapp.adapter.SettlementListAdapter;
import com.demo.takeoutapp.data.TakeDelivery;
import com.demo.takeoutapp.data.TakeUserinfo;
import com.demo.takeoutapp.http.Apis;
import com.demo.takeoutapp.http.HttpCallBack;
import com.demo.takeoutapp.http.MyHttpUtil;
import com.demo.takeoutapp.ui.BaseActivity;
import com.demo.takeoutapp.ui.person.SettingActivity;
import com.demo.takeoutapp.util.AppManager;
import com.demo.takeoutapp.util.Constant;
import com.demo.takeoutapp.util.GsonUtils;
import com.dhh.websocket.RxWebSocket;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import okhttp3.Call;

import static com.demo.takeoutapp.util.ToastUtil.toast;

/**
 * 结算
 */
public class SettlementActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout headGroup;
    private ImageView headLefticon;
    private TextView headText;
    private ImageView headRighticon;
    private TextView card1;
    private TextView card2;
    private TextView address;
    private LinearLayout time;
    private ListView foots;
    private TextView bzf;
    private TextView psf;
    private TextView hj;
    private TextView money;
    private TextView submit;
    private TextView sendtime;

    //选中的地址
    public static String addressInfo = "请选中地址";
    //配送员信息
    public static TakeUserinfo takeDelivery;

    @Override
    protected void onResume() {
        super.onResume();
        address.setText(addressInfo);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settlement);
        addressInfo = "请选中地址";
        takeDelivery = null;
        getOneDelivery();
        initView();
        initAdapter();
    }

    private void initView() {
        headGroup = (LinearLayout) findViewById(R.id.head_group);
        headLefticon = (ImageView) findViewById(R.id.head_lefticon);
        headText = (TextView) findViewById(R.id.head_text);
        headRighticon = (ImageView) findViewById(R.id.head_righticon);
        card1 = (TextView) findViewById(R.id.card1);
        card2 = (TextView) findViewById(R.id.card2);
        address = (TextView) findViewById(R.id.address);
        time = (LinearLayout) findViewById(R.id.time);
        foots = (ListView) findViewById(R.id.foots);
        bzf = (TextView) findViewById(R.id.bzf);
        psf = (TextView) findViewById(R.id.psf);
        hj = (TextView) findViewById(R.id.hj);
        money = (TextView) findViewById(R.id.money);
        submit = (TextView) findViewById(R.id.submit);
        sendtime = (TextView) findViewById(R.id.sendtime);

        headLefticon.setImageResource(R.mipmap.arrowleft_back);
        headLefticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        headText.setText("提交订单");

        sendtime.setText(getSendTime());
        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        address.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    /**
     * 获取当前时间加一个小时
     */
    private String getSendTime() {
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");// a为am/pm的标记
        java.util.Calendar Cal = java.util.Calendar.getInstance();
        Cal.setTime(new Date());
        Cal.add(java.util.Calendar.HOUR_OF_DAY, 1);
        return sdf.format(Cal.getTime());
    }

    private void initAdapter() {
        //计算高度
        int h = (int) (FoodDefailsActivity.gwCbeans.size() * getResources().getDimension(R.dimen.dp_70));
        ViewGroup.LayoutParams params = foots.getLayoutParams();
        params.height = h;
        foots.setLayoutParams(params);
        //包装费 一个数量算一块包装费
//        int tempbzf = 0;
//        for (int i=0;i<FoodDefailsActivity.gwCbeans.size();i++){
//            tempbzf = FoodDefailsActivity.gwCbeans.get(i).getNum() + tempbzf;
//        }
//        bzf.setText("¥"+tempbzf);
        //总金额
        hj.setText(getIntent().getStringExtra("summoney"));
        money.setText("应付:" + getIntent().getStringExtra("summoney"));
        SettlementListAdapter adapter = new SettlementListAdapter(this, FoodDefailsActivity.gwCbeans);
        foots.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.card1:
                time.setVisibility(View.VISIBLE);
                card1.setBackgroundColor(0xffffffff);
                card2.setBackgroundColor(0xffece8c5);
                break;
            case R.id.card2:
                time.setVisibility(View.GONE);
                //0xffece8c5
                //0xff 000000
                //0xff ffffff
                card1.setBackgroundColor(0xffece8c5);
                card2.setBackgroundColor(0xffffffff);
                break;
            case R.id.address:
                Intent intent = new Intent(SettlementActivity.this, SelectAddressActivity.class);
                startActivity(intent);
                break;
            case R.id.submit:
                //提交订单
                if (TextUtils.isEmpty(addressInfo) || addressInfo.equals("请选中地址")) {
                    toast("请选择地址");
                    return;
                }
                submitOrder();
                break;
        }
    }

    /**
     * 随机获取到的配送员
     */
    private void getOneDelivery() {
        show();
        MyHttpUtil.getInstance().GET(Apis.getOneDelivery, new HttpCallBack() {
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
                    takeDelivery = new TakeUserinfo();
                    takeDelivery.setId(data.getInt("id"));
                    takeDelivery.setAccount(data.getString("account"));
                    takeDelivery.setUsername(data.getString("username"));
                    takeDelivery.setPassword(data.getString("password"));
                    takeDelivery.setPortrait(data.getString("portrait"));
                    takeDelivery.setSex(data.getString("sex"));
                    takeDelivery.setBirthday(data.getString("birthday"));
                } else {
                    toast(rep.getString("message"), SettlementActivity.this);
                    finish();
                }
            }
        });
    }


    /**
     * 提交订单
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void submitOrder() {
        if (takeDelivery == null){
            toast("配送员未就绪");
            return;
        }
        show();
        JSONObject req = new JSONObject();
        try {
            //GsonUtils.toJson把一个list转成一个json格式到string字符串
            String foods = GsonUtils.toJson(FoodDefailsActivity.gwCbeans);
            req.put("userid", Constant.userinfo.getId());
            req.put("storeid", getIntent().getStringExtra("id"));
            req.put("address", addressInfo);
            req.put("price", getIntent().getStringExtra("summoney").replace("¥", ""));
            req.put("foods", foods);
            req.put("sendtime", sendtime.getText());
            req.put("deliveryinfo", GsonUtils.toJson(takeDelivery));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MyHttpUtil.getInstance().POST(Apis.addOrder, req.toString(), new HttpCallBack() {
            @Override
            public void Error(Call call, IOException e) {
                dismiss();
            }

            @Override
            public void Success(Call call, String res) throws Exception {
                dismiss();
                JSONObject rep = new JSONObject(res);
                if (rep.getInt("code") == 200) {
                    JSONObject json = new JSONObject();
                    json.put("info", "你有新的订单");
                    String msg = "{\"type\":2,\"to\":\"" + getIntent().getStringExtra("userid") + "\",\"msg\":" + json + "}";
                    //RxWebSocket.send 一个即时通讯三方的
                    RxWebSocket.send(Constant.socketurl + Constant.userinfo.getId(), msg);
                    startActivity(new Intent(SettlementActivity.this,DeliveryActivity.class));
                }
                toast(rep.getString("message"), SettlementActivity.this);
            }
        });
    }
}