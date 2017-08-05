package com.ogdev.popularmovies.provider;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.ogdev.popularmovies.BuildConfig;
import com.ogdev.popularmovies.provider.movie.MovieColumns;


public class MovieSQLiteOpenHelper extends SQLiteOpenHelper {
    public static final String DATABASE_FILE_NAME = "movies.db";
    // @formatter:off
    public static final String SQL_CREATE_TABLE_MOVIE = "CREATE TABLE IF NOT EXISTS "
            + MovieColumns.TABLE_NAME + " ( "
            + MovieColumns._ID + " INTEGER PRIMARY KEY, "
            + MovieColumns.TITLE + " TEXT NOT NULL, "
            + MovieColumns.OVERVIEW + " TEXT, "
            + MovieColumns.VOTE_AVERAGE + " REAL, "
            + MovieColumns.VOTE_COUNT + " INTEGER, "
            + MovieColumns.POPULARITY + " REAL, "
            + MovieColumns.RELEASE_DATE + " TEXT, "
            + MovieColumns.POSTER + " TEXT, "
            + MovieColumns.BACKDROP + " TEXT, "
            + MovieColumns.IS_FAVOURITE + " INTEGER NOT NULL DEFAULT 0 "
            + " );";

    private static final String TAG = MovieSQLiteOpenHelper.class.getSimpleName();
    private static final int DATABASE_VERSION = 1;
    private static MovieSQLiteOpenHelper sInstance;
    private final Context mContext;
    private final MovieSQLiteOpenHelperCallbacks mOpenHelperCallbacks;

    // @formatter:on

    private MovieSQLiteOpenHelper(Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
        mContext = context;
        mOpenHelperCallbacks = new MovieSQLiteOpenHelperCallbacks();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private MovieSQLiteOpenHelper(Context context, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION, errorHandler);
        mContext = context;
        mOpenHelperCallbacks = new MovieSQLiteOpenHelperCallbacks();
    }

    public static MovieSQLiteOpenHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = newInstance(context.getApplicationContext());
        }
        return sInstance;
    }

    private static MovieSQLiteOpenHelper newInstance(Context context) {
        return newInstancePostHoneycomb(context);
    }

    /*
     * Post Honeycomb.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static MovieSQLiteOpenHelper newInstancePostHoneycomb(Context context) {
        return new MovieSQLiteOpenHelper(context, new DefaultDatabaseErrorHandler());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onCreate");
        mOpenHelperCallbacks.onPreCreate(mContext, db);
        db.execSQL(SQL_CREATE_TABLE_MOVIE);
        mOpenHelperCallbacks.onPostCreate(mContext, db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
//        if (!db.isReadOnly()) {
//            setForeignKeyConstraintsEnabled(db);
//        }
        mOpenHelperCallbacks.onOpen(mContext, db);
    }

    private void setForeignKeyConstraintsEnabled(SQLiteDatabase db) {
        setForeignKeyConstraintsEnabledPostJellyBean(db);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setForeignKeyConstraintsEnabledPostJellyBean(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mOpenHelperCallbacks.onUpgrade(mContext, db, oldVersion, newVersion);
    }
}
