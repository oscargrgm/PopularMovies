package com.ogdev.popularmovies.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ogdev.popularmovies.database.DBContract.MovieColumns;
import com.ogdev.popularmovies.database.DBContract.ReviewColumns;
import com.ogdev.popularmovies.database.DBContract.VideoColumns;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "movies.db";

    private static final int DATABASE_VERSION = 1;

    private static final String COMMA_SEP = ", ";
    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    private static final String REAL_TYPE = " REAL";


    private static final String SQL_CREATE_MOVIE_TABLE =
            "CREATE TABLE " + MovieColumns.TABLE_NAME + " ("
                    + MovieColumns._ID + " INTEGER PRIMARY KEY " + COMMA_SEP
                    + MovieColumns.COLUMN_NAME_MOVIE_ID + INT_TYPE + COMMA_SEP
                    + MovieColumns.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP
                    + MovieColumns.COLUMN_NAME_OVERVIEW + TEXT_TYPE + COMMA_SEP
                    + MovieColumns.COLUMN_NAME_POSTER_PATH + TEXT_TYPE + COMMA_SEP
                    + MovieColumns.COLUMN_NAME_BACKDROP_PATH + TEXT_TYPE + COMMA_SEP
                    + MovieColumns.COLUMN_NAME_VOTE_AVERAGE + REAL_TYPE + COMMA_SEP
                    + MovieColumns.COLUMN_NAME_VOTE_COUNT + REAL_TYPE + COMMA_SEP
                    + MovieColumns.COLUMN_NAME_RELEASE_DATE + TEXT_TYPE + COMMA_SEP
                    + MovieColumns.COLUMN_NAME_POPULARITY + REAL_TYPE + COMMA_SEP
                    + MovieColumns.COLUMN_NAME_FAVORITE + INT_TYPE + COMMA_SEP
                    + MovieColumns.COLUMN_NAME_SOURCE + TEXT_TYPE
                    + ")";

    private static final String SQL_CREATE_VIDEO_TABLE =
            "CREATE TABLE " + VideoColumns.TABLE_NAME + " ("
                    + VideoColumns._ID + " INTEGER PRIMARY KEY " + COMMA_SEP
                    + VideoColumns.COLUMN_NAME_ISO6391 + TEXT_TYPE + COMMA_SEP
                    + VideoColumns.COLUMN_NAME_ISO31661 + TEXT_TYPE + COMMA_SEP
                    + VideoColumns.COLUMN_NAME_KEY + TEXT_TYPE + COMMA_SEP
                    + VideoColumns.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP
                    + VideoColumns.COLUMN_NAME_SITE + TEXT_TYPE + COMMA_SEP
                    + VideoColumns.COLUMN_NAME_SIZE + INT_TYPE + COMMA_SEP
                    + VideoColumns.COLUMN_NAME_TYPE + TEXT_TYPE + COMMA_SEP
                    + VideoColumns.COLUMN_NAME_MOVIE_ID + INT_TYPE + COMMA_SEP
                    + " FOREIGN KEY (" + VideoColumns.COLUMN_NAME_MOVIE_ID +  ") REFERENCES "
                    + MovieColumns.TABLE_NAME + " (" + MovieColumns.COLUMN_NAME_MOVIE_ID + ")"
                    + ")";

    private static final String SQL_CREATE_REVIEW_TABLE =
            "CREATE TABLE " + ReviewColumns.TABLE_NAME + " ("
                    + ReviewColumns._ID + " INTEGER PRIMARY KEY " + COMMA_SEP
                    + ReviewColumns.COLUMN_NAME_AUTHOR + TEXT_TYPE + COMMA_SEP
                    + ReviewColumns.COLUMN_NAME_CONTENT + TEXT_TYPE + COMMA_SEP
                    + ReviewColumns.COLUMN_NAME_URL + TEXT_TYPE + COMMA_SEP
                    + ReviewColumns.COLUMN_NAME_MOVIE_ID + INT_TYPE + COMMA_SEP
                    + " FOREIGN KEY (" + ReviewColumns.COLUMN_NAME_MOVIE_ID +  ") REFERENCES "
                    + MovieColumns.TABLE_NAME + " (" + MovieColumns.COLUMN_NAME_MOVIE_ID + ")"
                    + ")";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_VIDEO_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_REVIEW_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // This method will be override when the app is released and an update is required
    }
}
