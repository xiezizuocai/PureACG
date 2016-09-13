package com.xiezizuocai.puredaily.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.xiezizuocai.puredaily.R;
import com.xiezizuocai.puredaily.adapter.BaseAdapter;
import com.xiezizuocai.puredaily.adapter.LatestPicAdapter;
import com.xiezizuocai.puredaily.entity.Wallpaper;
import com.xiezizuocai.puredaily.task.FetchLatestPicTask;
import com.xiezizuocai.puredaily.ui.activity.HomeActivity;
import com.xiezizuocai.puredaily.ui.activity.PicDetailsActivity;
import com.xiezizuocai.puredaily.utils.CommonUtils;
import com.xiezizuocai.puredaily.utils.DividerItemDecoration;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;


@ContentView(R.layout.fragment_latest_pic)
public class LatestPicFragment extends BaseFragment {

    @ViewInject(R.id.recycler_view)
    private RecyclerView mRecyclerView;

    @ViewInject(R.id.swipe)
    private SwipeRefreshLayout mSwipe;  // 下拉刷新布局引用

    private Context mContext;

    private LatestPicAdapter mAdapter;

    private ArrayList<Wallpaper> mWallpapers;

    private int lastVisibleItem = 0;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);  // 设置含有选择菜单
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((HomeActivity) getActivity()).setToolbarTitle(R.string.fragment_title_latest_pic);  // 设置HomeActivity工具栏标题
        View view = super.onCreateView(inflater, container, savedInstanceState);
        initSwipe();  // 初始化下拉刷新组件
        fetchLatestPicData();  // 获取最新数据
        return view;
    }

    private void initSwipe() {
        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchLatestPicData();  // 获取最新数据
            }
        });
        mSwipe.setColorSchemeColors(CommonUtils.getThemePrimaryColor(mContext));  // 设置下拉刷新组件颜色
    }

    // 获取最新壁纸数据
    public void fetchLatestPicData() {

        FetchLatestPicTask.fetch(new FetchLatestPicTask.FetchLatestPicCallback() {
            @Override
            public void onSuccess(ArrayList<Wallpaper> wallpapers) {
                onFetchSuccess(wallpapers);  // 获取数据成功
            }

            @Override
            public void onError(String errorMsg) {
                onFetchFailed();  // 获取数据失败
            }
        });

    }

    // 获取数据成功
    private void onFetchSuccess(ArrayList<Wallpaper> wallpapers) {

        this.mWallpapers = wallpapers;

        if (mAdapter == null) {
            initRecyclerView();  // 初始化RecyclerView
        } else {
            mAdapter.syncData(mWallpapers);  // 同步数据
            mAdapter.notifyDataSetChanged();  // 更新数据
        }
        if (mSwipe.isRefreshing()) {
            mSwipe.setRefreshing(false);  // 停止下拉刷新
        }

    }

    // 初始化RecyclerView
    private void initRecyclerView() {

        mAdapter = new LatestPicAdapter(mContext, mWallpapers);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);

        mRecyclerView.setLayoutManager(linearLayoutManager);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState ==RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 >= mAdapter.getItemCount()) {
                    Snackbar.make(recyclerView,"    壁纸正在加载中 . . . ",Snackbar.LENGTH_SHORT).show();
                    fetchLatestPicData(); // 获取最新数据
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView,dx, dy);
                lastVisibleItem =linearLayoutManager.findLastVisibleItemPosition();
            }
        });

        mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder holder, int position) {
                PicDetailsActivity.startPicDetailsActivity(getActivity(), mWallpapers.get(position).getUrl());
            }
        });



    }

    // 获取数据失败
    private void onFetchFailed() {
        if (mSwipe.isRefreshing()) {
            mSwipe.setRefreshing(false);
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }



}
