package com.xiezizuocai.puredaily.task;

import android.os.AsyncTask;
import com.xiezizuocai.puredaily.constant.API;
import com.xiezizuocai.puredaily.entity.LatestInfo;
import com.xiezizuocai.puredaily.utils.ParserUtils;
import com.xiezizuocai.puredaily.utils.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class FetchLatestInfoTask {

    private static final long CACHE_MAX_AGE = 86400000L * 7;

    public interface FetchLatestInfoCallback {
        void onSuccess(ArrayList<LatestInfo> latestInfos);
        void onError(String errorMsg);
    }

    // 获取最新资讯数据
    public static void fetch( final FetchLatestInfoCallback fetchCallback) {
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


    // 解析最新结果
    private static void parseLatestResult(String result, final FetchLatestInfoCallback fetchCallback) {
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
            protected void onPostExecute(ArrayList<LatestInfo> latestInfos) {
                if (latestInfos != null) {
                    fetchCallback.onSuccess(latestInfos);
                } else if (this.errorMsg != null) {
                    fetchCallback.onError(this.errorMsg);
                }
            }
        }.execute(result);
    }

}
