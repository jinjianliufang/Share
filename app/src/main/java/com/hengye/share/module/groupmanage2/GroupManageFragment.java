package com.hengye.share.module.groupmanage2;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.hengye.draglistview.DragSortListView;
import com.hengye.share.R;
import com.hengye.share.model.greenrobot.GroupList;
import com.hengye.share.module.base.BaseFragment;
import com.hengye.share.ui.widget.dialog.LoadingDialog;
import com.hengye.share.ui.widget.dialog.SimpleTwoBtnDialog;
import com.hengye.share.ui.widget.listview.DragSortListViewBuilder;
import com.hengye.share.util.CommonUtil;
import com.hengye.share.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuhy on 16/9/19.
 */
public class GroupManageFragment extends BaseFragment implements GroupManageMvpView {

    @Override
    public int getLayoutResId() {
        return R.layout.activity_group_manage;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addPresenter(mPresenter = new GroupManagePresenter(this));

        initView();

        mPresenter.loadGroupList();
    }

    private DragSortListView mDragSortListView;
    private GroupManageAdapter mAdapter;

    private Dialog mConfirmDialog, mLoadingDialog;

    private GroupManagePresenter mPresenter;

    private void initView(){
        setHasOptionsMenu(true);
        mDragSortListView = (DragSortListView) findViewById(R.id.drag_sort_list_view);
        mDragSortListView.setAdapter(mAdapter = new GroupManageAdapter(getContext(), new ArrayList<GroupList>()));
        DragSortListViewBuilder.build(mDragSortListView);

        initUpdateGroupOrderDialog();
    }

    public void initUpdateGroupOrderDialog() {
        mLoadingDialog = new LoadingDialog(getContext());

        SimpleTwoBtnDialog builder = new SimpleTwoBtnDialog();

        builder.setContent(getString(R.string.label_update_group_order));

        builder.setNegativeButtonClickListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPresenter.setIsGroupUpdate(false);
                dialog.dismiss();
                finish();
            }
        });
        builder.setPositiveButtonClickListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mLoadingDialog.show();
                mPresenter.updateGroupOrder(mAdapter.getData());
            }
        });
        mConfirmDialog = builder.create(getContext());
    }

    @Override
    public boolean onBackPressed() {
        mPresenter.checkGroupOrderIsChange(mAdapter.getData());
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.activity_group_manager_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_update) {
            mPresenter.loadGroupList(false);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void checkGroupOrder(boolean isChange) {
        if(isChange){
            mConfirmDialog.show();
        }else{
            finish();
        }
    }

    @Override
    public void finish() {
        if(mPresenter.isGroupUpdate()){
            setResult(Activity.RESULT_OK);
        }
        super.finish();
    }

    @Override
    public void loadSuccess() {

    }

    @Override
    public void loadFail() {

    }

    @Override
    public void handleGroupList(boolean isCache, List<GroupList> groupLists) {
        if(!CommonUtil.isEmpty(groupLists)){
            if(groupLists.get(0).getVisible() == -1){
                groupLists.remove(0);
            }

            if(!isCache){
                setResult(Activity.RESULT_OK);
            }
        }
        mAdapter.refresh(groupLists);
//        L.debug("wbGroups : {}", wbGroups.toString());
    }

    @Override
    public void updateGroupOrderCallBack(boolean isSuccess) {
        mLoadingDialog.dismiss();
        if(isSuccess){
            ToastUtil.showToast("更新成功");
            setResult(Activity.RESULT_OK);
            finish();
        }else{
            ToastUtil.showToast("更新失败");
        }
    }
}
