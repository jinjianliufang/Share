package com.hengye.share.module.topicdetail;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.View;

import com.hengye.floatingactionbutton.FloatingActionsMenu;
import com.hengye.share.R;
import com.hengye.share.util.handler.TopicAdapterIdPager;
import com.hengye.share.util.handler.TopicIdHandler;
import com.hengye.share.module.util.encapsulation.base.DataHandler;
import com.hengye.share.module.util.encapsulation.base.Pager;
import com.hengye.share.model.Topic;
import com.hengye.share.model.TopicComment;
import com.hengye.share.model.greenrobot.TopicDraftHelper;
import com.hengye.share.module.profile.PersonalHomepageActivity;
import com.hengye.share.module.publish.TopicPublishActivity;
import com.hengye.share.module.topic.StatusFragment;
import com.hengye.share.ui.widget.dialog.DialogBuilder;
import com.hengye.share.ui.widget.fab.FabAnimator;
import com.hengye.share.module.util.encapsulation.view.listener.OnItemClickListener;
import com.hengye.share.util.ClipboardUtil;
import com.hengye.share.util.DataUtil;

import java.util.ArrayList;

public class TopicCommentFragment extends StatusFragment<TopicComment> implements TopicCommentMvpView, DialogInterface.OnClickListener {

    public static Bundle getBundle(Topic topic, boolean isComment) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("topic", topic);
        bundle.putBoolean("isComment", isComment);
        return bundle;
    }

    public static TopicCommentFragment newInstance(Topic topic, boolean isComment) {
        TopicCommentFragment fragment = new TopicCommentFragment();
        fragment.setArguments(getBundle(topic, isComment));
        return fragment;
    }

    TopicCommentAdapter mAdapter;
    TopicCommentPresenter mPresenter;
    Topic mTopic;
    boolean mIsComment;
    Dialog mTopicCommentDialog;
    int mCurrentPosition;
    TabLayout.Tab mTab;


    @Override
    public int getLoadingResId(){
        return R.layout.state_loading_top;
    }

    @Override
    public int getEmptyResId(){
        return R.layout.state_empty_top;
    }

    @Override
    public int getNoNetworkResId(){
        return R.layout.state_no_network_top;
    }

    @Override
    public int getServiceErrorResId() {
        return R.layout.state_service_error_top;
    }


    @Override
    protected void handleBundleExtra(Bundle bundle) {
        mTopic = (Topic) bundle.getSerializable("topic");
        mIsComment = bundle.getBoolean("isComment");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        setAdapter(mAdapter = new TopicCommentAdapter(getContext(), new ArrayList<TopicComment>()));
        mTopicPager = new TopicAdapterIdPager(mAdapter);
        mTopicPager.setForceRefresh(true);
        mHandler = new TopicIdHandler<>(mAdapter);

        addPresenter(mPresenter = new TopicCommentPresenter(this));

        onRefresh();

        initView();
        mTopicCommentDialog = mIsComment ? DialogBuilder.getTopicCommentDialog(getContext(), this) : DialogBuilder.getTopicRepostDialog(getContext(), this);
    }

    private void initView() {
        setRefreshEnable(false);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                final int id = view.getId();

//                为了显示波纹效果再启动
                getHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (id == R.id.iv_topic_avatar || id == R.id.tv_topic_username || id == R.id.tv_topic_description) {
                            TopicComment tc = mAdapter.getItem(position);
                            int childPosition = mAdapter.getActualItemPosition(position);
                            View startView = null;
                            View convertView = getRecyclerView().getChildAt(childPosition);
                            if (convertView != null && convertView.getTag() != null) {
                                TopicCommentAdapter.TopicCommentViewHolder tcv = (TopicCommentAdapter.TopicCommentViewHolder) convertView.getTag();
                                if (tcv != null) {
                                    startView = tcv.mTopicTitle.mAvatar;
                                }
                            }
                            if (startView == null) {
                                PersonalHomepageActivity.start(getActivity(), tc.getUserInfo());
                            } else {
                                PersonalHomepageActivity.start(getActivity(), startView, tc.getUserInfo());
                            }


                        } else {
                            mCurrentPosition = position;
                            mTopicCommentDialog.show();
                        }

                    }
                }, 100);
            }
        });

//        final AppBarLayout appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.appbar);
        final TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.tab);
        if (tabLayout != null) {
            mTab = tabLayout.getTabAt(mIsComment ? 0 : 1);
        }

        final FloatingActionsMenu actionsMenu = (FloatingActionsMenu) getActivity().findViewById(R.id.fab);
        if (actionsMenu != null) {
            FabAnimator
                    .create(actionsMenu)
                    .attachToRecyclerView(getRecyclerView())
                    .setCustomAnimator(new FabAnimator.CustomAnimator() {
                        @Override
                        public int getViewHeight() {
                            return actionsMenu.getAddButtion().getHeight();
                        }
                    });
        }

    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.loadWBCommentOrRepost(mTopic.getId(), mTopicPager.getFirstPage(), true, mIsComment);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        mPresenter.loadWBCommentOrRepost(mTopic.getId(), mTopicPager.getNextPage(), false, mIsComment);
    }

    @Override
    public void updateTotalCount(long totalCount) {
        if (mTab != null) {
            String str = String.format
                    (getString(mIsComment ? R.string.label_topic_comment_number : R.string.label_topic_repost_number)
                            , DataUtil.getCounter(totalCount));
            mTab.setText(str);
        }
    }

    TopicAdapterIdPager mTopicPager;
    TopicIdHandler<TopicComment> mHandler;

    @Override
    public DataHandler<TopicComment> getDataHandler() {
        return mHandler;
    }

    @Override
    public Pager getPager() {
        return mTopicPager;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        TopicComment topicComment = mAdapter.getItem(mCurrentPosition);
        if (topicComment == null) {
            return;
        }
        if (mIsComment) {
            switch (which) {
                case DialogBuilder.COMMENT_COPY:
                    ClipboardUtil.copyWBContent(topicComment.getContent());
                    break;
                case DialogBuilder.COMMENT_REPLY:
                    startActivity(TopicPublishActivity.getStartIntent(getContext(), TopicDraftHelper.getWBTopicDraftByCommentReply(mTopic, topicComment)));
                    break;
                case DialogBuilder.COMMENT_REPOST:
                    startActivity(TopicPublishActivity.getStartIntent(getContext(), TopicDraftHelper.getWBTopicDraftByCommentRepost(mTopic, topicComment)));
                    break;
            }

        } else {
            switch (which) {
                case DialogBuilder.REPOST_COPY:
                    ClipboardUtil.copyWBContent(topicComment.getContent());
                    break;
                case DialogBuilder.REPOST_DETAIL:
                    startActivity(TopicDetailActivity.getStartIntent(getContext(), topicComment.toTopic(), true));
                    break;
                case DialogBuilder.REPOST_REPOST:
                    startActivity(TopicPublishActivity.getStartIntent(getContext(), TopicDraftHelper.getWBTopicDraftByRepostRepost(topicComment)));
                    break;
                case DialogBuilder.REPOST_COMMENT:
                    startActivity(TopicPublishActivity.getStartIntent(getContext(), TopicDraftHelper.getWBTopicDraftByRepostComment(topicComment)));
                    break;
            }

        }

    }
}