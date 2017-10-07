package com.ogdev.popularmovies.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.ogdev.popularmovies.R;
import com.ogdev.popularmovies.fragments.MoviesFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String ARE_FRAGMENTS_FLAG = "ARE_FRAGMENTS_FLAG";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private ActionBar mActionBar;
    private FragmentManager mFragmentManager;
    private boolean areFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            areFragments = savedInstanceState.getBoolean(ARE_FRAGMENTS_FLAG);
        }
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.addOnBackStackChangedListener(this);

        initToolbar();
        if (!areFragments) {
            initMainFragment();
        }
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);

        mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setHomeButtonEnabled(true);
        }
    }

    private void initMainFragment() {
        mFragmentManager.beginTransaction()
                .replace(R.id.activity_main_frameLayout, new MoviesFragment(),
                        MoviesFragment.class.getSimpleName())
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        areFragments = (!mFragmentManager.getFragments().isEmpty());
        if (areFragments) {
            outState.putBoolean(ARE_FRAGMENTS_FLAG, true);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackStackChanged() {
        if (mFragmentManager.getBackStackEntryCount() > 0) {
            if (mActionBar != null) {
                mActionBar.setDisplayHomeAsUpEnabled(true);
            }
        } else {
            mActionBar.setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mFragmentManager.getBackStackEntryCount() > 0) {
            mFragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
