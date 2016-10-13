package com.xiezizuocai.pureacg.utils;

import com.xiezizuocai.pureacg.entity.AnimeComic;
import com.xiezizuocai.pureacg.entity.HotSong;
import com.xiezizuocai.pureacg.entity.LatestInfo;
import com.xiezizuocai.pureacg.entity.Wallpaper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ParserUtils {

    /**
     * 解析Latest最新资讯
     *
     * @param result
     * @return
     * @throws JSONException
     */
    public static ArrayList<LatestInfo> parseLatestResult(String result) throws JSONException {
        ArrayList<LatestInfo> results = new ArrayList<LatestInfo>();

        JSONObject resultJObj = new JSONObject(result);

        JSONObject resultBodyJObj = resultJObj.getJSONObject("showapi_res_body");

        JSONArray latestsJArray = resultBodyJObj.getJSONArray("result");

        JSONObject latestJObj = null;

        for (int pos = 0; pos < latestsJArray.length(); pos++) {
            latestJObj = latestsJArray.getJSONObject(pos);
            LatestInfo latest = new LatestInfo();
            latest.setContent(latestJObj.getString("content"));

            latest.setTime(latestJObj.getString("time"));
            latest.setTitle(latestJObj.getString("title"));
            latest.setUrl(latestJObj.getString("url"));

            if (latestJObj.has("cover")) {
                latest.setCover(latestJObj.getString("cover"));
            }
            results.add(latest);
        }
        return results;
    }

    /**
     * 解析新番连载
     *
     * @param result
     * @return
     * @throws JSONException
     */
    public static ArrayList<AnimeComic> parseAnimeComicResult(String result) throws JSONException {
        ArrayList<AnimeComic> results = new ArrayList<AnimeComic>();

        JSONObject resultJObj = new JSONObject(result);

        JSONObject resultBodyJObj = resultJObj.getJSONObject("showapi_res_body");

        JSONArray latestsJArray = resultBodyJObj.getJSONArray("result");

        JSONObject latestJObj = null;

        for (int pos = 0; pos < latestsJArray.length(); pos++) {
            latestJObj = latestsJArray.getJSONObject(pos);
            AnimeComic latest = new AnimeComic();

            latest.setTime(latestJObj.getString("time"));
            latest.setTitle(latestJObj.getString("title"));
            latest.setUrl(latestJObj.getString("url"));

            if (latestJObj.has("cover")) {
                latest.setCover(latestJObj.getString("cover"));
            }
            results.add(latest);
        }
        return results;
    }


    /**
     * 解析最新音乐榜
     *
     * @param result
     * @return
     * @throws JSONException
     */
    public static ArrayList<HotSong> parseMusicResult(String result) throws JSONException {
        ArrayList<HotSong> results = new ArrayList<HotSong>();

        JSONObject resultJObj = new JSONObject(result);

        JSONObject resultBodyJObj = resultJObj.getJSONObject("showapi_res_body");

        JSONArray songsJArray = resultBodyJObj.getJSONArray("result");

        JSONObject latestJObj = null;

        for (int pos = 0; pos < songsJArray.length(); pos++) {
            latestJObj = songsJArray.getJSONObject(pos);
            HotSong song = new HotSong();

            song.setArtists(latestJObj.getString("artists"));
            song.setAlias(latestJObj.getString("alias"));
            song.setName(latestJObj.getString("name"));
            song.setUrl(latestJObj.getString("url"));

            results.add(song);
        }
        return results;
    }


    /**
     * 解析高清壁纸
     *
     * @param result
     * @return
     * @throws JSONException
     */
    public static ArrayList<Wallpaper> parseLatestGankHuoResult(String result) throws JSONException {
        ArrayList<Wallpaper> results = new ArrayList<Wallpaper>();

        JSONObject resultJObj = new JSONObject(result);

        JSONObject resultBodyJObj = resultJObj.getJSONObject("showapi_res_body");

        JSONArray latestsJArray = resultBodyJObj.getJSONArray("result");

        JSONObject latestJObj = null;

        for (int pos = 0; pos < latestsJArray.length(); pos++) {
            latestJObj = latestsJArray.getJSONObject(pos);
            Wallpaper latest = new Wallpaper();

            latest.setExpires(latestJObj.getInt("expires"));
            latest.setName(latestJObj.getString("name"));
            latest.setUrl(latestJObj.getString("url"));

            results.add(latest);
        }
        return results;
    }

}
