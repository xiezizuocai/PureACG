package com.xiezizuocai.puredaily.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.xiezizuocai.puredaily.adapter.LatestDailyAdapter;
import com.xiezizuocai.puredaily.entity.LatestInfo;
import com.xiezizuocai.puredaily.task.FetchLatestDailyTask;
import com.xiezizuocai.puredaily.ui.activity.DailyDetailsActivity;
import com.xiezizuocai.puredaily.ui.activity.HomeActivity;
import com.xiezizuocai.puredaily.utils.CommonUtils;
import com.xiezizuocai.puredaily.utils.DividerItemDecoration;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;



@ContentView(R.layout.fragment_latest_daily)
public class LatestDailyFragment extends BaseFragment {

    @ViewInject(R.id.recycler_view)
    private RecyclerView mRecyclerView;

    @ViewInject(R.id.swipe)
    private SwipeRefreshLayout mSwipe;  // 下拉刷新布局引用

    private Context mContext;

    private LatestDailyAdapter mAdapter;

    private ArrayList<LatestInfo> mLatests;

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
        ((HomeActivity) getActivity()).setToolbarTitle(R.string.fragment_title_latest_daily);  // 设置HomeActivity工具栏标题
        View view = super.onCreateView(inflater, container, savedInstanceState);
        initSwipe();  // 初始化下拉刷新组件
        fetchLatestData(null);  // 获取最新数据
        return view;
    }

    private void initSwipe() {
        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchLatestData(null);  // 获取最新数据
            }
        });
        mSwipe.setColorSchemeColors(CommonUtils.getThemePrimaryColor(mContext));  // 设置下拉刷新组件颜色
    }

    /**
     * 获取消息
     *
     * @param date 指定获取的该日期的日报(date为null代表默认获取今日最新消息)
     */
    private void fetchLatestData(String date) {
        FetchLatestDailyTask.fetch(date, new FetchLatestDailyTask.FetchLatestDailyCallback() {
            @Override
            public void onSuccess(ArrayList<LatestInfo> latests) {
                onFetchSuccess(latests);  // 获取数据成功
            }

            @Override
            public void onError(String errorMsg) {
                onFetchFailed();  // 获取数据失败
            }
        });
    }

    // 获取数据成功
    private void onFetchSuccess(ArrayList<LatestInfo> latests) {

        this.mLatests = latests;
        if (mAdapter == null) {
            initRecyclerView();  // 初始化RecyclerView
        } else {
            mAdapter.syncData(mLatests);  // 同步数据
            mAdapter.notifyDataSetChanged();  // 更新数据
        }
        if (mSwipe.isRefreshing()) {
            mSwipe.setRefreshing(false);  // 停止下拉刷新
        }
    }

    // 初始化RecyclerView
    private void initRecyclerView() {
        mAdapter = new LatestDailyAdapter(mContext, mLatests);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(lm);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder holder, int position) {
                DailyDetailsActivity.startDailyDetailsActivity(getActivity(), mLatests.get(position).getUrl());
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
