package com.example.gebruiker.redditclient;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.gebruiker.redditclient.model.Post;


/**
 * A simple {@link Fragment} subclass.
 */
public class PostDetailFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();
    private Post post;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_post_detail, container, false);

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("post")) {
            post = bundle.getParcelable("post");
        }

        return v;
    }
}
