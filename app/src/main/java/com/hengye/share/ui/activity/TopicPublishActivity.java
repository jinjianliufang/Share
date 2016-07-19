package com.hengye.share.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hengye.photopicker.model.Photo;
import com.hengye.photopicker.view.PickPhotoView;
import com.hengye.share.ui.base.BaseActivity;
import com.hengye.share.R;
import com.hengye.share.model.AtUser;
import com.hengye.share.model.Parent;
import com.hengye.share.model.greenrobot.TopicDraft;
import com.hengye.share.model.greenrobot.TopicDraftHelper;
import com.hengye.share.service.TopicPublishService;
import com.hengye.share.ui.emoticon.EmoticonPicker;
import com.hengye.share.ui.emoticon.EmoticonPickerUtil;
import com.hengye.share.ui.widget.dialog.SimpleTwoBtnDialog;
import com.hengye.share.util.CommonUtil;
import com.hengye.share.util.DataUtil;
import com.hengye.share.util.DateUtil;
import com.hengye.share.util.IntentUtil;
import com.hengye.share.util.ToastUtil;
import com.hengye.share.util.UserUtil;

import java.util.ArrayList;

public class TopicPublishActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected String getRequestTag() {
        return super.getRequestTag();
    }

    @Override
    protected boolean setCustomTheme() {
        return super.setCustomTheme();
    }

    @Override
    protected boolean setToolBar() {
        return super.setToolBar();
    }

    public static Intent getStartIntent(Context context, TopicDraft topicDraft) {
        Intent intent = new Intent(context, TopicPublishActivity.class);
        intent.putExtra("topicDraft", topicDraft);
        return intent;
    }

    public static Intent getStartIntent(Context context, String content) {
        Intent intent = new Intent(context, TopicPublishActivity.class);
        intent.putExtra("topicDraft", TopicDraftHelper.getWBTopicDraftByTopicPublish(content));
        return intent;
    }

    public static Intent getAtTaStartIntent(Context context, String name) {
        return getStartIntent(context, "@" + name + " ");
    }

    private TopicDraft mTopicDraft;
    private String mTopicDraftContent;
    private final static int DEFAULT_TYPE = TopicDraftHelper.PUBLISH_TOPIC;

    @Override
    protected void handleBundleExtra() {
        mTopicDraft = (TopicDraft) getIntent().getSerializableExtra("topicDraft");
        if (mTopicDraft != null) {
            mTopicDraftContent = mTopicDraft.getContent();
        } else {
            mTopicDraft = new TopicDraft();
            mTopicDraft.setType(DEFAULT_TYPE);
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_topic_publish;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
        initData();
    }

    private ImageButton mPhotoPickerBtn, mMentionBtn, mEmoticonBtn, mPublishBtn;
    private PickPhotoView mPhotoPicker;
    private EmoticonPicker mEmoticonPicker;
    private RelativeLayout mContainer;
    private EditText mContent;
    private Dialog mSaveToDraftDialog, mSkipToLoginDialog;

    private void initView() {
        mContainer = (RelativeLayout) findViewById(R.id.rl_container);
        mContent = (EditText) findViewById(R.id.et_topic_publish);
        mContent.setSelection(0);
        mContent.setOnClickListener(this);
        mPhotoPickerBtn = (ImageButton) findViewById(R.id.btn_camera);
        mPhotoPickerBtn.setOnClickListener(this);
        mMentionBtn = (ImageButton) findViewById(R.id.btn_mention);
        mMentionBtn.setOnClickListener(this);
        mEmoticonBtn = (ImageButton) findViewById(R.id.btn_emoticon);
        mEmoticonBtn.setOnClickListener(this);
        mPublishBtn = (ImageButton) findViewById(R.id.btn_publish);
        mPublishBtn.setOnClickListener(this);
        mEmoticonPicker = (EmoticonPicker) findViewById(R.id.emoticon_picker);
        mEmoticonPicker.setEditText(this, ((LinearLayout) findViewById(R.id.ll_root)),
                mContent);
        mPhotoPicker = (PickPhotoView) findViewById(R.id.pick_photo);
        if(mTopicDraft.getUrls() != null){
            ArrayList<Photo> photos = new ArrayList<>();
            Photo photo = new Photo();
            photo.setDataPath(mTopicDraft.getUrls());
            photos.add(photo);
            mPhotoPicker.setAddPhotos(photos);
        }
        mContent.setFilters(new InputFilter[]{mAtUserInputFilter, mEmoticonPicker.getEmoticonInputFilter()});
        initSaveToDraftDialog();
    }

    public void initSaveToDraftDialog() {
        SimpleTwoBtnDialog builder = new SimpleTwoBtnDialog();
        if (mTopicDraft != null && mTopicDraft.getId() != null) {
            builder.setContent(getString(R.string.label_save_to_draft_override));
        } else {
            builder.setContent(getString(R.string.label_save_to_draft));
        }
        builder.setNegativeButtonClickListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.setPositiveButtonClickListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveToDraft();
                dialog.dismiss();
                finish();
            }
        });
        mSaveToDraftDialog = builder.create(this);
    }

    private void initData() {
        changeTitleStyle(mTopicDraft.getType());

        if (mTopicDraft == null || TextUtils.isEmpty(mTopicDraft.getContent())) {
            return;
        }
        mContent.setText(mTopicDraft.getContent());
        mContent.setSelection(mTopicDraft.getContent().length());
    }

    private void changeTitleStyle(int publishType) {
        int titleResId;
        switch (publishType) {
            case TopicDraftHelper.PUBLISH_COMMENT:
                titleResId = R.string.title_publish_comment;
                break;
            case TopicDraftHelper.REPLY_COMMENT:
                titleResId = R.string.title_reply_comment;
                break;
            case TopicDraftHelper.REPOST_TOPIC:
                titleResId = R.string.title_repost_topic;
                break;
            case TopicDraftHelper.PUBLISH_TOPIC:
            default:
                titleResId = R.string.title_publish_topic;
                break;
        }
        updateToolbarTitle(titleResId);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_mention) {
            IntentUtil.startActivityForResult(this, AtUserActivity.class, AtUserActivity.REQUEST_AT_USER);
        } else if (id == R.id.btn_emoticon) {
            if (mEmoticonPicker.isShown()) {
                hideEmoticonPicker(true);
            } else {
                showEmoticonPicker(
                        EmoticonPickerUtil.isKeyBoardShow(this));
            }
        } else if (id == R.id.et_topic_publish) {
            hideEmoticonPicker(true);
        } else if (id == R.id.btn_publish) {
            publishTopic();
        } else if (id == R.id.btn_camera){
            mPhotoPicker.performClick();
//            PhotoPicker.startPhotoPicker(this);
        }
    }

    @Override
    public void onBackPressed() {
        if (shouldSaveToDraft()) {
            mSaveToDraftDialog.show();
        } else {
            super.onBackPressed();
        }
    }

    private boolean shouldSaveToDraft() {
        String str = mContent.getText().toString();
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        if (str.equals(mTopicDraftContent)) {
            return false;
        }
        return true;
    }

    private TopicDraft generateTopicDraft() {
        TopicDraft td = new TopicDraft();
        td.setUid(UserUtil.getUid());
        td.setContent(mContent.getText().toString());
//        td.setDate(DateUtil.getChinaGMTDateFormat());
        td.setDate(DateUtil.getChinaGMTDate());
        td.setType(mTopicDraft.getType());
        td.setParentType(Parent.TYPE_WEIBO);
        if (mTopicDraft != null) {
            td.setId(mTopicDraft.getId());
            td.setTargetTopicId(mTopicDraft.getTargetTopicId());
            td.setTargetCommentId(mTopicDraft.getTargetCommentId());
        }
        if(!CommonUtil.isEmpty(mPhotoPicker.getPhotos())){
            td.setUrls(mPhotoPicker.getPhotos().get(0).getDataPath());
        }
        return td;
    }

    private void publishTopic() {

        if(!checkCanPublicTopic()){
            return;
        }

        TopicPublishService.publish(this, generateTopicDraft(), UserUtil.getToken());
        finish();
    }

    private void saveToDraft() {
        TopicDraftHelper.saveTopicDraft(generateTopicDraft());
        setResult(Activity.RESULT_OK);
        ToastUtil.showToast(R.string.label_save_to_draft_success);
    }

    private boolean checkCanPublicTopic(){
        boolean result = true;
        if(TextUtils.isEmpty(mContent.getText().toString())){
            result = false;
            ToastUtil.showToast("为什么连一个空格都不给我??");
        }else if(TextUtils.isEmpty(UserUtil.getToken())){
            result = false;
            showSkipToLoginDialog();
        }

        return result;
    }

    private void showSkipToLoginDialog(){
        if(mSkipToLoginDialog == null){
            mSkipToLoginDialog = AccountManageActivity.getLoginDialog(this);
        }
        mSkipToLoginDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == AtUserActivity.REQUEST_AT_USER) {
            if (data != null) {
                ArrayList<String> result = (ArrayList<String>) data.getSerializableExtra("atUser");
                EmoticonPickerUtil.addContentToEditTextEnd(mContent, AtUser.getFormatAtUserName(result));
            }
        }

        mPhotoPicker.handleResult(requestCode, resultCode, data);
