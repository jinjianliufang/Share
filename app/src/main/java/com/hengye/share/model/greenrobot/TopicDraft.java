package com.hengye.share.model.greenrobot;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table "TOPIC_DRAFT".
 */
public class TopicDraft implements java.io.Serializable {

    private Long id;
    /** Not-null value. */
    private String content;
    private java.util.Date date;
    private String urls;
    private String uid;
    private String targetTopicId;
    private String targetCommentId;
    private Integer isCommentOrigin;
    private Integer isComment;
    private Integer type;
    private Integer parentType;

    // KEEP FIELDS - put your custom fields here
    private static final long serialVersionUID = -3380622281318260025L;
    // KEEP FIELDS END

    public TopicDraft() {
    }

    public TopicDraft(Long id) {
        this.id = id;
    }

    public TopicDraft(Long id, String content, java.util.Date date, String urls, String uid, String targetTopicId, String targetCommentId, Integer isCommentOrigin, Integer isComment, Integer type, Integer parentType) {
        this.id = id;
        this.content = content;
        this.date = date;
        this.urls = urls;
        this.uid = uid;
        this.targetTopicId = targetTopicId;
        this.targetCommentId = targetCommentId;
        this.isCommentOrigin = isCommentOrigin;
        this.isComment = isComment;
        this.type = type;
        this.parentType = parentType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Not-null value. */
    public String getContent() {
        return content;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setContent(String content) {
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public Integer getIsCommentOrigin() {
        return isCommentOrigin;
    }

    public void setIsCommentOrigin(Integer isCommentOrigin) {
        this.isCommentOrigin = isCommentOrigin;
    }

    public Integer getIsComment() {
        return isComment;
    }

    public void setIsComment(Integer isComment) {
        this.isComment = isComment;
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

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}