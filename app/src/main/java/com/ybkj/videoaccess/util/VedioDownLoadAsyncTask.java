package com.ybkj.videoaccess.util;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.ybkj.videoaccess.app.ConstantSys;

import static android.content.Context.DOWNLOAD_SERVICE;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

public class VedioDownLoadAsyncTask extends AsyncTask<Integer,Integer,String> {
    private Context mContext;

    private long currentDownloadID;
    private boolean idDownloading = true;
    private List<String> vedioUrls;
    private int currentPosition = 0;
    private IOnVedioDownLoadFinish iOnVedioDownLoadFinish;

    public VedioDownLoadAsyncTask(Context mContext, List<String> vedioUrls, IOnVedioDownLoadFinish iOnVedioDownLoadFinish) {
        this.mContext = mContext;
        this.vedioUrls = vedioUrls;
        this.iOnVedioDownLoadFinish = iOnVedioDownLoadFinish;
    }

    @Override
    protected String doInBackground(Integer... integers) {
        // 文件名字从 1 开始
        currentPosition = 1;
        downloadByDownloadManager(mContext, vedioUrls.get(0),currentPosition);
        return String.valueOf(integers[0].intValue());
    }

    @Override
    protected void onPreExecute() {
        Toast.makeText(mContext, "开始下载", Toast.LENGTH_SHORT).show();
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        int value = values[0];
        if (value <= 100) {
            if (value == 100) {
                currentPosition ++;
                if(vedioUrls.size()>currentPosition){
                    downloadByDownloadManager(mContext, vedioUrls.get(currentPosition), currentPosition+1);
                }else{
//                    Toast.makeText(mContext, "下载完成", Toast.LENGTH_SHORT).show();
                    Log.e("onProgressUpdate", "download finish ---------");
                    if(iOnVedioDownLoadFinish != null){
                        iOnVedioDownLoadFinish.onFinished();
                    }
                }
            }
        }
    }

    public void downloadByDownloadManager(Context context, String vedioUrl, int position) {
//        DownloadManager.Request downloadRequest = new DownloadManager.Request(Uri.parse(httpDownloadUrl.toString()));
        DownloadManager.Request downloadRequest = new DownloadManager.Request(Uri.parse(vedioUrl));
        // 通过setAllowedNetworkTypes方法可以设置允许在何种网络下下载
//        downloadRequest.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        downloadRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        // 文件后缀
//        String fileFormat = PublicMethodUtils.regGetFileFormat(downloadUrlStr);
        String fileFormat = "mp4";

        // 获取文件名
//        String resourceName = PublicMethodUtils.regFormatInFile(downloadUrlStr, fileFormat);
        String resourceName = position+".mp4";

        // 本地保存地址
//        String resourcePath = ConstantSys.HOME_VEDIO_PATH;
        String resourcePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/homeVedio/";

        // 下载标题
        downloadRequest.setTitle("下载" + resourceName);
        File saveFile = new File(resourcePath, resourceName);
        downloadRequest.setDestinationUri(Uri.fromFile(saveFile));
        Log.e("resourcePath", resourcePath+" ---------");

        DownloadManager manager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
        currentDownloadID = manager.enqueue(downloadRequest);
        Log.e("e", "DownloadManager start downloading ---------");
        // 获取下载进度
        getDownloadProgress(manager, currentDownloadID, resourceName);
    }

    /**下载进度并返回完成
     *
     * @param manager
     * @param downloadID
     * @param resourceName
     */
    public void getDownloadProgress(final DownloadManager manager,final long downloadID, final String resourceName) {
        while (idDownloading) {
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(downloadID);
            Cursor cursor = manager.query(query);

            if (cursor.moveToFirst()) {
                long bytesDownloaded = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                long bytesTotal = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                final int downloadProgress = (int) (bytesDownloaded * 100 / bytesTotal);

                publishProgress(downloadProgress);

                Log.e("e",resourceName + ":下载进度: " + downloadProgress + "%");
                if (downloadProgress == 100) {
                    currentDownloadID = -1;
                    break;
                } else {
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        StringWriter stringWriter = new StringWriter();
                        e.printStackTrace(new PrintWriter(stringWriter, true));
                    }
                }
                cursor.close();
            }
        }
    }

    public interface IOnVedioDownLoadFinish{
        void onFinished();
    }

    public void remove(Context context){
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
        if (currentDownloadID >= 0){
            downloadManager.remove(currentDownloadID);
        }
        idDownloading = false;
    }
}
