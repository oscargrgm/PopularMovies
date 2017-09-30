package com.ogdev.popularmovies.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import com.ogdev.popularmovies.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class NetworkUtilities {

    // TODO Set your own API_KEY
    private final static String API_KEY = BuildConfig.API_KEY;

    private final static String BASE_URL = "https://api.themoviedb.org/3/movie/";

    private final static String PARAM_API_KEY = "api_key";

    private final static int CONNECTION_TIMEOUT_VALUE = 4000;
    private final static int READ_TIMEOUT_VALUE = 4000;

    public static boolean isConnected(Context context) {
        ConnectivityManager mManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mManager.getActiveNetworkInfo();

        return mNetworkInfo != null && mNetworkInfo.isConnected();
    }

    public static URL buildURL(String order) {
        Uri mUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(order)
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .build();

        URL mURL = null;
        try {
            mURL = new URL(mUri.toString());
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }

        return mURL;
    }

    public static String getResponseFromHttpsUrl(Context context, URL url) throws IOException {
        String response = null;

        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
        long mLastModified = SharedPreferencesUtilities.getLastModified(context);
        urlConnection.setIfModifiedSince(mLastModified); // 02/09/1992 02:00
        urlConnection.setConnectTimeout(CONNECTION_TIMEOUT_VALUE);
        urlConnection.setReadTimeout(READ_TIMEOUT_VALUE);

        int responseCode = urlConnection.getResponseCode();
        switch (responseCode) {
            case HttpURLConnection.HTTP_OK:
                mLastModified = urlConnection.getLastModified();
                SharedPreferencesUtilities.setLastModified(context, mLastModified);

                InputStream in = urlConnection.getInputStream();

                Scanner scanner = new Scanner(in);
                scanner.useDelimiter("\\A");

                boolean hasInput = scanner.hasNext();
                if (hasInput) {
                    response = scanner.next();
                }
                break;
            default:
                throw new IOException();
        }
        urlConnection.disconnect();

        return response;
    }
}