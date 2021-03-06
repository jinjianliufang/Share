package com.hengye.share.util;

import com.hengye.share.model.Address;
import com.hengye.share.model.StatusPublish;
import com.hengye.share.model.greenrobot.StatusDraft;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class UrlFactory {
    private static class UrlFactoryHolder {
        private final static UrlFactory INSTANCE = new UrlFactory();
    }

    private UrlFactory() {
    }

    public static UrlFactory getInstance() {
        return UrlFactoryHolder.INSTANCE;
    }

    //weibo url prefix
    private static final String URL_PREFIX_WEIBO = "https://api.weibo.com/2/";

    /**
     * 微博接口
     */
    //获得微博
    public static final String WB_USER_STATUS = "statuses/user_timeline.json";
    //获得微博
    public static final String WB_FRIEND_STATUS = "statuses/friends_timeline.json";
    //获得微博ID列表
    public static final String WB_FRIEND_STATUS_IDS = "statuses/friends_timeline/ids.json";
    //获得好友圈微博
    public static final String WB_BILATERAL_STATUS = "statuses/bilateral_timeline.json";
    //获得好友圈微博ID列表
    public static final String WB_BILATERAL_STATUS_IDS = "statuses/bilateral_timeline/ids.json";
    //获得某一分组的微博列表
    public static final String WB_GROUP_STATUS = "friendships/groups/timeline.json";
    //获得某一分组的微博ID列表
    public static final String WB_GROUP_STATUS_IDS = "friendships/groups/timeline/ids.json";
    //获取某个用户的各种消息未读数
    public static final String WB_UNREAD_COUNT = "remind/unread_count.json";
    //获取用户信息
    public static final String WB_USER_INFO = "users/show.json";
    //获取某个微博的评论列表
    public static final String WB_COMMENT_SHOW = "comments/show.json";
    //获取某个微博的转发列表
    public static final String WB_REPOST_SHOW = "statuses/repost_timeline.json";
    //我发出的评论列表
    public static final String WB_COMMENT_BY_ME = "comments/by_me.json";
    //我收到的评论列表
    public static final String WB_COMMENT_TO_ME = "comments/to_me.json";
    //@我的评论列表
    public static final String WB_COMMENT_MENTION = "comments/mentions.json";
    //@我的微博
    public static final String WB_STATUS_MENTION = "statuses/mentions.json";
    //我收藏的微博(支持page分页)
    public static final String WB_FAVORITES_STATUS = "favorites.json";
    //发表微博
    public static final String WB_PUBLISH_STATUS = "statuses/update.json";
    //发表微博
    public static final String WB_DESTROY_STATUS = "statuses/destroy.json";
    //发表微博,并且上传一张图片
    public static final String WB_PUBLISH_STATUS_UPLOAD = "statuses/upload.json";
    //上传一张图片
    public static final String WB_UPLOAD_PICTURE = "statuses/upload_pic.json";
    //发表微博, 可上传多张图片,高级接口
    public static final String WB_PUBLISH_STATUS_PICTURE = "statuses/upload_url_text.json";
    //获取用户的关注列表
    public static final String WB_USER_ATTENTIONS = "friendships/friends.json";
    //获取用户的关注列表
    public static final String WB_USER_FOLLOWERS = "friendships/followers.json";
    //对微博进行评论
    public static final String WB_COMMENT_STATUS = "comments/create.json";
    //回复评论
    public static final String WB_COMMENT_REPLY = "comments/reply.json";
    //转发微博
//    is_comment 是否在转发的同时发表评论，0：否、1：评论给当前微博、2：评论给原微博、3：都评论，默认为0 。
    public static final String WB_REPOST_STATUS = "statuses/repost.json";
    //搜索用户
    public static final String WB_SEARCH_USER = "search/users.json";
    //    https://api.weibo.com/2/search/users.json?q=uu&user_id=2207519004&count=20&page=1&filter_ori=0&filter_pic=0&access_token=2.00OXW56C06XASOc5a146b1b0pVluLB
    //搜索微博
    public static final String WB_SEARCH_PUBLIC = "search/public.json";
    //    https://api.weibo.com/2/search/public.json?q=uu&sid=o_weico&sort=social&source=211160679&user_id=2207519004&count=20&page=1&access_token=2.00OXW56C06XASOc5a146b1b0pVluLB
    //搜索话题
    public static final String WB_SEARCH_TOPICS = "search/topics.json";
    //添加收藏微博
    public static final String WB_STATUS_FAVORITE_CREATE = "favorites/create.json";
    //删除收藏微博
    public static final String WB_STATUS_FAVORITE_DESTROY = "favorites/destroy.json";

    //    获取好友的相册列表
//    public static final String WB_USER_ALBUM = "friendships/groups.json";
    //获取好友的分组信息
    public static final String WB_GROUP = "friendships/groups.json";
    //创建好友分组
    public static final String WB_GROUP_CREATE = "friendships/groups/create.json";
    //删除好友分组
    public static final String WB_GROUP_DESTROY = "friendships/groups/destroy.json";
    //更新好友分组
    public static final String WB_GROUP_UPDATE = "friendships/groups/update.json";
    //添加关注用户到好友分组
    public static final String WB_GROUP_MEMBER_ADD = "friendships/groups/members/add.json";
    //删除好友分组内的关注用户
    public static final String WB_GROUP_MEMBER_DESTROY = "friendships/groups/members/destroy.json";
    //获取某一好友分组下的成员列表
    public static final String WB_GROUP_MEMBER = "friendships/groups/members.json";
    //调整当前登录用户的好友分组顺序
    public static final String WB_GROUP_ORDER = "friendships/groups/order.json";
    //关注某用户
    public static final String WB_FOLLOW_CREATE = "friendships/create.json";
    //取消关注某用户
    public static final String WB_FOLLOW_DESTROY = "friendships/destroy.json";
    //短链转长链
    public static final String WB_EXPAND_URL = "short_url/info.json";
    //微博点赞创建
    public static final String WB_STATUS_LIKE = "attitudes/create.json";
    //微博点赞取消
    public static final String WB_STATUS_DISLIKE = "attitudes/destroy.json";
    //获取附近地点
    public static final String WB_PLACE_NEARBY = "place/nearby/pois.json";

    //以下接口前缀不一样的
    //token验证
    public static final String WB_OAUTH_TOKEN = "https://api.weibo.com/oauth2/access_token";
    //联想用户
    public static final String WB_SEARCH_USER_SUGGESTION = "http://s.weibo.com/ajax/suggestion";
    //    http://s.weibo.com/ajax/suggestion?where=gs_weibo&type=gs_weibo&key=uue&access_token=2.00OXW56C06XASOc5a146b1b0pVluLB
    //获取某个微博的评论列表，带点赞数量，需要高级授权
    public static final String WB_COMMENT_SHOW_WITH_LIKE = "https://api.weibo.cn/2/comments/show";
    //获取某个微博的热门评论列表，带点赞数量，需要高级授权
    public static final String WB_HOT_COMMENT_SHOW_WITH_LIKE = "http://api.weibo.cn/2/comments/hot_timeline";
    //评论点赞
    public static final String WB_COMMENT_LIKE = "http://api.weibo.cn/2/like/update";
    //评论取消点赞
    public static final String WB_COMMENT_DISLIKE = "http://api.weibo.cn/2/like/destroy";
    //微博点赞列表(支持page分页)
    public static final String WB_STATUS_LIKE_SHOW = "https://api.weibo.cn/2/like/show";
    //获取热门微博
    public static final String WB_HOT_STATUS = "http://m.weibo.cn/container/getIndex?containerid=102803";
    //获取热门话题
    public static final String WB_HOT_TOPIC = "http://weicoapi.weico.cc/portal.php?a=get_topic_weibo&c=default&page=0";
    //获取热门搜索
    public static final String WB_HOT_SEARCH = "http://weicoapi.weico.cc/portal.php?a=search_topic&c=default";

    /**
     * 微博接口
     */

    public static String getWBUrlPrefix() {
        return URL_PREFIX_WEIBO;
    }


    public static Map<String, Object> getPublishStatusParams(StatusPublish sp) {
        return getPublishStatusParams(sp, null);
    }

    public static Map<String, Object> getPublishStatusParams(StatusPublish sp, String picUrls) {
        Map<String, Object> map = new HashMap<>();
        StatusDraft sd = sp.getStatusDraft();
        map.put("access_token", sp.getToken());
        map.put("status", sd.getContent());
        if (sd.isAssignGroupVisible()) {
            map.put("visible", "3");
            map.put("list_id", sd.getAssignGroupIdStr());
        }
        if(picUrls != null){
            map.put("pic_id", picUrls);
        }

        Address address = sd.getAddressBean();
        if(address != null){
            map.put("long", address.getLongitude());
            map.put("lat", address.getLatitude());
        }
        return map;
    }

    public static Map<String, RequestBody> getPublishStatusParamsWrapToMultiPart(StatusPublish sp) {
        return getPublishStatusParamsWrapToMultiPart(sp, null);
    }

    public static Map<String, RequestBody> getPublishStatusParamsWrapToMultiPart(StatusPublish sp, String picUrls) {
        return convertMultiPartFormDataFromMap(getPublishStatusParams(sp, picUrls));
    }

    public static Map<String, RequestBody> convertMultiPartFormDataFromMap(Map<String, ?> map){
        Map<String, RequestBody> convert = new HashMap<>();
        for(Map.Entry<String, ?> entry : map.entrySet()){
            convert.put(entry.getKey(), convertMultiPartFormData(entry.getValue()));
        }
        return convert;
    }
    
    public static RequestBody convertMultiPartFormData(Object value) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), value.toString());
    }


    public String getWBUserStatusUrl() {
        return getWBUrlPrefix() + WB_USER_STATUS;
    }

    //    必选 	类型及范围 	说明
