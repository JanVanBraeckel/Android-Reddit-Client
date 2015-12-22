package com.example.gebruiker.redditclient.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Jan on 22/12/2015.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Batch {
    private List<Post> posts = new ArrayList<>();
    private String after;
}
