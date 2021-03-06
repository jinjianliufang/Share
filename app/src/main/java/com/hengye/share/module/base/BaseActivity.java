package com.hengye.share.module.base;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hengye.share.R;
import com.hengye.share.module.setting.SettingHelper;
import com.hengye.share.module.util.encapsulation.mvp.MvpPresenter;
import com.hengye.share.module.util.encapsulation.mvp.MvpView;
import com.hengye.share.module.util.encapsulation.view.listener.OnDoubleTapListener;
import com.hengye.share.ui.widget.common.CommonToolBar;
import com.hengye.share.util.NetworkUtil;
import com.hengye.share.util.RequestManager;
import com.hengye.share.util.ThemeUtil;
import com.hengye.skinloader.listener.OnSkinUpdateListener;

import java.util.HashSet;
import java.util.Set;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity
        implements OnSkinUpdateListener, MvpView {

    protected String getRequestTag() {
        return "BaseActivity";
    }

    /**
     * @return 如果为true, 则使用APP自定义的主题
     */
    protected boolean setCustomTheme() {
        return true;
    }

    protected boolean setToolBar() {
        return true;
    }

    protected boolean canSwipeBack() {
        return true;
    }

    protected boolean setFinishPendingTransition() {
        return true;
    }

    protected boolean observeNetworkChange() {
        return false;
    }

    protected boolean setBackground(){
        return true;
    }

    private int mThemeResId = 0;

    private SwipeBackHelper mSwipeHelper;

    private ActivityHelper mActivityHelper = new ActivityHelper();

    private Set<MvpPresenter> mPresenters;

    private Handler mHandler;

    private NetworkUtil.NetworkChangeReceiver mNetworkChangeReceiver;

    protected boolean mFirstClick = true;

    protected boolean mShowAnimationOnStart = true;

    private static BaseActivity mInstance;

    public ActivityHelper getActivityHelper() {
        return mActivityHelper;
    }

    public static BaseActivity getCurrentActivity() {
        if (mInstance != null && !mInstance.isOver()) {
            return mInstance;
        }
        return null;
    }

    /**
     * 兼容旧版的api
     * @return
     */
    public boolean isOver(){
        if(Build.VERSION.SDK_INT < 17){
            return mInstance.isFinishing();
        }else{
            return mInstance.isDestroyed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mInstance = this;
        mActivityHelper.dispatchActivityCreated(this, savedInstanceState);
        setCustomThemeIfNeeded(savedInstanceState);
//        SkinManager.getInstance().attach(this);
        super.onCreate(savedInstanceState);
        handleBundleExtra(getIntent());
        setupSwipeHelper();
        if (mSwipeHelper != null) {
            mSwipeHelper.onCreate();
        }

        setupContentView();
//        afterCreate(savedInstanceState);

//        final Bitmap cacheBitmap = ViewUtil.getDrawingCacheBitmap();
//        if(cacheBitmap != null) {
//            final ViewGroup rootView = (ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
//            final View maskView = new View(this);
//            maskView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//            rootView.addView(maskView);
//            cacheBitmap.setHasAlpha(true);
//            maskView.setBackground(new BitmapDrawable(cacheBitmap));
//            ViewUtil.setDrawingCacheBitmap(null);
//
//            getHandler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    maskView.setBackground(null);
//                    cacheBitmap.recycle();
//                }
//            }, 500);
//        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (mSwipeHelper != null) {
            mSwipeHelper.onPostCreate();
        }
    }

    @Override
    protected void onStart() {
        mActivityHelper.dispatchActivityStarted(this);
        super.onStart();
    }

    @Override
    protected void onResume() {
        mInstance = this;
        mActivityHelper.dispatchActivityResumed(this);
        super.onResume();
        resetFirstClick();
        replaceCustomThemeIfNeeded();
        observeNetworkChangeIfNeeded(true);
        if (mSwipeHelper != null) {
            mSwipeHelper.onResume();
        }
    }

    @Override
    protected void onPause() {
        mInstance = null;
        mActivityHelper.dispatchActivityPaused(this);
        super.onPause();
        observeNetworkChangeIfNeeded(false);
    }

    @Override
    protected void onStop() {
        mActivityHelper.dispatchActivityStopped(this);
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        mActivityHelper.dispatchActivitySaveInstanceState(this, outState);
        super.onSaveInstanceState(outState);
        outState.putInt("theme", mThemeResId);
    }

    @Override
    protected void onDestroy() {
        mActivityHelper.dispatchActivityDestroyed(this);
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        cancelPendingRequestsIfNeeded();
        detachMvpView();
//        SkinManager.getInstance().detach(this);
        mActivityHelper.clean();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mActivityHelper.dispatchActivityResulted(this, requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        if (mFirstClick) {
            mFirstClick = false;
            super.startActivityForResult(intent, requestCode, options);
            overridePendingTransitionOnStart();
        }
    }

    @Override
    public void onBackPressed() {
        if (!mActivityHelper.onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransitionOnFinish();
    }

    protected void handleBundleExtra(Intent intent) {
    }

    protected void setupContentView() {
        if (getLayoutResId() != 0) {
            setContentView(getLayoutResId());
        }
    }

    protected
    @LayoutRes
    int getLayoutResId() {
        return 0;
    }

    @Override
    public void setContentView(int layoutId) {
        setToolBarIfNeeded(View.inflate(this, layoutId, null));
        setBackgroundIfNeed();
    }

    @Override
    public void setContentView(View view) {
        setToolBarIfNeeded(view);
        setBackgroundIfNeed();
    }

    protected void overridePendingTransitionOnStart() {
        if (!isShowAnimationOnStart()) {
            setShowAnimationOnStart();
            return;
        }
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    protected void overridePendingTransitionOnFinish() {
        if (setFinishPendingTransition()) {
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }

    @Override
    public void setPresenter(MvpPresenter presenter) {
        if (mPresenters == null) {
            mPresenters = new HashSet<>();
        }
        mPresenters.add(presenter);
    }

    protected void detachMvpView() {
        if (mPresenters != null) {
            for (MvpPresenter presenter : mPresenters) {
                presenter.detachView();
            }
            mPresenters.clear();
        }
    }

    public void startActivity(Class<?> cls) {
        startActivity(new Intent(this, cls));
    }

    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(new Intent(this, cls), requestCode);
    }

    protected void cancelPendingRequestsIfNeeded() {
        if (getRequestTag() != null) {
            RequestManager.cancelPendingRequests(getRequestTag());
        }
    }

    protected void setCustomThemeIfNeeded(Bundle savedInstanceState) {
        if (setCustomTheme()) {
            if (savedInstanceState == null) {
                mThemeResId = SettingHelper.getAppThemeResId();
            } else {
                mThemeResId = savedInstanceState.getInt("theme");
            }
            setTheme(mThemeResId);
        }
    }

    protected void setBackgroundIfNeed(){
        if(setBackground()){
            View root = findViewById(android.R.id.content);
            if(root == null){
                root = findViewById(R.id.root_content);
            }
            if(root != null){
                root.setBackgroundColor(ThemeUtil.getBackgroundColor());
            }
        }
    }

    protected void replaceCustomThemeIfNeeded() {
        if (setCustomTheme() && mThemeResId != SettingHelper.getAppThemeResId()) {
            reStartActivity();
        }
    }

    @SuppressWarnings("ConstantConditions")
    protected void reStartActivity() {

//        ViewGroup rootView = (ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
//        rootView.buildDrawingCache();
//
//        ViewUtil.setDrawingCacheBitmap(Bitmap.createBitmap(rootView.getDrawingCache()));
        Intent intent = getIntent();
//        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
//        setHideAnimationOnStart();
        startActivity(intent);
    }

    protected void setToolBarIfNeeded(View view) {
        if (!setToolBar()) {
            super.setContentView(view);
        } else {
            super.setContentView(R.layout.activity_base);
            LinearLayout rootLayout = (LinearLayout) findViewById(R.id.layout_root);
            if (rootLayout == null) return;
            rootLayout.addView(view,
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            initToolbar();
        }
    }

    public void initToolbar() {
        mToolbar = (CommonToolBar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            mToolbar.setTitle(getToolbarTitle());
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onNavigationClick(v);
                }
            });
            mToolbar.addOnDoubleTapListener(new OnDoubleTapListener() {
                @Override
                public void onDoubleTap(View view) {
                    onToolbarDoubleClick(mToolbar);
                }
            });
        }
    }

    private CommonToolBar mToolbar;

    public CommonToolBar getToolbar() {
        return mToolbar;
    }

    public void updateToolbarTitle(String title) {
        if(mToolbar != null) {
            mToolbar.setTitle(title);
        }
    }

    public void updateToolbarTitle(@StringRes int resId) {
        updateToolbarTitle(getString(resId));
    }

    public void onNavigationClick(View v){
        onBackPressed();
    }

    public void onToolbarDoubleClick(Toolbar toolbar) {

    }

    protected CharSequence getToolbarTitle() {
        return getTitle();
    }

    protected void setupSwipeHelper() {
        if (mSwipeHelper == null) {
            if (canSwipeBack() && SettingHelper.isSwipeBack()) {
                mSwipeHelper = new SwipeBackHelper(this);
            }
        }
    }

    public void resetFirstClick() {
        mFirstClick = true;
    }

    public Handler getHandler() {
        if (mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper());
        }
        return mHandler;
    }

    protected void observeNetworkChangeIfNeeded(boolean isStart) {
        if (!observeNetworkChange()) {
            return;
        }

        if (isStart) {
            IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(getNetworkChangeReceiver(), intentFilter);
        } else {
            unregisterReceiver(getNetworkChangeReceiver());
        }
    }

    protected BroadcastReceiver getNetworkChangeReceiver() {
        if (mNetworkChangeReceiver == null) {
            mNetworkChangeReceiver = new NetworkUtil.NetworkChangeReceiver() {
                @Override
                public void onNetworkChange(boolean isConnected) {
                    BaseActivity.this.onNetworkChange(isConnected);
                }
            };
        }
        return mNetworkChangeReceiver;
    }

    protected void onNetworkChange(boolean isConnected) {

    }

    public boolean isShowAnimationOnStart() {
        return mShowAnimationOnStart;
    }

    public void setHideAnimationOnStart() {
        mShowAnimationOnStart = false;
    }

    public void setShowAnimationOnStart() {
        mShowAnimationOnStart = true;
    }

    @Override
    public void onSkinUpdate() {
    }
}
