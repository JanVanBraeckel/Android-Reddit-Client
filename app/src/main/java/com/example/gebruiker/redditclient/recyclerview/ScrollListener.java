package com.example.gebruiker.redditclient.recyclerview;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.TranslateAnimation;

/**
 * Created by Jan on 22/12/2015.
 */
public abstract class ScrollListener extends RecyclerView.OnScrollListener {
    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = true; // True if we are still waiting for the last set of data to load.
    private int visibleThreshold = 5; // The minimum amount of items to have below your current scroll position before loading more.
    int firstVisibleItem, visibleItemCount, totalItemCount;

    private FloatingActionButton mScrollToTop;
    private LinearLayoutManager mLinearLayoutManager;

    public ScrollListener(LinearLayoutManager linearLayoutManager, FloatingActionButton scrollToTop) {
        this.mLinearLayoutManager = linearLayoutManager;
        mScrollToTop = scrollToTop;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mLinearLayoutManager.getItemCount();
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

        if(firstVisibleItem > 10){
            mScrollToTop.setVisibility(View.VISIBLE);
        }else{
            mScrollToTop.setVisibility(View.GONE);
        }

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading && (totalItemCount - visibleItemCount)
                <= (firstVisibleItem + visibleThreshold)) {
            // End has been reached

            onLoadMore();

            loading = true;
        }
    }

    public void reset() {
        previousTotal = 0;
        loading = true;
    }

    public abstract void onLoadMore();
}
