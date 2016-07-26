package com.hengye.share.ui.fragment.encapsulation;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hengye.share.R;
import com.hengye.share.ui.base.BaseFragment;

/**
 * Created by yuhy on 16/7/18.
 */
public abstract class ContentFragment extends BaseFragment {

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_content;
    }

    abstract public int getContentResId();

    public int getLoadingResId(){
        return R.layout.widget_loading;
    }

    public int getEmptyResId(){
        return R.layout.widget_empty;
    }

    public int getNoNetworkResId(){
        return R.layout.widget_no_network;
    }

    /**
     * 如果为true, 则把getEmptyResId等当做layoutId添加进父view, 否则当做viewId查找获得相应的view;
     * @return
     */
    public boolean isLayoutInflateMode(){
        return true;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        if(isLayoutInflateMode()){
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            ViewGroup vg = (ViewGroup) view;
            layoutInflater.inflate(getLoadingResId(), vg);
            layoutInflater.inflate(getEmptyResId(), vg);
            layoutInflater.inflate(getNoNetworkResId(), vg);
            layoutInflater.inflate(getContentResId(), vg);

            mLoading = vg.getChildAt(0);
            mEmpty = vg.getChildAt(1);
            mNoNetwork = vg.getChildAt(2);
            mContent = vg.getChildAt(3);
        }else{
            mLoading = findViewById(getLoadingResId());
            mEmpty = findViewById(getEmptyResId());
            mNoNetwork = findViewById(getNoNetworkResId());
            mContent = findViewById(getContentResId());
        }


        mViews.append(TYPE_LOADING, mLoading);
        mViews.append(TYPE_EMPTY, mEmpty);
        mViews.append(TYPE_NO_NETWORK, mNoNetwork);
        mViews.append(TYPE_CONTENT, mContent);

        initLoading();
        initEmpty();
        initNoNetwork();
        initContent(savedInstanceState);

        showContent();
    }

    private static final int TYPE_LOADING = 1;
    private static final int TYPE_EMPTY = 2;
    private static final int TYPE_NO_NETWORK = 3;
    private static final int TYPE_CONTENT = 4;

    private SparseArray<View> mViews = new SparseArray<>();
    private int[] mViewTypes = new int[]{TYPE_LOADING, TYPE_EMPTY, TYPE_NO_NETWORK, TYPE_CONTENT};
//    private ImageView mLoadingIV;
//    private AnimationDrawable mLoadingDrawable;
    private View mLoading, mEmpty, mNoNetwork, mContent;

    protected void initLoading(){

//        mLoadingIV = (ImageView) getLoading().findViewById(R.id.loading);
//        mLoadingDrawable = (AnimationDrawable) mLoadingIV.getDrawable();
    }

    protected void initEmpty(){}

    protected void initNoNetwork(){}

    protected void initContent(@Nullable Bundle savedInstanceState){}

    public void hideLoading(){
        mLoading.setVisibility(View.GONE);
//        mLoadingDrawable.stop();
    }

    public View getLoading() {
        return mLoading;
    }

    public View getNoNetwork() {
        return mNoNetwork;
    }

    public View getEmpty() {
        return mEmpty;
    }

    public View getContent() {
        return mContent;
    }

    public void showLoading() {
        showView(TYPE_LOADING);
//        if(mLoadingDrawable == null){
//            mLoadingDrawable = (AnimationDrawable) mLoadingIV.getDrawable();
//        }
//        mLoadingDrawable.start();
    }

    public void showNoNetwork() {
        showView(TYPE_NO_NETWORK);
    }

    public void showEmpty() {
        showView(TYPE_EMPTY);
    }

    public void showContent() {
        showView(TYPE_CONTENT);
    }

    public void showView(int type) {
        View view = mViews.get(type);

        if (view == null) {
            return;
        }

        for (int t : mViewTypes) {
            if (t == type) {
                continue;
            }

            View v = mViews.get(t);

            if (v == null) {
                continue;
            }

            if (t == TYPE_LOADING) {
                hideLoading();
            }else{
                v.setVisibility(View.GONE);
            }
        }
        view.setVisibility(View.VISIBLE);
    }
}
