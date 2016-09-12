package com.xiezizuocai.puredaily.entity;

public class Wallpaper {

    private int expires;
    private String name;
    private String url;

    public Wallpaper() {

    }

    public Wallpaper(int expires, String name, String url) {
        this.expires = expires;
        this.name = name;
        this.url = url;
    }

    public int getExpires() {
        return expires;
    }

    public void setExpires(int expires) {
        this.expires = expires;
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
