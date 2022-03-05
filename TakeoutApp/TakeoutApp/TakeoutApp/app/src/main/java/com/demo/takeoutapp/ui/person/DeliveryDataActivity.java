package com.demo.takeoutapp.ui.person;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.demo.takeoutapp.R;
import com.demo.takeoutapp.adapter.SalesOrderAdapter;
import com.demo.takeoutapp.data.TakeOrder;
import com.demo.takeoutapp.http.Apis;
import com.demo.takeoutapp.http.HttpCallBack;
import com.demo.takeoutapp.http.MyHttpUtil;
import com.demo.takeoutapp.ui.BaseActivity;
import com.demo.takeoutapp.util.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;

import static com.demo.takeoutapp.util.ToastUtil.toast;

public class DeliveryDataActivity extends BaseActivity {

    private LinearLayout headGroup;
    private ImageView headLefticon;
    private TextView headText;
    private ImageView headRighticon;
    private ListView deliveryDataList;
    private TextView num;
    private TextView salary;

    private double sum = 0.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_data);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getOrder();
    }

    private void initView() {
        headGroup = (LinearLayout) findViewById(R.id.head_group);
        headLefticon = (ImageView) findViewById(R.id.head_lefticon);
        headText = (TextView) findViewById(R.id.head_text);
        headRighticon = (ImageView) findViewById(R.id.head_righticon);
        deliveryDataList = (ListView) findViewById(R.id.delivery_data_list);
        num = (TextView) findViewById(R.id.num);
        salary = (TextView) findViewById(R.id.salary);

        headLefticon.setImageResource(R.mipmap.arrowleft_back);
        headLefticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        headText.setText("配送员订单");
    }

    /**
     * 查询所有订单数据
     */
    private void getOrder() {
        show();
        MyHttpUtil.getInstance().GET(Apis.getOrder, new HttpCallBack() {
            @Override
            public void Error(Call call, IOException e) {
                dismiss();
            }

            @Override
            public void Success(Call call, String res) throws Exception {
                dismiss();
                JSONObject jsonObject = new JSONObject(res);
                if (jsonObject.getInt("code") == 200) {
                    List<TakeOrder> list = new ArrayList<>();
                    JSONArray array = jsonObject.getJSONArray("data");
                    for (int i = 0; i < array.length(); i++) {
                        TakeOrder takeOrder = new TakeOrder();
                        takeOrder.setId(array.getJSONObject(i).getInt("id"));
                        takeOrder.setStoreid(array.getJSONObject(i).getInt("storeid"));
                        takeOrder.setAddress(array.getJSONObject(i).getString("address"));
                        takeOrder.setPrice(array.getJSONObject(i).getString("price"));
                        takeOrder.setStatus(array.getJSONObject(i).getString("status"));
                        takeOrder.setFoods(array.getJSONObject(i).getString("foods"));
                        takeOrder.setCreatedtime(array.getJSONObject(i).getString("createdtime"));
                        takeOrder.setSendtime(array.getJSONObject(i).getString("sendtime"));
                        String temp = array.getJSONObject(i).getString("deliveryinfo");
                        if (!(temp.equals("null"))) {
                            takeOrder.setDeliveryinfo(temp);
                            JSONObject info = new JSONObject(temp);
                            if (info.getString("account").equals(Constant.userinfo.getAccount())) {
                                sum = Double.parseDouble(takeOrder.getPrice()) + sum;
                                list.add(takeOrder);
                            }
                        }
                    }
                    initAadapter(list);
                }
            }
        });
    }

    private void initAadapter(List<TakeOrder> list) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                num.setText("单量:" + list.size());
                BigDecimal b = new BigDecimal((sum * 0.10));
                salary.setText("收益:" + b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

                SalesOrderAdapter adapter = new SalesOrderAdapter(DeliveryDataActivity.this, list);
                adapter.setCallback(new SalesOrderAdapter.OperationCallback() {
                    @Override
                    public void confirm(TakeOrder takeOrder) {
                        //确认送达
                        updateOrder(takeOrder.getId().toString(), Constant.WAITCOMMENT);
                    }

                    @Override
                    public void comment(TakeOrder takeOrder) {

                    }

                    @Override
                    public void cancel(TakeOrder takeOrder) {

                    }
                });
                deliveryDataList.setAdapter(adapter);
            }
        });
    }

    /**
     * 获取当前时间
     */
    private String getSendTime() {
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");// a为am/pm的标记
        java.util.Calendar Cal = java.util.Calendar.getInstance();
        Cal.setTime(new Date());
        Cal.add(java.util.Calendar.HOUR_OF_DAY, 0);
        return sdf.format(Cal.getTime());
    }

    /**
     * 修改订单状态
     */
    private void updateOrder(String id, String status) {
        JSONObject req = new JSONObject();
        try {
            req.put("id", id);
            req.put("status", status);
            req.put("sendtime", getSendTime());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MyHttpUtil.getInstance().POST(Apis.updateOrder, req.toString(), new HttpCallBack() {
            @Override
            public void Error(Call call, IOException e) {

            }

            @Override
            public void Success(Call call, String res) throws Exception {
                JSONObject rep = new JSONObject(res);
                if (rep.getInt("code") == 200) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getOrder();
                        }
                    });
                }
                toast(rep.getString("message"), DeliveryDataActivity.this);
            }
        });
    }
}