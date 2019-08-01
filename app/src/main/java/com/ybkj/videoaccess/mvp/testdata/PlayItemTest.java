package com.ybkj.videoaccess.mvp.testdata;

import java.util.ArrayList;
import java.util.List;

/**
 * 玩法Item测试
 * <p>
 * Created by HH on 2018/1/22.
 */

public class PlayItemTest {
    /**
     * 获取比分玩法
     *
     * @return
     */
    public static List<String> getScore() {
        List<String> scores = new ArrayList<>();
        scores.add("1:0");
        scores.add("2:0");
        scores.add("2:1");
        scores.add("3:0");
        scores.add("3:1");
        scores.add("3:2");
        scores.add("4:0");
        scores.add("4:1");
        scores.add("4:2");
        scores.add("5:0");
        scores.add("5:1");
        scores.add("5:2");
        scores.add("胜其他");
        scores.add("0:0");
        scores.add("1:1");
        scores.add("2:2");
        scores.add("3:3");
        scores.add("平其他");
        scores.add("0:1");
        scores.add("0:2");
        scores.add("1:2");
        scores.add("0:3");
        scores.add("1:3");
        scores.add("2:3");
        scores.add("0:4");
        scores.add("1:4");
        scores.add("2:4");
        scores.add("0:5");
        scores.add("1:5");
        scores.add("2:5");
        scores.add("负其他");
        return scores;
    }

    /**
     * 获取让0球玩法
     *
     * @return
     */
    public static List<String> getDrawWin() {
        List<String> scores = new ArrayList<>();
        scores.add("胜 19:00");
        scores.add("平 5.95");
        scores.add("负 1.10");
        return scores;
    }

    /**
     * 获取让+1球玩法
     *
     * @return
     */
    public static List<String> getWin1() {
        List<String> scores = new ArrayList<>();
        scores.add("胜 19:00");
        scores.add("平 5.95");
        scores.add("负 1.10");
        return scores;
    }

    /**
     * 获取半全场玩法
     *
     * @return
     */
    public static List<String> getAllWin() {
        List<String> scores = new ArrayList<>();
        scores.add("胜胜");
        scores.add("胜平");
        scores.add("胜负");
        scores.add("平胜");
        scores.add("平平");
        scores.add("平负");
        scores.add("负胜");
        scores.add("负平");
        scores.add("负负");
        return scores;
    }

    /**
     * 获取总进球玩法
     *
     * @return
     */
    public static List<String> getAllScore() {
        List<String> scores = new ArrayList<>();
        scores.add("0球 38.00");
        scores.add("1球 38.00");
        scores.add("2球 38.00");
        scores.add("3球 38.00");
        scores.add("4球 38.00");
        scores.add("5球 38.00");
        scores.add("6球 38.00");
        scores.add("7+球 38.00");
        return scores;
    }


}
