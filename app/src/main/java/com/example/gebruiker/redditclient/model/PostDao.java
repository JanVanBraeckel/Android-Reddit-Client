package com.example.gebruiker.redditclient.model;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.SqlUtils;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

import com.example.gebruiker.redditclient.model.Post;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "POST".
*/
public class PostDao extends AbstractDao<Post, Long> {

    public static final String TABLENAME = "POST";

    /**
     * Properties of entity Post.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Title = new Property(1, String.class, "title", false, "TITLE");
        public final static Property ImageUrl = new Property(2, String.class, "imageUrl", false, "IMAGE_URL");
        public final static Property Author = new Property(3, String.class, "author", false, "AUTHOR");
        public final static Property Permalink = new Property(4, String.class, "permalink", false, "PERMALINK");
        public final static Property Thumbnail = new Property(5, String.class, "thumbnail", false, "THUMBNAIL");
        public final static Property Upvotes = new Property(6, Integer.class, "upvotes", false, "UPVOTES");
        public final static Property Subreddit = new Property(7, String.class, "subreddit", false, "SUBREDDIT");
        public final static Property Url = new Property(8, String.class, "url", false, "URL");
        public final static Property Selftext = new Property(9, String.class, "selftext", false, "SELFTEXT");
        public final static Property BatchId = new Property(10, Long.class, "batchId", false, "BATCH_ID");
    };

    private DaoSession daoSession;

    private Query<Post> batch_PostsQuery;

    public PostDao(DaoConfig config) {
        super(config);
    }
    
    public PostDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"POST\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"TITLE\" TEXT," + // 1: title
                "\"IMAGE_URL\" TEXT," + // 2: imageUrl
                "\"AUTHOR\" TEXT," + // 3: author
                "\"PERMALINK\" TEXT," + // 4: permalink
                "\"THUMBNAIL\" TEXT," + // 5: thumbnail
                "\"UPVOTES\" INTEGER," + // 6: upvotes
                "\"SUBREDDIT\" TEXT," + // 7: subreddit
                "\"URL\" TEXT," + // 8: url
                "\"SELFTEXT\" TEXT," + // 9: selftext
                "\"BATCH_ID\" INTEGER);"); // 10: batchId
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"POST\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Post entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(2, title);
        }
 
        String imageUrl = entity.getImageUrl();
        if (imageUrl != null) {
            stmt.bindString(3, imageUrl);
        }
 
        String author = entity.getAuthor();
        if (author != null) {
            stmt.bindString(4, author);
        }
 
        String permalink = entity.getPermalink();
        if (permalink != null) {
            stmt.bindString(5, permalink);
        }
 
        String thumbnail = entity.getThumbnail();
        if (thumbnail != null) {
            stmt.bindString(6, thumbnail);
        }
 
        Integer upvotes = entity.getUpvotes();
        if (upvotes != null) {
            stmt.bindLong(7, upvotes);
        }
 
        String subreddit = entity.getSubreddit();
        if (subreddit != null) {
            stmt.bindString(8, subreddit);
        }
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(9, url);
        }
 
        String selftext = entity.getSelftext();
        if (selftext != null) {
            stmt.bindString(10, selftext);
        }
 
        Long batchId = entity.getBatchId();
        if (batchId != null) {
            stmt.bindLong(11, batchId);
        }
    }

    @Override
    protected void attachEntity(Post entity) {
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
    public Post readEntity(Cursor cursor, int offset) {
        Post entity = new Post( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // title
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // imageUrl
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // author
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // permalink
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // thumbnail
            cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6), // upvotes
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // subreddit
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // url
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // selftext
            cursor.isNull(offset + 10) ? null : cursor.getLong(offset + 10) // batchId
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Post entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTitle(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setImageUrl(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setAuthor(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setPermalink(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setThumbnail(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setUpvotes(cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6));
        entity.setSubreddit(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setUrl(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setSelftext(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setBatchId(cursor.isNull(offset + 10) ? null : cursor.getLong(offset + 10));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Post entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Post entity) {
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
    
    /** Internal query to resolve the "posts" to-many relationship of Batch. */
    public List<Post> _queryBatch_Posts(Long batchId) {
        synchronized (this) {
            if (batch_PostsQuery == null) {
                QueryBuilder<Post> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.BatchId.eq(null));
                batch_PostsQuery = queryBuilder.build();
            }
        }
        Query<Post> query = batch_PostsQuery.forCurrentThread();
        query.setParameter(0, batchId);
        return query.list();
    }

    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getBatchDao().getAllColumns());
            builder.append(" FROM POST T");
            builder.append(" LEFT JOIN BATCH T0 ON T.\"BATCH_ID\"=T0.\"_id\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected Post loadCurrentDeep(Cursor cursor, boolean lock) {
        Post entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Batch batch = loadCurrentOther(daoSession.getBatchDao(), cursor, offset);
        entity.setBatch(batch);

        return entity;    
    }

    public Post loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<Post> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<Post> list = new ArrayList<Post>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<Post> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<Post> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
