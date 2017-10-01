package com.ogdev.popularmovies.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferencesUtilities {

    private static final String SORT_ORDER = "sortOrder";

    public static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
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
