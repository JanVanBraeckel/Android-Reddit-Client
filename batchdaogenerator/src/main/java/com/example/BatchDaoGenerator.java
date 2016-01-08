package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

public class BatchDaoGenerator {
    public static void main(String[] args) throws Exception{
        Schema schema = new Schema(1, "com.example.gebruiker.redditclient.model");
        schema.enableKeepSectionsByDefault();

        Entity batch = schema.addEntity("Batch");
        batch.addIdProperty().autoincrement();
        batch.addStringProperty("after");
        batch.addStringProperty("before");
        batch.addStringProperty("subreddit");

        Entity post = schema.addEntity("Post");
        post.addIdProperty().autoincrement();
        post.addStringProperty("title");
        post.addStringProperty("imageUrl");
        post.addStringProperty("author");
        post.addStringProperty("permalink");
        post.addStringProperty("thumbnail");
        post.addIntProperty("upvotes");
        post.addStringProperty("subreddit");
        post.addStringProperty("url");
        post.addStringProperty("selftext");

        post.implementsInterface("Parcelable");

        Property batchId = post.addLongProperty("batchId").getProperty();
        post.addToOne(batch, batchId);

        ToMany batchToPosts = batch.addToMany(post, batchId);
        batchToPosts.setName("posts");

        new DaoGenerator().generateAll(schema, "../app/src/main/java");
    }
}
