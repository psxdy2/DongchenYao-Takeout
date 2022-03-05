package com.demo.takeoutapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.takeoutapp.R;
import com.demo.takeoutapp.app.MyApplication;
import com.demo.takeoutapp.data.TakeUserinfo;
import com.demo.takeoutapp.ui.home.FragmentHome;
import com.demo.takeoutapp.ui.order.FragmentOrder;
import com.demo.takeoutapp.ui.person.FragmentPerson;
import com.demo.takeoutapp.ui.person.LoginActivity;
import com.demo.takeoutapp.ui.person.SettingActivity;
import com.demo.takeoutapp.util.Constant;
import com.demo.takeoutapp.util.NavigationManager;
import com.demo.takeoutapp.util.SystemTTS;
import com.dhh.websocket.RxWebSocket;
import com.dhh.websocket.WebSocketSubscriber;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.WebSocket;
import rx.schedulers.Schedulers;

import static com.demo.takeoutapp.util.ToastUtil.toast;

public class MainActivity extends AppCompatActivity {

    private FrameLayout container;
    private LinearLayout headGroup;
    private ImageView headLefticon;
    private TextView headText;
    private ImageView headRighticon;
    private BottomNavigationView bottomNavigationView;

    private SystemTTS systemTTS = SystemTTS.getInstance(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavigationManager.setStatusBarColor(this);
        NavigationManager.setBottomNavigationColor(this);
        setContentView(R.layout.activity_main);

        reqpermission();
    }

    private void initView() {
        container = (FrameLayout) findViewById(R.id.container);
        headGroup = (LinearLayout) findViewById(R.id.head_group);
        headLefticon = (ImageView) findViewById(R.id.head_lefticon);
        headRighticon = (ImageView) findViewById(R.id.head_righticon);
        headText = (TextView) findViewById(R.id.head_text);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);

        headText.setText("首页");
        headRighticon.setImageResource(R.mipmap.setting);
        headRighticon.setVisibility(View.INVISIBLE);
        headRighticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.container, new FragmentHome()).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.im:
                        if (Constant.userinfo == null) {
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            break;
                        }
                        headText.setText("首页");
                        headRighticon.setVisibility(View.INVISIBLE);
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new FragmentHome()).commit();
                        break;
                    case R.id.list:
                        if (Constant.userinfo == null) {
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            break;
                        }
                        headText.setText("订单");
                        headRighticon.setVisibility(View.INVISIBLE);
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new FragmentOrder()).commit();
                        break;
                    case R.id.person:
                        if (Constant.userinfo == null) {
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            break;
                        }
                        headText.setText("个人");
                        headRighticon.setVisibility(View.VISIBLE);
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new FragmentPerson()).commit();
                        break;
                }
                item.setChecked(false);
                return false;
            }
        });
    }

    /**
     * 准备用户信息
     */
    private void setUserInfo() {
//        TakeUserinfo userinfo = new TakeUserinfo();
//        userinfo.setId(5);
//        userinfo.setAccount("12312341234");
//        userinfo.setUsername("123");
//        userinfo.setPassword("123");
//        userinfo.setSex("外星人");
//        userinfo.setBirthday("天地同寿");
//        userinfo.setPortrait("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2915828601,496412029&fm=26&gp=0.jpg");
//        Constant.userinfo = userinfo;
        List<TakeUserinfo> userinfos = MyApplication.getMyApplication().getDaoSession().getTakeUserinfoDao().loadAll();
        if (userinfos.size() == 1) {
            Constant.userinfo = userinfos.get(0);
        } else {
            MyApplication.getMyApplication().getDaoSession().getTakeUserinfoDao().deleteAll();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Constant.userinfo != null){
            req();
        }
    }

    private void req() {
        RxWebSocket.get(Constant.socketurl+Constant.userinfo.getId())
                .subscribeOn(Schedulers.io())
                .subscribe(new WebSocketSubscriber() {

                    @Override
                    protected void onClose() {
                        super.onClose();
                        req();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        toast("链接云错误");
                    }

                    @Override
                    protected void onOpen(@NonNull WebSocket webSocket) {
                        super.onOpen(webSocket);
                        toast("链接云成功");
                    }

                    @Override
                    protected void onMessage(@NonNull String text) {
                        super.onMessage(text);
                        try {
                            JSONObject data = new JSONObject(text).getJSONObject("msg");
                            systemTTS.playText(data.getString("info"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    protected void onReconnect() {
                        super.onReconnect();
                        toast("重新链接");
                    }
                });
    }

    private void reqpermission() {
        XXPermissions.with(this)
                .permission(Permission.WRITE_EXTERNAL_STORAGE)
                .permission(Permission.READ_EXTERNAL_STORAGE)
                .request(new OnPermissionCallback() {
                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        if (all) {
                            setUserInfo();
                            initView();
                        } else {
                            toast("获取部分权限成功，但部分权限未正常授予");
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        if (never) {
                            toast("被永久拒绝授权，请手动授予权限");
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(MainActivity.this, permissions);
                        } else {
                            toast("获取权限失败");
                        }
                    }
                });
    }

}