package com.hengye.share.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.hengye.share.R;
import com.hengye.share.adapter.recyclerview.TopicAdapter;
import com.hengye.share.handler.data.DefaultDataHandler;
import com.hengye.share.handler.data.NumberPager;
import com.hengye.share.handler.data.TopicIdHandler;
import com.hengye.share.handler.data.TopicIdPager;
import com.hengye.share.handler.data.base.DataHandler;
import com.hengye.share.handler.data.base.DataType;
import com.hengye.share.handler.data.base.Pager;
import com.hengye.share.model.Topic;
import com.hengye.share.ui.base.BaseFragment;
import com.hengye.share.ui.fragment.encapsulation.paging.RecyclerRefreshFragment;
import com.hengye.share.ui.mvpview.TopicPageMvpView;
import com.hengye.share.ui.presenter.TopicPagePresenter;
import com.hengye.share.util.DataUtil;
import com.hengye.share.util.UserUtil;
import com.hengye.swiperefresh.PullToRefreshLayout;
import com.hengye.swiperefresh.listener.SwipeListener;

import java.util.ArrayList;
import java.util.List;

public class TopicPageFragment extends RecyclerRefreshFragment<Topic> implements TopicPageMvpView {

    public static TopicPageFragment newInstance(TopicPagePresenter.TopicGroup topicGroup, String keyword) {
        TopicPageFragment fragment = new TopicPageFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("topicGroup", topicGroup);
        bundle.putString("keyword", keyword);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static TopicPageFragment newInstance(TopicPagePresenter.TopicType topicType, String keyword) {
        return newInstance(new TopicPagePresenter.TopicGroup(topicType), keyword);
    }

    private TopicAdapter mAdapter;
    private TopicPagePresenter mPresenter;
    private TopicPagePresenter.TopicGroup topicGroup;

    private NumberPager mPager;
    private DefaultDataHandler<Topic> mHandler;
    private String mKeyword;

    @Override
    protected void handleBundleExtra() {
        topicGroup = (TopicPagePresenter.TopicGroup) getArguments().getSerializable("topicGroup");
        mKeyword = getArguments().getString("keyword");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setAdapter(mAdapter = new TopicAdapter(getContext(), new ArrayList<Topic>(), getRecyclerView()));
        mPager = new NumberPager();
        mHandler = new DefaultDataHandler<>(mAdapter);
        addPresenter(mPresenter = new TopicPagePresenter(this, topicGroup, mPager));
        mPresenter.setKeyword(mKeyword);
        setRefreshing(true);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        if (UserUtil.isUserEmpty()) {
            setRefreshing(false);
            return;
        }
        mPresenter.loadWBTopic(true);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if (!mAdapter.isEmpty()) {
            mPresenter.loadWBTopic(false);
        }
    }

    @Override
    public Pager getPager() {
        return mPager;
    }

    @Override
    public DataHandler<Topic> getDataHandler() {
        return mHandler;
    }

    public void refresh(String keyword) {
        if (keyword == null || TextUtils.isEmpty(keyword.trim())) {
            return;
        }
        mPresenter.setKeyword(keyword);
        setRefreshing(true);
    }

    @Override
    public void stopLoading(boolean isRefresh) {
        onTaskComplete();
    }

    @Override
    public void handleTopicData(List<Topic> data, boolean isRefresh) {
        int type = handleData(isRefresh, data);
        DataType.handleSnackBar(type, getParent(), data == null ? 0 : data.size());
    }

}
