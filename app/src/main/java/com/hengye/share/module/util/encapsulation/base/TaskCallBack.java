package com.hengye.share.module.util.encapsulation.base;

/**
 * Created by yuhy on 2016/10/20.
 */

public interface TaskCallBack {

    void onTaskStart();

    void onTaskComplete(int taskState);
}
