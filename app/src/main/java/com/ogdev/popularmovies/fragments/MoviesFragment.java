package com.ogdev.popularmovies.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesFragment extends Fragment {

    private static final int COLUMNS_DIVIDER_WITH = 400;

    @BindView(R.id.movies_recyclerView)
    RecyclerView mRecyclerView;
    private MoviesAdapter mAdapter;
    private ArrayList<Movie> mMovies;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;

        mMovies = DatabaseUtilities.getMoviesFromDatabase(getContext());
        if (!mMovies.isEmpty()) {
            view = inflater.inflate(R.layout.fragment_movies, container, false);
            ButterKnife.bind(this, view);

            mRecyclerView.setHasFixedSize(true);

            if (getSortOrder() == 1) {
                Collections.sort(mMovies, new PopularSorter());
            } else {
                Collections.sort(mMovies, new RatingSorter());
            }

            mAdapter = new MoviesAdapter(getContext(), mMovies, getFragmentManager());
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
//        mMovies = DatabaseUtilities.getMoviesFromDatabase(getContext());
        mMovies.clear();
        switch (item.getItemId()) {
            case R.id.ic_menu_popular:
                fetchMoviesAndUpdateList(SplashActivity.ORDER_POPULAR, 1);
                return true;
            case R.id.ic_menu_top_rated:
                fetchMoviesAndUpdateList(SplashActivity.ORDER_TOP_RATED, 2);
                return true;
        }

        return false;
    }

    private void fetchMoviesAndUpdateList(String order, int orderValue) {
        FetchMoviesTaskUtilities mTask = new FetchMoviesTaskUtilities(getContext(), order);
        try {
            mMovies = mTask.execute().get();
        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        } finally {
            if (!mMovies.isEmpty()) {
                Collections.sort(mMovies, new RatingSorter());
                setSortOrder(orderValue);
                mAdapter.updateData(mMovies);
            }
        }
    }

    private int getSortOrder() {
        return SharedPreferencesUtilities.getSortOrder(getContext());
    }

    private void setSortOrder(int mSortOrder) {
        SharedPreferencesUtilities.setSortOrder(getContext(), mSortOrder);
    }


}
