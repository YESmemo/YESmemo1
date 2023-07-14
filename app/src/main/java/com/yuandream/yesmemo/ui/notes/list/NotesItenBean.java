package com.yuandream.yesmemo.ui.notes.list;

import java.io.Serializable;

public class NotesItenBean implements Serializable {

    // 标题
    private String title;
    // 小标题
    private String title2;
    // 内容
    private String conent;
    // 编辑时间
    private String edittime;
    // 创建时间
    private String createtime;
    // 是否置顶
    private boolean pinned;

//    public NotesItenBean(String title, String title2, String conent, String edittime, String createtime, boolean pinned) {
//        this.title = title;
//        this.title2 = title2;
//        this.conent = conent;
//        this.edittime = edittime;
//        this.createtime = createtime;
//        this.pinned = pinned;
//    }


    @Override
    public String toString() {
        return "NotesItenBean{" +
                "title='" + title + '\'' +
                ", title2='" + title2 + '\'' +
                ", conent='" + conent + '\'' +
                ", edittime='" + edittime + '\'' +
                ", createtime='" + createtime + '\'' +
                ", pinned=" + pinned +
                '}';
    }

    public String getEdittime() {
        return edittime;
    }

    public void setEdittime(String edittime) {
        this.edittime = edittime;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle2() {
        return title2;
    }

    public void setTitle2(String title2) {
        this.title2 = title2;
    }

    public String getConent() {
        return conent;
    }

    public void setConent(String conent) {
        this.conent = conent;
    }

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }
}
