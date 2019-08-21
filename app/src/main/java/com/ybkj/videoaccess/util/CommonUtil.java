package com.ybkj.videoaccess.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * 各种公共工具类
 *
 * @author HH
 */
public class CommonUtil {

    /**
     * 判断网络连接情况
     *
     * @return : true:有网络  false:没有网络
     */
    public static boolean isNetwork(Context context) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo != null) {
            return mNetworkInfo.isAvailable();
        }

        return false;
    }

    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e("WifiPreferenceIpAddress", ex.toString());
            Log.e("SocketException", ex.toString());
        }
        return null;
    }

    public static String getIPAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE || info.getType() == ConnectivityManager.TYPE_ETHERNET) {//当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
                return ipAddress;
            }
        } else {
            //当前无网络连接,请在设置中打开网络
        }
        return null;
    }

    /**
     * 重启设备
     * @param context
     */
    public static void RebootDevice(Context context){
        try {
            PowerManager pManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            pManager.reboot(null);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }


    /**
     * 判别手机是否为正确手机号码；
     *
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkMobile(String mobile) {
        //return mobile.matches("[1][34578]\\d{9}");
        return mobile.matches("[1][345789]\\d{9}");
        //return mobile.matches("^1[0-9]{10}$");
    }

    /**
     * 判别姓名是否合法；
     *
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkName(String name) {
        return name.matches("^(([\\u4e00-\\u9fa5]{2,16})|(\\w{2,16}))$");
    }

    /**
     * 验证密码格式
     *
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkPassword(String password) {
        return isMatch("^[0-9a-zA-Z_]{6,12}(?<!_)$", password);
    }

    /**
     * 验证汉字
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isZh(CharSequence input) {
        return isMatch("^[\\u4e00-\\u9fa5]+$", input);
    }

    /**
     * 验证用户名
     * 取值范围为a-z,A-Z,0-9,"_",不能以"_"结尾,用户名必须是7-16位
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isUserName(CharSequence input) {
        return isMatch("^[0-9a-zA-Z_]{7,16}(?<!_)$", input);
    }

    /**
     * 校验银行卡卡号
     */
    public static boolean checkBankCard(String cardId) {
        return isMatch("^(\\d{16}|\\d{18}|\\d{19})$", cardId);
    }

    /**
     * 判断是否匹配正则
     *
     * @param regex 正则表达式
     * @param input 要匹配的字符串
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    private static boolean isMatch(String regex, CharSequence input) {
        return input != null && input.length() > 0 && Pattern.matches(regex, input);
    }

    /**
     * 验证密码格式
     *
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkVerifyCode(String verifyCode) {
        return verifyCode.length() == 5;
    }

    /**
     * 格式化手机号码
     *
     * @return string： 183****0000
     */
    public static String mobileFormat(String mobile) {
        if (checkMobile(mobile)) {
            return mobile.substring(0, 3) + "****" + mobile.substring(7, mobile.length());
        }

        return "";
    }

    /**
     * 格式化银行卡号
     *
     * @param carkNum
     * @return
     */
    public static String cardNumFormat(String carkNum) {
        if (TextUtils.isEmpty(carkNum)) return null;
        StringBuilder sb = new StringBuilder(carkNum);
        for (int i = 4; i <= sb.length() - 1; i += 5) {
            sb.insert(i, " ");
        }
        return sb.toString();
    }

    /**
     * 获取应用程序当前的版本号
     */
    public static String getVersionCode(Context context) {
        String versionCode;
        try {
            versionCode = String.valueOf(context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionCode);
        } catch (PackageManager.NameNotFoundException ex) {
            versionCode = "版本解析出现问题";
        }
        return versionCode;
    }

    /**
     * 获取应用名称
     */
    public static String getVersionName(Context context) {
        String name = "";
        try {
            name = context.getPackageManager().getPackageInfo(context.getPackageName(),
                    0).versionName;
        } catch (PackageManager.NameNotFoundException ex) {
            name = "";
        }
        return name;
    }

    /**
     * 隐藏软键盘
     */
    public static void hiddenSoftInput(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != activity.getCurrentFocus()) {
            imm.hideSoftInputFromWindow(activity.getCurrentFocus()
                    .getWindowToken(), 0);
        }
    }

    /**
     * 显示软键盘
     *
     * @param context
     * @param editText
     */
    public static void openSoftInput(Context context, View editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            editText.requestFocus();
            imm.showSoftInput(editText, 0);
        }
    }

    /**
     * 显示软键盘
     *
     * @param activity
     * @param view
     */
    public static void showSoftInput(Activity activity, View view) {
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, 0);
    }

    /**
     * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1
     *
     * @param s 需要得到长度的字符串
     * @return int 得到的字符串长度
     */
    public static int length(String s) {
        if (s == null) {
            return 0;
        }
        char[] c = s.toCharArray();
        int len = 0;
        for (int i = 0; i < c.length; i++) {
            len++;
            if (!isLetter(c[i])) {
                len++;
            }
        }
        return len;
    }

    public static boolean isLetter(char c) {
        int k = 0x80;
        return c / k == 0 ? true : false;
    }

    /**
     * double转String,保留小数点后两位
     */
    public static String doubleToString(double num) {
        //使用0.00不足位补0，#.##仅保留有效位
        return new DecimalFormat("0.00").format(num);
    }

    /**
     * 去掉多余的0
     *
     * @param num
     * @return
     */
    public static String getFloatFormat(float num) {
        if (num == 0f) {
            return "0";
        }

        String numStr = String.valueOf(new DecimalFormat("0.000").format(num));
        int flag = numStr.indexOf(".");
        for (int i = numStr.length() - 1; i > flag; i--) {
            if (numStr.charAt(i) != '0') {
                return numStr.substring(0, i + 1);
            }
        }

        return numStr.substring(0, flag);
    }

    /**
     * 去掉多余的0
     *
     * @param num
     * @return
     */
    public static String getFloatFormat(float num, int sizeZero) {
        if (sizeZero < 0) {
            return null;
        }

        String str = "0.";
        for (int i = 0; i < sizeZero; i++) {
            str += "0";
        }

        String numStr = String.valueOf(new DecimalFormat(str).format(num));
        int flag = numStr.indexOf(".");
        for (int i = numStr.length() - 1; i > flag; i--) {
            if (numStr.charAt(i) != '0') {
                return numStr.substring(0, i + 1);
            }
        }

        return numStr.substring(0, flag);
    }

    /**
     * 去掉多余的0
     *
     * @param num
     * @return
     */
    public static String getDoubleFormat(double num, int sizeZero) {
        if (sizeZero < 0) {
            return null;
        }

        String str = "0.";
        for (int i = 0; i < sizeZero; i++) {
            str += "0";
        }

        String numStr = String.valueOf(new DecimalFormat(str).format(num));
        int flag = numStr.indexOf(".");
        for (int i = numStr.length() - 1; i > flag; i--) {
            if (numStr.charAt(i) != '0') {
                return numStr.substring(0, i + 1);
            }
        }

        return numStr.substring(0, flag);
    }

    /**
     * 获取manifest中的pacakage属性
     */
    public static String getPackageName(Context ctx) {
        String packageName = "";
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), 0);
            packageName = pi.packageName;
            if (packageName == null || packageName.length() <= 0) {
                return "";
            }

        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return packageName;
    }

    /**
     * 获取设备ID(Android设备的唯一标识)
     */
    public static String getDeviceId(Context context) {
        StringBuilder deviceId = new StringBuilder();
        // 应用标志
        deviceId.append("ybLottery");

        //序列号（sn）
        String sn = Build.SERIAL;
        if (!TextUtils.isEmpty(sn) && !sn.contains("*")) {
            deviceId.append("sn");
            deviceId.append(sn);
            return deviceId.toString();
        }

        // ANDROID_ID是设备第一次启动时产生和存储的64bit的一个数
        String androidId = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        if (!TextUtils.isEmpty(androidId) && !"9774d56d682e549c".equals(androidId)
                && !androidId.contains("*")) {
            deviceId.append("androidId");
            deviceId.append(androidId);
            return deviceId.toString();
        }

        //如果上面都没有， 则生成一个uuid：随机码
        String uuid = getUUID(context);
        if (!TextUtils.isEmpty(uuid)) {
            deviceId.append("uuid");
            deviceId.append(uuid);
            return deviceId.toString();
        }

        return deviceId.toString();
    }


    /**
     * 获取手机的型号
     */
    public static String getAndroidModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 得到全局唯一UUID,当重新安装app时该ID会改变
     */
    private static final String PREFS_FILE = "device_id.xml";
    private static final String PREFS_DEVICE_ID = "device_id";

    public static String getUUID(Context context) {
        SharedPreferences mShare = context.getSharedPreferences(PREFS_FILE, 0);
        String uuid = "";
        if (mShare != null) {
            uuid = mShare.getString(PREFS_DEVICE_ID, "");
        }
        if (TextUtils.isEmpty(uuid)) {
            uuid = UUID.randomUUID().toString();
            mShare.edit().putString(PREFS_DEVICE_ID, uuid.toString()).commit();
        }
        return uuid;
    }

    /**
     * 数组转化为字符串
     *
     * @param array
     * @return
     */
    public static String arrayToStr(Integer[] array) {
        StringBuffer sb = new StringBuffer();
        for (int i : array) {
            if (i < 10) {
                sb.append("0" + i + " ");
            } else {
                sb.append(i + " ");
            }
        }

        if (sb.length() > 0) {
            //去掉结尾的空格
            return sb.substring(0, sb.length() - 1);
        }

        return sb.toString();
    }

    /**
     * 判断字符串是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            System.out.println(str.charAt(i));
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 开奖号码列表返回字符串
     *
     * @param lotteryNum
     * @return
     */
    public static String lotteryNumFormat(List<String> lotteryNum) {
        if (lotteryNum == null) {
            return "";
        }

        StringBuffer sb = new StringBuffer();
        for (String num : lotteryNum) {
            sb.append(num + " ");
        }

        return sb.substring(0, sb.length() - 1);
    }
}
