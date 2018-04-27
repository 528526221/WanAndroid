package com.xulc.wanandroid.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Date：2018/4/12
 * Desc：
 * Created by xuliangchun.
 */

public class KnowledgeSystem implements Serializable{
    private List<KnowledgeSystem> children;
    private int courseId;
    private int id;
    private String name;
    private int order;
    private int parentChapterId;
    private int visible;

    private String parentChapterName;

    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public List<KnowledgeSystem> getChildren() {
        return children == null?new ArrayList<KnowledgeSystem>():children;
    }

    public void setChildren(List<KnowledgeSystem> children) {
        this.children = children;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
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

    public void setOrder(int order) {
        this.order = order;
    }

    public int getParentChapterId() {
        return parentChapterId;
    }

    public void setParentChapterId(int parentChapterId) {
        this.parentChapterId = parentChapterId;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public String getParentChapterName() {
        return parentChapterName;
    }

    public void setParentChapterName(String parentChapterName) {
        this.parentChapterName = parentChapterName;
    }
}
