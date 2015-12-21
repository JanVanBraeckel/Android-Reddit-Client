package com.example.gebruiker.redditclient.service;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.example.gebruiker.redditclient.R;
import com.example.gebruiker.redditclient.model.Post;
import com.google.gson.Gson;

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
public class PostRequest extends Request<List<Post>> {

    private final List<Post> posts = new ArrayList<>();
    private final Response.Listener<List<Post>> mListener;
    private final Gson mGson = new Gson();

    public PostRequest(int requestMethod, String url, Response.Listener<List<Post>> listener, Response.ErrorListener errorListener) {
        super(requestMethod, url, errorListener);
        mListener = listener;
    }

    @Override
    protected Response<List<Post>> parseNetworkResponse(NetworkResponse response) {
        try {
            JSONObject jsonObject = new JSONObject(new String(response.data, HttpHeaderParser.parseCharset(response.headers)));
            JSONArray array = jsonObject.getJSONObject("data").getJSONArray("children");

            JSONArray filteredArray = new JSONArray();

            for(int i = 0; i < array.length(); i++){
                JSONObject row = array.getJSONObject(i).getJSONObject("data");
                JSONObject filteredRow = new JSONObject();

                filteredRow.put("title", row.getString("title"));
                filteredRow.put("author", row.getString("author"));
                if(row.has("preview")){
                    filteredRow.put("imageUrl", row.getJSONObject("preview").getJSONArray("images").getJSONObject(0).getJSONObject("source").getString("url"));
                }else{
                    filteredRow.put("imageUrl", "");
                }
                filteredRow.put("permalink", row.getString("permalink"));
                filteredRow.put("upvotes", row.getInt("ups"));
                filteredRow.put("thumbnail", row.getString("thumbnail"));

                filteredArray.put(filteredRow);
            }

            Post[] posts = mGson.fromJson(filteredArray.toString(), Post[].class);
            return Response.success(Arrays.asList(posts), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException | JSONException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(List<Post> response) {
        mListener.onResponse(response);
    }
}
