package com.hengye.share.module;

import android.text.SpannableString;
import android.text.TextUtils;

import com.hengye.share.module.sina.WBTopic;
import com.hengye.share.module.sina.WBTopicComment;
import com.hengye.share.module.sina.WBTopicComments;
import com.hengye.share.module.sina.WBTopics;
import com.hengye.share.util.DataUtil;
import com.hengye.share.util.thirdparty.WBUtil;
import com.hengye.share.util.CommonUtil;
import com.hengye.share.util.GsonUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Topic implements Serializable{

    private static final long serialVersionUID = 971288752432928272L;

    private Parent parent;

//    private String avatar;//头像
//    private String username;//名字
    private String date;//创建日期
    private String channel;//渠道，通过什么发表
    private String content;//内容
    private String id;//主题的唯一id
    private List<String> imageUrls;//缩略图
    private List<String> imageLargeUrls;//原图
    private Topic retweetedTopic;//被转发的主题

    private UserInfo userInfo;//用户信息

    private transient SpannableString urlSpannableString;

    public static ArrayList<Topic> getTopics(WBTopics wbTopics){
        if(wbTopics == null || CommonUtil.isEmptyCollection(wbTopics.getStatuses())){
            return null;
        }
        ArrayList<Topic> topics = new ArrayList<>();
        for(WBTopic entity : wbTopics.getStatuses()){
            topics.add(getTopic(entity));
        }
        return topics;
    }

    public static Topic getTopic(WBTopic entity){
        Topic topic = new Topic();
        topic.setParent(new Parent(GsonUtil.getInstance().toJson(entity), Parent.TYPE_WEIBO));
        if(entity == null){
            return topic;
        }
        topic.setUserInfo(UserInfo.getUserInfo(entity.getUser()));
//        if(entity.getUser() != null) {
//            topic.setAvatar(entity.getUser().getAvatar_large());
//            topic.setUsername(entity.getUser().getScreen_name());
//        }
        topic.setDate(entity.getCreated_at());
        topic.setChannel(entity.getSource());
        topic.setContent(entity.getText());
        topic.setId(entity.getIdstr());
        if(!CommonUtil.isEmptyCollection(entity.getPic_urls())){
            List<String> imageUrls = new ArrayList<>();
            List<String> imageLargeUrls = new ArrayList<>();
            for(WBTopic.Pic_urlsEntity urlsEntity : entity.getPic_urls()){
                imageUrls.add(WBUtil.getWBTopicImgUrl(urlsEntity.getThumbnail_pic(), WBUtil.IMAGE_TYPE_BMIDDLE));
                imageLargeUrls.add(WBUtil.getWBTopicImgUrl(urlsEntity.getThumbnail_pic(), WBUtil.IMAGE_TYPE_LARGE));
            }
            topic.setImageUrls(imageUrls);
            topic.setImageLargeUrls(imageLargeUrls);
        }

        if(entity.getRetweeted_status() != null){
            //获得一条转发的微博, 防止一直递归
            entity.getRetweeted_status().setRetweeted_status(null);
            topic.setRetweetedTopic(getTopic(entity.getRetweeted_status()));
        }
        return topic;
    }

    public static ArrayList<Topic> getTopics(WBTopicComments wbTopicComments){
        if(wbTopicComments == null || CommonUtil.isEmptyCollection(wbTopicComments.getComments())){
            return null;
        }
        ArrayList<Topic> topics = new ArrayList<>();
        for(WBTopicComment entity : wbTopicComments.getComments()){
            topics.add(getTopic(entity));
        }
        return topics;
    }

    public static Topic getTopic(WBTopicComment entity){
        Topic topic = new Topic();
        topic.setParent(new Parent(GsonUtil.getInstance().toJson(entity), Parent.TYPE_WEIBO));
        if(entity == null){
            return topic;
        }
        topic.setUserInfo(UserInfo.getUserInfo(entity.getUser()));
//        if(entity.getUser() != null) {
//            topic.setAvatar(entity.getUser().getAvatar_large());
//            topic.setUsername(entity.getUser().getScreen_name());
//        }
        topic.setDate(entity.getCreated_at());
        topic.setChannel(entity.getSource());
        topic.setContent(entity.getText());
        topic.setId(entity.getIdstr());
//        if(!CommonUtil.isEmptyCollection(entity.getPic_urls())){
//            List<String> imageUrls = new ArrayList<>();
//            List<String> imageLargeUrls = new ArrayList<>();
//            for(WBTopic.Pic_urlsEntity urlsEntity : entity.getPic_urls()){
//                imageUrls.add(WBUtil.getWBTopicImgUrl(urlsEntity.getThumbnail_pic(), WBUtil.IMAGE_TYPE_BMIDDLE));
//                imageLargeUrls.add(WBUtil.getWBTopicImgUrl(urlsEntity.getThumbnail_pic(), WBUtil.IMAGE_TYPE_LARGE));
//            }
//            topic.setImageUrls(imageUrls);
//            topic.setImageLargeUrls(imageLargeUrls);
//        }

        if(entity.getStatus() != null){
            //获得一条转发的微博, 防止一直递归
            entity.getStatus().setRetweeted_status(null);
            topic.setRetweetedTopic(getTopic(entity.getStatus()));
        }
        return topic;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "parent=" + parent +
                ", date='" + date + '\'' +
                ", channel='" + channel + '\'' +
                ", content='" + content + '\'' +
                ", id='" + id + '\'' +
                ", imageUrls=" + imageUrls +
                ", imageLargeUrls=" + imageLargeUrls +
                ", retweetedTopic=" + retweetedTopic +
                ", userInfo=" + userInfo +
                '}';
    }

    public SpannableString getUrlSpannableString() {
        if (!TextUtils.isEmpty(urlSpannableString)) {
            return urlSpannableString;
        } else {
            DataUtil.addTopicContentHighLightLinks(this);
            return urlSpannableString;
        }
    }

    public void setUrlSpannableString(SpannableString urlSpannableString) {
        this.urlSpannableString = urlSpannableString;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Topic getRetweetedTopic() {
        return retweetedTopic;
    }

    public void setRetweetedTopic(Topic retweetedTopic) {
        this.retweetedTopic = retweetedTopic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public List<String> getImageLargeUrls() {
        return imageLargeUrls;
    }

    public void setImageLargeUrls(List<String> imageLargeUrls) {
        this.imageLargeUrls = imageLargeUrls;
    }
}
