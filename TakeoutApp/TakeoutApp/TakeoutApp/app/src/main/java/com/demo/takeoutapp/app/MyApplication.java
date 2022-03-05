package com.demo.takeoutapp.app;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.demo.takeoutapp.gen.DaoMaster;
import com.demo.takeoutapp.gen.DaoSession;
import com.demo.takeoutapp.util.SystemTTS;
import com.hjq.permissions.XXPermissions;

import cn.bmob.v3.Bmob;

public class MyApplication extends Application {
    private static MyApplication myApplication;

    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        XXPermissions.setScopedStorage(true);
        myApplication = this;
        // 初始化. 建议在 Application 中进行初始化.
        //链接bmob数据库
        Bmob.resetDomain("https://open3.bmob.cn/8/");
        Bmob.initialize(this, "f94517abd8328addae4815a075a736b8");
        setDatabase();
        SystemTTS.getInstance(this);
    }

    public static MyApplication getMyApplication() {
        return myApplication;
    }

    private void setDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new DaoMaster.DevOpenHelper(this, "notes-db", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }


}
