package com.xiezizuocai.puredaily.ui.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.xiezizuocai.puredaily.R;
import com.xiezizuocai.puredaily.ui.fragment.LatestInfoFragment;
import com.xiezizuocai.puredaily.ui.fragment.LatestPicFragment;
import com.xiezizuocai.puredaily.ui.fragment.MusicFragment;
import com.xiezizuocai.puredaily.utils.CommonUtils;
import com.xiezizuocai.puredaily.utils.StatusBarUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;


@ContentView(R.layout.activity_home)
public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @ViewInject(R.id.drawer_layout)
    private DrawerLayout mDrawerLayout;  // 抽屉布局引用

    @ViewInject(R.id.nav_view)
    private NavigationView mNavigationView;  // 导航视图引用

    private ImageView mNavHeaderImgView;  // 导航视图头部图片引用


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 首页的抽屉透明兼容到Android4.4方案
        StatusBarUtils.setTopLayoutColor(this, (FrameLayout) findViewById(R.id.top_layout), CommonUtils.getThemePrimaryColor(this));
        setToolbarTitle(null);  // 设置工具栏标题
        initDrawerLayout();  // 初始化抽屉布局
        initNavView();  // 初始化导航视图
        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            switchFragment(new LatestInfoFragment());
        }

    }


    // 初始化导航视图
    private void initNavView() {
        // 初始化抽屉Header ImageView
        mNavHeaderImgView = (ImageView) mNavigationView.getHeaderView(0).findViewById(R.id.iv_header);
        mNavHeaderImgView.setImageResource(R.drawable.img_header);
    }



    // 初始化抽屉布局
    private void initDrawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    // 切换Fragment
    public void switchFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        // 抽屉视图是开启的
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);  // 关闭抽屉
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    // 导航栏选择
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // 最新资讯
            case R.id.nav_latest_daily: {
                LatestInfoFragment latestInfoFragment = new LatestInfoFragment();
                switchFragment(latestInfoFragment);
                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;
            }

            // 新番连载
            case R.id.nav_latest_anime_comic: {
                AnimeComicActivity.startAnimeComicActivity(this);
                break;
            }

            // 高清壁纸
            case R.id.nav_latest_pic:
                LatestPicFragment latestFragment = new LatestPicFragment();
                switchFragment(latestFragment);
                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;

            // ACG音乐榜
            case R.id.nav_music: {
                MusicFragment musicFragment = new MusicFragment();
                switchFragment(musicFragment);
                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;
            }

            // 设置
            case R.id.nav_settings: {
                mDrawerLayout.closeDrawer(GravityCompat.START);
                Snackbar.make(mDrawerLayout,"暂无 . . .",Snackbar.LENGTH_SHORT).show();
                break;
            }

            // 关于
            case R.id.nav_about: {
                AboutActivity.startAboutActivity(this);
                break;
            }

        }
        return false;
    }

}
