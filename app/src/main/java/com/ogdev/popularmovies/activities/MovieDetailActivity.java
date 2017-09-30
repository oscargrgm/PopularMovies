package com.ogdev.popularmovies.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ogdev.popularmovies.R;
import com.ogdev.popularmovies.adapters.MoviesAdapter;
import com.ogdev.popularmovies.models.Movie;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.movie_detail_poster_imageView)
    ImageView mMoviePoster;
    @BindView(R.id.movie_detail_title_textView)
    TextView mMovieTitle;
    @BindView(R.id.movie_detail_release_date_textView)
    TextView mMovieReleaseDate;
    @BindView(R.id.movie_detail_rating_textView)
    TextView mMovieRating;
    @BindView(R.id.movie_detail_synopsis_textView)
    TextView mMovieSynopsis;

    private Movie mMovie;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            mMovie = mBundle.getParcelable(MoviesAdapter.EXTRA_MOVIE_ID);
            initToolbar();
            initView();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return false;
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(mMovie.getTitle());
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initView() {
        Glide.with(this)
                .load(mMovie.getPosterURI("w185", "poster"))
                .placeholder(R.drawable.ic_movie_roll)
                .error(R.drawable.ic_filmstrip_off)
                .centerCrop()
                .into(mMoviePoster);
        mMovieTitle.setText(mMovie.getTitle());

        String mReleaseDate = mMovie.getReleaseDate();
        SimpleDateFormat mInputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat mOutputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            Date mDate = mInputFormat.parse(mReleaseDate);
            mReleaseDate = mOutputFormat.format(mDate);
        } catch (ParseException ex) {
            ex.printStackTrace();
        } finally {
            mMovieReleaseDate.setText("(" + mReleaseDate + ")");
        }

        mMovieRating.setText(String.valueOf(mMovie.getVoteAverage()).substring(0, 3));
        mMovieSynopsis.setText(mMovie.getOverview());
    }


}
