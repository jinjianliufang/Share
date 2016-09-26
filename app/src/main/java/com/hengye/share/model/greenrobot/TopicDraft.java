package com.hengye.share.model.greenrobot;

import org.greenrobot.greendao.annotation.*;

import org.greenrobot.greendao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
import com.hengye.share.model.Topic;
import com.hengye.share.module.topic.TopicPresenter;
import com.hengye.share.util.CommonUtil;
import com.hengye.share.util.ResUtil;
import com.hengye.share.R;
// KEEP INCLUDES END
/**
 * Entity mapped to table "TOPIC_DRAFT".
 */
@Entity(active = true)
public class TopicDraft implements java.io.Serializable {

    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private String content;
    private java.util.Date date;
    private String urls;

    @NotNull
    private String uid;
    private String targetTopicJson;
    private String targetTopicId;
    private String targetCommentId;
    private String targetCommentUserName;
    private String targetCommentContent;
    private String assignGroupIdStr;
    private Long publishTiming;
    private Integer isCommentOrigin;
    private int status;
    private Integer isMention;
    private Integer type;
    private Integer parentType;

    /** Used to resolve relations */
    @Generated
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated
    private transient TopicDraftDao myDao;

    // KEEP FIELDS - put your custom fields here
    private static final long serialVersionUID = -3380622281318260025L;

    private int notificationId = -1;
    // KEEP FIELDS END

    @Generated
    public TopicDraft() {
    }

    public TopicDraft(Long id) {
        this.id = id;
    }

