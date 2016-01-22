package com.hengye.share.model.greenrobot;

import com.hengye.share.model.greenrobot.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
import com.hengye.share.model.Parent;
import com.hengye.share.model.sina.WBUserInfo;
import com.hengye.share.model.sina.WBUserInfos;
import com.hengye.share.util.CommonUtil;
import com.hengye.share.util.GsonUtil;

import java.util.ArrayList;
// KEEP INCLUDES END
/**
 * Entity mapped to table "USER".
 */
public class User implements java.io.Serializable {

    private Long id;
    /** Not-null value. */
    private String uid;
    /** Not-null value. */
    private String token;
    private int parentType;
    private String parentJson;
    private String refreshToken;
    private Long expiresIn;
    private String name;
    private String avatar;
    private String gender;
    private String sign;
    private String cover;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient UserDao myDao;


    // KEEP FIELDS - put your custom fields here
    private static final long serialVersionUID = -308923963776940281L;
    // KEEP FIELDS END

    public User() {
    }

    public User(Long id) {
        this.id = id;
    }

    public User(Long id, String uid, String token, int parentType, String parentJson, String refreshToken, Long expiresIn, String name, String avatar, String gender, String sign, String cover) {
        this.id = id;
        this.uid = uid;
        this.token = token;
        this.parentType = parentType;
        this.parentJson = parentJson;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
        this.name = name;
        this.avatar = avatar;
        this.gender = gender;
        this.sign = sign;
        this.cover = cover;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getUserDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Not-null value. */
    public String getUid() {
        return uid;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /** Not-null value. */
    public String getToken() {
        return token;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setToken(String token) {
        this.token = token;
    }

    public int getParentType() {
        return parentType;
    }

    public void setParentType(int parentType) {
        this.parentType = parentType;
    }

    public String getParentJson() {
        return parentJson;
    }

    public void setParentJson(String parentJson) {
        this.parentJson = parentJson;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

    // KEEP METHODS - put your custom methods here

    public static ArrayList<User> getUsers(WBUserInfos wbUserInfos){
        if(wbUserInfos == null || CommonUtil.isEmptyCollection(wbUserInfos.getUsers())){
            return null;
        }
        ArrayList<User> users = new ArrayList<>();
        for(WBUserInfo entity : wbUserInfos.getUsers()){
            users.add(getUser(entity));
        }
        return users;
    }

    public static User getUser(WBUserInfo wbUserInfo){
        User user = new User();
        user.setParentType(Parent.TYPE_WEIBO);
        user.setParentJson(GsonUtil.getInstance().toJson(wbUserInfo));
        if(wbUserInfo == null){
            return user;
        }
        user.setUid(wbUserInfo.getIdstr());
        user.setName(wbUserInfo.getName());
        user.setAvatar(wbUserInfo.getAvatar_large());
        user.setGender(wbUserInfo.getGender());
        user.setSign(wbUserInfo.getDescription());
        user.setCover(wbUserInfo.getCover_image_phone());
        return user;
    }

    public WBUserInfo getWBUserInfoFromParent(){
        return GsonUtil.getInstance().fromJson(getParentJson(), WBUserInfo.class);
    }
    // KEEP METHODS END

}
