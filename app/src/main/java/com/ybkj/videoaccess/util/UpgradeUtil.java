package com.ybkj.videoaccess.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.widget.RemoteViews;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ybkj.videoaccess.R;
import com.ybkj.videoaccess.app.ConstantSys;
import com.ybkj.videoaccess.mvp.data.bean.VersionInfo;
import com.ybkj.videoaccess.mvp.view.dialog.PrometDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * 版本升级工具
 * <p>
 * Created by lp on 2016/9/19 0004.
 */
public class UpgradeUtil {
    private static final int NOTIFICATION_ID = 1000;
    private int fileSize = 100;
    private NotificationManager nm;
    private Notification n;
    private Gson gson;
    private Context context;
    private DownloadFileAsync downloadFileAsync;

    public UpgradeUtil(Context context) {
        this.context = context;
        gson = new GsonBuilder().serializeNulls().create();
        if (!FileUtil.sdCardIsAvailable()) {
            // SD卡不可用
        }
    }

    public void checkUpgrade() {
        if (upgradeListener != null) {
            upgradeListener.onStart();
        }

        // 获取最新版本号
        queryVersionInfo();
        /*if(needQuery){
            queryVersionInfo();
        }else {
            getVersion();
        }*/
    }

    /**
     * 查询版本更新信息
     */
    private void queryVersionInfo() {
        /*new ConfigMode().checkVersion(110,"欢乐").subscribe(new HttpSubscriber<>(new SubscriberResultListener() {
                    @Override
                    public void onSuccess(Object o) {
                        // 数据返回成功检测
                        VersionInfo versionInfo = (VersionInfo) o;
                        if(versionInfo!= null){
                            *//*if (upgradeListener != null) {
                                upgradeListener.onUpdate();
                            }*//*

                            PreferencesUtils.getInstance(ConstantSys.PREFERENCE_CONFIG).putString(MyApp.getAppContext(),
                                    ConstantSys.PREFERENCE_VERSION_INFO,new Gson().toJson(versionInfo));
                            getVersion();
                        }else{
                            if (upgradeListener != null) {
                                upgradeListener.noUpdate();
                            }
                        }
                    }

                    @Override
                    public void onError(HttpErrorException errorException) {
                        if (upgradeListener != null) {
                            upgradeListener.noUpdate();
                        }
                    }
                }));*/
    }

    /**
     * 获取最新版本号
     *
     * @return
     */
    private void getVersion() {
        VersionInfo versionInfo = gson.fromJson(PreferencesUtils.getInstance(ConstantSys.ConfigKey.PREFERENCE_SYSTEM_CONFIG).getString(
                ConstantSys.PREFERENCE_VERSION_INFO), VersionInfo.class);

        if (versionInfo != null && checkVersion(versionInfo.getVersion_id())) {
            Message message = new Message();
            message.obj = versionInfo.getUrl();
            handler.sendMessage(message);

            /*if (upgradeListener != null) {
                upgradeListener.onUpdate();
            }

            PrometDialog confirmDialog;
            confirmDialog = new PrometDialog(context*//*, new PrometDialog.OnConfirm() {
                @Override
                public void onConfirm() {
                    showNotification();
                    Message message = new Message();
                    message.obj = versionInfo.getUrl();
                    handler.sendMessage(message);
                }
            }*//*);
            confirmDialog.setTitle(context.getString(R.string.upgrade));
            confirmDialog.setMessage(context.getString(R.string.upgradeContent));
            if (versionInfo.getUpgradeType() == 2) {
                // 强更
                confirmDialog.setCancelable(false);
                confirmDialog.setClosedEnable(false);
            } else if (versionInfo.getUpgradeType() == 1) {
                // 有更新，可更新，可不更新
                confirmDialog.setCancelable(true);
            }
            confirmDialog.show();*/
        } else {
            // 无更新
            if (upgradeListener != null) {
                upgradeListener.noUpdate();
            }
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            ToastUtil.showMsg("最新版本正在下载，请稍后...");
            String url = (String) msg.obj;
            downloadApk(url);
            super.handleMessage(msg);
        }
    };


