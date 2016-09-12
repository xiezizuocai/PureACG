package com.xiezizuocai.puredaily.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


import com.xiezizuocai.puredaily.R;
import com.xiezizuocai.puredaily.constant.PrefConstants;
import com.xiezizuocai.puredaily.utils.ActivityCollector;
import com.xiezizuocai.puredaily.utils.CommonUtils;
import com.xiezizuocai.puredaily.utils.PrefUtils;

import org.xutils.x;

/********************************* BaseActivity，供其他Activity继承 *******************************/

public abstract class BaseActivity extends AppCompatActivity {

    protected Toolbar mToolbar;  // 工具栏引用

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置主题
        setTheme(CommonUtils.getSkinStyle(PrefUtils.getInt(this, PrefConstants.CURRENT_SKIN, PrefConstants.DEFAULT_SKIN)));
        // 注入当前activity
        x.view().inject(this);
        // 初始化工具栏
        initToolBar();
        // NavigationBar跟随主题色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(CommonUtils.getThemePrimaryColor(this));
        }
        // 添加当前activity到ActivityCollector的ArrayList集合中
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 将当前activity从ActivityCollector的ArrayList集合中移出
        ActivityCollector.removeActivity(this);
    }

    // 初始化工具栏
    private void initToolBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setHomeButtonEnabled(true);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void setToolbarTitle(String toolbarTitle) {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(toolbarTitle);
        }
    }

    public void setToolbarTitle(int resId) {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(getString(resId));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                onBackPressed();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
