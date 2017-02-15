package com.hengye.share.module.topic;

import android.os.Bundle;

import com.hengye.share.R;
import com.hengye.share.module.base.BaseActivity;

/**
 * Created by yuhy on 2016/11/10.
 */

public class TopicFavoriteActivity extends BaseActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, TopicFavoriteFragment.newInstance())
                .commit();
    }
}
