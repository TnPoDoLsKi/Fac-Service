package tn.igc.projectone;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;


public class SaveSharedPreference {
    public SaveSharedPreference() {
    }

    static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setMajor(Context context, String major) {
        Log.d("setMajor", "setMajor: " + major);

        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString("major", major);
        editor.apply();
    }

    public static void setToken(Context context, String token) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString("token", token);
        editor.apply();
    }

    public static String getMajor(Context context) {
        return getPreferences(context).getString("major", "");
    }

    public static String getToken(Context context) {
        return getPreferences(context).getString("token", "");
    }

    public static String getMajorName(Context context) {
        Log.d("getMajorName", "getMajorName: " + getPreferences(context).getString("majorName",""));
        return getPreferences(context).getString("majorName","");
    }
    public static void setMajorName(Context context, String major) {
        Log.d("setMajorName", "setMajorName: " + major);
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString("majorName", major);
        editor.apply();
    }
}

