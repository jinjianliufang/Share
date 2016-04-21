package com.hengye.share.ui.view;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hengye.share.R;
import com.hengye.share.util.ViewUtil;

public class SearchView extends FrameLayout implements View.OnClickListener{

    public SearchView(Context context) {
        this(context, null, -1);
    }

    public SearchView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public SearchView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init(context);
    }

    private void init(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.item_search, null);
        addView(view);

        mSearch = findViewById(R.id.card_search);
        mBackBtn = findViewById(R.id.iv_search_back);
        mClearBtn = findViewById(R.id.iv_clear_content);
        mSearchContent = (EditText) findViewById(R.id.et_search_content);
        mSearchResult = (ListView) findViewById(R.id.list_view);

        mBackBtn.setOnClickListener(this);
        mClearBtn.setOnClickListener(this);

        mSearchContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(mSearchContent.getText().toString())){
                    mClearBtn.setVisibility(View.GONE);
                }else{
                    mClearBtn.setVisibility(View.VISIBLE);
                }
            }

        });

        mSearchContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_SEARCH){
                    if(getSearchListener() != null){
                        getSearchListener().onSearch(getSearchContent());
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private View mSearch, mBackBtn, mClearBtn;
    private EditText mSearchContent;
    private ListView mSearchResult;

    private Activity mActivity;

    private onSearchListener mSearchListener;

    private int mMode = MODE_ANIMATION;

    public final static int MODE_ANIMATION = 1;
    public final static int MODE_FINISH = 2;

    public void setMode(int mode, Activity activity){
        this.mMode = mode;
        setup(activity);

        if(mode != MODE_ANIMATION){
            mSearch.setVisibility(View.VISIBLE);
        }
    }

    public int getMode(){
        return this.mMode;
    }

    public void setup(Activity activity){
        this.mActivity = activity;
    }

    public Activity getActivity(){
        return mActivity;
    }

    public onSearchListener getSearchListener() {
        return mSearchListener;
    }

    public void setSearchListener(onSearchListener searchListener) {
        this.mSearchListener = searchListener;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if(id == R.id.iv_search_back){
            if(getMode() == MODE_ANIMATION){
                handleSearch();
            }else if(getMode() == MODE_FINISH){
                getActivity().onBackPressed();
            }
            getActivity().onBackPressed();
        }else if(id == R.id.iv_clear_content){
            clearSearchContent();
        }
    }

    public String getSearchContent(){
        return mSearchContent.getText().toString();
    }

    public void setSearchContent(String content){
        mSearchContent.setText(content);
    }

    public void clearSearchContent() {
        mSearchContent.setText("");
    }

    public boolean isSearchShow(){
        return mSearch.getVisibility() == View.VISIBLE;
    }

    public void handleSearch() {
        handleSearch(!isSearchShow());
    }

    public void handleSearch(boolean isToShow) {
        if (isToShow) {
            startSearchShowAnimation();
        } else {
            startSearchHideAnimation();
        }
    }


    private void startSearchHideAnimation() {
        final Animator searchHideAnimator = ViewAnimationUtils.createCircularReveal(mSearch,
                mSearch.getWidth(),
                ViewUtil.getActionBarHeight() / 2,
                (float) Math.hypot(mSearch.getWidth(), mSearch.getHeight()),
                0);
        searchHideAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mSearchContent.setText("");
                mSearch.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mSearch.setVisibility(View.GONE);
                ViewUtil.hideKeyBoard(mSearchContent);
                mSearchResult.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        searchHideAnimator.setDuration(300);
        searchHideAnimator.start();
    }

    private void startSearchShowAnimation() {
        final Animator searchShowAnimator = ViewAnimationUtils.createCircularReveal(mSearch,
                mSearchContent.getWidth(),
                ViewUtil.getActionBarHeight() / 2,
                0,
                (float) Math.hypot(mSearch.getWidth(), mSearch.getHeight()));
        searchShowAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mSearch.setVisibility(View.VISIBLE);
                mSearch.setEnabled(true);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mSearchResult.setVisibility(View.VISIBLE);
                ViewUtil.showKeyBoard(mSearchContent);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        searchShowAnimator.setDuration(300);
        searchShowAnimator.start();
    }

    public interface onSearchListener{
        void onSearch(String content);
    }
}