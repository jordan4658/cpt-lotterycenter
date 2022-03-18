package com.caipiao.task.server.pojo;

/**
 * 开奖记录
 */
public class LotterySGReocrd {
    private String game;

    private String issue;

    private String number;

    private String openStatus;

    private String time;

    public LotterySGReocrd() {
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOpenStatus() {
        return openStatus;
    }

    public void setOpenStatus(String openStatus) {
        this.openStatus = openStatus;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "LotterySGReocrd{" +
                "game='" + game + '\'' +
                ", issue='" + issue + '\'' +
                ", number='" + number + '\'' +
                ", openStatus='" + openStatus + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
