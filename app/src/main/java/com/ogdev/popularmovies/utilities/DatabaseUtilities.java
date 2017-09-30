package com.ogdev.popularmovies.utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.ogdev.popularmovies.database.DBContract.MovieColumns;
import com.ogdev.popularmovies.database.DBContract.MovieEntry;
import com.ogdev.popularmovies.models.Movie;

import java.util.ArrayList;

public class DatabaseUtilities {

    public static void addMovieToDatabase(Context context, Movie movie) {
        ContentValues contentValues = new ContentValues();
        setContentValuesFromMovie(contentValues, movie);
        context.getContentResolver().insert(MovieEntry.CONTENT_URI, contentValues);
    }

    public static void addMoviesToDatabase(Context context, ArrayList<Movie> movies) {
        ContentValues[] contentValuesArray = new ContentValues[movies.size()];
        for (int i = 0 ; i < movies.size() ; i++) {
            Movie movie = movies.get(i);
            ContentValues contentValues = new ContentValues();
            setContentValuesFromMovie(contentValues, movie);
            contentValuesArray[i] = contentValues;
        }
        context.getContentResolver().bulkInsert(MovieEntry.CONTENT_URI, contentValuesArray);
    }

    public static ArrayList<Movie> getMoviesFromDatabase(Context context) {
        ArrayList<Movie> movies = new ArrayList<>();

        Cursor cursor = context.getContentResolver().query(MovieEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Movie movie = new Movie();
                setMovieDataFromCursor(cursor, movie);
                movies.add(movie);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return movies;
    }

    private static void setContentValuesFromMovie(ContentValues contentValues, Movie movie) {
        contentValues.put(MovieColumns.COLUMN_NAME_TITLE, movie.getTitle());
        contentValues.put(MovieColumns.COLUMN_NAME_OVERVIEW, movie.getOverview());
        contentValues.put(MovieColumns.COLUMN_NAME_POSTER_PATH, movie.getPosterPath());
        contentValues.put(MovieColumns.COLUMN_NAME_BACKDROP_PATH, movie.getBackdropPath());
        contentValues.put(MovieColumns.COLUMN_NAME_VOTE_AVERAGE, movie.getVoteAverage());
        contentValues.put(MovieColumns.COLUMN_NAME_VOTE_COUNT, movie.getVoteCount());
        contentValues.put(MovieColumns.COLUMN_NAME_RELEASE_DATE, movie.getReleaseDate());
        contentValues.put(MovieColumns.COLUMN_NAME_POPULARITY, movie.getPopularity());
        contentValues.put(MovieColumns.COLUMN_NAME_FAVORITE, movie.getFavorite());
    }

    private static void setMovieDataFromCursor(Cursor cursor, Movie movie) {
        int id = cursor.getInt(cursor.getColumnIndex(MovieColumns._ID));
        String title = cursor.getString(cursor.getColumnIndex(MovieColumns.COLUMN_NAME_TITLE));
        String overview = cursor.getString(cursor.getColumnIndex(MovieColumns.COLUMN_NAME_OVERVIEW));
        String posterPath = cursor.getString(cursor.getColumnIndex(MovieColumns.COLUMN_NAME_POSTER_PATH));
        String backdropPath = cursor.getString(cursor.getColumnIndex(MovieColumns.COLUMN_NAME_BACKDROP_PATH));
        double voteAverage = cursor.getDouble(cursor.getColumnIndex(MovieColumns.COLUMN_NAME_VOTE_AVERAGE));
        long voteCount = cursor.getLong(cursor.getColumnIndex(MovieColumns.COLUMN_NAME_VOTE_COUNT));
        String releaseDate = cursor.getString(cursor.getColumnIndex(MovieColumns.COLUMN_NAME_RELEASE_DATE));
        float popularity = cursor.getFloat(cursor.getColumnIndex(MovieColumns.COLUMN_NAME_POPULARITY));
        int favorite = cursor.getInt(cursor.getColumnIndex(MovieColumns.COLUMN_NAME_FAVORITE));

        movie.setId(id);
        movie.setTitle(title);
        movie.setOverview(overview);
        movie.setPosterPath(posterPath);
        movie.setBackdropPath(backdropPath);
        movie.setVoteAverage(voteAverage);
        movie.setVoteCount(voteCount);
        movie.setReleaseDate(releaseDate);
        movie.setPopularity(popularity);
        movie.setFavorite(favorite);
    }
}
