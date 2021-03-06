package com.example.gebruiker.redditclient.model;

import com.example.gebruiker.redditclient.model.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
import de.greenrobot.dao.AbstractDao;
import android.os.Parcel;
import android.os.Parcelable;
// KEEP INCLUDES END
/**
 * Entity mapped to table "POST".
 */
public class Post implements Parcelable {

    private Long id;
    private String title;
    private String imageUrl;
    private String author;
    private String permalink;
    private String thumbnail;
    private Integer upvotes;
    private String subreddit;
    private String url;
    private String selftext;
    private Long batchId;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient PostDao myDao;

    private Batch batch;
    private Long batch__resolvedKey;


    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public Post() {
    }

    public Post(Long id) {
        this.id = id;
    }

    public Post(Long id, String title, String imageUrl, String author, String permalink, String thumbnail, Integer upvotes, String subreddit, String url, String selftext, Long batchId) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.author = author;
        this.permalink = permalink;
        this.thumbnail = thumbnail;
        this.upvotes = upvotes;
        this.subreddit = subreddit;
        this.url = url;
        this.selftext = selftext;
        this.batchId = batchId;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPostDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Integer getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(Integer upvotes) {
        this.upvotes = upvotes;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public void setSubreddit(String subreddit) {
        this.subreddit = subreddit;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSelftext() {
        return selftext;
    }

    public void setSelftext(String selftext) {
        this.selftext = selftext;
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    /** To-one relationship, resolved on first access. */
    public Batch getBatch() {
        Long __key = this.batchId;
        if (batch__resolvedKey == null || !batch__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            BatchDao targetDao = daoSession.getBatchDao();
            Batch batchNew = targetDao.load(__key);
            synchronized (this) {
                batch = batchNew;
            	batch__resolvedKey = __key;
            }
        }
        return batch;
    }

    public void setBatch(Batch batch) {
        synchronized (this) {
            this.batch = batch;
            batchId = batch == null ? null : batch.getId();
            batch__resolvedKey = batchId;
        }
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

    public Post(String title, String imageUrl, String author, String permalink, String thumbnail, int upvotes, String subreddit, String url, String selftext) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.author = author;
        this.permalink = permalink;
        this.thumbnail = thumbnail;
        this.upvotes = upvotes;
        this.subreddit = subreddit;
        this.url = url;
        this.selftext = selftext;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(imageUrl);
        dest.writeString(author);
        dest.writeString(permalink);
        dest.writeString(thumbnail);
        dest.writeString(subreddit);
    }

    protected Post(Parcel in) {
        title = in.readString();
        imageUrl = in.readString();
        author = in.readString();
        permalink = in.readString();
        thumbnail = in.readString();
        subreddit = in.readString();
    }

    public static final Parcelable.Creator<Post> CREATOR = new Parcelable.Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };
    // KEEP METHODS END

}
