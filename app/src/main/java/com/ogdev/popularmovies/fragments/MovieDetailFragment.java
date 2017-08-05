package com.ogdev.popularmovies.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
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

public class MovieDetailFragment extends Fragment {

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        ButterKnife.bind(this, view);

        Bundle mBundle = getArguments();
        mMovie = mBundle.getParcelable(MoviesAdapter.EXTRA_MOVIE_ID);
        initView();

        return view;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.ic_menu_popular).setVisible(false);
        menu.findItem(R.id.ic_menu_top_rated).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onPause() {
        super.onPause();
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
