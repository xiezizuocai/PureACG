package com.xiezizuocai.puredaily.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.xiezizuocai.puredaily.R;
import com.xiezizuocai.puredaily.utils.ImageUtil;
import com.xiezizuocai.puredaily.utils.ShareUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Administrator on 2016/8/16.
 */
@ContentView(R.layout.activity_latest_pic_details)
public class PicDetailsActivity extends BaseActivity {

    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;

    @ViewInject(R.id.image_view)
    private ImageView image;

    private String url;

     private PhotoViewAttacher attacher;

    private Bitmap bitmap;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getIntent().getStringExtra("url");
        initView();
    }

    public static void startPicDetailsActivity(Activity activity, String url) {
        Intent intent = new Intent(activity, PicDetailsActivity.class);
        intent.putExtra("url", url);
        activity.startActivity(intent);
    }


    private void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_white_24dp);
        setToolbarTitle(R.string.fragment_title_latest_pic);  // 设置工具栏标题

        attacher = new PhotoViewAttacher(image);
        Glide.with(this)
                .load(url)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        image.setImageBitmap(resource);
                        attacher.update();
                        bitmap = resource;
                    }
                });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_pic_details_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case android.R.id.home:
                finish();
                break;

            case R.id.action_share:
                ShareUtil.shareImage(this, ImageUtil.saveImage(this,url,bitmap,image,"share"));
                break;

            case R.id.action_save:
                ImageUtil.saveImage(this,url,bitmap,image,"save");
                break;

            case R.id.action_click_me:

                break;
        }
        return super.onOptionsItemSelected(item);
    }


}