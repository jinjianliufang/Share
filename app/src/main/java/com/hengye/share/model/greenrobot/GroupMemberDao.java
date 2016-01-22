package com.hengye.share.model.greenrobot;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.hengye.share.model.greenrobot.GroupMember;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "GROUP_MEMBER".
*/
public class GroupMemberDao extends AbstractDao<GroupMember, Long> {

    public static final String TABLENAME = "GROUP_MEMBER";

    /**
     * Properties of entity GroupMember.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Uid = new Property(1, String.class, "uid", false, "UID");
        public final static Property Tid = new Property(2, String.class, "tid", false, "TID");
        public final static Property Gid = new Property(3, String.class, "gid", false, "GID");
        public final static Property Name = new Property(4, String.class, "name", false, "NAME");
        public final static Property Gender = new Property(5, String.class, "gender", false, "GENDER");
        public final static Property Description = new Property(6, String.class, "description", false, "DESCRIPTION");
        public final static Property Avatar = new Property(7, String.class, "avatar", false, "AVATAR");
        public final static Property Verified = new Property(8, Integer.class, "verified", false, "VERIFIED");
        public final static Property VerifiedType = new Property(9, Integer.class, "verifiedType", false, "VERIFIED_TYPE");
        public final static Property Following = new Property(10, Integer.class, "following", false, "FOLLOWING");
        public final static Property FollowMe = new Property(11, Integer.class, "followMe", false, "FOLLOW_ME");
        public final static Property IsGroupMember = new Property(12, Integer.class, "isGroupMember", false, "IS_GROUP_MEMBER");
    };

    private DaoSession daoSession;


    public GroupMemberDao(DaoConfig config) {
        super(config);
    }
    
    public GroupMemberDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"GROUP_MEMBER\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ASC AUTOINCREMENT ," + // 0: id
                "\"UID\" TEXT NOT NULL ," + // 1: uid
                "\"TID\" TEXT NOT NULL ," + // 2: tid
                "\"GID\" TEXT NOT NULL ," + // 3: gid
                "\"NAME\" TEXT NOT NULL ," + // 4: name
                "\"GENDER\" TEXT," + // 5: gender
                "\"DESCRIPTION\" TEXT," + // 6: description
                "\"AVATAR\" TEXT," + // 7: avatar
                "\"VERIFIED\" INTEGER," + // 8: verified
                "\"VERIFIED_TYPE\" INTEGER," + // 9: verifiedType
                "\"FOLLOWING\" INTEGER," + // 10: following
                "\"FOLLOW_ME\" INTEGER," + // 11: followMe
                "\"IS_GROUP_MEMBER\" INTEGER);"); // 12: isGroupMember
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"GROUP_MEMBER\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, GroupMember entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getUid());
        stmt.bindString(3, entity.getTid());
        stmt.bindString(4, entity.getGid());
        stmt.bindString(5, entity.getName());
 
        String gender = entity.getGender();
        if (gender != null) {
            stmt.bindString(6, gender);
        }
 
        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(7, description);
        }
 
        String avatar = entity.getAvatar();
        if (avatar != null) {
            stmt.bindString(8, avatar);
        }
 
        Integer verified = entity.getVerified();
        if (verified != null) {
            stmt.bindLong(9, verified);
        }
 
        Integer verifiedType = entity.getVerifiedType();
        if (verifiedType != null) {
            stmt.bindLong(10, verifiedType);
        }
 
        Integer following = entity.getFollowing();
        if (following != null) {
            stmt.bindLong(11, following);
        }
 
        Integer followMe = entity.getFollowMe();
        if (followMe != null) {
            stmt.bindLong(12, followMe);
        }
 
        Integer isGroupMember = entity.getIsGroupMember();
        if (isGroupMember != null) {
            stmt.bindLong(13, isGroupMember);
        }
    }

    @Override
    protected void attachEntity(GroupMember entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public GroupMember readEntity(Cursor cursor, int offset) {
        GroupMember entity = new GroupMember( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // uid
            cursor.getString(offset + 2), // tid
            cursor.getString(offset + 3), // gid
            cursor.getString(offset + 4), // name
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // gender
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // description
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // avatar
            cursor.isNull(offset + 8) ? null : cursor.getInt(offset + 8), // verified
            cursor.isNull(offset + 9) ? null : cursor.getInt(offset + 9), // verifiedType
            cursor.isNull(offset + 10) ? null : cursor.getInt(offset + 10), // following
            cursor.isNull(offset + 11) ? null : cursor.getInt(offset + 11), // followMe
            cursor.isNull(offset + 12) ? null : cursor.getInt(offset + 12) // isGroupMember
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, GroupMember entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUid(cursor.getString(offset + 1));
        entity.setTid(cursor.getString(offset + 2));
        entity.setGid(cursor.getString(offset + 3));
        entity.setName(cursor.getString(offset + 4));
        entity.setGender(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setDescription(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setAvatar(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setVerified(cursor.isNull(offset + 8) ? null : cursor.getInt(offset + 8));
        entity.setVerifiedType(cursor.isNull(offset + 9) ? null : cursor.getInt(offset + 9));
        entity.setFollowing(cursor.isNull(offset + 10) ? null : cursor.getInt(offset + 10));
        entity.setFollowMe(cursor.isNull(offset + 11) ? null : cursor.getInt(offset + 11));
        entity.setIsGroupMember(cursor.isNull(offset + 12) ? null : cursor.getInt(offset + 12));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(GroupMember entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(GroupMember entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
