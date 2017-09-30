package com.ogdev.popularmovies.utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.ogdev.popularmovies.database.DBContract.MovieColumns;
import com.ogdev.popularmovies.database.DBContract.MovieEntry;
import com.ogdev.popularmovies.database.DBContract.VideoColumns;
import com.ogdev.popularmovies.database.DBContract.VideoEntry;
import com.ogdev.popularmovies.database.DBContract.ReviewColumns;
import com.ogdev.popularmovies.database.DBContract.ReviewEntry;
import com.ogdev.popularmovies.models.Movie;
import com.ogdev.popularmovies.models.Review;
import com.ogdev.popularmovies.models.Video;

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

    public static void addVideosToDatabase(Context context, ArrayList<Video> videos) {
        ContentValues[] contentValuesArray = new ContentValues[videos.size()];
        for (int i = 0 ; i < videos.size() ; i++) {
            Video video = videos.get(i);
            ContentValues contentValues = new ContentValues();
            setContentValuesFromVideo(contentValues, video);
            contentValuesArray[i] = contentValues;
        }
        context.getContentResolver().bulkInsert(VideoEntry.CONTENT_URI, contentValuesArray);
    }

    public static void addReviewsToDatabase(Context context, ArrayList<Review> reviews) {
        ContentValues[] contentValuesArray = new ContentValues[reviews.size()];
        for (int i = 0 ; i < reviews.size() ; i++) {
            Review review = reviews.get(i);
            ContentValues contentValues = new ContentValues();
            setContentValuesFromReview(contentValues, review);
            contentValuesArray[i] = contentValues;
        }
        context.getContentResolver().bulkInsert(ReviewEntry.CONTENT_URI, contentValuesArray);
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
                Movie movie = getMovieFromCursor(cursor);
                movies.add(movie);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return movies;
    }

    public static ArrayList<Video> getVideosFromMovie(Context context, int movieId) {
        ArrayList<Video> videos = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(VideoEntry.CONTENT_URI,
                null,
                VideoColumns.COLUMN_NAME_MOVIE_ID + "=?",
                new String[] {
                    String.valueOf(movieId)
                },
                null
        );
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Video video = getVideoFromCursor(cursor);
                videos.add(video);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return videos;
    }

    public static ArrayList<Review> getReviewsFromMovie(Context context, int movieId) {
        ArrayList<Review> reviews = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(ReviewEntry.CONTENT_URI,
                null,
                ReviewColumns.COLUMN_NAME_MOVIE_ID + "=?",
                new String[] {
                        String.valueOf(movieId)
                },
                null
        );
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Review review = getReviewFromCursor(cursor);
                reviews.add(review);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return reviews;
    }
    private static void setContentValuesFromMovie(ContentValues contentValues, Movie movie) {
        contentValues.put(MovieColumns.COLUMN_NAME_MOVIE_ID, movie.getMovieId());
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

    private static void setContentValuesFromVideo(ContentValues contentValues, Video video) {
        contentValues.put(VideoColumns.COLUMN_NAME_ISO6391, video.getIso6391());
        contentValues.put(VideoColumns.COLUMN_NAME_ISO31661, video.getIso31661());
        contentValues.put(VideoColumns.COLUMN_NAME_KEY, video.getKey());
        contentValues.put(VideoColumns.COLUMN_NAME_NAME, video.getName());
        contentValues.put(VideoColumns.COLUMN_NAME_SITE, video.getSite());
        contentValues.put(VideoColumns.COLUMN_NAME_SIZE, video.getSize());
        contentValues.put(VideoColumns.COLUMN_NAME_TYPE, video.getType());
        contentValues.put(VideoColumns.COLUMN_NAME_MOVIE_ID, video.getMovieId());
    }

    private static void setContentValuesFromReview(ContentValues contentValues, Review review) {
        contentValues.put(ReviewColumns.COLUMN_NAME_AUTHOR, review.getAuthor());
        contentValues.put(ReviewColumns.COLUMN_NAME_CONTENT, review.getContent());
        contentValues.put(ReviewColumns.COLUMN_NAME_URL, review.getUrl());
        contentValues.put(ReviewColumns.COLUMN_NAME_MOVIE_ID, review.getMovieId());
    }

    private static Movie getMovieFromCursor(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(MovieColumns._ID));
        int movieId = cursor.getInt(cursor.getColumnIndex(MovieColumns.COLUMN_NAME_MOVIE_ID));
        String title = cursor.getString(cursor.getColumnIndex(MovieColumns.COLUMN_NAME_TITLE));
        String overview = cursor.getString(cursor.getColumnIndex(MovieColumns.COLUMN_NAME_OVERVIEW));
        String posterPath = cursor.getString(cursor.getColumnIndex(MovieColumns.COLUMN_NAME_POSTER_PATH));
        String backdropPath = cursor.getString(cursor.getColumnIndex(MovieColumns.COLUMN_NAME_BACKDROP_PATH));
        double voteAverage = cursor.getDouble(cursor.getColumnIndex(MovieColumns.COLUMN_NAME_VOTE_AVERAGE));
        long voteCount = cursor.getLong(cursor.getColumnIndex(MovieColumns.COLUMN_NAME_VOTE_COUNT));
        String releaseDate = cursor.getString(cursor.getColumnIndex(MovieColumns.COLUMN_NAME_RELEASE_DATE));
        float popularity = cursor.getFloat(cursor.getColumnIndex(MovieColumns.COLUMN_NAME_POPULARITY));
        int favorite = cursor.getInt(cursor.getColumnIndex(MovieColumns.COLUMN_NAME_FAVORITE));

        return new Movie(id, movieId, title, overview,releaseDate, posterPath, backdropPath,
                voteAverage, voteCount, popularity, favorite);
    }

    private static Video getVideoFromCursor(Cursor cursor) {
        String id = cursor.getString(cursor.getColumnIndex(VideoColumns._ID));
        String iso6391 = cursor.getString(cursor.getColumnIndex(VideoColumns.COLUMN_NAME_ISO6391));
        String iso31661 = cursor.getString(cursor.getColumnIndex(VideoColumns.COLUMN_NAME_ISO31661));
        String key = cursor.getString(cursor.getColumnIndex(VideoColumns.COLUMN_NAME_KEY));
        String name = cursor.getString(cursor.getColumnIndex(VideoColumns.COLUMN_NAME_NAME));
        String site = cursor.getString(cursor.getColumnIndex(VideoColumns.COLUMN_NAME_SITE));
        int size = cursor.getInt(cursor.getColumnIndex(VideoColumns.COLUMN_NAME_SIZE));
        String type = cursor.getString(cursor.getColumnIndex(VideoColumns.COLUMN_NAME_TYPE));
        int movieId = cursor.getInt(cursor.getColumnIndex(VideoColumns.COLUMN_NAME_MOVIE_ID));

        return new Video(id, iso6391, iso31661, key, name, site, size, type, movieId);
    }

    private static Review getReviewFromCursor(Cursor cursor) {
        String id = cursor.getString(cursor.getColumnIndex(ReviewColumns._ID));
        String author = cursor.getString(cursor.getColumnIndex(ReviewColumns.COLUMN_NAME_AUTHOR));
        String content = cursor.getString(cursor.getColumnIndex(ReviewColumns.COLUMN_NAME_CONTENT));
        String url = cursor.getString(cursor.getColumnIndex(ReviewColumns.COLUMN_NAME_URL));
        int movieId = cursor.getInt(cursor.getColumnIndex(ReviewColumns.COLUMN_NAME_MOVIE_ID));

        return new Review(id, author, content, url, movieId);
    }
}
