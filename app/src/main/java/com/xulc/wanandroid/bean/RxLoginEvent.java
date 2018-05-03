package com.xulc.wanandroid.bean;

/**
 * Date：2018/5/2
 * Desc：
 * Created by xuliangchun.
 */

public class RxLoginEvent {
    private String eventMessage;

    public RxLoginEvent(String eventMessage) {
        this.eventMessage = eventMessage;
    }

    public String getEventMessage() {
        return eventMessage;
    }
}
