package com.xulc.wanandroid.bean;

import java.util.List;

/**
 * Date：2018/4/16
 * Desc：
 * Created by xuliangchun.
 */

public class User {
    private List<Object> collectIds;
    private String email;
    private String icon;
    private String id;
    private String password;
    private int type;
    private String username;

    public List<Object> getCollectIds() {
        return collectIds;
    }

    public void setCollectIds(List<Object> collectIds) {
        this.collectIds = collectIds;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
