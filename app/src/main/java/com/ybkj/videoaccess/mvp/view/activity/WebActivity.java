package com.ybkj.videoaccess.mvp.view.activity;

import android.Manifest;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ybkj.videoaccess.R;
import com.ybkj.videoaccess.mvp.base.BaseActivity;
import com.ybkj.videoaccess.mvp.data.bean.WebViewBean;
import com.ybkj.videoaccess.util.ToastUtil;
import com.ybkj.videoaccess.util.UpgradeUtil;
import com.ybkj.videoaccess.weight.BaseProgress;
import com.ybkj.videoaccess.weight.refresh_loadmore.RefreshLayout;

import butterknife.BindView;

import static com.ybkj.videoaccess.util.IntentUtil.PARAM_WEB;

/**
 * WebView通用界面
 * 跳转到该页面，需要传递WebViewBean Bean
 * <p>
 * Created by HH on 2016/8/22.
 */
public class WebActivity extends BaseActivity {
    @BindView(R.id.webViewProgress)
    BaseProgress webProgress;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    @BindView(R.id.webView)
    WebView webView;
    private WebViewBean webViewBean;
    private UpgradeUtil upgradeUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        webViewBean = (WebViewBean) getIntent().getSerializableExtra(PARAM_WEB);

        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    protected String setTitle() {
        if (webViewBean != null) {
            return webViewBean.getTitle();
        }

        return "";
    }

    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void initView() {
        // android 5.0以上默认不支持Mixed Content
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(
                    WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(webViewBean.getUrl());
        WebSettings webSettings = webView.getSettings();
        webSettings.setUseWideViewPort(true);  //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true);// 缩放至屏幕的大小
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

                if (webProgress != null) {
                    webProgress.setProgress(newProgress);
                }

                if (newProgress == 100 && webProgress != null) {
                    webProgress.setVisibility(View.GONE);
                }
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.endsWith(".apk")){
                    // 调用系统下载器
                    upgradeUtil = new UpgradeUtil(WebActivity.this);
                    checkPermission(url);
                }else {
                    webView.loadUrl(url);
                }
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (webProgress != null) {
                    refreshLayout.setRefreshEnable(false);
                    webProgress.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);

                refreshLayout.setRefreshEnable(true);
            }

            /**
             * 对于https开头的网页显示空白问题处理
             * @param view
             * @param handler
             * @param error
             */
            @Override
            public void onReceivedSslError(WebView view,
                                           SslErrorHandler handler, SslError error) {
                handler.proceed();// 接受所有网站的证书
            }
        });

        refreshLayout.setOnRefresh(new RefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.loadUrl(webViewBean.getUrl());
                refreshLayout.onFinish();
            }

            @Override
            public void onLoadMore() {

            }
        });
    }

    private void checkPermission(String url) {
        requestPermission(1, Manifest.permission.WRITE_EXTERNAL_STORAGE, new OnRequestPermissionListener() {
            @Override
            public void onPermissionSuccess(int requestId) {
                ToastUtil.showMsg("最新版本正在下载，请稍后...");
                upgradeUtil.showNotification();
                upgradeUtil.downloadApk(url);
            }

            @Override
            public void onPermissionFailure(int requestId) {
                ToastUtil.showMsg(getString(R.string.promet_no_permission_promet));
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (webView != null) {
            webView.removeAllViews();
            webView.destroy();
        }
    }

}
