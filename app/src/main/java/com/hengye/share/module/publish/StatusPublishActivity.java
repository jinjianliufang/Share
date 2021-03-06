package com.hengye.share.module.publish;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hengye.share.model.Address;
import com.hengye.share.module.accountmanage.AccountManageActivity;
import com.hengye.share.module.base.BaseActivity;
import com.hengye.share.R;
import com.hengye.share.model.AtUser;
import com.hengye.share.model.Parent;
import com.hengye.share.model.greenrobot.StatusDraft;
import com.hengye.share.model.greenrobot.StatusDraftHelper;
import com.hengye.share.module.map.AroundAddressFragment;
import com.hengye.share.module.status.StatusPresenter;
import com.hengye.share.service.StatusPublishService;
import com.hengye.share.ui.support.textspan.SimpleContentSpan;
import com.hengye.share.ui.widget.StatusEditText;
import com.hengye.share.ui.widget.dialog.DateAndTimePickerDialog;
import com.hengye.share.ui.widget.emoticon.EmoticonPicker;
import com.hengye.share.ui.widget.emoticon.EmoticonPickerUtil;
import com.hengye.share.ui.support.listener.DefaultTextWatcher;
import com.hengye.share.ui.widget.dialog.SimpleTwoBtnDialog;
import com.hengye.share.ui.widget.image.GridGalleryEditorView;
import com.hengye.share.ui.widget.util.SelectorLoader;
import com.hengye.share.ui.widget.util.ShapeLoader;
import com.hengye.share.util.CommonUtil;
import com.hengye.share.util.DataUtil;
import com.hengye.share.util.DateUtil;
import com.hengye.share.util.EncodeUtil;
import com.hengye.share.util.IntentUtil;
import com.hengye.share.util.L;
import com.hengye.share.util.ResUtil;
import com.hengye.share.util.ToastUtil;
import com.hengye.share.util.UserUtil;
import com.hengye.share.util.ViewUtil;
import com.hengye.share.util.intercept.Action;
import com.hengye.share.util.intercept.AdTokenInterceptor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StatusPublishActivity extends BaseActivity implements View.OnClickListener {

    public static Intent getStartIntent(Context context, StatusDraft statusDraft) {
        Intent intent = new Intent(context, StatusPublishActivity.class);
        intent.putExtra("topicDraft", statusDraft);
        return intent;
    }

    public static Intent getStartIntent(Context context, String content) {
        Intent intent = new Intent(context, StatusPublishActivity.class);
        intent.putExtra("topicDraft", StatusDraftHelper.getWBStatusDraftByStatusPublish(content));
        return intent;
    }


    public static Intent getAtTaStartIntent(Context context, String name) {
        return getAtTaStartIntent(context, name, false);
    }

    public static Intent getAtTaStartIntent(Context context, String name, boolean isWithAtChar) {
        if (!isWithAtChar) {
            name = "@" + name;
        }
        return getStartIntent(context, name + " ");
    }

    /**
     * 从草稿箱传过来的草稿, 如果没有则生成一条
     */
    private StatusDraft mStatusDraft;
    private String mStatusDraftContent, mStatusDraftImages;
    private final static int DEFAULT_TYPE = StatusDraftHelper.PUBLISH_TOPIC;

    @Override
    protected void handleBundleExtra(Intent intent) {
        mStatusDraft = (StatusDraft) intent.getSerializableExtra("topicDraft");
        if (mStatusDraft != null) {
            mStatusDraftContent = mStatusDraft.getContent();
            mStatusDraftImages = mStatusDraft.getUrls();
        } else {
            mStatusDraft = new StatusDraft();
            mStatusDraft.setType(DEFAULT_TYPE);
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_status_publish;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private final int MAX_CHINESE_CONTENT_LENGTH = 140;

    private ImageButton mPhotoPickerBtn, mMentionBtn, mEmoticonBtn, mPublishBtn;
    private GridGalleryEditorView mGalleryEditor;
    private EmoticonPicker mEmoticonPicker;
    private View mContainer;
    private TextView mContentLengthTxt, mGroupVisibleStatusTxt, mLocationTxt;
    private ImageButton mEditLocationIcon, mCloseLocationBtn, mGroupVisibleStatusIcon;
    private View mLocationParent, mLocation, mLocationDivider, mGroupVisibleStatus;
    private StatusEditText mContent;
    private ScrollView mScrollView;
    private CheckBox mPublishCB;
    private Dialog mSaveToDraftDialog, mSkipToLoginDialog;
    private boolean mIsWithAtChar;
    private int mCurrentContentLength;
    private int mContentTextSize;
    private Address mAddress;

    @Override
    public void onNavigationClick(View v) {
        super.onNavigationClick(v);
    }

    @SuppressWarnings("ConstantConditions")
    private void initView() {
        changeTitleStyle(mStatusDraft.getType());

        Bitmap avatarBitmap = UserUtil.getUserAvatarBitmap();
        if (avatarBitmap != null) {
            int size = ResUtil.getDimensionPixelSize(R.dimen.icon_size_normal);
//            int sample = BitmapUtil.findBestSampleSize(avatarBitmap.getWidth(), avatarBitmap.getHeight(), size, size);
            avatarBitmap = Bitmap.createScaledBitmap(avatarBitmap, size, size, true);
        }
        if (avatarBitmap != null) {
            Drawable bd = new BitmapDrawable(getResources(), avatarBitmap);
            getToolbar().setContentInsetStartWithNavigation(ViewUtil.dp2px(10.0f));
            getToolbar().setNavigationIcon(bd, true);
        }else{
//            getToolbar().setNavigationIcon(null);
        }

        String space = "";
        getToolbar().setTitle(space + getToolbar().getTitle());
        String name = UserUtil.getName();
        if(CommonUtil.isEmpty(name)){
            name = "";
        }
        getToolbar().setSubtitle(space + name);

        mContainer = findViewById(R.id.rl_container);
        mContent = (StatusEditText) findViewById(R.id.et_status_publish);
        mContent.setSelection(0);
        mContent.setOnClickListener(this);
        mContentTextSize = (int) mContent.getTextSize();
        mContentLengthTxt = (TextView) findViewById(R.id.tv_content_length);
        mGroupVisibleStatusTxt = (TextView) findViewById(R.id.tv_group_visible_status);
        mGroupVisibleStatus = findViewById(R.id.layout_group_visible_status);
        mGroupVisibleStatus.setOnClickListener(this);
        mGroupVisibleStatusIcon = (ImageButton) findViewById(R.id.ic_group_visible_status);
        mLocationTxt = (TextView) findViewById(R.id.tv_location);
        mLocationParent = findViewById(R.id.layout_location_parent);
        mLocation = findViewById(R.id.layout_location);
        mLocation.setOnClickListener(this);
        mLocationDivider = findViewById(R.id.divider_location);
        mEditLocationIcon = (ImageButton) findViewById(R.id.ic_edit_location);
        mCloseLocationBtn = (ImageButton) findViewById(R.id.btn_close_location);
        mCloseLocationBtn.setOnClickListener(this);
        float locationBgRadius = ResUtil.getDimensionPixelSize(R.dimen.status_publish_options_tip_radius);
        int locationNormalColor = ResUtil.getColor(R.color.white);
        int locationPressedColor = ResUtil.getColor(R.color.grey_300);
        Drawable locationNormalBg = ShapeLoader.getInstance().getRectConnerBackground(locationNormalColor, locationBgRadius);
        Drawable locationPressedBg = ShapeLoader.getInstance().getRectConnerBackground(locationPressedColor, locationBgRadius);
        SelectorLoader.getInstance().setBackgroundSelector(mLocation, locationNormalBg, locationPressedBg);
        Drawable groupVisibleStatusNormalBg = ShapeLoader.getInstance().getRectConnerBackground(locationNormalColor, locationBgRadius);
        Drawable groupVisibleStatusPressedBg = ShapeLoader.getInstance().getRectConnerBackground(locationPressedColor, locationBgRadius);
        SelectorLoader.getInstance().setBackgroundSelector(mGroupVisibleStatus, groupVisibleStatusNormalBg, groupVisibleStatusPressedBg);
        float[] closeLocationBgRadius = new float[]{0, 0, locationBgRadius, locationBgRadius, locationBgRadius, locationBgRadius, 0, 0};
        Drawable closeLocationNormalBg = ShapeLoader.getInstance().getRectConnerBackground(locationNormalColor, closeLocationBgRadius);
        Drawable closeLocationPressedBg = ShapeLoader.getInstance().getRectConnerBackground(locationPressedColor, closeLocationBgRadius);
        SelectorLoader.getInstance().setBackgroundSelector(mCloseLocationBtn, closeLocationNormalBg, closeLocationPressedBg);
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
        mScrollView = (ScrollView) findViewById(R.id.scrollView);
        mPublishCB = (CheckBox) findViewById(R.id.checkbox_publish);
        mContent.addTextChangedListener(new DefaultTextWatcher() {

            int lastLineCount = mContent.getLineCount();

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //自动调整EditText的位置
                int currentLineCount = mContent.getLineCount();
                if (currentLineCount > mContent.getMinLines()) {
                    int differCount = lastLineCount - currentLineCount;
                    if (differCount > 0) {
                        mScrollView.scrollBy(0, -mContent.getLineHeight() * differCount);
                    }
                }
                lastLineCount = currentLineCount;

                updateContentLength();

                //输入@的时候自动弹出At用户的界面
                boolean findAt = false;
                if (count == 1) {
                    String str = s.toString();
                    String end = str.substring(str.length() - 1);
                    if ("@".equals(end)) {
                        if (start == 0) {
                            findAt = true;
                        } else if (str.length() > 1) {
                            String lastChar = str.substring(str.length() - 2, str.length() - 1);
                            if (" ".equals(lastChar) || "\n".equals(lastChar)) {
                                findAt = true;
                            }
                        }
                    }
                }
                if (findAt) {
                    L.debug("find at action");
                    startAtUser(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //避免监听死循环，因为要改动text
//                mContent.removeTextChangedListener(this);
//                SpannableString ss = DataUtil.convertNormalStringToSpannableString(null, s, false);
//                mContent.ensureRange(ss);
//                mContent.setTextKeepState(ss);
//                mContent.addTextChangedListener(this);



//                List<SimpleContentSpan> simpleContentSpans = DataUtil.convertNormalStringToSimpleContentUrlSpans(s);
//                mContent.ensureRange(simpleContentSpans);
//                if (simpleContentSpans != null) {
////                    mContent.ensureRange(simpleContentSpans);
//                    for (SimpleContentSpan span : simpleContentSpans) {
//                        s.setSpan(span, span.getStart(), span.getEnd(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    }
//                }
            }
        });

        mGalleryEditor = (GridGalleryEditorView) findViewById(R.id.gallery_edit);
        if (mStatusDraft.getUrls() != null) {
            mGalleryEditor.setPaths(mStatusDraft.getUrlList());
        }
        mContent.setFilters(new InputFilter[]{mEmoticonPicker.getEmoticonInputFilter(), mStatusInputFilter});

        ViewUtil.hideKeyBoardOnTouch(mScrollView, mContent);
        ViewUtil.hideKeyBoardOnTouch(mGalleryEditor, mContent);
        initSaveToDraftDialog();

        initViewByType();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_mention) {
            startAtUser(false);
        } else if (id == R.id.btn_emoticon) {
            if (mEmoticonPicker.isShown()) {
                hideEmoticonPicker(true);
            } else {
                showEmoticonPicker(
                        EmoticonPickerUtil.isKeyBoardShow(this));
            }
        } else if (id == R.id.et_status_publish) {
            hideEmoticonPicker(true);
        } else if (id == R.id.btn_publish) {
            publishStatusIfCan();
        } else if (id == R.id.btn_camera) {
            mGalleryEditor.startPhotoPicker();
        } else if (id == R.id.layout_location) {
            AroundAddressFragment.start(this, REQUEST_LOCATION);
        } else if (id == R.id.layout_group_visible_status) {
            performMenuClick();
        } else if (id == R.id.btn_close_location) {
            updateLocation(null);
        }
    }

    @Override
    public void onBackPressed() {
        if (shouldSaveToDraft()) {
            mSaveToDraftDialog.show();
        } else {
            updateDraftIfNeed();
            super.onBackPressed();
        }
    }

    private void performMenuClick() {
        onOptionsItemSelected(mAssignGroupVisible);
//        mAssignGroupVisible.expandActionView();
//        openOptionsMenu();
    }

    MenuItem mAllGroupsVisible, mAssignGroupVisible, mPublishTiming, mCancelPublishTiming;
    List<StatusPresenter.StatusGroup> mStatusGroups;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (mStatusDraft.getType() != StatusDraftHelper.PUBLISH_TOPIC
                && mStatusDraft.getType() != StatusDraftHelper.REPOST_TOPIC) {
            return false;
        }

        getMenuInflater().inflate(R.menu.activity_publish, menu);

        mAllGroupsVisible = menu.findItem(R.id.all_groups_visible);
        mAssignGroupVisible = menu.findItem(R.id.assign_group_visible);
        mPublishTiming = menu.findItem(R.id.publish_timing);
        mCancelPublishTiming = menu.findItem(R.id.publish_timing_cancel);

        mStatusGroups = StatusPresenter.StatusGroup.getStatusGroups();

        if (!CommonUtil.isEmpty(mStatusGroups)) {
            SubMenu subMenu = mAssignGroupVisible.getSubMenu();
//            SubMenu subMenu = menu.addSubMenu(R.id.topic_publish, 1, mAssignGroupVisible.getOrder(), ResUtil.getString(R.string.label_assign_group_visible));
            if (subMenu != null) {
                for (int i = 0; i < mStatusGroups.size(); i++) {
                    StatusPresenter.StatusGroup tg = mStatusGroups.get(i);
                    subMenu.add(1, i, i, tg.getName());
                }
            }

        } else {
            mAllGroupsVisible.setVisible(false);
            mAssignGroupVisible.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        } else if (id == R.id.all_groups_visible) {
            //所有人可见
            mStatusDraft.setAssignGroupVisible(false, null);
            updateGroupVisibleStatus();
        } else if (id == R.id.publish_timing) {
            //定时发布
            getPublishTimingDialog().show();
        } else if (id == R.id.publish_timing_cancel) {
            //取消定时发布
            mStatusDraft.cancelTiming();
        } else {
            //指定分组可见
            int groupIndex = item.getItemId();
            if (mStatusGroups != null && groupIndex < mStatusGroups.size()) {
                StatusPresenter.StatusGroup tp = mStatusGroups.get(groupIndex);

                mStatusDraft.setAssignGroupIdStr(tp.getGroupList().getGid());
                updateGroupVisibleStatus();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private final static int REQUEST_AT_USER = 1;
    private final static int REQUEST_LOCATION = 2;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_AT_USER && data != null) {
                //@用户
                //noinspection unchecked
                ArrayList<String> result = (ArrayList<String>) data.getSerializableExtra("atUser");
                String userNames = AtUser.getFormatAtUserName(result);
                if (mContent.getText().toString().endsWith("@") && !CommonUtil.isEmpty(userNames)) {
                    Editable editable = mContent.getText();
                    editable.replace(editable.length() - 1, editable.length(), "");
                }
                EmoticonPickerUtil.addContentToEditTextEnd(mContent, userNames);
            } else if (requestCode == REQUEST_LOCATION && data != null) {
                //定位
                updateLocation((Address) data.getSerializableExtra(AroundAddressFragment.EXTRA_ADDRESS));
            }
        }
    }

    /**
     * 直接在afterTextChanged里设置span就好
     */
    private InputFilter mStatusInputFilter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
//            return source;
            if(dstart >= dend){
                //只有在输入内容时才设置span，否则删除时设置span没意义，因为此时source为空
                SpannableString value = SpannableString.valueOf(source);
                List<SimpleContentSpan> simpleContentSpans = DataUtil.convertNormalStringToSimpleContentUrlSpans(mContentTextSize, value);
                mContent.ensureRange(simpleContentSpans);
                if (simpleContentSpans != null) {
                    for (SimpleContentSpan span : simpleContentSpans) {
                        value.setSpan(span, span.getStart(), span.getEnd(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }
                return value;
            }
            return source;
//            return DataUtil.convertNormalStringToSpannableString(null, source, false);
        }
    };

    private void initViewByType() {
        switch (mStatusDraft.getType()) {
            case StatusDraftHelper.PUBLISH_COMMENT:
            case StatusDraftHelper.REPLY_COMMENT:
                mPublishCB.setVisibility(View.VISIBLE);
                mPublishCB.setText(R.string.label_publish_comment_and_repost_to_me);
                mPublishCB.setChecked(mStatusDraft.isCommentOrRepostConcurrently());
                mGroupVisibleStatus.setVisibility(View.GONE);
                mLocationParent.setVisibility(View.GONE);
                mPhotoPickerBtn.setVisibility(View.GONE);
                break;
            case StatusDraftHelper.REPOST_TOPIC:
                mPublishCB.setVisibility(View.VISIBLE);
                mPublishCB.setText(R.string.label_repost_status_and_commend_to_author);
                mPublishCB.setChecked(mStatusDraft.isCommentOrRepostConcurrently());
                mGroupVisibleStatus.setVisibility(View.VISIBLE);
                updateGroupVisibleStatus();
                mLocationParent.setVisibility(View.GONE);
                updateLocation(mStatusDraft.getAddressBean());
                mPhotoPickerBtn.setVisibility(View.GONE);
                break;
            case StatusDraftHelper.PUBLISH_TOPIC:
                mPublishCB.setVisibility(View.GONE);
                mGroupVisibleStatus.setVisibility(View.VISIBLE);
                updateGroupVisibleStatus();
                mLocationParent.setVisibility(View.VISIBLE);
                updateLocation(mStatusDraft.getAddressBean());
                mPhotoPickerBtn.setVisibility(View.VISIBLE);
            default:
                break;
        }
    }

    public void initSaveToDraftDialog() {
        SimpleTwoBtnDialog builder = new SimpleTwoBtnDialog();
        if (mStatusDraft != null && mStatusDraft.getId() != null) {
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
        if (mStatusDraft == null || TextUtils.isEmpty(mStatusDraft.getContent())) {
            return;
        }

        if (!mStatusDraft.isSaved() && mStatusDraft.getType() == StatusDraftHelper.REPOST_TOPIC) {
            mStatusDraftContent = "//" + mStatusDraft.getContent();
            mContent.setText(mStatusDraftContent);
            mContent.setSelection(0);
        } else {
            mContent.setText(mStatusDraft.getContent());
            mContent.setSelection(mStatusDraft.getContent().length());
        }
        updateContentLength();
    }

    private void changeTitleStyle(int publishType) {
        int titleResId;
        switch (publishType) {
            case StatusDraftHelper.PUBLISH_COMMENT:
                titleResId = R.string.title_comment_publish;
                break;
            case StatusDraftHelper.REPLY_COMMENT:
                titleResId = R.string.title_comment_reply;
                break;
            case StatusDraftHelper.REPOST_TOPIC:
                titleResId = R.string.title_status_repost;
                break;
            case StatusDraftHelper.PUBLISH_TOPIC:
            default:
                titleResId = R.string.title_status_publish;
                break;
        }
        updateToolbarTitle(titleResId);
    }

    /**
     * @param isWithAtChar 是否由@去打开AtUser，是的话在回调的时候把前面的@去掉
     */
    private void startAtUser(boolean isWithAtChar) {
        mIsWithAtChar = isWithAtChar;
        IntentUtil.startActivityForResult(this, AtUserActivity.class, REQUEST_AT_USER);
    }

    private boolean shouldSaveToDraft() {
        if (UserUtil.isUserEmpty()) {
            return false;
        }
        return hasChangeContent();
    }

    private boolean hasChangeContent() {
        String str = mContent.getText().toString();
        return !(CommonUtil.isEquals(mStatusDraftContent, str) && CommonUtil.isEquals(mStatusDraftImages, getPhotoPickerUrls()));
    }

    private String getPhotoPickerUrls() {
        return CommonUtil.toSplit(mGalleryEditor.getPaths(), StatusDraft.DELIMITER_URL);
    }

    private StatusDraft generateStatusDraft() {
        StatusDraft td = new StatusDraft();
        td.setUid(UserUtil.getUid());
        td.setContent(mContent.getText().toString());
//        td.setDate(DateUtil.getChinaGMTDateFormat());
        td.setDate(hasChangeContent() ? DateUtil.getChinaGMTDate() : mStatusDraft.getDate());
        td.setType(mStatusDraft.getType());
        td.setParentType(Parent.TYPE_WEIBO);

        td.setTargetStatusId(mStatusDraft.getTargetStatusId());
        td.setTargetStatusJson(mStatusDraft.getTargetStatusJson());
        td.setTargetCommentId(mStatusDraft.getTargetCommentId());
        td.setTargetCommentUserName(mStatusDraft.getTargetCommentUserName());
        td.setTargetCommentContent(mStatusDraft.getTargetCommentContent());
        td.setAssignGroupIdStr(mStatusDraft.getAssignGroupIdStr());
        td.setPublishTiming(mStatusDraft.getPublishTiming());
        td.setAddressBean(mAddress);

        if (mPublishCB.isChecked()) {
            td.setIsCommentOrRepostConcurrently(true);
        }
        if (!CommonUtil.isEmpty(mGalleryEditor.getPaths())) {
            if (CommonUtil.isEmpty(td.getContent())) {
                td.setContent(ResUtil.getString(R.string.label_status_picture_publish_content_empty));
            }
            td.setUrls(getPhotoPickerUrls());
        }

        if (mStatusDraft.isSaved()) {
            //草稿已存在,保存ID
            td.setId(mStatusDraft.getId());
        }

        //插入或更新一条草稿, 标记为发送中
        StatusDraftHelper.saveStatusDraft(td, StatusDraft.NORMAL);
        return td;
    }

    private void publishStatusIfCan() {
        if (checkCanPublishStatus()) {
            publicStatus();
        }
    }

    private void publicStatus(){
        StatusPublishService.publish(this, generateStatusDraft(), UserUtil.getToken());

        if (mStatusDraft.isSaved()) {
            //让草稿箱知道有草稿被发送了, 刷新界面
            setResult(Activity.RESULT_OK);
        }
        finish();
    }

    private void saveToDraft() {
        generateStatusDraft();
        setResult(Activity.RESULT_OK);
        ToastUtil.showToast(R.string.label_save_to_draft_success);
    }

    private void updateDraftIfNeed() {
        if (mStatusDraft.isSaved()) {
            StatusDraftHelper.updateStatusDraft(mStatusDraft);
            setResult(Activity.RESULT_OK);
        }
    }

    private boolean checkCanPublishStatus() {
        boolean result = true;
        if (TextUtils.isEmpty(mContent.getText().toString()) && CommonUtil.isEmpty(mGalleryEditor.getPaths())) {
            result = false;
            ToastUtil.showToast(R.string.label_status_publish_field_empty);
        } else if (mCurrentContentLength > MAX_CHINESE_CONTENT_LENGTH) {
            result = false;
            ToastUtil.showToast(R.string.tip_status_publish_content_length_error);
        } else if (TextUtils.isEmpty(UserUtil.getToken())) {
            result = false;
            showSkipToLoginDialog();
        }else if(mGalleryEditor.getPaths() != null && mGalleryEditor.getPaths().size() > 1){
            //发多张图片需要高级授权
            getAdTokenInterceptor().start();
            return false;
        }

        return result;
    }

    AdTokenInterceptor mAdTokenInterceptor;
    public AdTokenInterceptor getAdTokenInterceptor() {
        if(mAdTokenInterceptor == null){
            mAdTokenInterceptor = new AdTokenInterceptor(this);
            mAdTokenInterceptor.setAction(new Action() {
                @Override
                public void run() {
                    publicStatus();
                }
            });
        }
        return mAdTokenInterceptor;
    }

    private void showSkipToLoginDialog() {
        if (mSkipToLoginDialog == null) {
            mSkipToLoginDialog = AccountManageActivity.getLoginDialog(this);
        }
        mSkipToLoginDialog.show();
    }

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

    private void updateLocation(Address address) {
        mAddress = address;
        if (address == null) {
            //重置显示位置内容
            mEditLocationIcon.setImageResource(R.drawable.ic_add_location_white_48dp);
            mLocationTxt.setText(R.string.label_show_location);
            mLocationDivider.setVisibility(View.GONE);
            mCloseLocationBtn.setVisibility(View.GONE);
        } else {
            //更新位置
            mEditLocationIcon.setImageResource(R.drawable.ic_edit_location_white_48dp);
            mLocationTxt.setText(address.getName());
            mLocationDivider.setVisibility(View.VISIBLE);
            mCloseLocationBtn.setVisibility(View.VISIBLE);
        }
        //显示位置内容布局的宽度是0，重新设置文字后要重新测量宽度才会自适应变化
        mLocationTxt.requestLayout();
    }

    /**
     * 更新还可以输入多少字数
     */
    private void updateContentLength() {
        mCurrentContentLength = EncodeUtil.getChineseLength(mContent.getText().toString());
        int differLength = Math.abs(MAX_CHINESE_CONTENT_LENGTH - mCurrentContentLength);
        mContentLengthTxt.setText(String.valueOf(mCurrentContentLength));
        if (mCurrentContentLength <= MAX_CHINESE_CONTENT_LENGTH) {
            mContentLengthTxt.setTextColor(ResUtil.getColor(R.color.text_grey));
//            mContentLengthTxt.setText(ResUtil.getString(R.string.label_topic_publish_content_length_less, differLength));
        } else {
            mContentLengthTxt.setTextColor(ResUtil.getColor(R.color.text_red_warn));
//            mContentLengthTxt.setText(ResUtil.getString(R.string.label_topic_publish_content_length_more, differLength));
        }
    }

    /**
     * 更新指定分组可见的内容
     */
    private void updateGroupVisibleStatus() {
        mGroupVisibleStatusTxt.setText(mStatusDraft.getGroupName());
        mGroupVisibleStatusIcon.setImageResource(
                mStatusDraft.isAssignGroupVisible() ?
                        R.drawable.ic_lock_outline_white_48dp :
                        R.drawable.ic_public_white_48dp);
    }


    DateAndTimePickerDialog mPublishTimingDialog;

    private DateAndTimePickerDialog getPublishTimingDialog() {
        if (mPublishTimingDialog == null) {
            long timeInMillis = mStatusDraft.isPublishTiming() ? mStatusDraft.getPublishTiming() : System.currentTimeMillis();
            mPublishTimingDialog = new DateAndTimePickerDialog(this, timeInMillis);
            mPublishTimingDialog.setOnSetListener(new DateAndTimePickerDialog.OnSetListener() {
                @Override
                public void onSet(Calendar calendar, long timeInMillis) {
                    mStatusDraft.setPublishTiming(timeInMillis);
                }
            });
            mPublishTimingDialog.setOnTimeUpdateListener(new DateAndTimePickerDialog.onTimeUpdateListener() {
                @Override
                public boolean onTimeUpdate(boolean isSelectDate, long timeInMillis) {
                    long duration = timeInMillis - System.currentTimeMillis();
                    if (duration < 0) {
                        ToastUtil.showToast(R.string.tip_publish_on_time_options_invalid);
                        return false;
                    }

                    return true;
                }
            });
        }
        return mPublishTimingDialog;
    }

}
