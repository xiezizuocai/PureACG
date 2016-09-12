package com.xiezizuocai.puredaily.entity;

/**
 * Created by Administrator on 2016/9/7.
 */
public class HotSong {

    private String artists;  // 艺术家
    private String alias;  // 出处
    private String name;  // 歌曲名
    private String url;  // 网页链接


    public HotSong() {

    }

    public HotSong(String artists, String alias, String name, String url) {
        this.artists = artists;
        this.alias = alias;
        this.name = name;
        this.url = url;
    }

    public String getArtists() {
        return artists;
    }

    public void setArtists(String artists) {
        this.artists = artists;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
