package com.ybkj.videoaccess.util;

import com.ybkj.videoaccess.R;
import com.ybkj.videoaccess.app.ActivityManager;
import com.ybkj.videoaccess.app.ConstantSys;
import com.ybkj.videoaccess.app.MyApp;
import com.ybkj.videoaccess.eventbus.EventBusEmpty;
import com.ybkj.videoaccess.mvp.data.bean.Member;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import static com.ybkj.videoaccess.app.ConstantSys.ConfigKey.PREFERENCE_SYSTEM_CONFIG;
import static com.ybkj.videoaccess.app.ConstantSys.PREFERENCE_USER_KEY_MONEY;
import static com.ybkj.videoaccess.app.ConstantSys.PREFERENCE_USER_KEY_NICK;

/**
 * 用户信息数据工具
 * <p>修改网络数据实现本地同步</p>
 * <p>修改网络数据实现页面同步更新</p>
 * <p>获取用户基本信息</p>
 * <p>退出登录以及登录成功的页面通知</p>
 * <p>
 * Created by HH on 2016/7/8.
 */
public class UserUtil {
    /**
     * 保存用户账号
     *
     * @param account
     */
    public static void setUserAccount(String account) {
        PreferencesUtils.getInstance(PREFERENCE_SYSTEM_CONFIG).putString(PREFERENCE_USER_KEY_NICK, account);
    }

    /**
     * 获取用户账号
     */
    public static String getUserAccount() {
        return PreferencesUtils.getInstance(PREFERENCE_SYSTEM_CONFIG).getString(PREFERENCE_USER_KEY_NICK);
    }

    /**
     * /**
     * 获取用户ID
     */
    public static int getUserId() {
        PreferencesUtils preferencesUtils = PreferencesUtils.getInstance();
        return preferencesUtils.getInt(ConstantSys.PREFERENCE_USER_KEY_ID);
    }

    /**
     * 获取用户余额
     */
    public static float getUserPrice() {
        PreferencesUtils preferencesUtils = PreferencesUtils.getInstance();
        List<Double> money = preferencesUtils.getDataList(PREFERENCE_USER_KEY_MONEY);
        if (money != null && money.size() >= 2) {
            return (float) (money.get(0) + money.get(1));
        } else {
            return 0f;
        }
    }

    /**
     * 获取用户头像地址
     * 从User Info里面拿，如果没有，拿本地。否则，用户没有设置头像
     *
     * @return
     */
    public static String getUserHead() {
        String headPath;
        PreferencesUtils preferencesUtils = PreferencesUtils.getInstance();
        headPath = preferencesUtils.getString(ConstantSys.PREFERENCE_USER_KEY_HEADIMG);
        if (headPath != null) {
            return headPath;
        }

        headPath = preferencesUtils.getString(ConstantSys.PREFERENCE_USER_KEY_HEADIMG_LOCAL);
        if (headPath != null) {
            return headPath;
        }

        return null;
    }

    /**
     * 获取用户信息，返回字符串类型
     *
     * @param key
     * @return
     */
    public static String getUserInfoForKeyStr(String key) {
        PreferencesUtils preferencesUtils = PreferencesUtils.getInstance();
        return preferencesUtils.getString(key);
    }

    /**
     * 从pre中读取user对象
     *
     * @return
     */
    public static Member getUserForPre() {
        PreferencesUtils preferencesUtils = PreferencesUtils.getInstance();
        if (!isLogin()) {
            //没有数据（退出登录的情况）
            return null;
        }

        Member user = new Member();
        user.setId(preferencesUtils.getInt(ConstantSys.PREFERENCE_USER_KEY_ID));
        user.setNickname(preferencesUtils.getString(ConstantSys.PREFERENCE_USER_KEY_NICK));
        user.setHeadImage(preferencesUtils.getString(ConstantSys.PREFERENCE_USER_KEY_HEADIMG));
        user.setToken(preferencesUtils.getString(ConstantSys.PREFERENCE_USER_KEY_TOKEN));
        user.setAccountNo(preferencesUtils.getString(ConstantSys.PREFERENCE_USER_KEY_MOBILE));
        user.setMoney(preferencesUtils.getDataList(PREFERENCE_USER_KEY_MONEY));
        user.setScore(preferencesUtils.getDataList(ConstantSys.PREFERENCE_USER_KEY_SCORE));

        return user;
    }

