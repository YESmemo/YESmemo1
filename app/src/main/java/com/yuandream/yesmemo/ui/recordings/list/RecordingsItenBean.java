package com.yuandream.yesmemo.ui.recordings.list;

import java.io.Serializable;
import java.util.Date;

public class RecordingsItenBean implements Serializable {

    // 标题
    private String title;
    // 录音文件路径
    private String recordingpath;

    @Override
    public String toString() {
        return "RecordingsItenBean{" +
                "title='" + title + '\'' +
                ", recordingpath='" + recordingpath + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRecordingpath() {
        return recordingpath;
    }

    public void setRecordingpath(String recordingpath) {
        this.recordingpath = recordingpath;
    }
}
