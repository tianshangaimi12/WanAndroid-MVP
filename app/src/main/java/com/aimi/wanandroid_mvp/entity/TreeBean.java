package com.aimi.wanandroid_mvp.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class TreeBean implements Parcelable {
    private List<TreeBean> children;
    private int courseId;
    private int id;
    private String name;
    private int order;
    private int parentChapterId;
    private boolean userControlSetTop;
    private int visible;

    public List<TreeBean> getChildren() {
        return children;
    }

    private void setChildren(List<TreeBean> children) {
        this.children = children;
    }

    public int getCourseId() {
        return courseId;
    }

    private void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    private void setOrder(int order) {
        this.order = order;
    }

    public int getParentChapterId() {
        return parentChapterId;
    }

    public void setParentChapterId(int parentChapterId) {
        this.parentChapterId = parentChapterId;
    }

    public boolean isUserControlSetTop() {
        return userControlSetTop;
    }

    public void setUserControlSetTop(boolean userControlSetTop) {
        this.userControlSetTop = userControlSetTop;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(children);
        dest.writeInt(courseId);
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(order);
        dest.writeInt(parentChapterId);
        dest.writeInt(userControlSetTop ? 0 : 1);
        dest.writeInt(visible);
    }

    public static final Creator<TreeBean> CREATOR = new Creator<TreeBean>() {
        @Override
        public TreeBean createFromParcel(Parcel source) {
            return new TreeBean(source);
        }

        @Override
        public TreeBean[] newArray(int size) {
            return new TreeBean[size];
        }
    };

    public TreeBean(Parcel source) {
        if (getChildren() == null) {
            setChildren(new ArrayList<>());
        }
        source.readTypedList(getChildren(), TreeBean.CREATOR);
        setCourseId(source.readInt());
        setId(source.readInt());
        setName(source.readString());
        setOrder(source.readInt());
        setParentChapterId(source.readInt());
        setUserControlSetTop(source.readInt() == 0);
        setVisible(source.readInt());
    }
}
