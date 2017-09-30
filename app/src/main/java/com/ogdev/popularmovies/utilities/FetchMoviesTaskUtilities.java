package com.ogdev.popularmovies.utilities;

import android.content.Context;
import android.os.AsyncTask;

import com.ogdev.popularmovies.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class FetchMoviesTaskUtilities extends AsyncTask<Void, Void, ArrayList<Movie>> {

    private static final String OBJECT_IDENTIFIER = "results";
    private final Context mContext;
    private final String mOrder;

    public FetchMoviesTaskUtilities(Context context, String order) {
        this.mContext = context;
        this.mOrder = order;
    }

    @Override
    protected ArrayList<Movie> doInBackground(Void... params) {
        URL mURL = NetworkUtilities.buildURL(mOrder);
        ArrayList<Movie> mMovies = new ArrayList<>();

        try {
            String mResult = NetworkUtilities.getResponseFromHttpsUrl(mContext, mURL);
            if (mResult != null && !mResult.isEmpty()) {
                JSONObject mBillboardJson = new JSONObject(mResult);
                JSONArray mBillboardArray = mBillboardJson.getJSONArray(OBJECT_IDENTIFIER);

                for (int i = 0 ; i < mBillboardArray.length() ; i++) {
                    JSONObject mMovieJson = mBillboardArray.getJSONObject(i);
                    Movie mMovie = Movie.deserialize(mMovieJson);
                    mMovies.add(mMovie);
                }
            }
        } catch (IOException | JSONException ex) {
            ex.printStackTrace();
        }
        return mMovies;
    }

}
