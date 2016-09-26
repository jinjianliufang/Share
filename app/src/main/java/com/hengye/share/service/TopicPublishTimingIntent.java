package com.hengye.share.service;

import android.content.Intent;

/**
 * Created by yuhy on 2016/9/26.
 */

public class TopicPublishTimingIntent extends Intent {

    public static final String ACTION = "com.hengye.share.ACTION_TIMING_PUBLISH";

    private long timing;

    public TopicPublishTimingIntent(long timing) {
        this.timing = timing;
        putExtra("timing", timing);
//        addFlags(FLAG_INCLUDE_STOPPED_PACKAGES);
        setAction(ACTION);
    }

    @Override
    public boolean filterEquals(Intent other) {
        if (other instanceof TopicPublishTimingIntent) {
            return ((TopicPublishTimingIntent) other).timing == timing;
        }
        return false;
    }
}