//        List<Photo> photos = PhotoPicker.resolvePhotoPicker(requestCode, resultCode, data);
//        if(!CommonUtil.isEmpty(photos)){
//
//        }
    }

    private InputFilter mAtUserInputFilter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            return DataUtil.convertNormalStringToSpannableString(TopicPublishActivity.this, String.valueOf(source));
        }
    };

    private void showEmoticonPicker(boolean showAnimation) {
        mEmoticonPicker.show(showAnimation);
        lockContainerHeight(EmoticonPickerUtil.getAppContentHeight(this));
    }

    public void hideEmoticonPicker(boolean showKeyBoard) {
        if (mEmoticonPicker.isShown()) {
            if (showKeyBoard) {
                //this time softkeyboard is hidden
                lockContainerHeight(mEmoticonPicker.getTop());
                mEmoticonPicker.hide();
                EmoticonPickerUtil.showKeyBoard(mContent);
                mContent.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        unlockContainerHeight();
                    }
                }, 200L);
            } else {
                mEmoticonPicker.hide();
                unlockContainerHeight();
            }
        }
    }

    private void lockContainerHeight(int height) {
        LinearLayout.LayoutParams localLayoutParams = (LinearLayout.LayoutParams) mContainer
                .getLayoutParams();
        localLayoutParams.height = height;
        localLayoutParams.weight = 0.0F;
    }

    public void unlockContainerHeight() {
        ((LinearLayout.LayoutParams) mContainer.getLayoutParams()).weight = 1.0F;
    }
}
