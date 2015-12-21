package com.example.gebruiker.redditclient;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.example.gebruiker.redditclient.model.Post;
import com.example.gebruiker.redditclient.service.RedditService;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PostListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    @Bind(R.id.swiperefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Bind(R.id.content_list)
    RecyclerView mRecyclerView;

    private RedditService mRedditService;

    private RedditService.VolleyCallback<List<Post>> mCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_post_list, container, false);

        ButterKnife.bind(this, v);

        mRedditService = RedditService.getInstance(getContext());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        mCallback = new RedditService.VolleyCallback<List<Post>>() {
            @Override
            public void onSuccess(List<Post> result) {
                MainAdapter adapter = new MainAdapter(result, getContext());
                mRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onError(VolleyError error) {
                Log.e("error", error.getMessage());
            }
        };

        mRedditService.getPosts(mCallback, "funny");

        mSwipeRefreshLayout.setOnRefreshListener(this);

        return v;
    }


    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public void loadPosts(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_askreddit) {
            mRecyclerView.setAdapter(null);
            mRedditService.getPosts(mCallback, "askreddit");
        } else if (id == R.id.nav_funny) {
            mRecyclerView.setAdapter(null);
            mRedditService.getPosts(mCallback, "funny");
        } else if (id == R.id.nav_programmerhumor) {
            mRecyclerView.setAdapter(null);
            mRedditService.getPosts(mCallback, "programmerhumor");
        } else if (id == R.id.nav_tifu) {
            mRecyclerView.setAdapter(null);
            mRedditService.getPosts(mCallback, "tifu");
        } else if (id == R.id.nav_wtf) {
            mRecyclerView.setAdapter(null);
            mRedditService.getPosts(mCallback, "wtf");
        } else if (id == R.id.nav_todayilearned) {
            mRecyclerView.setAdapter(null);
            mRedditService.getPosts(mCallback, "todayilearned");
        }
    }

    private class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder>{

        private Context mContext;
        private List<Post> mPosts;

        public class MainViewHolder extends RecyclerView.ViewHolder {
            private final ImageView mImage;
            private final TextView mTitle;

            public MainViewHolder(View itemView) {
                super(itemView);
                mImage = ButterKnife.findById(itemView, R.id.postImage);
                mTitle = ButterKnife.findById(itemView, R.id.postTitle);
            }
        }

        public MainAdapter(List<Post> posts, Context context) {
            mPosts = posts;
            mContext = context;
        }

        @Override
        public MainAdapter.MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MainViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(MainAdapter.MainViewHolder holder, int position) {
            final Post post = mPosts.get(position);

            holder.mTitle.setText(post.getTitle());
            Glide.with(mContext)
                    .load(post.getThumbnail())
                    .placeholder(R.drawable.reddit)
                    .fitCenter()
                    .into(holder.mImage);
        }

        @Override
        public int getItemCount() {
            return mPosts.size();
        }
    }
}
