package com.demo.takeoutapp.util;

import android.app.Activity;
import android.widget.Toast;

import com.demo.takeoutapp.app.MyApplication;

/**
 * 吐司工具类
 */
public class ToastUtil {

    public static void toast(String msg) {
        try {
            Toast.makeText(MyApplication.getMyApplication(), msg, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void toast(String msg, Activity activity) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Toast.makeText(MyApplication.getMyApplication(), msg, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
