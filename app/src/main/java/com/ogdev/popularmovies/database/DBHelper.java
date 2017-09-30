package com.ogdev.popularmovies.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ogdev.popularmovies.database.DBContract.MovieColumns;

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
            + MovieColumns.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP
            + MovieColumns.COLUMN_NAME_OVERVIEW + TEXT_TYPE + COMMA_SEP
            + MovieColumns.COLUMN_NAME_POSTER_PATH + TEXT_TYPE + COMMA_SEP
            + MovieColumns.COLUMN_NAME_BACKDROP_PATH + TEXT_TYPE + COMMA_SEP
            + MovieColumns.COLUMN_NAME_VOTE_AVERAGE + REAL_TYPE + COMMA_SEP
            + MovieColumns.COLUMN_NAME_VOTE_COUNT + REAL_TYPE + COMMA_SEP
            + MovieColumns.COLUMN_NAME_RELEASE_DATE + TEXT_TYPE + COMMA_SEP
            + MovieColumns.COLUMN_NAME_POPULARITY + REAL_TYPE + COMMA_SEP
            + MovieColumns.COLUMN_NAME_FAVORITE + INT_TYPE
            + ")";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}