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

public final class NetworkUtilities {

    // TODO Set your own API_KEY
    private final static String API_KEY = BuildConfig.API_KEY;

    private final static String BASE_URL = "https://api.themoviedb.org/3/movie/";

    private final static String PARAM_API_KEY = "api_key";

    private final static int CONNECTION_TIMEOUT_VALUE = 4000;
    private final static int READ_TIMEOUT_VALUE = 4000;

    public static boolean isConnected(Context context) {
        ConnectivityManager mManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mManager == null) {
            return false;
        }
        NetworkInfo mNetworkInfo = mManager.getActiveNetworkInfo();
        return mNetworkInfo != null && mNetworkInfo.isConnected();
    }

    public static URL buildURL(String order) {
        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(order)
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }

        return url;
    }

    public static URL buildInformationURL(int movieId, String informationType) {
        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(String.valueOf(movieId))
                .appendPath(informationType)
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .build();
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpsUrl(URL url) throws IOException {
        String response = null;

        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
        urlConnection.setConnectTimeout(CONNECTION_TIMEOUT_VALUE);
        urlConnection.setReadTimeout(READ_TIMEOUT_VALUE);

        int responseCode = urlConnection.getResponseCode();
        switch (responseCode) {
            case HttpURLConnection.HTTP_OK:
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
