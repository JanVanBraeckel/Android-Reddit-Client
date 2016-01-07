package com.example.gebruiker.redditclient;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.gebruiker.redditclient.model.Post;
import com.example.gebruiker.redditclient.recyclerview.MainAdapter;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MainAdapter.PostClickedListener {
    private final String TAG = getClass().getSimpleName();

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @Bind(R.id.nav_view)
    NavigationView mNavigationView;
    private PostListFragment mPostListFragment;
    private FragmentManager mFragmentManager;
    private ActionBarDrawerToggle mToggle;
    private String currentSubreddit = "Funny";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);


        mToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(mToggle);
        mToggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);

        if (mFragmentManager == null)
            mFragmentManager = getSupportFragmentManager();

        mFragmentManager.addOnBackStackChangedListener(() -> {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                configureWithBackstack();
            } else {
                configureWithoutBackstack();
            }
        });

        mPostListFragment = new PostListFragment();

        mFragmentManager.beginTransaction().replace(R.id.container, mPostListFragment, "list").commit();

        getSupportActionBar().setTitle(currentSubreddit);
    }

    private void configureWithBackstack() {
        mToggle.setDrawerIndicatorEnabled(false);
        mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToggle.setToolbarNavigationClickListener(v -> {
            if (mFragmentManager == null)
                mFragmentManager = getSupportFragmentManager();
            mFragmentManager.popBackStack();
            getSupportActionBar().setTitle(currentSubreddit);
        });
    }

    private void configureWithoutBackstack() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mToggle.setDrawerIndicatorEnabled(true);
        mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.nav_settings) {
            Intent intent = new Intent(this, RedditSettings.class);
            startActivity(intent);
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        currentSubreddit = item.getTitle().toString();

        mPostListFragment.loadPosts(currentSubreddit);

        getSupportActionBar().setTitle(currentSubreddit);

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void postClicked(Post post) {
        if (mFragmentManager == null) {
            mFragmentManager = getSupportFragmentManager();
        }

        getSupportActionBar().setTitle(post.getTitle());

        Bundle args = new Bundle();
        args.putParcelable("post", post);

        PostDetailFragment fragment = new PostDetailFragment();
        fragment.setArguments(args);

        mFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
                .replace(R.id.container, fragment, "detail")
                .addToBackStack(null)
                .commit();
    }
}
