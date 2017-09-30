package com.ogdev.popularmovies.database;


import android.net.Uri;
import android.provider.BaseColumns;

public class DBContract {

    public static final String CONTENT_AUTHORITY = "com.ogdev.popularmovies";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MOVIE = "movie";

    public interface MovieColumns extends BaseColumns {
        String TABLE_NAME = "movie";
        String COLUMN_NAME_TITLE = "title";
        String COLUMN_NAME_OVERVIEW = "overview";
        String COLUMN_NAME_POSTER_PATH = "poster_path";
        String COLUMN_NAME_BACKDROP_PATH = "backdrop_path";
        String COLUMN_NAME_VOTE_AVERAGE = "vote_average";
        String COLUMN_NAME_VOTE_COUNT = "vote_count";
        String COLUMN_NAME_RELEASE_DATE = "release_date";
        String COLUMN_NAME_POPULARITY = "popularity";
        String COLUMN_NAME_FAVORITE = "favorite";
    }

    public static class MovieEntry implements MovieColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_MOVIE)
                .build();

        public static String getMovieId(Uri uri) {
            return uri.getLastPathSegment();
        }

        public static Uri buildMovieUri(String id) {
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }
    }
}
