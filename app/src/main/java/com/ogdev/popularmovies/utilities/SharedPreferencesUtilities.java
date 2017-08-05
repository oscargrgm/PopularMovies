package com.ogdev.popularmovies.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferencesUtilities {

    private static final String LAST_MODIFIED = "lastModified";
    private static final String SORT_ORDER = "sortOrder";

    public static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setLastModified(Context context, long lastModified) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putLong(LAST_MODIFIED, lastModified);
        editor.apply();
    }

    public static long getLastModified(Context context) {
        // Wed, 02 Sep 1992 02:00:00
        return getSharedPreferences(context).getLong(LAST_MODIFIED, 697602600000L);
    }

    public static void setSortOrder(Context context, int sortOrder) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putInt(SORT_ORDER, sortOrder);
        editor.apply();
    }

    public static int getSortOrder(Context context) {
        // Wed, 02 Sep 1992 02:00:00
        return getSharedPreferences(context).getInt(SORT_ORDER, 1);
    }
}
