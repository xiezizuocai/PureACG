package com.xiezizuocai.puredaily.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wang.avi.AVLoadingIndicatorView;
import com.xiezizuocai.puredaily.R;
import com.xiezizuocai.puredaily.utils.ImageUtil;
import com.xiezizuocai.puredaily.utils.ShareUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import uk.co.senab.photoview.PhotoViewAttacher;


@ContentView(R.layout.activity_latest_details)
public class DailyDetailsActivity extends BaseActivity implements NestedScrollView.OnScrollChangeListener{

    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;

    @ViewInject(R.id.web_view)
    private WebView mWebView;

    @ViewInject(R.id.loading)
    private AVLoadingIndicatorView mLoadingView;

    @ViewInject(R.id.fab_go_to_top)
    private FloatingActionButton mGoToTopFab;

    @ViewInject(R.id.nested_scrollview)
    private NestedScrollView mNestedScrollView;

    private String mUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUrl = getIntent().getStringExtra("url");

        initListener();
        initView();
        initWebView();

        hideGoToTopFab();  //隐藏 返回顶部的悬浮按钮
    }

    private void initListener() {
        mNestedScrollView.setOnScrollChangeListener(this);
    }

    private void hideGoToTopFab() {
        mNestedScrollView.post(new Runnable() {
            @Override
            public void run() {
                mGoToTopFab.hide();  //隐藏 返回顶部的悬浮按钮
            }
        });
    }


    @Event(value = R.id.fab_go_to_top, type = View.OnClickListener.class)
    private void onFabToTopClick(View view) {
        mNestedScrollView.smoothScrollTo(0, 0);
    }


    private void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_white_24dp);
        setToolbarTitle(R.string.activity_title_latest_daily);  // 设置工具栏标题
    }

    private void initWebView() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setBuiltInZoomControls(false);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        mWebView.getSettings().setAppCacheEnabled(true);

        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100){
                    mLoadingView.setVisibility(View.GONE);
                }
            }
        });

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mWebView.loadUrl(mUrl);
    }

    public static void startDailyDetailsActivity(Activity activity, String url) {
        Intent intent = new Intent(activity, DailyDetailsActivity.class);
        intent.putExtra("url", url);
        activity.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_latest_details_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case android.R.id.home:
                finish();
                break;

            case R.id.action_share:
                break;

            case R.id.action_click_me:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mWebView.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.destroy();
    }

    /**
     * Called when the scroll position of a view changes.
     *
     * @param v The view whose scroll position has changed.
     * @param scrollX Current horizontal scroll origin.
     * @param scrollY Current vertical scroll origin.
     * @param oldScrollX Previous horizontal scroll origin.
     * @param oldScrollY Previous vertical scroll origin.
     */
    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if ((oldScrollY - scrollY) > ViewConfiguration.get(this).getScaledTouchSlop()) {
            mGoToTopFab.show();  // 收缩状态
        } else if ((scrollY - oldScrollY > ViewConfiguration.get(this).getScaledTouchSlop())) {
            mGoToTopFab.hide();  // 展开状态
        }
    }

}
