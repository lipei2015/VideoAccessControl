package com.ybkj.videoaccess.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;

/**
 * 文件工具类
 *
 * Created by HH on 2018/1/18
 */
public class FileUtil {

    /**
     * 如果存在SD卡则将缓存写入SD卡,否则写入手机内存
     */
    public static String getCacheDir(Context context) {
        String cacheDir;
        if (context.getExternalCacheDir() != null && sdCardIsAvailable()) {
            cacheDir = context.getExternalCacheDir().toString();
        } else {
            cacheDir = context.getCacheDir().toString();
        }
        return cacheDir;
    }

    /**
     * 检测SD卡是否可用
     *
     * @return true为可用，否则为不可用
     */
    public static boolean sdCardIsAvailable() {
        String status = Environment.getExternalStorageState();
        return status.equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取SD卡路径
     *
     * @return
     */
    public static String getSdCardPath() {
        return Environment.getExternalStorageDirectory().toString();
    }

    /**
     * 根据文件路径创建文件夹和文件
     * <p>
     * eg: /userImg/userImg.jpg
     * 先依据/拆分为字符串数组，然后逐级创建文件夹和文件
     */
    public static File createMoreFiles(String path) {
        String sdPath = getSdCardPath();
        StringBuilder sb = new StringBuilder(sdPath);
        String[] filesArr = path.split("/");
        File files;

        //逐级创建文件夹
        for (String filesName : filesArr) {
            if (filesArr.length < 1 || filesName.contains(".")) {
                continue;
            }

            sb.append(File.separator).append(filesName);
            files = new File(sb.toString());
            if (!files.exists()) {
                files.mkdirs();
            }
        }

        //创建文件
        return createFile(path);
    }

    /**
     * 创建文件
     *
     * @param fileName
     */
    public static File createFile(String fileName) {
        if (fileName.indexOf(".") == -1) {
            return null;
        }

        String path = getSdCardPath() + fileName;
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 删除某个文件夹下全部文件
     *
     * @param filePath 文件夹路径
     */
    /**
     * 递归删除文件和文件夹
     *
     * @param file
     *            要删除的根目录
     */
    public static void deleteFile(File file) {
        if (file.exists() == false) {
            return;
        } else {
            if (file.isFile()) {
                file.delete();
                return;
            }
            if (file.isDirectory()) {
                File[] childFile = file.listFiles();
                if (childFile == null || childFile.length == 0) {
                    file.delete();
                    return;
                }
                for (File f : childFile) {
                    deleteFile(f);
                }
                // 这里不删除文件夹，在6.0以下系统会不能下载图片和显示
                //file.delete();
            }
        }
    }

    /**
     * 将Bitmap写入SD卡中
     *
     * @param bm
     * @param filesName 文件名称
     */
    public static void saveFile(Bitmap bm, String filesName) {
        saveBitmapToCard(filesName, bm);
    }

    public static InputStream getInputStream(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return new ByteArrayInputStream(baos.toByteArray());
    }

    /**
     * 对文件进行压缩
     * @param filePath 原文件路径
     * @return
     */
    public static InputStream getInputStream(String filePath) {
        InputStream inputStream = null;
        try {
            File file = new File(filePath);
            inputStream = new FileInputStream(file);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    /**
     * 保存头像到SD卡
     *
     * @param fileName
     * @param bm
     */
    public static File saveBitmapToCard(String fileName,
                                        Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        return inputstreamToFile(is, fileName);
    }

    /**
     * InputStream to file
     *
     * @param ins
     * @param fileName
     */
    public static File inputstreamToFile(InputStream ins, String fileName) {
        createMoreFiles(fileName);
        OutputStream os;
        try {
            os = new FileOutputStream(getSdCardPath() + fileName);
            int bytesRead;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return createFile(fileName);
    }

    /**
     * 获取照片InputStream
     */
    public static InputStream crieImage(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return new ByteArrayInputStream(baos.toByteArray());
    }

    /**
     * 调用此方法自动计算指定文件或指定文件夹的大小
     *
     * @param filePath
     *            文件路径
     * @return 计算好的带B、KB、MB、GB的字符串
     */
    public static String getAutoFileOrFilesSize(String filePath) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FormetFileSize(blockSize);
    }

    /**
     * 获取指定文件夹
     *
     * @param f
     * @return
     * @throws Exception
     */
    private static long getFileSizes(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSizes(flist[i]);
            } else {
                size = size + getFileSize(flist[i]);
            }
        }
        return size;
    }

    /**
     * 获取指定文件大小
     *
     * @return
     * @throws Exception
     */
    private static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis;
            fis = new FileInputStream(file);
            size = fis.available();
            fis.close();
        } else {
            file.createNewFile();
        }
        return size;
    }

    /**
     * 转换文件大小
     *
     * @param fileS
     * @return
     */
    private static String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString;
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    /**
     * 判断文件夹是否存在，不存在就新建
     * @param path
     */
    public static void createDirectory(String path){
        File file = new File(path);
        if(!file.exists()){
            file.mkdir();
        }
    }

}
