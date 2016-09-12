package com.xiezizuocai.puredaily.entity;

public class AnimeComic {

    private String cover;  // 封面图片Url
    private String time;  // 时间
    private String title;  // 标题
    private String url;  // 网页链接

    public AnimeComic() {

    }

    public AnimeComic(String cover, String time, String title, String url) {
        this.cover = cover;
        this.time = time;
        this.title = title;
        this.url = url;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
