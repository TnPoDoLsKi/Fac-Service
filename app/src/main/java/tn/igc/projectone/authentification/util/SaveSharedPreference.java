package tn.igc.projectone.authentification.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class SaveSharedPreference {
    static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
    public static void setMajor(Context context, String major) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString("major",major);
        editor.apply();
    }
    public static void setToken(Context context, String token) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString("token",token);
        editor.apply();
    }
    public static String getMajor(Context context) {
        return getPreferences(context).getString("major","");
    }
    public static String getToken(Context context) {
        return getPreferences(context).getString("token","");
    }
}