    /**
     * 用户登录成功以后执行的操作
     *
     * @param user
     */
    public static void loginSuccess(Member user) {
        //保存用户名称
        setUserAccount(user.getAccountNo());
        //将数据保存到SharePreferences
        UserUtil.saveUserToPre(user);
        //进行数据更新操作
        EventBus.getDefault().post(user);

        /**
         极光推送使用用户Id设置别名
         if (!PreferencesUtils.getInstance(ConstantSys.PREFERENCE_CONFIG).getBoolean(MyApp.getAppContext(), ConstantSys.PREFERENCE_JPUSH_ALIGAS)) {
         JpushUtil.setAlias(String.valueOf(UserUtil.getUserId()));
         }
         //JpushUtil.setTag();
         */
        /**
         * 登录环信后台
         new ChatPresenter(null).start(null, null);
         */
    }

    /**
     * 保存User对象进入pre
     *
     * @param user
     */
    public static void saveUserToPre(Member user) {
        if (user == null) {
            return;
        }

        PreferencesUtils preferencesUtils = PreferencesUtils.getInstance();
        preferencesUtils.putInt(ConstantSys.PREFERENCE_USER_KEY_ID, user.getId());
        preferencesUtils.putString(ConstantSys.PREFERENCE_USER_KEY_NICK, user.getNickname());
        preferencesUtils.putString(ConstantSys.PREFERENCE_USER_KEY_MOBILE, user.getAccountNo());
        preferencesUtils.putString(ConstantSys.PREFERENCE_USER_KEY_HEADIMG, user.getHeadImage());
        preferencesUtils.putString(ConstantSys.PREFERENCE_USER_KEY_TOKEN, user.getToken());
        preferencesUtils.setDataList(PREFERENCE_USER_KEY_MONEY, user.getMoney());
        preferencesUtils.setDataList(ConstantSys.PREFERENCE_USER_KEY_SCORE, user.getScore());

    }


    /**
     * 修改数据
     *
     * @param key
     * @param value
     * @param updateStatus 修改状态（-1表示不用页面同步，其他参照UpdateStatus常量表）
     */
    public static void updateInfo(String key, String value, int updateStatus) {
        PreferencesUtils preferencesUtils = PreferencesUtils.getInstance();
        if (preferencesUtils.getString(key) != null && preferencesUtils.getString(key).equals(value)) {
            //数据和原始数据相同
            return;
        } else {
            preferencesUtils.putString(key, value);
            if (updateStatus != -1) {
                EventBus.getDefault().post(new EventBusEmpty.UpdateStatusEvent(updateStatus, value));
            }
        }
    }


    /**
     * 获取用户昵称
     */
    public static String getUserNick() {
        PreferencesUtils preferencesUtils = PreferencesUtils.getInstance();
        if (preferencesUtils.getString(ConstantSys.PREFERENCE_USER_KEY_NICK) == null) {
            //没有昵称返回用户手机号
            String mobile = preferencesUtils.getString(ConstantSys.PREFERENCE_USER_KEY_MOBILE);
            if (mobile != null) {
                return CommonUtil.mobileFormat(mobile);
            }

            return preferencesUtils.getString(ConstantSys.PREFERENCE_USER_KEY_MOBILE);
        }
        return preferencesUtils.getString(ConstantSys.PREFERENCE_USER_KEY_NICK);
    }

    /**
     * 清空用户数据
     */
    public static void clearUser() {
        PreferencesUtils.getInstance().cleanFile();
    }

    /**
     * 是否登录或者清除了数据(不会跳转到登录页)
     *
     * @return false:没有登录或者用户清除了数据
     */
    public static boolean isLogin() {
        PreferencesUtils preferencesUtils = PreferencesUtils.getInstance();
        if (preferencesUtils.getInt(ConstantSys.PREFERENCE_USER_KEY_ID) == -1) {
            return false;
        }

        return true;
    }

    /**
     * 是否登录或者清除了数据
     *
     * @return false:没有登录或者用户清除了数据
     */
    public static boolean isLoginToLogin() {
        PreferencesUtils preferencesUtils = PreferencesUtils.getInstance();
        if (preferencesUtils.getInt(ConstantSys.PREFERENCE_USER_KEY_ID) == -1) {
            ToastUtil.showMsg(MyApp.getAppContext().getString(R.string.goods_need_login));
            IntentUtil.startToUserLoginActivity(ActivityManager.getInstance().getTopActivity());
            return false;
        }

        return true;
    }

    /**
     * 退出登录执行的操作
     */
    public static void exitLogin() {
        //清除与用户相关的数据
        clearUser();
        PreferencesUtils.getInstance().putString(ConstantSys.PREFERENCE_USER_KEY_TOKEN, null);
        //EventBus通知清除页面登录信息
        Member member = new Member();
        member.setId(-1);
        EventBus.getDefault().post(member);
    }
}
