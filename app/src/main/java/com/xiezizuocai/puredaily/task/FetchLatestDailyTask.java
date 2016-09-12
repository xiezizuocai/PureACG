package com.xiezizuocai.puredaily.task;

import android.os.AsyncTask;
import com.xiezizuocai.puredaily.constant.API;
import com.xiezizuocai.puredaily.entity.LatestInfo;
import com.xiezizuocai.puredaily.utils.ParserUtils;
import com.xiezizuocai.puredaily.utils.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class FetchLatestDailyTask {

    private static final long CACHE_MAX_AGE = 86400000L * 7;

    public interface FetchLatestDailyCallback {
        void onSuccess(ArrayList<LatestInfo> latests);

        void onError(String errorMsg);
    }

    // 获取最新的数据
    private static void fetch(final FetchLatestDailyCallback fetchCallback) {
        Request.requestUrl(API.LATEST_INFO, CACHE_MAX_AGE, false, new Request.RequestCallback() {
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

    // 获取某一天的数据
    public static void fetch(String date, final FetchLatestDailyCallback fetchCallback) {
        if (date == null) {
            fetch(fetchCallback);
            return;
        }
        Request.requestUrl(API.LATEST_INFO + date, CACHE_MAX_AGE, false, new Request.RequestCallback() {
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
    private static void parseLatestResult(String result, final FetchLatestDailyCallback fetchCallback) {
        new AsyncTask<String, Void, ArrayList<LatestInfo>>() {

            private String errorMsg = null;

            @Override
            protected ArrayList<LatestInfo> doInBackground(String... params) {
                String result = params[0];
                try {
                    return ParserUtils.parseLatestResult(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                    this.errorMsg = e.toString();
                }
                return null;
            }

            @Override
            protected void onPostExecute(ArrayList<LatestInfo> latests) {
                if (latests != null) {
                    fetchCallback.onSuccess(latests);
                } else if (this.errorMsg != null) {
                    fetchCallback.onError(this.errorMsg);
                }
            }
        }.execute(result);
    }

}
