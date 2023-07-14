package com.yuandream.yesmemo.ui.plans.list;

import java.io.Serializable;
import java.util.Date;
import java.util.Timer;

public class PalnsItenBean implements Serializable {

    // 标题
    private String title;
    // 语音文件路径
    private String speechpath;
    // 提醒日期
    private Date remindertime;

    @Override
    public String toString() {
        return "PalnsItenBean{" +
                "title='" + title + '\'' +
                ", speechpath='" + speechpath + '\'' +
                ", remindertime=" + remindertime +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSpeechpath() {
        return speechpath;
    }

    public void setSpeechpath(String speechpath) {
        this.speechpath = speechpath;
    }

    public Date getRemindertime() {
        return remindertime;
    }

    public void setRemindertime(Date remindertime) {
        this.remindertime = remindertime;
    }
}
