package com.hengye.share.model.greenrobot;

import com.hengye.share.model.greenrobot.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table "GROUP_MEMBER".
 */
public class GroupMember implements java.io.Serializable {

    private Long id;
    /** Not-null value. */
    private String uid;
    /** Not-null value. */
    private String tid;
    /** Not-null value. */
    private String gid;
    /** Not-null value. */
    private String name;
    private String gender;
    private String description;
    private String avatar;
    private Integer verified;
    private Integer verifiedType;
    private Integer following;
    private Integer followMe;
    private Integer isGroupMember;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient GroupMemberDao myDao;


    // KEEP FIELDS - put your custom fields here
    private static final long serialVersionUID = -8624301148884169307L;
    // KEEP FIELDS END

    public GroupMember() {
    }

    public GroupMember(Long id) {
        this.id = id;
    }

    public GroupMember(Long id, String uid, String tid, String gid, String name, String gender, String description, String avatar, Integer verified, Integer verifiedType, Integer following, Integer followMe, Integer isGroupMember) {
        this.id = id;
        this.uid = uid;
        this.tid = tid;
        this.gid = gid;
        this.name = name;
        this.gender = gender;
        this.description = description;
        this.avatar = avatar;
        this.verified = verified;
        this.verifiedType = verifiedType;
        this.following = following;
        this.followMe = followMe;
        this.isGroupMember = isGroupMember;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getGroupMemberDao() : null;
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
    public String getTid() {
        return tid;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setTid(String tid) {
        this.tid = tid;
    }

    /** Not-null value. */
    public String getGid() {
        return gid;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setGid(String gid) {
        this.gid = gid;
    }

    /** Not-null value. */
    public String getName() {
        return name;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getVerified() {
        return verified;
    }

    public void setVerified(Integer verified) {
        this.verified = verified;
    }

    public Integer getVerifiedType() {
        return verifiedType;
    }

    public void setVerifiedType(Integer verifiedType) {
        this.verifiedType = verifiedType;
    }

    public Integer getFollowing() {
        return following;
    }

    public void setFollowing(Integer following) {
        this.following = following;
    }

    public Integer getFollowMe() {
        return followMe;
    }

    public void setFollowMe(Integer followMe) {
        this.followMe = followMe;
    }

    public Integer getIsGroupMember() {
        return isGroupMember;
    }

    public void setIsGroupMember(Integer isGroupMember) {
        this.isGroupMember = isGroupMember;
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
    // KEEP METHODS END

}