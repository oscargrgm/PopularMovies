package com.ogdev.popularmovies.database;


import android.net.Uri;
import android.provider.BaseColumns;

public class DBContract {

    public static final String CONTENT_AUTHORITY = "com.ogdev.popularmovies";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MOVIE = "movie";
    public static final String PATH_VIDEO = "video";
    public static final String PATH_REVIEW = "review";

    public interface MovieColumns extends BaseColumns {
        String TABLE_NAME = "movie";
        String COLUMN_NAME_MOVIE_ID = "movie_id";
        String COLUMN_NAME_TITLE = "title";
        String COLUMN_NAME_OVERVIEW = "overview";
        String COLUMN_NAME_POSTER_PATH = "poster_path";
        String COLUMN_NAME_BACKDROP_PATH = "backdrop_path";
        String COLUMN_NAME_VOTE_AVERAGE = "vote_average";
        String COLUMN_NAME_VOTE_COUNT = "vote_count";
        String COLUMN_NAME_RELEASE_DATE = "release_date";
        String COLUMN_NAME_POPULARITY = "popularity";
        String COLUMN_NAME_FAVORITE = "favorite";
        String COLUMN_NAME_SOURCE = "source";
    }

    public interface VideoColumns extends BaseColumns {
        String TABLE_NAME = "video";
        String COLUMN_NAME_ISO6391 = "iso6391";
        String COLUMN_NAME_ISO31661 = "iso31661";
        String COLUMN_NAME_KEY = "key";
        String COLUMN_NAME_NAME = "name";
        String COLUMN_NAME_SITE = "site";
        String COLUMN_NAME_SIZE = "size";
        String COLUMN_NAME_TYPE = "type";
        String COLUMN_NAME_MOVIE_ID = "movie_id";
    }

    public interface ReviewColumns extends BaseColumns {
        String TABLE_NAME = "review";
        String COLUMN_NAME_AUTHOR = "author";
        String COLUMN_NAME_CONTENT = "content";
        String COLUMN_NAME_URL = "url";
        String COLUMN_NAME_MOVIE_ID = "movie_id";
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

    public static class VideoEntry implements MovieColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_VIDEO)
                .build();

        public static String getVideoId(Uri uri) {
            return uri.getLastPathSegment();
        }

        public static Uri buildVideoUri(String id) {
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }
    }

    public static class ReviewEntry implements MovieColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_REVIEW)
                .build();

        public static String getReviewId(Uri uri) {
            return uri.getLastPathSegment();
        }

        public static Uri buildReviewUri(String id) {
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }
    }
}
