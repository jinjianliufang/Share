package com.hengye.share.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.hengye.share.ui.base.BaseActivity;
import com.hengye.share.util.UserUtil;
import com.hengye.share.util.thirdparty.ParseTokenWeiboAuthListener;
import com.hengye.share.util.thirdparty.ThirdPartyUtils;
import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.sso.SsoHandler;

public class ThirdPartyLoginActivity extends BaseActivity {

    @Override
    protected boolean setCustomTheme() {
        return false;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        super.afterCreate(savedInstanceState);

        initData();

        try {
            mSsoHandler.authorize(ThirdPartyUtils.REQUEST_CODE_FOR_WEIBO, new WBAuthListener(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initData() {
        if (mWeiboAuth == null) {
            mWeiboAuth = ThirdPartyUtils.getWeiboData(this);
        }
        if (mSsoHandler == null) {
            mSsoHandler = new SsoHandler(this, mWeiboAuth);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ThirdPartyUtils.REQUEST_CODE_FOR_WEIBO && mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }


    /**
     * 微博 Web 授权类，提供登陆等功能
     */
    private WeiboAuth mWeiboAuth;

    /**
     * 注意：SsoHandler 仅当 SDK 支持 SSO 时有效
     */
    private SsoHandler mSsoHandler;

    class WBAuthListener extends ParseTokenWeiboAuthListener {

        @Override
        public void onComplete(Bundle values) {
            super.onComplete(values);
            if (mAccessToken != null && mAccessToken.isSessionValid()) {
                UserUtil.updateUser(mAccessToken);
//                mPresenter.loadWBUserInfo();
                setResult(Activity.RESULT_OK);
            }
            finish();
        }

        @Override
        public void onCancel() {
            super.onCancel();
            finish();
        }
    }
}