//    source 	false 	string 	采用OAuth授权方式不需要此参数，其他授权方式为必填参数，数值为应用的AppKey。
//    access_token 	false 	string 	采用OAuth授权方式为必填参数，其他授权方式不需要此参数，OAuth授权后获得。
//    since_id 	false 	int64 	若指定此参数，则返回ID比since_id大的微博（即比since_id时间晚的微博），默认为0。
//    max_id 	false 	int64 	若指定此参数，则返回ID小于或等于max_id的微博，默认为0。
//    count 	false 	int 	单页返回的记录条数，最大不超过100，默认为20。
//    page 	false 	int 	返回结果的页码，默认为1。
//    base_app 	false 	int 	是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0。
//    feature 	false 	int 	过滤类型ID，0：全部、1：原创、2：图片、3：视频、4：音乐，默认为0。
//    trim_user 	false 	int 	返回值中user字段开关，0：返回完整user字段、1：user字段仅返回user_id，默认为0。
//    获得微博
    public String getWBFriendStatusUrl() {
        return getWBUrlPrefix() + WB_FRIEND_STATUS;
    }

    //获得好友圈微博
    public String getWBBilateralStatusUrl() {
        return getWBUrlPrefix() + WB_BILATERAL_STATUS;
    }

    //    必选 	类型及范围 	说明
