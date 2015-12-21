package com.example.gebruiker.redditclient.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Created by Jan on 21/12/2015.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Post {
    private String title, imageUrl, author, permalink, thumbnail;
    private int upvotes;
}