    @Generated
    public TopicDraft(Long id, String content, java.util.Date date, String urls, String uid, String targetTopicJson, String targetTopicId, String targetCommentId, String targetCommentUserName, String targetCommentContent, String assignGroupIdStr, Long publishTiming, Integer isCommentOrigin, int status, Integer isMention, Integer type, Integer parentType) {
        this.id = id;
        this.content = content;
        this.date = date;
        this.urls = urls;
        this.uid = uid;
        this.targetTopicJson = targetTopicJson;
        this.targetTopicId = targetTopicId;
        this.targetCommentId = targetCommentId;
        this.targetCommentUserName = targetCommentUserName;
        this.targetCommentContent = targetCommentContent;
        this.assignGroupIdStr = assignGroupIdStr;
        this.publishTiming = publishTiming;
        this.isCommentOrigin = isCommentOrigin;
        this.status = status;
        this.isMention = isMention;
        this.type = type;
        this.parentType = parentType;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTopicDraftDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    public String getContent() {
        return content;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setContent(@NotNull String content) {
        this.content = content;
    }

    public java.util.Date getDate() {
        return date;
    }

    public void setDate(java.util.Date date) {
        this.date = date;
    }

    public String getUrls() {
        return urls;
    }

    public void setUrls(String urls) {
        this.urls = urls;
    }

    @NotNull
    public String getUid() {
        return uid;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setUid(@NotNull String uid) {
        this.uid = uid;
    }

    public String getTargetTopicJson() {
        return targetTopicJson;
    }

    public void setTargetTopicJson(String targetTopicJson) {
        this.targetTopicJson = targetTopicJson;
    }

    public String getTargetTopicId() {
        return targetTopicId;
    }

    public void setTargetTopicId(String targetTopicId) {
        this.targetTopicId = targetTopicId;
    }

    public String getTargetCommentId() {
        return targetCommentId;
    }

    public void setTargetCommentId(String targetCommentId) {
        this.targetCommentId = targetCommentId;
    }

    public String getTargetCommentUserName() {
        return targetCommentUserName;
    }

    public void setTargetCommentUserName(String targetCommentUserName) {
        this.targetCommentUserName = targetCommentUserName;
    }

    public String getTargetCommentContent() {
        return targetCommentContent;
    }

    public void setTargetCommentContent(String targetCommentContent) {
        this.targetCommentContent = targetCommentContent;
    }

    public String getAssignGroupIdStr() {
        return assignGroupIdStr;
    }

    public void setAssignGroupIdStr(String assignGroupIdStr) {
        this.assignGroupIdStr = assignGroupIdStr;
    }

    public Long getPublishTiming() {
        return publishTiming;
    }

    public void setPublishTiming(Long publishTiming) {
        this.publishTiming = publishTiming;
    }

    public Integer getIsCommentOrigin() {
        return isCommentOrigin;
    }

    public void setIsCommentOrigin(Integer isCommentOrigin) {
        this.isCommentOrigin = isCommentOrigin;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Integer getIsMention() {
        return isMention;
    }

    public void setIsMention(Integer isMention) {
        this.isMention = isMention;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getParentType() {
        return parentType;
    }

    public void setParentType(Integer parentType) {
        this.parentType = parentType;
    }

    /**
    * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
    * Entity must attached to an entity context.
    */
    @Generated
    public void delete() {
        __throwIfDetached();
        myDao.delete(this);
    }

    /**
    * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
    * Entity must attached to an entity context.
    */
    @Generated
    public void update() {
        __throwIfDetached();
        myDao.update(this);
    }

    /**
    * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
    * Entity must attached to an entity context.
    */
    @Generated
    public void refresh() {
        __throwIfDetached();
        myDao.refresh(this);
    }

    @Generated
    private void __throwIfDetached() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
    }

    // KEEP METHODS - put your custom methods here

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TopicDraft that = (TopicDraft) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public void setIsCommentOrigin(boolean isCommentOrigin) {
        setIsCommentOrigin(isCommentOrigin ? 1 : 0);
    }

    public void setIsMention(boolean isMention) {
        setIsMention(isMention ? 0 : 1);
    }

    public boolean isCommentOrRepostConcurrently() {
        if (getIsCommentOrigin() != null && getIsCommentOrigin() == 1) {
            return true;
        }
        return false;
    }

    public void setIsCommentOrRepostConcurrently(boolean flag) {
        setIsCommentOrigin(flag);
    }

    transient Topic topic, targetTopic;

    public Topic getTopic() {
        if (topic == null) {
            topic = generateTopic();
        }
        return topic;
    }

    public Topic getTargetTopic() {
        if (getTargetTopicJson() != null && targetTopic == null) {
            targetTopic = Topic.fromJson(getTargetTopicJson());
        }
        return targetTopic;
    }

    public String getDesc() {
        if (content != null) {
            return content;
        } else {
            return "";
        }
    }

    public String getRepostContent() {
        switch (getType()) {
            case TopicDraftHelper.PUBLISH_COMMENT:
                if (CommonUtil.noEmpty(getTargetCommentUserName(), getTargetCommentContent())) {
                    return getContent() + "//" + "@" + getTargetCommentUserName() + ":" + getTargetCommentContent();
                }
                break;
            case TopicDraftHelper.REPLY_COMMENT:
                if (CommonUtil.noEmpty(getTargetCommentUserName(), getTargetCommentContent())) {
                    return "回复" + "@" + getTargetCommentUserName() + ":" + getContent()
                            + "//" + "@" + getTargetCommentUserName() + ":" + getTargetCommentContent()
                            + "//" + "@" + getTargetTopic().getUserInfo().getName() + ":" + getTargetTopic().getContent();
                }
                break;
            default:
                break;
        }
        return getContent();
    }

    public Topic generateTopic() {
        return TopicDraftHelper.getTopic(this);
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    /**
     * @return 是否指定分组可见
     */
    public boolean isAssignGroupVisible() {
        if (CommonUtil.isEmpty(assignGroupIdStr)) {
            return false;
        }

        String assignGroupName = getAssignGroupName();
        boolean isExistGroupIds = !CommonUtil.isEmpty(assignGroupName);

        if (!isExistGroupIds) {
            setAssignGroupIdStr(null);
        }
        return isExistGroupIds;
    }

    /**
     * 设置是否指定分组可见, 如果不是指定分组, assignGroupIdStr传null;
     * @param isAssignGroupVisible
     * @param assignGroupIdStr
     */
    public void setAssignGroupVisible(boolean isAssignGroupVisible, String assignGroupIdStr) {
        setAssignGroupIdStr(isAssignGroupVisible ? assignGroupIdStr : null);
    }

    /**
     * @return 返回指定分组的名字, 如果不存在则返回null;
     */
    private String getAssignGroupName() {
        return TopicPresenter.TopicGroup.getTopicGroupName(assignGroupIdStr);
    }

    /**
     * @return 返回当前应该显示的分组名字;
     */
    public String getGroupName() {
        String groupName;
        if (isAssignGroupVisible()) {
            //指定分组可见
            groupName = getAssignGroupName();
        } else {
            //所有人可见
            groupName = ResUtil.getString(R.string.label_all_groups_visible);
        }
        if (groupName == null) {
            //分组不存在
            groupName = ResUtil.getString(R.string.label_assign_group_inexistent);
        }
        return groupName;
    }

    /**
     * @return 是否定时发布
     */
    public boolean isPublishTiming() {
        return publishTiming != null && publishTiming != 0L;
    }

    public long getTiming(){
        return publishTiming == null ? 0 : publishTiming;
    }

    public void cancelTiming(){
        setPublishTiming(null);
    }

    public void mark(int status){
        setStatus(status);
    }

    /**
     *  新建的草稿
     */
    public static final int NORMAL = 0;

    /**
     *  发送中的草稿
     */
    public static final int SENDING = 1;


    /**
     *  发送失败的草稿
     */
    public static final int ERROR = 2;

    /**
     *  定时发布的草稿
     */
    public static final int TIMING = 3;

    public static final Integer[] VISIBLE = new Integer[]{NORMAL, ERROR, TIMING};

    /**
     * @return 如果已保存到数据库, 则返回true;
     */
    public boolean isSaved(){
        if(getId() != null && getId() != 0){
            return true;
        }
        return false;
    }
    // KEEP METHODS END

}
