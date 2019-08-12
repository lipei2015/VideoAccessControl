package com.ybkj.videoaccess.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 * <p>
 * Created by HH on 2018/1/18
 */

public class DataUtil {
    /**
     * 时间日期格式化到年月日时分秒毫秒.
     */
    public static String dateFormatYMDHMSMS = "yyyy-MM-dd HH:mm:ss";

    public static String dateFormatYMDHMSMSString = "yyyyMMddHHmmss";

    /**
     * 时间日期格式化到年月日.
     */
    public static String dateFormatYMD = "yyyy-MM-dd";

    /**
     * long转换为Date类型
     *
     * @param currentTime 要转换的long类型的时间
     * @return yyyy-MM-dd HH:mm:ss
     * @throws ParseException
     */
    public static String getYMDHMS(long currentTime) {
        try {
            Date data = new Date(currentTime);
            return new SimpleDateFormat(dateFormatYMDHMSMS).format(data);
        } catch (Exception e) {
            return "未知时间";
        }
    }

    public static String getYMDHMSString(long currentTime) {
        try {
            Date data = new Date(currentTime);
            return new SimpleDateFormat(dateFormatYMDHMSMSString).format(data);
        } catch (Exception e) {
            return "未知时间";
        }
    }

    /**
     * long转换为Date类型
     *
     * @param currentTime 要转换的long类型的时间
     * @return yyyy-MM-dd
     * @throws ParseException
     */
    public static String getYMD(long currentTime) {
        try {
            Date data = new Date(currentTime);
            return new SimpleDateFormat(dateFormatYMD).format(data);
        } catch (Exception e) {
            return "未知时间";
        }
    }

    /**
     * 获取年月日,返回几天或者几分钟前
     *
     * @param msgTime
     * @return
     */
    public static String getDate(Long myTime, Long msgTime) {
        if (myTime == null || msgTime == null) {
            return "";
        }
        StringBuffer time = new StringBuffer();
        Date date = new Date(msgTime);
        Calendar msgCal = Calendar.getInstance();
        msgCal.setTime(date);
        int mYear = msgCal.get(Calendar.YEAR);
        int mMonth = msgCal.get(Calendar.MONTH) + 1;
        int mDate = msgCal.get(Calendar.DATE);
        int mHour = msgCal.get(Calendar.HOUR_OF_DAY);
        int mMinite = msgCal.get(Calendar.MINUTE);

        Calendar toCal = Calendar.getInstance();
        Date today = new Date(myTime);
        toCal.setTime(today);
        int tYear = toCal.get(Calendar.YEAR);
        int tMonth = toCal.get(Calendar.MONTH) + 1;
        int tDate = toCal.get(Calendar.DATE);
        int tHour = toCal.get(Calendar.HOUR_OF_DAY);
        int tMinite = toCal.get(Calendar.MINUTE);

        if (mYear == tYear && mMonth == tMonth && mDate == tDate) {
            if (tHour == mHour) {
                return ((tMinite - mMinite) <= 0 ? 1 : (tMinite - mMinite)) + "分钟前";
            } else {
                if (tHour - mHour == 1) {
                    // 分钟数
                    long mills = (toCal.getTimeInMillis() - msgCal.getTimeInMillis()) / (1000 * 60);
                    if (mills > 60) {
                        return "1小时前";
                    } else {
                        return (mills <= 0 ? 1 : mills) + "分钟前";
                    }
                } else {
                    return (tHour - mHour) + "小时前";
                }
            }
        } else {
            if (mYear == tYear) {
                if (mMonth == tMonth) {
                    if (tDate - mDate == 1) {
                        time.append("昨天");
                    } else {
                        time.append(getDoubleTime("" + mMonth) + "-"
                                + getDoubleTime("" + mDate));
                    }
                } else {
                    time.append(getDoubleTime("" + mMonth) + "-"
                            + getDoubleTime("" + mDate));
                }
            } else {
                time.append(mYear + "-" + getDoubleTime("" + mMonth) + "-"
                        + getDoubleTime("" + mDate));
            }
        }

        return time.toString();
    }

    public static String getDoubleTime(String time) {
        if (time.length() == 1) {
            return "0" + time;
        } else {
            return time;
        }
    }

    public static String getNewDate(String oldTime){
        try{
            return oldTime.substring(0,oldTime.indexOf("."));
        }catch (Exception e){
            return oldTime;
        }
    }
}
