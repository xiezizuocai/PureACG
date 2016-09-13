package com.xiezizuocai.puredaily.task;

import android.os.AsyncTask;

import com.xiezizuocai.puredaily.constant.API;
import com.xiezizuocai.puredaily.entity.AnimeComic;
import com.xiezizuocai.puredaily.utils.ParserUtils;
import com.xiezizuocai.puredaily.utils.Request;

import org.json.JSONException;

import java.util.ArrayList;


public class FetchAnimeComicTask {

    private static final long CACHE_MAX_AGE = 86400000L * 7;

    public static final int TYPE_BILIBILI = 1;
    public static final int TYPE_ACFUN = 2;
    public static final int TYPE_TENCENT = 3;
    public static final int TYPE_U17 = 4;

    public interface FetchAmimeComicCallback {
        void onSuccess(int UrlType,ArrayList<AnimeComic> animeComics);
        void onError(String errorMsg);
    }

    // 获取最新的数据
    public static void fetch(final int UrlType,final FetchAmimeComicCallback fetchCallback) {

        String anime_comic_api = null;

        if(UrlType == TYPE_BILIBILI) {
            anime_comic_api = API.LATEST_BILIBILI_AMIME;
        } else if(UrlType == TYPE_ACFUN) {
            anime_comic_api = API.LATEST_ACFUN_ANIME;
        } else if(UrlType == TYPE_TENCENT) {
            anime_comic_api = API.LATEST_TENCENT_COMIC;
        } else if(UrlType == TYPE_U17) {
            anime_comic_api = API.LATEST_U17_COMIC;
        }

        Request.requestUrl(anime_comic_api, CACHE_MAX_AGE, false, new Request.RequestCallback() {
            @Override
            public void onSuccess(String result) {
                parseAnimeComicResult(UrlType,result, fetchCallback);
            }

            @Override
            public void onError(String errorMsg) {
                fetchCallback.onError(errorMsg);
            }
        });
    }


    // 解析最新结果
    private static void parseAnimeComicResult(final int UrlType,String result, final FetchAmimeComicCallback fetchCallback) {
        new AsyncTask<String, Void, ArrayList<AnimeComic>>() {

            private String errorMsg = null;

            @Override
            protected ArrayList<AnimeComic> doInBackground(String... params) {
                String result = params[0];
                try {
                    return ParserUtils.parseAnimeComicResult(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                    this.errorMsg = e.toString();
                }
                return null;
            }

            @Override
            protected void onPostExecute(ArrayList<AnimeComic> animeComics) {
                if (animeComics != null) {
                    fetchCallback.onSuccess(UrlType,animeComics);
                } else if (this.errorMsg != null) {
                    fetchCallback.onError(this.errorMsg);
                }
            }
        }.execute(result);
    }

}
