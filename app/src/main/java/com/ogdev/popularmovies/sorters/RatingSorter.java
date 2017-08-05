package com.ogdev.popularmovies.sorters;

import com.ogdev.popularmovies.models.Movie;

import java.util.Comparator;

public class RatingSorter implements Comparator<Movie> {

    @Override
    public int compare(Movie mFirstMovie, Movie mSecondMovie) {
        if (mFirstMovie.getVoteAverage() < mSecondMovie.getVoteAverage()) {
            return 1;
        } else if (mFirstMovie.getVoteAverage() > mSecondMovie.getVoteAverage()) {
            return -1;
        } else {
            return 0;
        }
    }
}