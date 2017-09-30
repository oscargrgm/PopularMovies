package com.ogdev.popularmovies.utilities;

import android.content.Context;
import android.os.AsyncTask;

import com.ogdev.popularmovies.activities.MovieDetailActivity;
import com.ogdev.popularmovies.models.Video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class FetchVideosTaskUtilities extends AsyncTask<Void, Void, ArrayList<Video>> {

    private static final String OBJECT_IDENTIFIER = "results";
    private final Context mContext;
    private final int mMovieId;

    public FetchVideosTaskUtilities(Context context, int movieId) {
        this.mContext = context;
        this.mMovieId = movieId;
    }

    @Override
    protected ArrayList<Video> doInBackground(Void... params) {
        URL mURL = NetworkUtilities.buildInformationURL(mMovieId, MovieDetailActivity.INFO_TYPE_VIDEOS);
        ArrayList<Video> videos = new ArrayList<>();

        try {
            String mResult = NetworkUtilities.getResponseFromHttpsUrl(mContext, mURL);
            if (mResult != null && !mResult.isEmpty()) {
                JSONObject resultJSON = new JSONObject(mResult);
                JSONArray resultArray = resultJSON.getJSONArray(OBJECT_IDENTIFIER);

                for (int i = 0 ; i < resultArray.length() ; i++) {
                    JSONObject videoJSON = resultArray.getJSONObject(i);
                    Video video = Video.deserialize(videoJSON);
                    video.setMovieId(mMovieId);
                    videos.add(video);
                }
            }
        } catch (IOException | JSONException ex) {
            ex.printStackTrace();
        }
        return videos;
    }
}
