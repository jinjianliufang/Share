package com.hengye.share.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hengye.share.R;
import com.hengye.share.model.sina.WBUserInfo;
import com.hengye.share.ui.activity.TopicPublishActivity;
import com.hengye.share.ui.base.BaseFragment;
import com.hengye.share.util.CommonUtil;

/**
 * Created by yuhy on 16/7/19.
 */
public class PersonalHomepageAboutFragment extends BaseFragment implements View.OnClickListener{

    public static PersonalHomepageAboutFragment newInstance(WBUserInfo wbUserInfo){
        PersonalHomepageAboutFragment fragment = new PersonalHomepageAboutFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("wbUserInfo", wbUserInfo);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void handleBundleExtra() {
        super.handleBundleExtra();
        mWbUserInfo = (WBUserInfo)getArguments().getSerializable("wbUserInfo");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_personalhomepage_about;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRemark = findViewById(R.id.item_remark);
        mGroup = findViewById(R.id.item_group);
        mWBAuth = findViewById(R.id.item_wb_auth);
        mLocation = findViewById(R.id.item_location);
        mSign = findViewById(R.id.item_sign);

        findViewById(R.id.btn_at_ta).setOnClickListener(this);
        findViewById(R.id.btn_private_message).setOnClickListener(this);
        findViewById(R.id.btn_detail_info).setOnClickListener(this);
        updateUserInfo();
    }

    WBUserInfo mWbUserInfo;
    View mRemark, mGroup, mWBAuth, mLocation, mSign;

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.btn_at_ta){
            startActivity(TopicPublishActivity.getAtTaStartIntent(getContext(), mWbUserInfo.getName()));
        }
    }

    public void updateUserInfo(WBUserInfo wbUserInfo){
        mWbUserInfo = wbUserInfo;
    }

    public void updateUserInfo(){
        if(mWbUserInfo == null){
            return;
        }

        mGroup.setVisibility(View.GONE);
        setItemValue(mRemark, "备注", CommonUtil.isEmpty(mWbUserInfo.getRemark()) ? "未设置" : mWbUserInfo.getRemark());
        if(CommonUtil.isEmpty(mWbUserInfo.getVerified_reason())){
            mWBAuth.setVisibility(View.GONE);
        }else{
            setItemValue(mWBAuth, "微博认证", mWbUserInfo.getVerified_reason());
        }

        setItemValue(mLocation, "所在地", mWbUserInfo.getLocation());

        setItemValue(mSign, "签名", mWbUserInfo.getDescription());
    }

    private TextView setItemValue(int itemParentId, String itemKey, String itemValue) {
        return setItemValue(findViewById(itemParentId), itemKey, itemValue);
    }

    private TextView setItemValue(View parent, String itemKey, String itemValue) {
        if (parent != null) {
            TextView key = (TextView) parent.findViewById(R.id.tv_key);
            key.setText(itemKey);
            TextView value = (TextView) parent.findViewById(R.id.tv_value);
            value.setText(itemValue);
            return value;
        }
        return null;
    }
}