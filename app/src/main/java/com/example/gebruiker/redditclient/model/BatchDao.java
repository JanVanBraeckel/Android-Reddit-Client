package com.example.gebruiker.redditclient.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.example.gebruiker.redditclient.model.Batch;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "BATCH".
*/
public class BatchDao extends AbstractDao<Batch, Long> {

    public static final String TABLENAME = "BATCH";

    /**
     * Properties of entity Batch.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property After = new Property(1, String.class, "after", false, "AFTER");
        public final static Property Before = new Property(2, String.class, "before", false, "BEFORE");
        public final static Property Subreddit = new Property(3, String.class, "subreddit", false, "SUBREDDIT");
    };

    private DaoSession daoSession;


    public BatchDao(DaoConfig config) {
        super(config);
    }
    
    public BatchDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"BATCH\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"AFTER\" TEXT," + // 1: after
                "\"BEFORE\" TEXT," + // 2: before
                "\"SUBREDDIT\" TEXT);"); // 3: subreddit
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"BATCH\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Batch entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String after = entity.getAfter();
        if (after != null) {
            stmt.bindString(2, after);
        }
 
        String before = entity.getBefore();
        if (before != null) {
            stmt.bindString(3, before);
        }
 
        String subreddit = entity.getSubreddit();
        if (subreddit != null) {
            stmt.bindString(4, subreddit);
        }
    }

    @Override
    protected void attachEntity(Batch entity) {
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
    public Batch readEntity(Cursor cursor, int offset) {
        Batch entity = new Batch( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // after
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // before
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3) // subreddit
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Batch entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setAfter(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setBefore(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setSubreddit(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Batch entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Batch entity) {
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
