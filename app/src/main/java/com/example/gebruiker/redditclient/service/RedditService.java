package com.example.gebruiker.redditclient.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.gebruiker.redditclient.R;
import com.example.gebruiker.redditclient.model.Batch;
import com.example.gebruiker.redditclient.model.BatchDao;
import com.example.gebruiker.redditclient.model.DaoMaster;
import com.example.gebruiker.redditclient.model.DaoSession;
import com.example.gebruiker.redditclient.model.Post;
import com.example.gebruiker.redditclient.model.PostDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jan on 21/12/2015.
 */
public class RedditService implements SharedPreferences.OnSharedPreferenceChangeListener{
    private final String TAG = "RedditService";

    private RequestQueue mRequestQueue;
    private static RedditService instance;
    private Context mContext;
    private final String BASEURL = "http://api.reddit.com";
    private String previousSubreddit = "";
    private List<Long> batchIds;
    private String after = "";
    private BatchDao mBatchDao;
    private PostDao mPostDao;
    private int postsPerBatch;

    public static RedditService getInstance(Context context) {
        if (instance == null) {
            instance = new RedditService(context);
        }

        return instance;
    }

    private RedditService(Context context) {
        mContext = context;
        mRequestQueue = Volley.newRequestQueue(mContext);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        postsPerBatch = Integer.parseInt(sharedPreferences.getString(mContext.getString(R.string.batch_size), "25"));

        Log.e(TAG, "RedditService() postsBerBatch: " +  postsPerBatch);

        initDatabase();
    }

    public void getPosts(final VolleyCallback<Batch> callback, String subreddit) {
        callback.onStart();

        if (subreddit != null && !subreddit.isEmpty()) {
            if (!subreddit.equals(previousSubreddit)) {
                previousSubreddit = subreddit;
                batchIds = new ArrayList<>();
            }
        }

        Batch batch = getBatch(subreddit);

        if (batch != null && batch.getPosts() != null && !batch.getPosts().isEmpty()) {
            batchIds.add(batch.getId());
            after = batch.getAfter();
            callback.onSuccess(batch, false);
            Log.e(TAG, "GETPOSTS NO NETWORK CALL");
        } else {
            Log.e(TAG, "GETPOSTS NETWORK CALL");
            Log.e(TAG, "Get " + postsPerBatch + " posts");
            String req = BASEURL + "/r/" + (subreddit == null || subreddit.isEmpty() ? previousSubreddit : subreddit) + ".json?limit=" + postsPerBatch;
            Log.e("Request", req);
            PostRequest postRequest = new PostRequest(
                    Request.Method.GET,
                    BASEURL + "/r/" + (subreddit == null || subreddit.isEmpty() ? previousSubreddit : subreddit) + ".json?limit=" + postsPerBatch,
                    (result) -> {
                        after = result.getAfter();
                        mBatchDao.insert(result);
                        batchIds.add(result.getId());
                        List<Post> posts = result.getPosts();
                        Log.e(TAG, "getPosts() returned: " + posts.size() + " posts");
                        for (Post post : posts) {
                            post.setBatchId(result.getId());
                            mPostDao.insertOrReplace(post);
                        }
                        callback.onSuccess(result, false);
                    },
                    callback::onError
            );

            mRequestQueue.add(postRequest);
        }
    }

    public void getNextPosts(final VolleyCallback<Batch> callback) {
        callback.onStart();

        Batch batch = getBatch(previousSubreddit);

        if (batch != null && batch.getPosts() != null && !batch.getPosts().isEmpty()) {
            batchIds.add(batch.getId());
            after = batch.getAfter();
            callback.onSuccess(batch, true);
            Log.e(TAG, "GETNEXTPOSTS NO NETWORK CALL");
        } else {
            Log.e(TAG, "GETNEXTPOSTS NETWORK CALL");
            Log.e(TAG, "Get " + postsPerBatch + " posts");
            PostRequest postRequest = new PostRequest(
                    Request.Method.GET,
                    BASEURL + "/r/" + previousSubreddit + ".json?limit=" + postsPerBatch + (after != null && !after.isEmpty() ? "&after=" + after : ""),
                    (result) -> {
                        after = result.getAfter();
                        mBatchDao.insert(result);
                        batchIds.add(result.getId());
                        List<Post> posts = result.getPosts();
                        Log.e(TAG, "getPosts() returned: " + posts.size() + " posts");
                        for (Post post : posts) {
                            post.setBatchId(result.getId());
                            mPostDao.insertOrReplace(post);
                        }
                        callback.onSuccess(result, true);
                    },
                    callback::onError
            );

            mRequestQueue.add(postRequest);
        }
    }

    public void refreshPosts(VolleyCallback<Batch> callback) {
        callback.onStart();

        batchIds = new ArrayList<>();

        mBatchDao.queryBuilder()
                .where(BatchDao.Properties.Subreddit.eq(previousSubreddit))
                .buildDelete()
                .executeDeleteWithoutDetachingEntities();

        mPostDao.queryBuilder()
                .where(PostDao.Properties.Subreddit.eq(previousSubreddit))
                .buildDelete()
                .executeDeleteWithoutDetachingEntities();

        Log.e(TAG, "Get " + postsPerBatch + " posts");
        PostRequest postRequest = new PostRequest(
                Request.Method.GET,
                BASEURL + "/r/" + previousSubreddit + ".json?limit=" + postsPerBatch,
                (result) -> {
                    after = result.getAfter();
                    mBatchDao.insert(result);
                    batchIds.add(result.getId());
                    List<Post> posts = result.getPosts();
                    Log.e(TAG, "getPosts() returned: " + posts.size() + " posts");
                    for (Post post : posts) {
                        post.setBatchId(result.getId());
                        mPostDao.insertOrReplace(post);
                    }
                    callback.onSuccess(result, false);
                },
                callback::onError
        );

        mRequestQueue.add(postRequest);
    }

    private void initDatabase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(mContext, "reddit-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();

        mBatchDao = daoSession.getBatchDao();
        mPostDao = daoSession.getPostDao();
    }

    public void destroyed() {
        batchIds = new ArrayList<>();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals(mContext.getString(R.string.batch_size)))
            postsPerBatch = Integer.parseInt(sharedPreferences.getString(key, "25"));

        Log.e(TAG, "onSharedPreferenceChanged() returned: " + postsPerBatch);
    }

    public interface VolleyCallback<T> {
        void onStart();

        void onSuccess(T result, boolean add);

        void onError(VolleyError error);
    }

    private Batch getBatch(String sub) {
        List<Batch> batches = new ArrayList<>();

        String subreddit;

        if (sub == null) {
            subreddit = previousSubreddit;
        } else {
            subreddit = sub;
        }

        if (after != null) {
            batches = mBatchDao.queryBuilder()
                    .where(BatchDao.Properties.Subreddit.eq(subreddit))
                    .list();
        }

        if (!batches.isEmpty()) {
            if (batchIds.isEmpty()) {
                return batches.get(0);
            }

            Batch batch = null;

            List<Batch> possible = new ArrayList<>();
            for (Batch bat : batches) {
                if (!batchIds.contains(bat.getId())) {
                    possible.add(bat);
                }
            }

            if (!possible.isEmpty())
                batch = possible.get(0);

            if (batch == null) {
                return null;
            }

            if (batch.getAfter() != null)
                after = batch.getAfter();

            batch.getPosts();
            return batch;
        }

        return null;
    }
}
