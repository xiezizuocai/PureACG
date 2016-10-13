package com.xiezizuocai.pureacg.task;

import android.os.AsyncTask;

import com.xiezizuocai.pureacg.constant.API;
import com.xiezizuocai.pureacg.entity.HotSong;
import com.xiezizuocai.pureacg.utils.ParserUtils;
import com.xiezizuocai.pureacg.utils.Request;

import org.json.JSONException;

import java.util.ArrayList;


public class FetchMusicTask {

    private static final long CACHE_MAX_AGE = 86400000L * 7;

    public interface FetchMusicCallback {
        void onSuccess(ArrayList<HotSong> songs);
        void onError(String errorMsg);
    }


    // 获取音乐榜最新数据
    public static void fetch(final FetchMusicCallback fetchCallback) {

        Request.requestUrl(API.LATEST_MUSIC , CACHE_MAX_AGE, false, new Request.RequestCallback() {
            @Override
            public void onSuccess(String result) {
                parseMusicResult(result, fetchCallback);
            }

            @Override
            public void onError(String errorMsg) {
                fetchCallback.onError(errorMsg);
            }
        });
    }


    // 解析最新结果
    private static void parseMusicResult(String result, final FetchMusicCallback fetchCallback) {
        new AsyncTask<String, Void, ArrayList<HotSong>>() {

            private String errorMsg = null;

            @Override
            protected ArrayList<HotSong> doInBackground(String... params) {
                String result = params[0];
                try {
                    return ParserUtils.parseMusicResult(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                    this.errorMsg = e.toString();
                }
                return null;
            }

            @Override
            protected void onPostExecute(ArrayList<HotSong> songs) {
                if (songs != null) {
                    fetchCallback.onSuccess(songs);
                } else if (this.errorMsg != null) {
                    fetchCallback.onError(this.errorMsg);
                }
            }
        }.execute(result);
    }

}
