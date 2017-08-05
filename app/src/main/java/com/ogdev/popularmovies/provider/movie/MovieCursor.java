package com.ogdev.popularmovies.provider.movie;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ogdev.popularmovies.provider.base.AbstractCursor;


/**
 * Cursor wrapper for the {@code movie} table.
 */
public class MovieCursor extends AbstractCursor implements MovieModel {
    public MovieCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(MovieColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code title} value.
     * Cannot be {@code null}.
     */
    @NonNull
    public String getTitle() {
        String res = getStringOrNull(MovieColumns.TITLE);
        if (res == null)
            throw new NullPointerException("The value of 'title' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code overview} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getOverview() {
        return getStringOrNull(MovieColumns.OVERVIEW);
    }

    /**
     * Get the {@code vote_average} value.
     * Can be {@code null}.
     */
    @NonNull
    public Float getVoteAverage() {
        return getFloatOrNull(MovieColumns.VOTE_AVERAGE);
    }

    /**
     * Get the {@code vote_count} value.
     * Can be {@code null}.
     */
    @NonNull
    public Integer getVoteCount() {
        return getIntegerOrNull(MovieColumns.VOTE_COUNT);
    }

    /**
     * Get the {@code popularity} value.
     * Can be {@code null}.
     */
    @NonNull
    public Float getPopularity() {
        return getFloatOrNull(MovieColumns.POPULARITY);
    }

    /**
     * Get the {@code release_date} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getReleaseDate() {
        return getStringOrNull(MovieColumns.RELEASE_DATE);
    }

    /**
     * Get the {@code poster} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getPoster() {
        return getStringOrNull(MovieColumns.POSTER);
    }

    /**
     * Get the {@code backdrop} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getBackdrop() {
        return getStringOrNull(MovieColumns.BACKDROP);
    }

    /**
     * Get the {@code is_favourite} value.
     */
    public boolean getIsFavourite() {
        Boolean res = getBooleanOrNull(MovieColumns.IS_FAVOURITE);
        if (res == null)
            throw new NullPointerException("The value of 'is_favourite' in the database was null, which is not allowed according to the model definition");
        return res;
    }
}
