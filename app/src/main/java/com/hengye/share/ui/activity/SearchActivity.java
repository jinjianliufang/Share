package com.hengye.share.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.hengye.share.adapter.recyclerview.TopicAdapter;
import com.hengye.share.model.Topic;
import com.hengye.share.model.UserInfo;
import com.hengye.share.ui.base.BaseActivity;
import com.hengye.share.R;
import com.hengye.share.adapter.recyclerview.SearchUserAdapter;
import com.hengye.share.ui.mvpview.SearchMvpView;
import com.hengye.share.ui.presenter.SearchPresenter;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity implements View.OnClickListener, SearchMvpView{

    @Override
    protected String getRequestTag() {
        return super.getRequestTag();
    }

    @Override
    protected boolean setCustomTheme() {
        return super.setCustomTheme();
    }

    @Override
    protected boolean setToolBar() {
        return false;
    }

    @Override
    protected boolean canSwipeBack() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);

        setupPresenter(mPresenter = new SearchPresenter(this));
        initView();
    }

    private ImageButton mBackBtn, mSearchBtn;
    private EditText mContent;
    private RecyclerView mUserRV, mTopicRV;

    private SearchUserAdapter mUserAdapter;
    private TopicAdapter mTopicAdapter;

    private SearchPresenter mPresenter;

    private void initView(){
        mBackBtn = (ImageButton) findViewById(R.id.btn_back);
        mBackBtn.setOnClickListener(this);
        mSearchBtn = (ImageButton) findViewById(R.id.btn_search);
        mSearchBtn.setOnClickListener(this);
        mContent = (EditText) findViewById(R.id.et_search);
        mUserRV = (RecyclerView) findViewById(R.id.recycler_view_user);
        mUserRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mUserRV.setAdapter(mUserAdapter = new SearchUserAdapter(this, new ArrayList<UserInfo>()));
        mUserRV.setItemAnimator(new DefaultItemAnimator());
        mTopicRV = (RecyclerView) findViewById(R.id.recycler_view_topic);
        mTopicRV.setAdapter(mTopicAdapter = new TopicAdapter(this, new ArrayList<Topic>()));
        mTopicRV.setLayoutManager(new LinearLayoutManager(this));
        mTopicRV.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.btn_back){
            onBackPressed();
        }else if(id == R.id.btn_search){
            mPresenter.loadWBSearchContent(mContent.getText().toString().trim());
        }
    }

    @Override
    public void handleSearchUserData(List<UserInfo> userInfos) {
        mUserAdapter.refresh(userInfos);
    }

    @Override
    public void handleSearchPublicData(List<Topic> topics) {
        mTopicAdapter.refresh(topics);
    }


}