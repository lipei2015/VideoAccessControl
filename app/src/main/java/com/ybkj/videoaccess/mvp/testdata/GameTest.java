package com.ybkj.videoaccess.mvp.testdata;

import com.ybkj.videoaccess.mvp.testdata.bean.ScheduleBean;
import com.ybkj.videoaccess.mvp.testdata.bean.ScheduleScoreBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 比赛信息Test
 * <p>
 * Created by HH on 2018/1/20.
 */

public class GameTest {
    /**
     * 赛程测试数据
     *
     * @return
     */
    public static List<ScheduleBean> getSchedules() {

        List<ScheduleBean> list = new ArrayList<>();

        list.add(new ScheduleBean("俄罗斯", "巴西", "2017-10-20 04:03", "A组"));
        list.add(new ScheduleBean("俄罗斯", "巴西", "2017-10-20 04:03", "A组"));
        list.add(new ScheduleBean("俄罗斯", "巴西", "2017-10-20 04:03", "A组"));
        list.add(new ScheduleBean("俄罗斯", "巴西", "2017-10-20 04:03", "A组"));

        list.add(new ScheduleBean("俄罗斯", "巴西", "2017-10-20 04:03", "B组"));
        list.add(new ScheduleBean("俄罗斯", "巴西", "2017-10-20 04:03", "B组"));
        list.add(new ScheduleBean("俄罗斯", "巴西", "2017-10-20 04:03", "B组"));
        list.add(new ScheduleBean("俄罗斯", "巴西", "2017-10-20 04:03", "B组"));

        list.add(new ScheduleBean("俄罗斯", "巴西", "2017-10-20 04:03", "C组"));
        list.add(new ScheduleBean("俄罗斯", "巴西", "2017-10-20 04:03", "C组"));
        list.add(new ScheduleBean("俄罗斯", "巴西", "2017-10-20 04:03", "C组"));
        list.add(new ScheduleBean("俄罗斯", "巴西", "2017-10-20 04:03", "C组"));

        list.add(new ScheduleBean("俄罗斯", "巴西", "2017-10-20 04:03", "D组"));
        list.add(new ScheduleBean("俄罗斯", "巴西", "2017-10-20 04:03", "D组"));
        list.add(new ScheduleBean("俄罗斯", "巴西", "2017-10-20 04:03", "D组"));
        list.add(new ScheduleBean("俄罗斯", "巴西", "2017-10-20 04:03", "D组"));

        list.add(new ScheduleBean("俄罗斯", "巴西", "2017-10-20 04:03", "E组"));
        list.add(new ScheduleBean("俄罗斯", "巴西", "2017-10-20 04:03", "E组"));
        list.add(new ScheduleBean("俄罗斯", "巴西", "2017-10-20 04:03", "E组"));
        list.add(new ScheduleBean("俄罗斯", "巴西", "2017-10-20 04:03", "E组"));

        return list;
    }

    /**
     * 积分测试数据
     *
     * @return
     */
    public static List<ScheduleScoreBean> getGameScore() {

        List<ScheduleScoreBean> list = new ArrayList<>();

        list.add(new ScheduleScoreBean("A组", "俄罗斯", 14, 8, 2, 6, 5, 1, 2));
        list.add(new ScheduleScoreBean("A组", "俄罗斯", 14, 8, 2, 6, 5, 1, 2));
        list.add(new ScheduleScoreBean("A组", "俄罗斯", 14, 8, 2, 6, 5, 1, 2));
        list.add(new ScheduleScoreBean("A组", "俄罗斯", 14, 8, 2, 6, 5, 1, 2));

        list.add(new ScheduleScoreBean("B组", "俄罗斯", 14, 8, 2, 6, 5, 1, 2));
        list.add(new ScheduleScoreBean("B组", "俄罗斯", 14, 8, 2, 6, 5, 1, 2));
        list.add(new ScheduleScoreBean("B组", "俄罗斯", 14, 8, 2, 6, 5, 1, 2));
        list.add(new ScheduleScoreBean("B组", "俄罗斯", 14, 8, 2, 6, 5, 1, 2));

        list.add(new ScheduleScoreBean("C组", "俄罗斯", 14, 8, 2, 6, 5, 1, 2));
        list.add(new ScheduleScoreBean("C组", "俄罗斯", 14, 8, 2, 6, 5, 1, 2));
        list.add(new ScheduleScoreBean("C组", "俄罗斯", 14, 8, 2, 6, 5, 1, 2));
        list.add(new ScheduleScoreBean("C组", "俄罗斯", 14, 8, 2, 6, 5, 1, 2));

        list.add(new ScheduleScoreBean("D组", "俄罗斯", 14, 8, 2, 6, 5, 1, 2));
        list.add(new ScheduleScoreBean("D组", "俄罗斯", 14, 8, 2, 6, 5, 1, 2));
        list.add(new ScheduleScoreBean("D组", "俄罗斯", 14, 8, 2, 6, 5, 1, 2));
        list.add(new ScheduleScoreBean("D组", "俄罗斯", 14, 8, 2, 6, 5, 1, 2));

        list.add(new ScheduleScoreBean("E组", "俄罗斯", 14, 8, 2, 6, 5, 1, 2));
        list.add(new ScheduleScoreBean("E组", "俄罗斯", 14, 8, 2, 6, 5, 1, 2));
        list.add(new ScheduleScoreBean("E组", "俄罗斯", 14, 8, 2, 6, 5, 1, 2));
        list.add(new ScheduleScoreBean("E组", "俄罗斯", 14, 8, 2, 6, 5, 1, 2));

        return list;
    }
}
