package com.xiezizuocai.pureacg.ui.activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.wang.avi.AVLoadingIndicatorView;
import com.xiezizuocai.pureacg.R;
import com.xiezizuocai.pureacg.adapter.AnimeComicAdapter;
import com.xiezizuocai.pureacg.adapter.BaseAdapter;
import com.xiezizuocai.pureacg.entity.AnimeComic;
import com.xiezizuocai.pureacg.task.FetchAnimeComicTask;
import com.xiezizuocai.pureacg.utils.DividerItemDecoration;
import com.xiezizuocai.pureacg.utils.ImageUtil;
import com.xiezizuocai.pureacg.utils.ShareUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;


@ContentView(R.layout.activity_latest_anime_comic)
public class AnimeComicActivity extends BaseActivity {

    @ViewInject(R.id.recycler_view)
    private RecyclerView mRecyclerView;

    @ViewInject(R.id.loading)
    private AVLoadingIndicatorView mLoadingView;

    @ViewInject(R.id.no_data_area)
    private LinearLayout mNoDataArea;

    private AnimeComicAdapter mAdapter;

    private ArrayList<AnimeComic> mAnimeComics = new ArrayList<>();

    private static final int Bilibili = 1;

    private static final int AcFun = 2;

    private static final int Tencent = 3;

    private static final int U17 = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle(R.string.activity_title_anime_comic);

        featchAnimeComicData(Bilibili);
    }

    private void featchAnimeComicData(int webType) {

        startLoadingAnim();

        switch (webType) {

            case Bilibili:
                FetchAnimeComicTask.fetch(FetchAnimeComicTask.TYPE_BILIBILI, new FetchAnimeComicTask.FetchAmimeComicCallback() {
                    @Override
                    public void onSuccess(int UrlType,ArrayList<AnimeComic> bilibiliAnimeComics) {
                        stopLoadingAnim();
                        if(bilibiliAnimeComics.size() == 0) {
                            mRecyclerView.setVisibility(View.GONE);
                            mNoDataArea.setVisibility(View.VISIBLE);
                            return;
                        }
                        mNoDataArea.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mAnimeComics.addAll(bilibiliAnimeComics);
                        onFetchSuccess();
                    }
                    @Override
                    public void onError(String errorMsg) {
                        stopLoadingAnim();
                    }
                });
                break;

            case AcFun:
                FetchAnimeComicTask.fetch(FetchAnimeComicTask.TYPE_ACFUN, new FetchAnimeComicTask.FetchAmimeComicCallback() {
                    @Override
                    public void onSuccess(int UrlType,ArrayList<AnimeComic> acFunAnimeComics) {
                        stopLoadingAnim();
                        if(acFunAnimeComics.size() == 0) {
                            mRecyclerView.setVisibility(View.GONE);
                            mNoDataArea.setVisibility(View.VISIBLE);
                            return;
                        }
                        mNoDataArea.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mAnimeComics.addAll(acFunAnimeComics);
                        onFetchSuccess();
                    }

                    @Override
                    public void onError(String errorMsg) {
                        stopLoadingAnim();
                    }
                });
                break;

            case Tencent:
                FetchAnimeComicTask.fetch(FetchAnimeComicTask.TYPE_TENCENT, new FetchAnimeComicTask.FetchAmimeComicCallback() {
                    @Override
                    public void onSuccess(int UrlType,ArrayList<AnimeComic> tencentAnimeComics) {
                        stopLoadingAnim();
                        if(tencentAnimeComics.size() == 0) {
                            mRecyclerView.setVisibility(View.GONE);
                            mNoDataArea.setVisibility(View.VISIBLE);
                            return;
                        }
                        mNoDataArea.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mAnimeComics.addAll(tencentAnimeComics);
                        onFetchSuccess();
                    }

                    @Override
                    public void onError(String errorMsg) {
                        stopLoadingAnim();
                    }
                });
                break;

            case U17:
                FetchAnimeComicTask.fetch(FetchAnimeComicTask.TYPE_U17, new FetchAnimeComicTask.FetchAmimeComicCallback() {
                    @Override
                    public void onSuccess(int UrlType,ArrayList<AnimeComic> u17AnimeComics) {
                        stopLoadingAnim();
                        if(u17AnimeComics.size() == 0) {
                            mRecyclerView.setVisibility(View.GONE);
                            mNoDataArea.setVisibility(View.VISIBLE);
                            return;
                        }
                        mNoDataArea.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mAnimeComics.addAll(u17AnimeComics);
                        onFetchSuccess();
                    }

                    @Override
                    public void onError(String errorMsg) {
                        stopLoadingAnim();
                    }
                });
                break;

            default:
                break;

        }

    }


    private void onFetchSuccess() {
        initRecyclerView();
    }

    private void initRecyclerView() {
        mAdapter = new AnimeComicAdapter(this, mAnimeComics);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder holder, int position) {
                Uri uri = Uri.parse(mAnimeComics.get(position).getUrl());
                Intent intent  = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

//                DailyDetailsActivity.startDailyDetailsActivity(AnimeComicActivity.this, mAnimeComics.get(position).getUrl());
            }
        });
    }

    public static void startAnimeComicActivity(Activity activity) {
        Intent intent = new Intent(activity, AnimeComicActivity.class);
        activity.startActivity(intent);
    }

    private void startLoadingAnim() {
        mLoadingView.setVisibility(View.VISIBLE);
    }

    private void stopLoadingAnim() {
        mLoadingView.setVisibility(View.GONE);
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_anime_comic_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.action_bilibili:
                mAnimeComics.clear();
                featchAnimeComicData(Bilibili);
                break;

            case R.id.action_acfun:
                mAnimeComics.clear();
                featchAnimeComicData(AcFun);
                break;

            case R.id.action_tencent:
                mAnimeComics.clear();
                featchAnimeComicData(Tencent);
                break;

            case R.id.action_u17:
                mAnimeComics.clear();
                featchAnimeComicData(U17);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
