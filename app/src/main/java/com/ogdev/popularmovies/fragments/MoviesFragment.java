package com.ogdev.popularmovies.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ogdev.popularmovies.R;
import com.ogdev.popularmovies.activities.SplashActivity;
import com.ogdev.popularmovies.adapters.MoviesAdapter;
import com.ogdev.popularmovies.models.Movie;
import com.ogdev.popularmovies.sorters.PopularSorter;
import com.ogdev.popularmovies.sorters.RatingSorter;
import com.ogdev.popularmovies.utilities.DatabaseUtilities;
import com.ogdev.popularmovies.utilities.FetchMoviesTaskUtilities;
import com.ogdev.popularmovies.utilities.SharedPreferencesUtilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

import butterknife.ButterKnife;

public class MoviesFragment extends Fragment {

    private static final int COLUMNS_DIVIDER_WITH = 400;

    private MoviesAdapter mAdapter;
    private ArrayList<Movie> mMovies;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setActionBarTitle();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;

        int sortOrder = getSortOrder();
        mMovies = getMoviesFromSortOrder(sortOrder);

        if (!mMovies.isEmpty()) {
            view = inflater.inflate(R.layout.fragment_movies, container, false);
            ButterKnife.bind(this, view);

            RecyclerView mRecyclerView = view.findViewById(R.id.movies_recyclerView);
            mRecyclerView.setHasFixedSize(true);

            switch (sortOrder) {
                case 1:
                    Collections.sort(mMovies, new PopularSorter());
                    break;
                case 2:
                    Collections.sort(mMovies, new RatingSorter());
                    break;
            }

            mAdapter = new MoviesAdapter(getContext(), mMovies);
            GridLayoutManager mLayoutManager =
                    new GridLayoutManager(getContext(), getNumberOfColumns());

            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(mLayoutManager);
        } else {
            view = inflater.inflate(R.layout.fragment_no_connection, container, false);
            ButterKnife.bind(this, view);
        }

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMovies = getMoviesFromSortOrder(getSortOrder());
        mAdapter.updateData(mMovies);
    }

    private void setActionBarTitle() {
        String title = getTitleFromOrder();
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    private String getTitleFromOrder() {
        switch (getSortOrder()) {
            case 1:
                return getString(R.string.menu_title_popular);
            case 2:
                return getString(R.string.menu_title_top_rated);
            case 3:
                return getString(R.string.menu_title_favorites);
            default:
                return getString(R.string.menu_title_popular);
        }
    }

    private int getNumberOfColumns() {
        DisplayMetrics mMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(mMetrics);

        int mWidth = mMetrics.widthPixels;
        int mColumns = mWidth / COLUMNS_DIVIDER_WITH;
        if (mColumns < 2) {
            return 2;
        }
        return mColumns;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mMovies.clear();
        switch (item.getItemId()) {
            case R.id.ic_menu_popular:
                setSortOrder(1);
                mMovies = DatabaseUtilities.getMoviesFromDatabase(getContext(), SplashActivity.ORDER_POPULAR);
                if (!mMovies.isEmpty()) {
                    mAdapter.updateData(mMovies);
                } else {
                    fetchMoviesAndUpdateList(SplashActivity.ORDER_POPULAR);
                }
                return true;
            case R.id.ic_menu_top_rated:
                setSortOrder(2);
                mMovies = DatabaseUtilities.getMoviesFromDatabase(getContext(), SplashActivity.ORDER_TOP_RATED);
                if (!mMovies.isEmpty()) {
                    mAdapter.updateData(mMovies);
                } else {
                    fetchMoviesAndUpdateList(SplashActivity.ORDER_TOP_RATED);
                }
                return true;
            case R.id.ic_menu_favorite:
                setSortOrder(3);
                mMovies = DatabaseUtilities.getFavoriteMovies(getContext());
                mAdapter.updateData(mMovies);
                return true;
        }

        return false;
    }

    private void fetchMoviesAndUpdateList(String order) {
        FetchMoviesTaskUtilities mTask = new FetchMoviesTaskUtilities(order);
        try {
            mMovies = mTask.execute().get();
        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        } finally {
            if (!mMovies.isEmpty()) {
                DatabaseUtilities.addMoviesToDatabase(getContext(), order, mMovies);
                Collections.sort(mMovies, new RatingSorter());
                mAdapter.updateData(mMovies);
            }
        }
    }

    private ArrayList<Movie> getMoviesFromSortOrder(int sortOrder) {
        switch (sortOrder) {
            case 1:
                return DatabaseUtilities.getMoviesFromDatabase(getContext(), SplashActivity.ORDER_POPULAR);
            case 2:
                return DatabaseUtilities.getMoviesFromDatabase(getContext(), SplashActivity.ORDER_TOP_RATED);
            case 3:
                return DatabaseUtilities.getFavoriteMovies(getContext());
            default:
                return DatabaseUtilities.getMoviesFromDatabase(getContext(), SplashActivity.ORDER_POPULAR);
        }
    }

    private int getSortOrder() {
        return SharedPreferencesUtilities.getSortOrder(getContext());
    }

    private void setSortOrder(int mSortOrder) {
        SharedPreferencesUtilities.setSortOrder(getContext(), mSortOrder);
        setActionBarTitle();
    }
}
