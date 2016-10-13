package com.hengye.share.module.topic;

import com.hengye.share.model.Topic;
import com.hengye.share.module.mvp.MvpView;

import java.util.List;

public interface TopicMvpView extends MvpView {

    void onTaskComplete(boolean isRefresh, boolean isSuccess);

    void handleCache(List<Topic> data);

    void handleNoMoreTopics();

    void handleTopicData(List<Topic> data, boolean isRefresh);

}
