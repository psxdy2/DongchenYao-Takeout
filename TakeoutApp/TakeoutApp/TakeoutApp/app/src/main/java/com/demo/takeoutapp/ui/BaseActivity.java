package com.demo.takeoutapp.ui;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.demo.takeoutapp.R;
import com.demo.takeoutapp.ui.store.MyStoreActivity;
import com.demo.takeoutapp.util.AppManager;
import com.demo.takeoutapp.util.NavigationManager;

//AppCompatActivity系统提供
public class BaseActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;

    public void show() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setTitle("提示");
            mProgressDialog.setMessage("加载中...");
            mProgressDialog.show();
        } else {
            mProgressDialog.show();
        }
    }

    public void dismiss(){
        if (mProgressDialog != null){
            mProgressDialog.dismiss();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        NavigationManager.setStatusBarColor(this);
        NavigationManager.setBottomNavigationColor(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }
}
