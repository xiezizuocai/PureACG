package com.xiezizuocai.pureacg.entity;

import java.io.Serializable;
import java.util.Arrays;


// 最新动漫资讯
public class LatestInfo {

    private String content;  // 内容
    private String cover;  // 封面图片Url
    private String time;  // 时间
    private String title;  // 标题
    private String url;  // 网页链接

    public LatestInfo() {

    }

    public LatestInfo(String content, String cover, String time, String title, String url) {
        this.content = content;
        this.cover = cover;
        this.time = time;
        this.title = title;
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
