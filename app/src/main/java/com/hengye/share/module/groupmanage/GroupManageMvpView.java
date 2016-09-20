package com.hengye.share.module.groupmanage;

import com.hengye.share.model.greenrobot.GroupList;
import com.hengye.share.module.mvp.MvpView;

import java.util.List;

public interface GroupManageMvpView extends MvpView {

    void loadSuccess();

    void loadFail();

    void handleGroupList(boolean isCache, List<GroupList> groupLists);

    void updateGroupOrderCallBack(boolean isSuccess);

    void checkGroupOrder(boolean isChange);
}