//    source 	false 	string 	采用OAuth授权方式不需要此参数，其他授权方式为必填参数，数值为应用的AppKey。
//    access_token 	false 	string 	采用OAuth授权方式为必填参数，其他授权方式不需要此参数，OAuth授权后获得。
//    since_id 	false 	int64 	若指定此参数，则返回ID比since_id大的微博（即比since_id时间晚的微博），默认为0。
//    max_id 	false 	int64 	若指定此参数，则返回ID小于或等于max_id的微博，默认为0。
//    count 	false 	int 	单页返回的记录条数，最大不超过100，默认为20。
//    page 	false 	int 	返回结果的页码，默认为1。
//    base_app 	false 	int 	是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0。
//    feature 	false 	int 	过滤类型ID，0：全部、1：原创、2：图片、3：视频、4：音乐，默认为0。
//    获得微博的ID数
    public String getWBFriendStatusIdsUrl() {
        return getWBUrlPrefix() + WB_FRIEND_STATUS_IDS;
    }

    //获得某一分组的微博列表
    public String getWBGroupStatusUrl() {
        return getWBUrlPrefix() + WB_GROUP_STATUS;
    }

    //    必选	类型及范围	说明
//    source	false	string	采用OAuth授权方式不需要此参数，其他授权方式为必填参数，数值为应用的AppKey。
//    access_token	false	string	采用OAuth授权方式为必填参数，其他授权方式不需要此参数，OAuth授权后获得。
//    uid	true	int64	需要获取消息未读数的用户UID，必须是当前登录用户。
//    callback	false	string	JSONP回调函数，用于前端调用返回JS格式的信息。
//    unread_message	false	boolean	未读数版本。0：原版未读数，1：新版未读数。默认为0。
//    获取某个用户的各种消息未读数
    public String getWBUserUnReadCountUrl() {
        return getWBUrlPrefix() + WB_UNREAD_COUNT;
    }

    //    必选 	类型及范围 	说明