    /**
     * 检测版本是否更新
     *
     * @param newVersion
     * @return
     */
    private boolean checkVersion(int newVersion) {
        PackageInfo pi = null;
        try {
            pi = context.getPackageManager().getPackageInfo(context.getApplicationContext().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return pi != null && pi.versionCode < newVersion;
    }

    /**
     * 开启通知栏，进行下载进度显示
     */
    public void showNotification() {
        nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        RemoteViews view = new RemoteViews(context.getPackageName(),
                R.layout.notification_download_apk);
        view.setTextViewText(R.id.notificationInfo, context.getString(R.string.downloadProgress));
        view.setProgressBar(R.id.numberbar, 100, 0, false);
        n = new Notification();
        n.flags = Notification.FLAG_ONGOING_EVENT;
        n.contentView = view;
        n.icon = R.mipmap.ic_launcher;
        nm.notify(NOTIFICATION_ID, n);
    }

    /**
     * 下载失败
     */
    private void notifacationDownloadFailure(String info) {
        n.contentView.setTextViewText(R.id.notificationInfo, info);
        n.contentView.setProgressBar(R.id.numberbar, fileSize, 0, false);
        nm.notify(NOTIFICATION_ID, n);
    }

    /**
     * 设置通知栏内的内容
     *
     * @param progress 当前进度
     * @param info     显示信息
     */
    public void setNotificationValue(int progress, String info) {
        n.contentView.setTextViewText(R.id.notificationInfo, info);
        n.contentView.setProgressBar(R.id.numberbar, fileSize, progress, false);
        nm.notify(NOTIFICATION_ID, n);
    }

    /**
     * 开启线程进行APK下载
     *
     * @param apkUrl APK网络路径
     */
    public void downloadApk(String apkUrl) {
        downloadFileAsync = new DownloadFileAsync();
        downloadFileAsync.execute(apkUrl);
    }

    public void destroy() {
        if (downloadFileAsync != null) {
            downloadFileAsync.cancel(true);
        }
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    private class DownloadFileAsync extends AsyncTask<String, String, String> {
        String filePath = "";

        @Override
        protected String doInBackground(String... aurl) {

            try {
                int down_step = 2;// 提示step
                int totalSize;// 文件总大小
                int downloadCount = 0;// 已经下载好的大小
                int updateCount = 0;// 已经上传的文件大小

                InputStream inputStream;
                OutputStream outputStream;

                URL url = new URL(aurl[0]);
                HttpURLConnection httpURLConnection;

                //关键代码
                //ignore https certificate validation |忽略 https 证书验证
                if (url.getProtocol().toUpperCase().equals("HTTPS")) {
                    trustAllHosts();
                    HttpsURLConnection https = (HttpsURLConnection) url
                            .openConnection();
                    https.setHostnameVerifier(DO_NOT_VERIFY);
                    httpURLConnection = https;
                } else {
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                }

                httpURLConnection.setConnectTimeout(15 * 1000);
                httpURLConnection.setReadTimeout(15 * 1000);
                // 获取下载文件的size
                totalSize = httpURLConnection.getContentLength();

                if (httpURLConnection.getResponseCode() == 404) {
                    throw new Exception("fail!");
                    //这个地方应该加一个下载失败的处理，但是，因为我们在外面加了一个try---catch，已经处理了Exception,
                }

                String filename = "/lottery_" + System.currentTimeMillis() + ".apk";
                FileUtil.createMoreFiles(ConstantSys.FILE_TEMP);
                filePath = FileUtil.getSdCardPath() + ConstantSys.FILE_TEMP + filename;

                inputStream = httpURLConnection.getInputStream();
                outputStream = new FileOutputStream(filePath, false);// 文件存在则覆盖掉

                byte buffer[] = new byte[1024];
                int readsize;

                while ((readsize = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, readsize);
                    downloadCount += readsize;// 时时获取下载到的大小
                    /*** 每次增张3%**/
                    if (updateCount == 0 || (downloadCount * 100 / totalSize - down_step) >= updateCount) {
                        updateCount += down_step;
                        // 改变通知栏
                        publishProgress("" + updateCount);
                    }
                }

                httpURLConnection.disconnect();
                inputStream.close();
                outputStream.close();

                return "";
            } catch (Exception e) {
                e.printStackTrace();
                // 下载失败
                notifacationDownloadFailure(context.getString(R.string.downloadFailed));
            }

            return null;
        }

        protected void onProgressUpdate(String... progress) {
            if (Integer.parseInt(progress[0]) == 100) {
                setNotificationValue(Integer.parseInt(progress[0]), context.getString(R.string.downloadComplete));
            } else {
                setNotificationValue(Integer.parseInt(progress[0]), context.getString(R.string.downloading) + progress[0] + "%");
            }
        }

        @Override
        protected void onPostExecute(String unused) {
            if (unused != null) {
                // 下载结束
                nm.cancel(NOTIFICATION_ID);
                autoInstallApk(filePath);
            }
        }
    }

    /**
     * 自动安装
     *
     * @param apkPath
     */
    public void autoInstallApk(String apkPath) {
        Intent i = new Intent();
        i.setAction(Intent.ACTION_VIEW);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setDataAndType(Uri.fromFile(new File(apkPath)), "application/vnd.android.package-archive");
        context.startActivity(i);
    }

    private UpgradeListener upgradeListener;

    public void setUpgradeListener(UpgradeListener upgradeListener) {
        this.upgradeListener = upgradeListener;
    }

    public interface UpgradeListener {
        /**
         * 开始检查
         */
        void onStart();

        /**
         * 有更新
         */
        void onUpdate();

        /**
         * 无更新
         */
        void noUpdate();
    }

    /**
     * 获取当前版本号
     *
     * @return
     */
    private long getLoacalVersion() {
        PackageInfo pi = null;
        try {
            pi = context.getPackageManager().getPackageInfo(context.getApplicationContext().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return pi.versionCode;
    }

    public static void trustAllHosts() {
        TrustManager
                [] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[]{};
            }

            public void checkClientTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }
        }};

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection
                    .setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

}
