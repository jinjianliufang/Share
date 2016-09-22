package com.hengye.share.model.greenrobot;

import org.greenrobot.greendao.annotation.*;

import com.hengye.share.model.greenrobot.DaoSession;
import org.greenrobot.greendao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table "SHARE_JSON".
 */
@Entity(active = true)
public class ShareJson implements java.io.Serializable {

    @Id
    @NotNull
    private String model;
    private String json;

    /** Used to resolve relations */
    @Generated
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated
    private transient ShareJsonDao myDao;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    @Generated
    public ShareJson() {
    }

    public ShareJson(String model) {
        this.model = model;
    }

    @Generated
    public ShareJson(String model, String json) {
        this.model = model;
        this.json = json;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getShareJsonDao() : null;
    }

    @NotNull
    public String getModel() {
        return model;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setModel(@NotNull String model) {
        this.model = model;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
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
    // KEEP METHODS END

}
