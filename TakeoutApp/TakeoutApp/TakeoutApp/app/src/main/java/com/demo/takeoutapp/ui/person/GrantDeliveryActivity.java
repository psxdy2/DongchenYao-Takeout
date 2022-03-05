package com.demo.takeoutapp.ui.person;

import android.os.Bundle;
import android.view.View;

import com.demo.takeoutapp.R;
import com.demo.takeoutapp.http.Apis;
import com.demo.takeoutapp.http.HttpCallBack;
import com.demo.takeoutapp.http.MyHttpUtil;
import com.demo.takeoutapp.ui.BaseActivity;
import com.demo.takeoutapp.ui.home.FoodDefailsActivity;
import com.demo.takeoutapp.ui.home.SettlementActivity;
import com.demo.takeoutapp.util.AppManager;
import com.demo.takeoutapp.util.Constant;
import com.demo.takeoutapp.util.GsonUtils;
import com.dhh.websocket.RxWebSocket;
import com.kproduce.roundcorners.RoundButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;

import static com.demo.takeoutapp.util.ToastUtil.toast;

public class GrantDeliveryActivity extends BaseActivity {

    private RoundButton apply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grant_delivery);
        initView();
    }

    private void initView() {
        apply = (RoundButton) findViewById(R.id.apply);

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDelivery();
            }
        });
    }

    /**
     * 申请成为骑手
     */
    private void addDelivery() {
        show();
        JSONObject req = new JSONObject();
        try {
            req.put("id", Constant.userinfo.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MyHttpUtil.getInstance().POST(Apis.addDelivery, req.toString(), new HttpCallBack() {
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
                toast(rep.getString("message"), GrantDeliveryActivity.this);
            }
        });
    }
}