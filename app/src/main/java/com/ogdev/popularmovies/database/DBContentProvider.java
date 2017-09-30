package com.ogdev.popularmovies.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class DBContentProvider extends ContentProvider {

    private static final String TAG = DBContentProvider.class.getSimpleName();

    private static final int MOVIE = 100;
    private static final int MOVIE_ID = 101;

    public static final String CONTENT_AUTHORITY = "com.ogdev.popularmovies";

    private SQLiteOpenHelper mDBHelper;
    private final static UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    @Override
    public boolean onCreate() {
        mDBHelper = new DBHelper(getContext());

        sUriMatcher.addURI(CONTENT_AUTHORITY, DBContract.PATH_MOVIE, MOVIE);
        sUriMatcher.addURI(CONTENT_AUTHORITY, DBContract.PATH_MOVIE + "/#", MOVIE_ID);

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        UriArgs args = getUriMatchArguments(uri, projection, selection, selectionArgs, sortOrder);

        if (args.table == null)
            throw new UnsupportedOperationException("Unknown table for uri: " + uri);

        final SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.query(args.table, args.proj, args.where, args.args, null, null, args.order);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        UriArgs args = getUriMatchArguments(uri, null, null, null, null);

        if (args.table == null)
            throw new UnsupportedOperationException("Unknown table for uri: " + uri);

        final SQLiteDatabase db = mDBHelper.getWritableDatabase();
        long id = 0;
        try {
            id = db.insertOrThrow(args.table, null, contentValues);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        switch (args.uriMatch) {
            case MOVIE:
                return DBContract.MovieEntry.buildMovieUri(String.valueOf(id));
            default:
                throw new UnsupportedOperationException("Unknown table for uri: " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    private class UriArgs {
        public int uriMatch;
        public String table;
        public String where;
        public String[] args;
        public String[] proj;
        public String order;
    }

    /**
     * A partir de una uri obtiene la tabla y la clausula de seleccion de la
     * query a realizar en la base de datos.
     *
     * @param uri
     *            es la uri recivida en el contentProvider
     * @param preSelection
     *            la clausula de selección previa
     *
     * @return Devuelve un {@link UriArgs} con la tabla y la clausula de
     *         seleccion
     */
    private UriArgs getUriMatchArguments(Uri uri, String[] preProjection, String preSelection, String[] preArgs, String preOrder) {
        UriArgs res = new UriArgs();

        res.where = preSelection;
        res.uriMatch = sUriMatcher.match(uri);
        res.args = preArgs;
        res.order = preOrder;
        res.proj = preProjection;

        switch (res.uriMatch) {
            case MOVIE:
                res.table = DBContract.MovieColumns.TABLE_NAME;
                break;
            case MOVIE_ID:
                res.table = DBContract.MovieColumns.TABLE_NAME;
                if (res.where == null)
                    res.where = DBContract.MovieColumns._ID + "=?";
                if (res.args == null)
                    res.args = new String[] { DBContract.MovieEntry.getMovieId(uri) };
                break;
        }
        return res;
    }
}