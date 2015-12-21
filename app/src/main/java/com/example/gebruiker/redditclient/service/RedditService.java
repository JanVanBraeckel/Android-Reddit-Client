package com.example.gebruiker.redditclient.service;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
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

    public void getPosts(final VolleyCallback<List<Post>> callback, String subreddit) {
        PostRequest postRequest = new PostRequest(Request.Method.GET, BASEURL + "/r/" + subreddit + ".json", new Response.Listener<List<Post>>() {
            @Override
            public void onResponse(List<Post> response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error);
            }
        });


        mRequestQueue.add(postRequest);
    }

    public interface VolleyCallback<T> {
        void onSuccess(T result);

        void onError(VolleyError error);
    }
}
