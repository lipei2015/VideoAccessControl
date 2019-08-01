package com.ybkj.videoaccess.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ybkj.videoaccess.app.ConstantSys;
import com.ybkj.videoaccess.app.MyApp;

import java.util.ArrayList;
import java.util.List;

/**
 * Preferences存储工具类
 * <p>
 * Created by HH on 2018/1/19
 */
public class PreferencesUtils {
    public String preName;

    private PreferencesUtils(String name) {
        preName = name;
    }

    public static PreferencesUtils getInstance() {
        return new PreferencesUtils(ConstantSys.PREFERENCE_USER_NAME);
    }

    /**
     * @param name
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static PreferencesUtils getInstance(String name) {
        return new PreferencesUtils(name);
    }

    /**
     * put string preferences
     *
     * @param key   The name of the preference to modify
     * @param value The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public boolean putString(String key, String value) {
        SharedPreferences settings = MyApp.getAppContext().getSharedPreferences(preName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    /**
     * get string preferences
     *
     * @param key The name of the preference to retrieve
     * @return The preference value if it exists, or null. Throws ClassCastException if there is a preference with this
     * name that is not a string
     * @see #
     */
    public String getString(String key) {
        return getString(key, null);
    }

    /**
     * get string preferences
     *
     * @param key          The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     * this name that is not a string
     */
    public String getString(String key, String defaultValue) {
        SharedPreferences settings = MyApp.getAppContext().getSharedPreferences(preName, Context.MODE_PRIVATE);
        return settings.getString(key, defaultValue);
    }

    /**
     * put int preferences
     *
     * @param key   The name of the preference to modify
     * @param value The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public boolean putInt(String key, int value) {
        SharedPreferences settings = MyApp.getAppContext().getSharedPreferences(preName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    /**
     * get int preferences
     *
     * @param key The name of the preference to retrieve
     * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this
     * name that is not a int
     * @see #getInt(String, int)
     */
    public int getInt(String key) {
        return getInt(key, -1);
    }

    /**
     * get int preferences
     *
     * @param key          The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     * this name that is not a int
     */
    public int getInt(String key, int defaultValue) {
        SharedPreferences settings = MyApp.getAppContext().getSharedPreferences(preName, Context.MODE_PRIVATE);
        return settings.getInt(key, defaultValue);
    }

    /**
     * put long preferences
     *
     * @param key   The name of the preference to modify
     * @param value The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public boolean putLong(String key, long value) {
        SharedPreferences settings = MyApp.getAppContext().getSharedPreferences(preName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    /**
     * get long preferences
     *
     * @param key The name of the preference to retrieve
     * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this
     * name that is not a long
     * @see #getLong(String, long)
     */
    public long getLong(String key) {
        return getLong(key, -1);
    }

    /**
     * get long preferences
     *
     * @param key          The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     * this name that is not a long
     */
    public long getLong(String key, long defaultValue) {
        SharedPreferences settings = MyApp.getAppContext().getSharedPreferences(preName, Context.MODE_PRIVATE);
        return settings.getLong(key, defaultValue);
    }

    /**
     * put float preferences
     *
     * @param key   The name of the preference to modify
     * @param value The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public boolean putFloat(String key, float value) {
        SharedPreferences settings = MyApp.getAppContext().getSharedPreferences(preName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    /**
     * get float preferences
     *
     * @param key The name of the preference to retrieve
     * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this
     * name that is not a float
     * @see #getFloat(String, float)
     */
    public float getFloat(String key) {
        return getFloat(key, -1);
    }

    /**
     * get float preferences
     *
     * @param key          The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     * this name that is not a float
     */
    public float getFloat(String key, float defaultValue) {
        SharedPreferences settings = MyApp.getAppContext().getSharedPreferences(preName, Context.MODE_PRIVATE);
        return settings.getFloat(key, defaultValue);
    }

    /**
     * put boolean preferences
     *
     * @param key   The name of the preference to modify
     * @param value The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public boolean putBoolean(String key, boolean value) {
        SharedPreferences settings = MyApp.getAppContext().getSharedPreferences(preName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    /**
     * get boolean preferences, default is false
     *
     * @param key The name of the preference to retrieve
     * @return The preference value if it exists, or false. Throws ClassCastException if there is a preference with this
     * name that is not a boolean
     * @see #getBoolean(String, boolean)
     */
    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    /**
     * get boolean preferences
     *
     * @param key          The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     * this name that is not a boolean
     */
    public boolean getBoolean(String key, boolean defaultValue) {
        SharedPreferences settings = MyApp.getAppContext().getSharedPreferences(preName, Context.MODE_PRIVATE);
        return settings.getBoolean(key, defaultValue);
    }

    /**
     * 保存List
     *
     * @param key
     * @param datalist
     */
    public <T> boolean setDataList(String key, List<T> datalist) {
        if (null == datalist || datalist.size() <= 0)
            return false;

        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(datalist);
        return putString(key, strJson);
    }

    /**
     * 获取List
     *
     * @param key
     * @return
     */
    public <T> List<T> getDataList(String key) {
        List<T> datalist = new ArrayList<T>();
        String strJson = getString(key, null);
        if (null == strJson) {
            return datalist;
        }
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, new TypeToken<List<T>>() {
        }.getType());
        return datalist;
    }

    /**
     * clean this preferences file for name is preName;
     */
    public void cleanFile() {
        SharedPreferences settings = MyApp.getAppContext().getSharedPreferences(ConstantSys.PREFERENCE_USER_NAME, Context.MODE_PRIVATE);
        settings.edit().clear().commit();
    }

}