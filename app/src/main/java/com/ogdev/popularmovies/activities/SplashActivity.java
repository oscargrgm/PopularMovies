package com.ogdev.popularmovies.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ogdev.popularmovies.R;
import com.ogdev.popularmovies.models.Movie;
import com.ogdev.popularmovies.utilities.DatabaseUtilities;
import com.ogdev.popularmovies.utilities.FetchMoviesTaskUtilities;
import com.ogdev.popularmovies.utilities.NetworkUtilities;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    private final static String LOG_TAG = SplashActivity.class.getSimpleName();
    public final static String ORDER_POPULAR = "popular";
    public final static String ORDER_TOP_RATED = "top_rated";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        if (!DatabaseUtilities.getMoviesFromDatabase(getApplicationContext()).isEmpty()) {
            startMainActivity();
        } else if (NetworkUtilities.isConnected(this)) {
            FetchMoviesTaskUtilities mTask = new FetchMoviesTaskUtilities(this, ORDER_POPULAR);
            ArrayList<Movie> mMovies = new ArrayList<>();
            try {
                mMovies = mTask.execute().get();
            } catch (InterruptedException | ExecutionException ex) {
                ex.printStackTrace();
            } finally {
                if (!mMovies.isEmpty()) {
                    DatabaseUtilities.addMoviesToDatabase(this, mMovies);
                }
                startMainActivity();
            }
        }
    }

    private void startMainActivity() {
        Intent mIntent = new Intent(this, MainActivity.class);
        startActivity(mIntent);
        finish();
    }
}
