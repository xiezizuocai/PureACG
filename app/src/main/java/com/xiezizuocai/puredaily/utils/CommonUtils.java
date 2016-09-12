package com.xiezizuocai.puredaily.utils;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.util.TypedValue;


import com.xiezizuocai.puredaily.R;
import com.xiezizuocai.puredaily.ui.activity.HomeActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CommonUtils {

    // 重启APP
    public static void restartApp(Context context) {
        ActivityCollector.finishAll();  // 结束所有Activity
        context.startActivity(new Intent(context, HomeActivity.class));  // 开启HomeActivity
    }

    // 获取主题colorPrimary，一般是操作栏的颜色
    public static int getThemePrimaryColor(Context context) {
        final TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimary, value, true);
        return value.data;
    }

    public static int getSkinStyle(int pos) {
        int style = 0;
        switch (pos) {
            case 0:
                style = R.style.AppTheme_Blue;
                break;
            case 1:
                style = R.style.AppTheme_LightBlue;
                break;
            case 2:
                style = R.style.AppTheme_Pink;
                break;
            case 3:
                style = R.style.AppTheme_Red;
                break;
            case 4:
                style = R.style.AppTheme_Purple;
                break;
            case 5:
                style = R.style.AppTheme_DeepPurple;
                break;
            case 6:
                style = R.style.AppTheme_Teal;
                break;
            case 7:
                style = R.style.AppTheme_DeepOrange;
                break;
            case 8:
                style = R.style.AppTheme_Green;
                break;
            case 9:
                style = R.style.AppTheme_Cyan;
                break;
            case 10:
                style = R.style.AppTheme_Orange;
                break;
            case 11:
                style = R.style.AppTheme_Indigo;
                break;
            case 12:
                style = R.style.AppTheme_Brown;
                break;
            case 13:
                style = R.style.AppTheme_BlueGray;
                break;
            case 14:
                style = R.style.AppTheme_Amber;
                break;
        }
        return style;
    }

    // 判断当前时间是白天还是晚上
    public static boolean nowIsDay(Context context) {
        return isExistInterval(6, 0, 18, 0) ? true : false;
    }

    // 判断当前时间是否在指定的时间段内
    public static boolean isExistInterval(int startHour, int startMinuteOfHour, int endHour, int endMinuteOfHour) {
        boolean isExist = false;
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);// 获取小时,自动24小时制
        int minute = cal.get(Calendar.MINUTE);// 获取分钟
        int minuteOfDay = hour * 60 + minute;// 从0:00分开是到目前为止的分钟数
        int start = startHour * 60 + startMinuteOfHour;
        int end = endHour * 60 + endMinuteOfHour;
        isExist = minuteOfDay >= start && minuteOfDay < end;
        return isExist;
    }


    /**
     * 获取当前App版本号:versionName
     *
     * @return 返回当前App的版本名称
     */
    public static String getVersionName(Context context) {
        // 得到包管理器
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取当前App版本号:versionCode
     *
     * @return 返回当前App的版本号
     */
    public static int getVersionCode(Context context) {
        // 得到包管理器
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 跳转至某个URL页面
    public static void jumpTo(Activity activity, String url) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        activity.startActivity(intent);
    }

    // 复制到剪切板
    public static void copy2Clipboard(Activity activity, String content) {
        ClipboardManager clipboardManager = (ClipboardManager) activity.getSystemService(Activity.CLIPBOARD_SERVICE);
        clipboardManager.setText(content);
    }

}

