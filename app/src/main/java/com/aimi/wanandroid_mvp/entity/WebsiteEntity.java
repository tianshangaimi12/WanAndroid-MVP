package com.aimi.wanandroid_mvp.entity;

import java.io.Serializable;

public class WebsiteEntity implements Serializable {
    private int id;
    private String link;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
