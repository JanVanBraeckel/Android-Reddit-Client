package com.example.gebruiker.redditclient.service;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.example.gebruiker.redditclient.R;
import com.example.gebruiker.redditclient.model.Batch;
import com.example.gebruiker.redditclient.model.Post;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Jan on 21/12/2015.
 */
public class PostRequest extends Request<Batch> {
    private final Response.Listener<Batch> mListener;
    private final Gson mGson = new GsonBuilder().registerTypeAdapter(Batch.class, new BatchDeserializer()).create();

    public PostRequest(int requestMethod, String url, Response.Listener<Batch> listener, Response.ErrorListener errorListener) {
        super(requestMethod, url, errorListener);
        mListener = listener;
    }

    @Override
    protected Response<Batch> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

            Batch batch = mGson.fromJson(json, Batch.class);

            return Response.success(batch, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(Batch response) {
        mListener.onResponse(response);
    }
}
