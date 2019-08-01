package com.ybkj.videoaccess.mvp.testdata.bean;

/**
 * 赛程积分Bean
 *
 * Created by HH on 2018/1/20.
 */

public class ScheduleScoreBean {
    private String group;
    private String team;
    private int score;
    private int getBall;
    private int lastBall;
    private int winBall;
    private int winGames;
    private int drawGames;
    private int failGames;

    public ScheduleScoreBean(String group, String team, int score, int getBall,
                             int lastBall, int winBall, int winGames, int drawGames,
                             int failGames){
        this.group = group;
        this.team = team;
        this.score = score;
        this.getBall = getBall;
        this.lastBall = lastBall;
        this.winBall = winBall;
        this.winGames = winGames;
        this.drawGames = drawGames;
        this.failGames = failGames;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getGetBall() {
        return getBall;
    }

    public void setGetBall(int getBall) {
        this.getBall = getBall;
    }

    public int getLastBall() {
        return lastBall;
    }

    public void setLastBall(int lastBall) {
        this.lastBall = lastBall;
    }

    public int getWinBall() {
        return winBall;
    }

    public void setWinBall(int winBall) {
        this.winBall = winBall;
    }

    public int getWinGames() {
        return winGames;
    }

    public void setWinGames(int winGames) {
        this.winGames = winGames;
    }

    public int getDrawGames() {
        return drawGames;
    }

    public void setDrawGames(int drawGames) {
        this.drawGames = drawGames;
    }

    public int getFailGames() {
        return failGames;
    }

    public void setFailGames(int failGames) {
        this.failGames = failGames;
    }
}
