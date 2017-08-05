package com.ogdev.popularmovies.utilities;

import android.content.Context;

import com.ogdev.popularmovies.models.Movie;
import com.ogdev.popularmovies.provider.movie.MovieColumns;
import com.ogdev.popularmovies.provider.movie.MovieContentValues;
import com.ogdev.popularmovies.provider.movie.MovieCursor;
import com.ogdev.popularmovies.provider.movie.MovieSelection;

import java.util.ArrayList;

public class DatabaseUtilities {

    public static void addMovieToContentProvider(Context context, Movie movie) {
        MovieContentValues movieContentValues = new MovieContentValues();
        movieContentValues.putID(movie.getId());
        movieContentValues.putTitle(movie.getTitle());
        movieContentValues.putReleaseDate(movie.getReleaseDate());
        movieContentValues.putVoteAverage((float) movie.getVoteAverage());
        movieContentValues.putOverview(movie.getOverview());
        movieContentValues.putPoster(movie.getPosterPath());
        movieContentValues.putVoteCount((int) movie.getVoteCount());
        movieContentValues.putIsFavourite(true);
        movieContentValues.putBackdrop(movie.getBackdropPath());
        movieContentValues.putPopularity(movie.getPopularity());
        context.getContentResolver().insert(MovieColumns.CONTENT_URI, movieContentValues.values());
    }

    public static ArrayList<Movie> getMoviesFromDatabase(Context context) {
        ArrayList<Movie> mMovies = new ArrayList<>();

        MovieSelection mWhere = new MovieSelection();
        MovieCursor mCursor = mWhere.query(context);
        if (mCursor != null) {
            try {
                while (mCursor.moveToNext()) {
                    Movie mMovie = new Movie(mCursor.getId(), mCursor.getTitle(), mCursor.getOverview(),
                            mCursor.getReleaseDate(), mCursor.getPoster(), mCursor.getBackdrop(),
                            mCursor.getVoteAverage(), mCursor.getVoteCount(), mCursor.getPopularity());

                    mMovies.add(mMovie);
                }
            } finally {
                mCursor.close();
            }
        }

        return mMovies;
    }
}
