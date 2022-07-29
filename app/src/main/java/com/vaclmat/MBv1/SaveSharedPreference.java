package com.vaclmat.MBv1;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import static com.vaclmat.MBv1.PreferencesUtility.*;

class SaveSharedPreference {
    private static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    static void setLoggedIn(Context context, boolean loggedIn) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(LOGGED_IN_PREF, loggedIn);
        editor.apply();
    }

    static boolean getLoggedStatus(Context context) {
        return getPreferences(context).getBoolean(LOGGED_IN_PREF, false);
    }
}
