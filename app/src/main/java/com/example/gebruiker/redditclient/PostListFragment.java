package com.example.gebruiker.redditclient;


import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.example.gebruiker.redditclient.model.Batch;
import com.example.gebruiker.redditclient.recyclerview.MainAdapter;
import com.example.gebruiker.redditclient.recyclerview.ScrollListener;
import com.example.gebruiker.redditclient.service.RedditService;


import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PostListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.swiperefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Bind(R.id.content_list)
    RecyclerView mRecyclerView;

    @Bind(R.id.fab)
    FloatingActionButton mScrollToTop;

    private RedditService mRedditService;

    private RedditService.VolleyCallback<Batch> mCallback;

    private ScrollListener mScrollListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_post_list, container, false);

        ButterKnife.bind(this, v);

        mRedditService = RedditService.getInstance(getContext());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        mScrollListener = new ScrollListener(layoutManager, mScrollToTop) {
            @Override
            public void onLoadMore() {
                mRedditService.getNextPosts(mCallback);
            }
        };

        mRecyclerView.addOnScrollListener(mScrollListener);

        mCallback = new RedditService.VolleyCallback<Batch>() {
            @Override
            public void onStart() {
                mSwipeRefreshLayout.setRefreshing(true);
            }

            @Override
            public void onSuccess(Batch result, boolean add) {
                mSwipeRefreshLayout.setRefreshing(false);
                if (add) {
                    ((MainAdapter) mRecyclerView.getAdapter()).addItems(result.getPosts());
                } else {
                    MainAdapter adapter = new MainAdapter(result.getPosts(), getContext());
                    mRecyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onError(VolleyError error) {
                mSwipeRefreshLayout.setRefreshing(false);
                Log.e("error", error.getMessage());
            }
        };

        mRedditService.getPosts(mCallback, "funny");

        mSwipeRefreshLayout.setOnRefreshListener(this);

        return v;
    }

    @Override
    public void onDestroy() {
        mRedditService.destroyed();
        super.onDestroy();
    }

    @Override
    public void onRefresh() {
        mScrollListener.reset();
        mRecyclerView.setAdapter(null);
        mRedditService.refreshPosts(mCallback);
    }

    public void loadPosts(MenuItem item) {
        mScrollListener.reset();
        mRecyclerView.setAdapter(null);

        final String subreddit = item.getTitle().toString().toLowerCase().replace(" ", "");
        mRedditService.getPosts(mCallback, subreddit);
    }

    @OnClick(R.id.fab)
    public void fabClicked() {
        mRecyclerView.smoothScrollToPosition(0);
    }
}
