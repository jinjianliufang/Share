package com.hengye.share.module.util.encapsulation;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.hengye.share.R;
import com.hengye.share.ui.widget.common.CommonViewPager;

/**
 * Created by yuhy on 16/7/18.
 */
public abstract class ViewPagerFragment extends ContentFragment implements ViewPager.OnPageChangeListener {

    @Override
    public int getContentResId() {
        return R.layout.fragment_viewpager;
    }

    @Override
    public void onViewCreated(View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
            setUpViewPager(savedInstanceState);
    }

    protected
    @IdRes
    int getViewPagerId() {
        return R.id.view_pager;
    }

    protected CommonViewPager findViewPager() {
        CommonViewPager viewPager = (CommonViewPager) findViewById(getViewPagerId());
        if (viewPager == null) {
            viewPager = (CommonViewPager) getActivity().findViewById(getViewPagerId());
        }
        return viewPager;
    }

    public CommonViewPager getViewPager() {
        return mViewPager;
    }

    public PagerAdapter getAdapter(){
        return mViewPager.getAdapter();
    }

    private CommonViewPager mViewPager;

    private int mCurrentPosition = 0;

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mCurrentPosition = mViewPager.getCurrentItem();
        outState.putInt("position", mCurrentPosition);
    }

    protected void setUpViewPager(Bundle savedInstanceState) {

        mViewPager = findViewPager();
        mCurrentPosition = savedInstanceState == null ? getDefaultSelectPosition() : savedInstanceState.getInt("position");

        mViewPager.setOffscreenPageLimit(getOffscreenPageLimit());

        if(getAdapter() != null && mCurrentPosition >= getAdapter().getCount()){
            mCurrentPosition = 0;
        }
        mViewPager.setCurrentItem(mCurrentPosition);
        mViewPager.addOnPageChangeListener(this);
    }

    protected int getOffscreenPageLimit(){
        return 0;
    }

    protected int getDefaultSelectPosition(){
        return 0;
    }

    public void setAdapter(PagerAdapter adapter) {
        mViewPager.setAdapter(adapter);
    }

    protected void updateViewPager() {
        if(getAdapter() != null){
            getAdapter().notifyDataSetChanged();
        }
    }

    public int getCurrentPosition() {
        return mViewPager.getCurrentItem();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        mCurrentPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

}