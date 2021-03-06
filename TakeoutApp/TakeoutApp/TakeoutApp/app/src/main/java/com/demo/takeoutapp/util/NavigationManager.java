package com.demo.takeoutapp.util;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

/**
 * 导航工具类
 */
public class NavigationManager {
    private static int bottomColor = Color.parseColor("#f5f6f7");
    private static int colorId = Color.parseColor("#f5f6f7");

    /**
     * 设置底部导航栏颜色
     * @param activity
     */
    public static void setBottomNavigationColor(Activity activity){
        setStatusBarColor(activity,bottomColor);
    }

    public static void setBottomNavigationColor(Activity activity,int color){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setNavigationBarColor(color);
        }
    }


    /**
     * 修改状态栏颜色，支持4.4以上版本
     * @param activity
     */
    public static void setStatusBarColor(Activity activity) {
        setStatusBarColor(activity,colorId);
    }

    public static void setStatusBarColor(Activity activity,int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
    }

}
