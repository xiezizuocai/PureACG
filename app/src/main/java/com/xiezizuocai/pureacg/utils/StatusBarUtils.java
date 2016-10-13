package com.xiezizuocai.pureacg.utils;

import android.app.Activity;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
// 状态栏工具类
public class StatusBarUtils {
    // 设置状态栏颜色
    public static void setStatusBarColor(Activity activity, int statusBarColor) {

        /**
         * 5.0以下机型无法直接设置状态栏颜色，可以模拟一个纯色的View放到状态栏下面间接达到着色效果。
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            /* （一个状态栏大小的）LinearLayout布局 */
            View rectView = new View(activity);
            LinearLayout.LayoutParams params                    // 宽                             高
                    = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
            rectView.setLayoutParams(params);

            // 设置背景颜色为状态栏的颜色
            rectView.setBackgroundColor(statusBarColor);

            // 获取activity的整个Window界面的最顶层View
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            // 添加rectView到decorView
            decorView.addView(rectView);

            // 获取到布局的根节点
            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);

            /* 将rootView的paddingTop从0设置为状态栏的高度 */
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);
        }
    }

    /**
     * 首页的抽屉透明兼容到Android4.4方案
     *
     * @param activity
     * @param topLayout
     * @param statusBarColor
     */
    public static void setTopLayoutColor(Activity activity, FrameLayout topLayout, int statusBarColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ViewGroup.LayoutParams params = topLayout.getLayoutParams();
            params.height = getStatusBarHeight(activity);
            topLayout.setBackgroundColor(statusBarColor);
        }
    }

    // 获取状态栏高度
    public static int getStatusBarHeight(Activity activity) {
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return activity.getResources().getDimensionPixelSize(resourceId);
    }

}
