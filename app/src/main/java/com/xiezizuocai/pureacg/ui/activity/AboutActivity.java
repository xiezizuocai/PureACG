package com.xiezizuocai.pureacg.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;;


import com.xiezizuocai.pureacg.R;
import com.xiezizuocai.pureacg.utils.CommonUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;


@ContentView(R.layout.activity_about)
public class AboutActivity extends BaseActivity {

    @ViewInject(R.id.collapsing_toolbar)
    private CollapsingToolbarLayout mCollapsingToolbarLayout;  // （图片）折叠工具栏布局引用

    @ViewInject(R.id.tv_version_info)
    private TextView mVersionInfo;

    @ViewInject(R.id.iv_header_img)
    private ImageView mHeaderImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        mCollapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        mVersionInfo.setText(R.string.activity_about_version_info);
        mHeaderImg.setImageResource(R.drawable.image_version);
    }

    @Event(value = R.id.rl_weibo, type = View.OnClickListener.class)
    private void jumpToWeibo(View view) {
        CommonUtils.jumpTo(this, "http://weibo.com/u/2331619287");
    }

    public static void startAboutActivity(Activity activity) {
        activity.startActivity(new Intent(activity, AboutActivity.class));
    }

}
