package com.ogdev.popularmovies.utilities;

import android.content.Context;
import android.os.AsyncTask;

import com.ogdev.popularmovies.activities.MovieDetailActivity;
import com.ogdev.popularmovies.models.Review;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class FetchReviewsTaskUtilities extends AsyncTask<Void, Void, ArrayList<Review>> {

    private static final String OBJECT_IDENTIFIER = "results";
    private final int mMovieId;

    public FetchReviewsTaskUtilities(int movieId) {
        this.mMovieId = movieId;
    }

    @Override
    protected ArrayList<Review> doInBackground(Void... params) {
        URL mURL = NetworkUtilities.buildInformationURL(mMovieId, MovieDetailActivity.INFO_TYPE_REVIEWS);
        ArrayList<Review> reviews = new ArrayList<>();

        try {
            String mResult = NetworkUtilities.getResponseFromHttpsUrl(mURL);
            if (mResult != null && !mResult.isEmpty()) {
                JSONObject resultJSON = new JSONObject(mResult);
                JSONArray resultArray = resultJSON.getJSONArray(OBJECT_IDENTIFIER);

                for (int i = 0 ; i < resultArray.length() ; i++) {
                    JSONObject videoJSON = resultArray.getJSONObject(i);
                    Review review = Review.deserialize(videoJSON);
                    review.setMovieId(mMovieId);
                    reviews.add(review);
                }
            }
        } catch (IOException | JSONException ex) {
            ex.printStackTrace();
        }
        return reviews;
    }
}
