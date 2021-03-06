package com.hengye.share.module.search;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.hengye.share.R;
import com.hengye.share.model.UserInfo;
import com.hengye.share.module.base.ShareRecyclerFragment;
import com.hengye.share.module.profile.PersonalHomepageActivity;
import com.hengye.share.module.profile.UserAttentionContract;
import com.hengye.share.module.profile.UserAttentionPresenter;
import com.hengye.share.module.util.encapsulation.base.DefaultDataHandler;
import com.hengye.share.module.util.encapsulation.base.TaskState;
import com.hengye.share.module.util.encapsulation.view.listener.OnItemClickListener;
import com.hengye.share.ui.widget.recyclerview.DividerItemDecoration;
import com.hengye.share.util.CommonUtil;
import com.hengye.share.util.ResUtil;
import com.hengye.share.util.handler.StatusNumberPager;

import java.util.ArrayList;

public class SearchUserFragment extends ShareRecyclerFragment<UserInfo>
        implements SearchUserContract.View,
        UserAttentionContract.View {

    public static Bundle getStartBundle(String keywords, ArrayList<UserInfo> userInfos) {
        return getStartBundle(keywords, userInfos, false);
    }

    public static Bundle getStartBundle(String keywords, ArrayList<UserInfo> userInfos, boolean selectMode) {
        Bundle bundle = new Bundle();
        bundle.putString("keywords", keywords);
        bundle.putSerializable("userInfos", userInfos);
        bundle.putBoolean("selectMode", selectMode);
        return bundle;
    }

    private SearchUserAdapter mAdapter;
    private SearchUserContract.Presenter mPresenter;
    private UserAttentionContract.Presenter mUserAttentionPresenter;
    private StatusNumberPager mPager;
    private boolean mRequesting = false;

    private String mKeywords;
    private ArrayList<UserInfo> mUserInfos;
    private boolean mSelectMode;

    @Override
    public String getTitle() {
        return ResUtil.getString(R.string.title_activity_search_user);
    }

    @Override
    protected boolean isShowScrollbars() {
        return true;
    }

    @Override
    protected void handleBundleExtra(Bundle bundle) {
        super.handleBundleExtra(bundle);
        mKeywords = bundle.getString("keywords");
        mUserInfos = (ArrayList<UserInfo>) bundle.getSerializable("userInfos");
        mSelectMode = bundle.getBoolean("selectMode", false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getRecyclerView().addItemDecoration(new DividerItemDecoration(getContext()));
        setPager(mPager = new StatusNumberPager(1, 15));
        setAdapter(mAdapter = new SearchUserAdapter(getContext(), mUserInfos, mSelectMode), true);
        setDataHandler(new DefaultDataHandler<>(mAdapter));
        mPresenter = new SearchUserPresenter(this);
        mUserAttentionPresenter = new UserAttentionPresenter(this);

        //已经存在数据
        if (!CommonUtil.isEmpty(mUserInfos)) {
            mPager.handlePage(true);
        }

        if (mAdapter.getBasicItemCount() >= mPager.getPageSize()) {
            setLoadEnable(true);
        }

        if(mSelectMode){
            startRefresh();
        }

    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.searchWBUser(mKeywords, true, mPager.getFirstPage(), mPager.getPageSize());
    }

    @Override
    public void onLoad() {
        super.onLoad();
        mPresenter.searchWBUser(mKeywords, false, mPager.getNextPage(), mPager.getPageSize());
    }

    @Override
    public void onItemClick(View view, int position) {
        int id = view.getId();
        //选择模式
        if (mSelectMode) {
            if (id == R.id.iv_avatar) {
                PersonalHomepageActivity.start(getContext(),
                        view,
                        mAdapter.getItem(position));
            }else{
                //选择用户
                UserInfo userInfo = mAdapter.getItem(position);
                Intent intent = new Intent();
                intent.putExtra("user", userInfo);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        } else {
            if (id == R.id.layout_attention) {
                if (!mRequesting) {
                    mUserAttentionPresenter.followUser(mAdapter.getItem(position));
                }
            } else {
                PersonalHomepageActivity.start(getContext(),
                        view.findViewById(R.id.iv_avatar),
                        mAdapter.getItem(position));
            }
        }
    }

    @Override
    public void onFollowStart() {
        mRequesting = true;
    }

    @Override
    public void onFollowComplete(int taskState) {
        mRequesting = false;
        TaskState.toastFailState(taskState);
    }

    @Override
    public void onFollowSuccess(UserInfo userInfo) {
        mAdapter.notifyItemChanged(userInfo);
    }
}
