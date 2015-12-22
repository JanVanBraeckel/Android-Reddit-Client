package com.example.gebruiker.redditclient.service;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.gebruiker.redditclient.model.Batch;
import com.example.gebruiker.redditclient.model.Post;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jan on 21/12/2015.
 */
public class RedditService {
    private RequestQueue mRequestQueue;
    private static RedditService instance;
    private Context mContext;
    private final String BASEURL = "http://api.reddit.com";
    private String previousSubreddit = "";
    private String after="";

    public static RedditService getInstance(Context context) {
        if (instance == null) {
            instance = new RedditService(context);
        }

        return instance;
    }

    private RedditService(Context context) {
        mContext = context;
        mRequestQueue = Volley.newRequestQueue(mContext);
    }

    public void getPosts(final VolleyCallback<Batch> callback, String subreddit) {
        callback.onStart();

        if(subreddit != null && !subreddit.isEmpty())
            previousSubreddit = subreddit;

        PostRequest postRequest = new PostRequest(
                Request.Method.GET,
                BASEURL + "/r/" + (subreddit == null || subreddit.isEmpty() ? previousSubreddit : subreddit) + ".json",
                (result) -> {
                    after = result.getAfter();
                    callback.onSuccess(result, false);
                },
                callback::onError
        );

        mRequestQueue.add(postRequest);
    }

    public void getNextPosts(final VolleyCallback<Batch> callback){
        callback.onStart();
        PostRequest postRequest = new PostRequest(
                Request.Method.GET,
                BASEURL + "/r/" + previousSubreddit + ".json" + (after != null && !after.isEmpty() ? "?after="+after:""),
                (result) -> {
                    after = result.getAfter();
                    callback.onSuccess(result, true);
                },
                callback::onError
        );

        mRequestQueue.add(postRequest);
    }

    public interface VolleyCallback<T> {
        void onStart();
        void onSuccess(T result, boolean add);
        void onError(VolleyError error);
    }
}
