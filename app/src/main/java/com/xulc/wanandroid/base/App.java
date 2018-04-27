package com.xulc.wanandroid.base;

import android.app.Application;
import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.tencent.bugly.crashreport.CrashReport;
import com.xulc.wanandroid.R;

/**
 * Date：2018/3/8
 * Desc：
 * Created by xuliangchun.
 */

public class App extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        CrashReport.initCrashReport(getApplicationContext(), "7c22ff2be7", false);

        Utils.init(this);
        ToastUtils.setMsgColor(ContextCompat.getColor(this,R.color.bottom_text_focus));

    }

    public static Context getAppContext() {
        return context;
    }
}
