package com.hengye.share.module.groupmanage;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hengye.share.R;
import com.hengye.share.module.util.encapsulation.view.recyclerview.CommonAdapter;
import com.hengye.share.module.util.encapsulation.view.recyclerview.ItemViewHolder;
import com.hengye.share.module.status.StatusPresenter.StatusGroup;
import com.hengye.share.module.status.StatusPresenter.StatusType;
import com.hengye.share.module.base.ShareRecyclerFragment;
import com.hengye.share.util.SPUtil;
import com.hengye.share.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuhy on 16/9/8.
 */
public class GroupListFragment extends ShareRecyclerFragment<StatusGroup> {

    @Override
    protected boolean isShowScrollbars() {
        return true;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setAdapter(mAdapter = new GroupListAdapter(getContext(), new ArrayList<StatusGroup>()));
        mAdapter.setOnItemClickListener(this);
        mLayoutManager = (LinearLayoutManager)getRecyclerView().getLayoutManager();
        getRecyclerView().setScrollbarFadingEnabled(true);
        getPullToRefresh().setRefreshEnable(false);

        mMaxWidth = mDefaultOffset = ViewUtil.dp2px(175f);
        mMaxHeight = ViewUtil.dp2px(340f);
        mHalfScreenWidth = ViewUtil.getScreenWidth(getActivity()) / 2;

        //只保存最后一个用户的selectPosition;
        mLastSelectPosition = SPUtil.getInt("GroupListSelectPosition", 0);

    }

    @Override
    public void onPause() {
        super.onPause();
        //不在onPause保存preference会失败；
        SPUtil.putInt("GroupListSelectPosition", mAdapter.getSelectPosition());
    }


    private void adjustSize(){
        if(getView() == null){
            return;
        }

        // 设置列表的尺寸
        int width = mMaxWidth;
        if (width > mHalfScreenWidth) {
            width = mHalfScreenWidth;
        }

        if (mAdapter.getItemCount() > 7) {
            getView().setLayoutParams(new FrameLayout.LayoutParams(width, mMaxHeight));
        }else {
            getView().setLayoutParams(new FrameLayout.LayoutParams(width, FrameLayout.LayoutParams.WRAP_CONTENT));
        }
    }

    public void load(boolean isRefresh, boolean selected){
        List<StatusGroup> groups = StatusGroup.getAllStatusGroups();
        StatusGroup sg = new StatusGroup(StatusType.NONE);
        groups.add(sg);

        mAdapter.refresh(groups);
        adjustSize();

        if(isRefresh){
            mLastSelectPosition = 0;
        }else if(mLastSelectPosition < 0 || mLastSelectPosition >= mAdapter.getBasicItemCount()){
            mLastSelectPosition = 0;
        }
        mAdapter.setSelectPosition(mLastSelectPosition);

        if(selected) {
            selectGroupByPosition(mLastSelectPosition);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        super.onItemClick(view, position);

        boolean isSelected = true;
        if(position != mAdapter.getLastItemPosition()){
            //当为最后的位置时,选择分组管理,不设置选中效果
            isSelected = mAdapter.setSelectPosition(position);
        }
        if (isSelected){
            selectGroupByPosition(position);
        }
    }

    protected void selectGroupByPosition(int position){
        if (getActivity() instanceof OnGroupSelectedCallback) {
            ((OnGroupSelectedCallback) getActivity()).onGroupSelected(position, mAdapter.getItem(position));
        }
    }

    GroupListAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    int mMaxWidth, mMaxHeight, mDefaultOffset, mHalfScreenWidth;
    int mLastSelectPosition;

    public void scrollToSelectPosition() {
        mLayoutManager.scrollToPositionWithOffset(mAdapter.getSelectPosition(), mDefaultOffset);
    }

    public interface OnGroupSelectedCallback {

        void onGroupSelected(int position, StatusGroup group);

    }

    public static class GroupListAdapter extends CommonAdapter<StatusGroup> {

        public GroupListAdapter(Context context, List<StatusGroup> data) {
            super(context, data);
        }

        @Override
        public GroupListViewHolder onCreateBasicItemViewHolder(ViewGroup parent, int viewType) {
            return new GroupListViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_group_list, parent, false));
        }

        @Override
        public void onBindBasicItemView(ItemViewHolder holder, int position) {
            super.onBindBasicItemView(holder, position);
            holder.itemView.setBackgroundResource(isSelectPosition(position) ? R.drawable.ripple_grey : R.drawable.ripple_white);
        }

        public static class GroupListViewHolder extends ItemViewHolder<StatusGroup> {

            TextView text;

            public GroupListViewHolder(View v) {
                super(v);
                text = (TextView) findViewById(R.id.tv_text);
            }

            @Override
            public void bindData(Context context, StatusGroup statusGroup, int position) {
                text.setText(statusGroup.getName());

            }
        }
    }
}
