package com.xiezizuocai.puredaily.task;

import android.os.AsyncTask;


import com.xiezizuocai.puredaily.constant.API;
import com.xiezizuocai.puredaily.entity.Wallpaper;
import com.xiezizuocai.puredaily.utils.ParserUtils;
import com.xiezizuocai.puredaily.utils.Request;

import org.json.JSONException;

import java.util.ArrayList;


public class FetchLatestPicTask {

    private static final long CACHE_MAX_AGE = 86400000L * 7;

    public interface FetchLatestPicCallback {
        void onSuccess(ArrayList<Wallpaper> latests);

        void onError(String errorMsg);
    }

    // 获取最新的数据
    public static void fetch(int pages,final FetchLatestPicCallback fetchCallback) {
        Request.requestUrl(API.LATEST_PIC + pages, CACHE_MAX_AGE, false, new Request.RequestCallback() {
            @Override
            public void onSuccess(String result) {
                parseLatestResult(result, fetchCallback);
            }

            @Override
            public void onError(String errorMsg) {
                fetchCallback.onError(errorMsg);
            }
        });
    }

    // 解析最新结果
    private static void parseLatestResult(String result, final FetchLatestPicCallback fetchCallback) {
        new AsyncTask<String, Void, ArrayList<Wallpaper>>() {

            private String errorMsg = null;

            @Override
            protected ArrayList<Wallpaper> doInBackground(String... params) {
                String result = params[0];
                try {
                    return ParserUtils.parseLatestGankHuoResult(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                    this.errorMsg = e.toString();
                }
                return null;
            }

            @Override
            protected void onPostExecute(ArrayList<Wallpaper> latests) {
                if (latests != null) {
                    fetchCallback.onSuccess(latests);
                } else if (this.errorMsg != null) {
                    fetchCallback.onError(this.errorMsg);
                }
            }
        }.execute(result);
    }

}