//    source 	false 	string 	采用OAuth授权方式不需要此参数，其他授权方式为必填参数，数值为应用的AppKey。
//    access_token 	false 	string 	采用OAuth授权方式为必填参数，其他授权方式不需要此参数，OAuth授权后获得。
//    uid 	false 	int64 	需要查询的用户ID。
//    screen_name 	false 	string 	需要查询的用户昵称。
//    注意事项
//
//    参数uid与screen_name二者必选其一，且只能选其一；
//    接口升级后，对未授权本应用的uid，将无法获取其个人简介、认证原因、粉丝数、关注数、微博数及最近一条微博内容
    public String getWBUserInfoUrl() {
        return getWBUrlPrefix() + WB_USER_INFO;
    }


    //    必选	类型及范围	说明
//    source	false	string	采用OAuth授权方式不需要此参数，其他授权方式为必填参数，数值为应用的AppKey。
//    access_token	false	string	采用OAuth授权方式为必填参数，其他授权方式不需要此参数，OAuth授权后获得。
//    id	true	int64	需要查询的微博ID。
//    since_id	false	int64	若指定此参数，则返回ID比since_id大的评论（即比since_id时间晚的评论），默认为0。
//    max_id	false	int64	若指定此参数，则返回ID小于或等于max_id的评论，默认为0。
//    count	false	int	单页返回的记录条数，默认为50。
//    page	false	int	返回结果的页码，默认为1。
//    filter_by_author	false	int	作者筛选类型，0：全部、1：我关注的人、2：陌生人，默认为0。
    public String getWBCommentUrl() {
        return getWBUrlPrefix() + WB_COMMENT_SHOW;
    }

    //    必选	类型及范围	说明
