package com.hengye.share.ui.widget.common;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.hengye.share.R;
import com.hengye.share.helper.ReflectionHelpers;
import com.hengye.share.ui.widget.listener.OnDoubleClickListener;
import com.hengye.share.util.ThemeUtil;
import com.hengye.share.util.ViewUtil;

public class CommonToolBar extends Toolbar {

    long mLastClickTime;
    OnDoubleClickListener mOnDoubleClickListener;

    public CommonToolBar(Context context) {
        this(context, null);
    }

    public CommonToolBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, android.support.v7.appcompat.R.attr.toolbarStyle);
    }

    public CommonToolBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (isInEditMode()) {
            return;
        }
        init();
    }

    public void init() {
        this.setNavigationIcon(R.drawable.ic_arrow_back_white_48dp);
        if (getNavigationIcon() != null) {
            getNavigationIcon().setTint(ThemeUtil.getTintColor());
        }
        setBackgroundColor(ThemeUtil.getColor());
        setTitleTextColor(ThemeUtil.getTextColor());
        setSubtitleTextColor(ThemeUtil.getTextColor());
        if (getNavigation() != null) {
//            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) getNavigation().getLayoutParams();
            int actionBarHeight = ViewUtil.getActionBarHeight();
            int size = getResources().getDimensionPixelSize(R.dimen.icon_size_small);
//            lp.height = size;
//            lp.width = size;
//            lp.setMarginStart(actionBarHeight - size);
//            getNavigation().requestLayout();
            int padding = (actionBarHeight - size) / 2;
            getNavigation().setPadding(0, padding, 0, padding);
            getNavigation().setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
//        this.setNavigationOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (getContext() instanceof Activity) {
//                    ((Activity) getContext()).onBackPressed();
//                }
//            }
//        });
    }

    private static final int DOUBLE_TAP_TIMEOUT = ViewConfiguration.getDoubleTapTimeout();

    public boolean onTouchEvent(MotionEvent ev) {
        boolean handler = super.onTouchEvent(ev);
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (this.mLastClickTime != 0L && System.currentTimeMillis() - this.mLastClickTime <= DOUBLE_TAP_TIMEOUT) {
                performDoubleClick();
            }
            this.mLastClickTime = System.currentTimeMillis();
        }
        return handler;
    }

    public void performDoubleClick() {
        if (getOnDoubleClickListener() != null) {
            getOnDoubleClickListener().onDoubleClick(this);
        }
    }

    public OnDoubleClickListener getOnDoubleClickListener() {
        return mOnDoubleClickListener;
    }

    public void setOnDoubleClickListener(OnDoubleClickListener onDoubleClickListener) {
        this.mOnDoubleClickListener = onDoubleClickListener;
    }

    ImageButton mNavigation;

    public ImageButton getNavigation() {
        if (mNavigation != null) {
            return mNavigation;
        }

        ReflectionHelpers.callInstanceMethod(Toolbar.class, this, "ensureNavButtonView");

        Object obj = ReflectionHelpers.getField(this, "mNavButtonView");
        if (obj != null && obj instanceof ImageButton) {
            mNavigation = (ImageButton) obj;
            return mNavigation;
        }
        return null;
    }
}