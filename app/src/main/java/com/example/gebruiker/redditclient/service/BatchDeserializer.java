package com.example.gebruiker.redditclient.service;

import com.example.gebruiker.redditclient.R;
import com.example.gebruiker.redditclient.model.Batch;
import com.example.gebruiker.redditclient.model.Post;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jan on 22/12/2015.
 */
public class BatchDeserializer implements JsonDeserializer<Batch> {
    @Override
    public Batch deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject().getAsJsonObject("data");

        final Batch batch = new Batch();

        if (!jsonObject.get("after").isJsonNull())
            batch.setAfter(jsonObject.get("after").getAsString());
        if (!jsonObject.get("before").isJsonNull())
            batch.setBefore(jsonObject.get("before").getAsString());

        final List<Post> batchPosts = new ArrayList<>();

        final JsonArray postArray = jsonObject.getAsJsonArray("children");

        for (JsonElement element : postArray) {
            JsonObject data = element.getAsJsonObject().get("data").getAsJsonObject();

            String title = data.get("title").getAsString();
            String author = data.get("author").getAsString();
            String imageUrl = "";
            if (data.has("preview"))
                imageUrl = data.getAsJsonObject("preview").getAsJsonArray("images").get(0).getAsJsonObject().getAsJsonObject("source").get("url").getAsString();
            String permalink = data.get("permalink").getAsString();
            int upvotes = data.get("ups").getAsInt();
            String thumbnail = data.get("thumbnail").getAsString();
            String subreddit = data.get("subreddit").getAsString().toLowerCase().replace(" ", "");
            String url = data.get("url").getAsString();
            String selftext = "";
            if(data.has("selftext") && !data.get("selftext").isJsonNull()){
                selftext = data.get("selftext").getAsString();
            }
            batch.setSubreddit(subreddit);

            batchPosts.add(new Post(title, imageUrl, author, permalink, thumbnail, upvotes, subreddit, url, selftext));
        }

        batch.setPosts(batchPosts);

        return batch;
    }
}
