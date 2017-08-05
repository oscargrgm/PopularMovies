package com.ogdev.popularmovies.provider.movie;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ogdev.popularmovies.provider.base.BaseModel;

/**
 * Data model for the {@code movie} table.
 */
public interface MovieModel extends BaseModel {

    /**
     * Get the {@code title} value.
     * Cannot be {@code null}.
     */
    @NonNull
    String getTitle();

    /**
     * Get the {@code overview} value.
     * Can be {@code null}.
     */
    @Nullable
    String getOverview();

    /**
     * Get the {@code vote_average} value.
     * Can be {@code null}.
     */
    @Nullable
    Float getVoteAverage();

    /**
     * Get the {@code vote_count} value.
     * Can be {@code null}.
     */
    @Nullable
    Integer getVoteCount();

    /**
     * Get the {@code popularity} value.
     * Can be {@code null}.
     */
    @Nullable
    Float getPopularity();

    /**
     * Get the {@code release_date} value.
     * Can be {@code null}.
     */
    @Nullable
    String getReleaseDate();

    /**
     * Get the {@code poster} value.
     * Can be {@code null}.
     */
    @Nullable
    String getPoster();

    /**
     * Get the {@code backdrop} value.
     * Can be {@code null}.
     */
    @Nullable
    String getBackdrop();

    /**
     * Get the {@code is_favourite} value.
     */
    boolean getIsFavourite();
}
