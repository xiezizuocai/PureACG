package com.xiezizuocai.puredaily.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.xiezizuocai.puredaily.adapter.MusicAdapter;
import com.xiezizuocai.puredaily.entity.HotSong;
import com.xiezizuocai.puredaily.task.FetchMusicTask;
import com.xiezizuocai.puredaily.ui.activity.HomeActivity;
import com.xiezizuocai.puredaily.utils.CommonUtils;
import com.xiezizuocai.puredaily.utils.DividerItemDecoration;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;



@ContentView(R.layout.fragment_music)
public class MusicFragment extends BaseFragment {

    @ViewInject(R.id.music_recycler_view)
    private RecyclerView mRecyclerView;

    @ViewInject(R.id.music_swipe)
    private SwipeRefreshLayout mSwipe;  // 下拉刷新布局引用

    private Context mContext;

    private MusicAdapter mAdapter;

    private ArrayList<HotSong> mSongs;

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
        ((HomeActivity) getActivity()).setToolbarTitle(R.string.fragment_title_music);  // 设置HomeActivity工具栏标题
        View view = super.onCreateView(inflater, container, savedInstanceState);

        initSwipe();  // 初始化下拉刷新组件
        fetchMusicData();  // 获取最新数据
        return view;
    }

    private void initSwipe() {
        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchMusicData();  // 获取最新数据
            }
        });
        mSwipe.setColorSchemeColors(CommonUtils.getThemePrimaryColor(mContext));  // 设置下拉刷新组件颜色
    }


    // 获取音乐榜数据
    private void fetchMusicData() {
        FetchMusicTask.fetch(new FetchMusicTask.FetchMusicCallback() {
            @Override
            public void onSuccess(ArrayList<HotSong> songs) {
                onFetchSuccess(songs);  // 获取数据成功
            }

            @Override
            public void onError(String errorMsg) {
                onFetchFailed();  // 获取数据失败
            }
        });
    }

    // 获取数据成功
    private void onFetchSuccess(ArrayList<HotSong> songs) {

        this.mSongs = songs;
        if (mAdapter == null) {
            initRecyclerView();  // 初始化RecyclerView
        } else {
            mAdapter.syncData(mSongs);  // 同步数据
            mAdapter.notifyDataSetChanged();  // 更新数据
        }
        if (mSwipe.isRefreshing()) {
            mSwipe.setRefreshing(false);  // 停止下拉刷新
        }
    }

    // 初始化RecyclerView
    private void initRecyclerView() {
        mAdapter = new MusicAdapter(mContext, mSongs);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(lm);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder holder, int position) {
                Uri uri = Uri.parse(mSongs.get(position).getUrl());
                Intent intent  = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
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
