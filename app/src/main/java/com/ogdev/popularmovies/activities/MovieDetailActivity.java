package com.ogdev.popularmovies.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ogdev.popularmovies.R;
import com.ogdev.popularmovies.adapters.MoviesAdapter;
import com.ogdev.popularmovies.adapters.ReviewsAdapter;
import com.ogdev.popularmovies.adapters.VideosAdapter;
import com.ogdev.popularmovies.models.Movie;
import com.ogdev.popularmovies.models.Review;
import com.ogdev.popularmovies.models.Video;
import com.ogdev.popularmovies.utilities.DatabaseUtilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    @BindView(R.id.movie_detail_reviews)
    LinearLayout mReviewsLayout;
    @BindView(R.id.reviews_layout_title)
    TextView mReviewsTextView;
    @BindView(R.id.reviews_layout_recyclerView)
    RecyclerView mReviewsRecyclerView;

    @BindView(R.id.movie_detail_videos)
    LinearLayout mVideosLayout;
    @BindView(R.id.videos_layout_title)
    TextView mVideosTextView;
    @BindView(R.id.videos_layout_recyclerView)
    RecyclerView mVideosRecyclerView;

    private static final int COLUMNS_DIVIDER_WITH = 400;

    public static final String INFO_TYPE_VIDEOS = "videos";
    public static final String INFO_TYPE_REVIEWS = "reviews";

    private Movie mMovie;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            mMovie = mBundle.getParcelable(MoviesAdapter.EXTRA_MOVIE_KEY);
            initToolbar();
            initView();
            fetchReviewsAndDisplay();
            fetchVideosAndDisplay();
        } else {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.movie_detail_menu, menu);

        MenuItem favoriteItem = menu.findItem(R.id.ic_menu_favorite);
        if (mMovie.getFavorite() == 1) {
            favoriteItem.setIcon(R.drawable.ic_favorite_filled);
            favoriteItem.setTitle(R.string.menu_title_non_favorite);
        } else {
            favoriteItem.setIcon(R.drawable.ic_favorite_empty);
            favoriteItem.setTitle(R.string.menu_title_favorite);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.ic_menu_favorite:
                if (mMovie.getFavorite() == 1) {
                    if (DatabaseUtilities.updateFavoriteField(this, mMovie.getId(), 0)) {
                        mMovie.setFavorite(0);
                        item.setTitle(R.string.menu_title_favorite);
                        item.setIcon(R.drawable.ic_favorite_empty);
                    }
                } else {
                    if (DatabaseUtilities.updateFavoriteField(this, mMovie.getId(), 1)) {
                        mMovie.setFavorite(1);
                        item.setTitle(R.string.menu_title_non_favorite);
                        item.setIcon(R.drawable.ic_favorite_filled);
                    }
                }
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

    private void fetchReviewsAndDisplay() {
        ArrayList<Review> reviews = DatabaseUtilities.getReviewsFromMovie(this, mMovie.getMovieId());
        if (!reviews.isEmpty()) {
            ReviewsAdapter reviewsAdapter = new ReviewsAdapter(this, reviews);
            mReviewsRecyclerView.setAdapter(reviewsAdapter);
            GridLayoutManager mLayoutManager =
                    new GridLayoutManager(this, getNumberOfColumns());

            mReviewsRecyclerView.setLayoutManager(mLayoutManager);

            mReviewsLayout.setVisibility(View.VISIBLE);
        }
    }

    private void fetchVideosAndDisplay() {
        ArrayList<Video> videos = DatabaseUtilities.getVideosFromMovie(this, mMovie.getMovieId());
        if (!videos.isEmpty()) {
            VideosAdapter videosAdapter = new VideosAdapter(this, videos);
            mVideosRecyclerView.setHasFixedSize(true);
            mVideosRecyclerView.setAdapter(videosAdapter);

            GridLayoutManager mLayoutManager =
                    new GridLayoutManager(this, getNumberOfColumns());

            mVideosRecyclerView.setLayoutManager(mLayoutManager);

            mVideosLayout.setVisibility(View.VISIBLE);
        }
    }

    private int getNumberOfColumns() {
        DisplayMetrics mMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(mMetrics);

        int mWidth = mMetrics.widthPixels;
        int mColumns = mWidth / COLUMNS_DIVIDER_WITH;
        if (mColumns < 2) {
            return 2;
        }
        return mColumns;
    }
}
