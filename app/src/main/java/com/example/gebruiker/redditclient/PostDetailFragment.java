package com.example.gebruiker.redditclient;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gebruiker.redditclient.model.Post;

import org.markdown4j.Markdown4jProcessor;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class PostDetailFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();
    private Post post;

    @Bind(R.id.postTitle)
    TextView mPostTitle;
    @Bind(R.id.postImage)
    ImageView mPostImage;
    @Bind(R.id.postUrl)
    TextView mPostUrl;
    @Bind(R.id.postBody)
    TextView mPostBody;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_post_detail, container, false);

        ButterKnife.bind(this, v);

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("post")) {
            post = bundle.getParcelable("post");
            loadDetails();
        }

        ((AppCompatActivity )getActivity()).getSupportActionBar().setTitle(post.getTitle());

        return v;
    }

    private void loadDetails() {
        mPostTitle.setText(post.getTitle());

        if(post.getImageUrl().isEmpty()){
            Glide.with(getContext())
                    .load(R.drawable.reddit)
                    .fitCenter()
                    .into(mPostImage);
        }else{
        Glide.with(getContext())
                .load(post.getImageUrl())
                .fitCenter()
                .into(mPostImage);
        }

        mPostUrl.setText(Html.fromHtml("<a href=" + post.getUrl() + ">" + post.getUrl() + "</a>"));
        mPostUrl.setMovementMethod(LinkMovementMethod.getInstance());

        mPostBody.setText(Html.fromHtml(formatText(post.getSelftext())));
        mPostBody.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private String formatText(String selftext){
        try {
           String html =  new Markdown4jProcessor().process(selftext);
            return html;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return selftext;
    }
}
