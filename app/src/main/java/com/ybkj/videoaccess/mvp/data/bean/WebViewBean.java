package com.ybkj.videoaccess.mvp.data.bean;

import java.io.Serializable;

/**
 * 跳转WebView 传递参数
 *
 * Created by HH on 2016/7/7.
 */
public class WebViewBean implements Serializable {
    private String title;
    private String url;

    public WebViewBean(){

    }

    public WebViewBean(String title, String url){
        this.title = title;
        this.url = url;
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
