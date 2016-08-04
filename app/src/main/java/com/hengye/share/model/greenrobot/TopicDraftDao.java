package com.hengye.share.model.greenrobot;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.hengye.share.model.greenrobot.TopicDraft;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "TOPIC_DRAFT".
*/
public class TopicDraftDao extends AbstractDao<TopicDraft, Long> {

    public static final String TABLENAME = "TOPIC_DRAFT";

    /**
     * Properties of entity TopicDraft.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Content = new Property(1, String.class, "content", false, "CONTENT");
        public final static Property Date = new Property(2, java.util.Date.class, "date", false, "DATE");
        public final static Property Urls = new Property(3, String.class, "urls", false, "URLS");
        public final static Property Uid = new Property(4, String.class, "uid", false, "UID");
        public final static Property TargetTopicJson = new Property(5, String.class, "targetTopicJson", false, "TARGET_TOPIC_JSON");
        public final static Property TargetTopicId = new Property(6, String.class, "targetTopicId", false, "TARGET_TOPIC_ID");
        public final static Property TargetCommentId = new Property(7, String.class, "targetCommentId", false, "TARGET_COMMENT_ID");
        public final static Property TargetCommentUserName = new Property(8, String.class, "targetCommentUserName", false, "TARGET_COMMENT_USER_NAME");
        public final static Property IsCommentOrigin = new Property(9, Integer.class, "isCommentOrigin", false, "IS_COMMENT_ORIGIN");
        public final static Property IsMention = new Property(10, Integer.class, "isMention", false, "IS_MENTION");
        public final static Property Type = new Property(11, Integer.class, "type", false, "TYPE");
        public final static Property ParentType = new Property(12, Integer.class, "parentType", false, "PARENT_TYPE");
    };

    private DaoSession daoSession;


    public TopicDraftDao(DaoConfig config) {
        super(config);
    }
    
    public TopicDraftDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TOPIC_DRAFT\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ASC AUTOINCREMENT ," + // 0: id
                "\"CONTENT\" TEXT NOT NULL ," + // 1: content
                "\"DATE\" INTEGER," + // 2: date
                "\"URLS\" TEXT," + // 3: urls
                "\"UID\" TEXT NOT NULL ," + // 4: uid
                "\"TARGET_TOPIC_JSON\" TEXT," + // 5: targetTopicJson
                "\"TARGET_TOPIC_ID\" TEXT," + // 6: targetTopicId
                "\"TARGET_COMMENT_ID\" TEXT," + // 7: targetCommentId
                "\"TARGET_COMMENT_USER_NAME\" TEXT," + // 8: targetCommentUserName
                "\"IS_COMMENT_ORIGIN\" INTEGER," + // 9: isCommentOrigin
                "\"IS_MENTION\" INTEGER," + // 10: isMention
                "\"TYPE\" INTEGER," + // 11: type
                "\"PARENT_TYPE\" INTEGER);"); // 12: parentType
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TOPIC_DRAFT\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, TopicDraft entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getContent());
 
        java.util.Date date = entity.getDate();
        if (date != null) {
            stmt.bindLong(3, date.getTime());
        }
 
        String urls = entity.getUrls();
        if (urls != null) {
            stmt.bindString(4, urls);
        }
        stmt.bindString(5, entity.getUid());
 
        String targetTopicJson = entity.getTargetTopicJson();
        if (targetTopicJson != null) {
            stmt.bindString(6, targetTopicJson);
        }
 
        String targetTopicId = entity.getTargetTopicId();
        if (targetTopicId != null) {
            stmt.bindString(7, targetTopicId);
        }
 
        String targetCommentId = entity.getTargetCommentId();
        if (targetCommentId != null) {
            stmt.bindString(8, targetCommentId);
        }
 
        String targetCommentUserName = entity.getTargetCommentUserName();
        if (targetCommentUserName != null) {
            stmt.bindString(9, targetCommentUserName);
        }
 
        Integer isCommentOrigin = entity.getIsCommentOrigin();
        if (isCommentOrigin != null) {
            stmt.bindLong(10, isCommentOrigin);
        }
 
        Integer isMention = entity.getIsMention();
        if (isMention != null) {
            stmt.bindLong(11, isMention);
        }
 
        Integer type = entity.getType();
        if (type != null) {
            stmt.bindLong(12, type);
        }
 
        Integer parentType = entity.getParentType();
        if (parentType != null) {
            stmt.bindLong(13, parentType);
        }
    }

    @Override
    protected void attachEntity(TopicDraft entity) {
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
    public TopicDraft readEntity(Cursor cursor, int offset) {
        TopicDraft entity = new TopicDraft( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // content
            cursor.isNull(offset + 2) ? null : new java.util.Date(cursor.getLong(offset + 2)), // date
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // urls
            cursor.getString(offset + 4), // uid
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // targetTopicJson
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // targetTopicId
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // targetCommentId
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // targetCommentUserName
            cursor.isNull(offset + 9) ? null : cursor.getInt(offset + 9), // isCommentOrigin
            cursor.isNull(offset + 10) ? null : cursor.getInt(offset + 10), // isMention
            cursor.isNull(offset + 11) ? null : cursor.getInt(offset + 11), // type
            cursor.isNull(offset + 12) ? null : cursor.getInt(offset + 12) // parentType
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, TopicDraft entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setContent(cursor.getString(offset + 1));
        entity.setDate(cursor.isNull(offset + 2) ? null : new java.util.Date(cursor.getLong(offset + 2)));
        entity.setUrls(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setUid(cursor.getString(offset + 4));
        entity.setTargetTopicJson(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setTargetTopicId(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setTargetCommentId(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setTargetCommentUserName(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setIsCommentOrigin(cursor.isNull(offset + 9) ? null : cursor.getInt(offset + 9));
        entity.setIsMention(cursor.isNull(offset + 10) ? null : cursor.getInt(offset + 10));
        entity.setType(cursor.isNull(offset + 11) ? null : cursor.getInt(offset + 11));
        entity.setParentType(cursor.isNull(offset + 12) ? null : cursor.getInt(offset + 12));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(TopicDraft entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(TopicDraft entity) {
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
