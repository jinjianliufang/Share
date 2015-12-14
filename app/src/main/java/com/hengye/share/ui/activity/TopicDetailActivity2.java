package com.hengye.share.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.hengye.share.BaseActivity;
import com.hengye.share.R;
import com.hengye.share.adapter.RecyclerViewCommentAdapter;
import com.hengye.share.adapter.RecyclerViewTopicAdapter;
import com.hengye.share.module.Topic;
import com.hengye.share.ui.fragment.TopicCommentFragment;
import com.hengye.swiperefresh.PullToRefreshLayout;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

public class TopicDetailActivity2 extends BaseActivity {

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

    public static Intent getIntentToStart(Context context, Topic topic) {
        Intent intent = new Intent(context, TopicDetailActivity2.class);
        intent.putExtra(Topic.class.getSimpleName(), topic);
        return intent;
    }

    Topic mTopic;

    @Override
    protected void handleBundleExtra() {
        mTopic = (Topic) getIntent().getSerializableExtra(Topic.class.getSimpleName());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_topic_detail);

        initTopicView();
        initView();


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private PullToRefreshLayout mPullToRefreshLayout;
    private RecyclerViewCommentAdapter mAdapter;
    private ViewPager mViewPager;

    private Oauth2AccessToken mWBAccessToken;

    class CommentFragmentPageAdapter extends FragmentPagerAdapter{

        public CommentFragmentPageAdapter(){
            super(getSupportFragmentManager());
        }

        @Override
        public Fragment getItem(int position) {
            TopicCommentFragment fragment = TopicCommentFragment.newInstance(mTopic.getId());
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position == 0){
                return getString(R.string.title_page_comment);
            }else if(position == 1){
                return getString(R.string.title_page_repost);
            }else{
                return null;
            }
        }
    }

    private void initTopicView(){
        RecyclerViewTopicAdapter.TopicViewHolder topicViewHolder = new RecyclerViewTopicAdapter.TopicViewHolder(findViewById(R.id.item_topic));
        topicViewHolder.bindData(this, mTopic);
    }

    private void initView() {
        if (mTopic == null) {
            return;
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        mViewPager.setAdapter(new CommentFragmentPageAdapter());
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabsFromPagerAdapter(mViewPager.getAdapter());

//        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(mAdapter = new RecyclerViewCommentAdapter(this, new ArrayList<TopicComment>(), mTopic));
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//
//        mWBAccessToken = SPUtil.getSinaAccessToken();
//        mPullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.pull_to_refresh);
//        mPullToRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                if (mWBAccessToken == null || TextUtils.isEmpty(mWBAccessToken.getToken())) {
//                    mPullToRefreshLayout.setRefreshing(false);
//                    return;
//                }
//
//                RequestManager.addToRequestQueue(getWBCommentRequest(mWBAccessToken.getToken(), mTopic.getId(), "0", true), getRequestTag());
////                if (!CommonUtil.isEmptyCollection(mAdapter.getData())) {
////                    String id = mAdapter.getData().get(0).getId();
////                    RequestManager.addToRequestQueue(getWBTopicIdsRequest(mWBAccessToken.getToken(), id), getRequestTag());
////                }else{
////                    RequestManager.addToRequestQueue(getWBTopicRequest(mWBAccessToken.getToken(), 0 + "", true), getRequestTag());
////                }
//            }
//        });
//        mPullToRefreshLayout.setOnLoadListener(new PullToRefreshLayout.OnLoadListener() {
//            @Override
//            public void onLoad() {
//                if (!CommonUtil.isEmptyCollection(mAdapter.getData())) {
//                    String id = CommonUtil.getLastItem(mAdapter.getData()).getId();
//                    RequestManager.addToRequestQueue(getWBCommentRequest(mWBAccessToken.getToken(), mTopic.getId(), id, false), getRequestTag());
//                } else {
//                    mPullToRefreshLayout.setLoading(false);
//                    mPullToRefreshLayout.setLoadEnable(false);
//                }
//            }
//        });
//
//        mAdapter.setOnItemClickListener(new ViewUtil.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
////                Toast.makeText(MainActivity.this, "click item : " + position, Toast.LENGTH_SHORT).show();
//            }
//        });
    }


}
