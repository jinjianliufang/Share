package com.hengye.share.module.accountmanage;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hengye.share.R;
import com.hengye.share.model.greenrobot.User;
import com.hengye.share.module.base.BaseActivity;
import com.hengye.share.module.sso.ThirdPartyLoginActivity;
import com.hengye.share.module.util.encapsulation.view.listener.OnItemClickListener;
import com.hengye.share.module.util.encapsulation.view.listener.OnItemLongClickListener;
import com.hengye.share.ui.widget.dialog.DialogBuilder;
import com.hengye.share.ui.widget.dialog.SimpleTwoBtnDialog;
import com.hengye.share.util.UserUtil;
import com.hengye.share.util.intercept.AdTokenInterceptor;
import com.hengye.share.util.thirdparty.ThirdPartyUtils;

import java.util.ArrayList;
import java.util.List;

public class AccountManageActivity extends BaseActivity implements AccountManageContract.View, DialogInterface.OnClickListener {

    public final static int ACCOUNT_CHANGE = 5;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_account_manage;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new AccountManagePresenter(this);
        initView();
    }

    private AccountManagePresenter mPresenter;
    private AccountManageAdapter mAdapter;
    private RecyclerView mRecyclerView;

    private User mSelectAccountOriginal;
    private boolean mHasSelectOriginalAccount;
    private int mSelectAccountNowIndex, mSelectAccountLongClickIndex;
    private View mSelectAccountBtn, mLogoutButton;
    private boolean mIsFirstTime = true;

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter = new AccountManageAdapter(this, new ArrayList<User>(), new AccountManageCallBack()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == mSelectAccountNowIndex) {
                    return;
                }
                if (mSelectAccountBtn != null) {
                    mSelectAccountBtn.setVisibility(View.GONE);
                }
                mSelectAccountBtn = view.findViewById(R.id.btn_check);
                if (mSelectAccountBtn != null) {
                    mSelectAccountBtn.setVisibility(View.VISIBLE);
                }
                mSelectAccountNowIndex = position;
            }
        });

        mAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(View view, int position) {
                mSelectAccountLongClickIndex = position;
                showLongClickDialog();
                return false;
            }
        });


        mLogoutButton = findViewById(R.id.btn_logout);
        updateLogoutBtn();
        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout(mAdapter.getItem(mSelectAccountNowIndex));
            }
        });
        mPresenter.loadUsers();
    }

    private void showLongClickDialog() {
        User user = mAdapter.getItem(mSelectAccountLongClickIndex);
        String authorize = getString(user.getAdToken() == null ?
                R.string.label_account_ad_authorize :
                R.string.label_account_ad_authorize_again);

        DialogBuilder
                .getItemDialog(this, this,
                authorize,
                getString(R.string.label_account_logout))
                .show();
    }

    private void updateLogoutBtn() {
        mLogoutButton.setVisibility(UserUtil.isUserEmpty() ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case 0:
            default:
                User user = mAdapter.getItem(mSelectAccountLongClickIndex);
                startActivityForResult(ThirdPartyLoginActivity.getAdTokenStartIntent(this, user.getUid()), ThirdPartyUtils.REQUEST_CODE_FOR_WEIBO);
                break;
            case 1:
                logout(mAdapter.getItem(mSelectAccountLongClickIndex));
                break;
        }
    }

    private void logout(User user) {
        if (user != null) {
            User accountSelectNow = mAdapter.getItem(mSelectAccountNowIndex);
            UserUtil.deleteUser(user);
            mAdapter.removeItem(user);
            //如果注销的是当前用户
            if (user.equals(accountSelectNow)) {
                if (!mAdapter.isEmpty()) {
                    mRecyclerView.getChildAt(0).performClick();
                } else {
                    mSelectAccountNowIndex = -1;
                }
            } else {
                mSelectAccountNowIndex = mAdapter.getItemPosition(accountSelectNow);
            }
        }
    }

    @Override
    public void loadSuccess(List<User> data, int currentUserIndex) {
        if (!mHasSelectOriginalAccount) {
            mHasSelectOriginalAccount = true;
            if (0 <= currentUserIndex && currentUserIndex < data.size()) {
                mSelectAccountOriginal = data.get(currentUserIndex);
            }
        }
        mSelectAccountNowIndex = currentUserIndex;
        mAdapter.refresh(data);

        if(!mIsFirstTime){
            new AdTokenInterceptor(this).start();
        }else{
            mIsFirstTime = false;
        }
    }

    @Override
    public void loadFail() {
        if (!mHasSelectOriginalAccount) {
            mHasSelectOriginalAccount = true;
        }

        mIsFirstTime = false;
    }

    private boolean isChangeAccount(User original, User now) {
        if (original == null && now == null) {
            return false;
        } else {
            return original == null || now == null || !original.equals(now);
        }
    }

    @Override
    public void finish() {
        User user = mAdapter.getItem(mSelectAccountNowIndex);
        if (isChangeAccount(mSelectAccountOriginal, user)) {
            setResult(Activity.RESULT_OK);
            UserUtil.changeCurrentUser(user);
        }
        super.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ThirdPartyUtils.REQUEST_CODE_FOR_WEIBO && resultCode == Activity.RESULT_OK) {
            mPresenter.loadUsers();
        }
        updateLogoutBtn();
    }

    public class AccountManageCallBack {
        public int getAccountSelectIndex() {
            return mSelectAccountNowIndex;
        }

        public void setAccountSelectBtn(View v) {
            mSelectAccountBtn = v;
        }
    }

    public static Dialog getLoginDialog(final Context context) {
        SimpleTwoBtnDialog stbd = new SimpleTwoBtnDialog();
        stbd.setContent(R.string.label_to_login);
        stbd.setPositiveButtonClickListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.startActivity(new Intent(context, AccountManageActivity.class));
            }
        });

        return stbd.create(context);
    }
}