//    source	false	string	采用OAuth授权方式不需要此参数，其他授权方式为必填参数，数值为应用的AppKey。
//    access_token	false	string	采用OAuth授权方式为必填参数，其他授权方式不需要此参数，OAuth授权后获得。
//    id	true	int64	需要查询的微博ID。
//    since_id	false	int64	若指定此参数，则返回ID比since_id大的微博（即比since_id时间晚的微博），默认为0。
//    max_id	false	int64	若指定此参数，则返回ID小于或等于max_id的微博，默认为0。
//    count	false	int	单页返回的记录条数，最大不超过200，默认为20。
//    page	false	int	返回结果的页码，默认为1。
//    filter_by_author	false	int	作者筛选类型，0：全部、1：我关注的人、2：陌生人，默认为0。
    public String getWBRepostUrl() {
        return getWBUrlPrefix() + WB_REPOST_SHOW;
    }

    //我发出的评论列表
    public String getWBCommentByMeUrl() {
        return getWBUrlPrefix() + WB_COMMENT_BY_ME;
    }

    //我收到的评论列表
    public String getWBCommentToMeUrl() {
        return getWBUrlPrefix() + WB_COMMENT_TO_ME;
    }

    //@我的评论列表
    public String getWBCommentMentionUrl() {
        return getWBUrlPrefix() + WB_COMMENT_MENTION;
    }

    //@我的微博
    public String getWBStatusMentionUrl() {
        return getWBUrlPrefix() + WB_STATUS_MENTION;
    }

    //我收藏的微博
    public String getWBStatusFavoritesUrl() {
        return getWBUrlPrefix() + WB_FAVORITES_STATUS;
    }

    //发表微博
    public String getWBStatusPublishUrl() {
        return getWBUrlPrefix() + WB_PUBLISH_STATUS;
    }

    //发表微博,并且上传一张图片
    public String getWBStatusPublishPhotoUrl() {
        return getWBUrlPrefix() + WB_PUBLISH_STATUS_UPLOAD;
    }

    //获取用户的关注列表
    public String getWBAttentionUrl() {
        return getWBUrlPrefix() + WB_USER_ATTENTIONS;
    }

    //    comment	true	string	评论内容，必须做URLencode，内容不超过140个汉字。
    //    id	true	int64	需要评论的微博ID。
    //    comment_ori	false	int	当评论转发微博时，是否评论给原微博，0：否、1：是，默认为0。
    //对微博进行评论
    public String getWBStatusCommentCreateUrl() {
        return getWBUrlPrefix() + WB_COMMENT_STATUS;
    }

    //    cid	true	int64	需要回复的评论ID。
    //    id	true	int64	需要评论的微博ID。
    //    comment	true	string	回复评论内容，必须做URLencode，内容不超过140个汉字。
    //    without_mention	false	int	回复中是否自动加入“回复@用户名”，0：是、1：否，默认为0。
    //    comment_ori	false	int	当评论转发微博时，是否评论给原微博，0：否、1：是，默认为0。
    //回复评论
    public String getWBStatusCommentReplyUrl() {
        return getWBUrlPrefix() + WB_COMMENT_REPLY;
    }

    //    id	true	int64	要转发的微博ID。
//    status	false	string	添加的转发文本，必须做URLencode，内容不超过140个汉字，不填则默认为“转发微博”。
//    is_comment	false	int	是否在转发的同时发表评论，0：否、1：评论给当前微博、2：评论给原微博、3：都评论，默认为0 。
    //转发微博
    public String getWBStatusRepostUrl() {
        return getWBUrlPrefix() + WB_REPOST_STATUS;
    }

    //搜索用户
    public String getWBSearchUserUrl() {
        return getWBUrlPrefix() + WB_SEARCH_USER;
    }

    //搜索用户建议
    public String getWBSearchUserSuggestionUrl() {
        return WB_SEARCH_USER_SUGGESTION;
    }

    //搜索微博
    public String getWBSearchPublicUrl() {
        return getWBUrlPrefix() + WB_SEARCH_PUBLIC;
    }

    //搜索话题
    public String getWBSearchStatusUrl() {
        return getWBUrlPrefix() + WB_SEARCH_TOPICS;
    }

    //添加收藏微博
    public String getWBCreateFavoritesUrl() {
        return getWBUrlPrefix() + WB_STATUS_FAVORITE_CREATE;
    }

    //删除收藏微博
    public String getWBDestroyFavoritesUrl() {
        return getWBUrlPrefix() + WB_STATUS_FAVORITE_DESTROY;
    }

}