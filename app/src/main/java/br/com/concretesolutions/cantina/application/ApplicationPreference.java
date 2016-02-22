package br.com.concretesolutions.cantina.application;


import android.content.Context;
import android.content.SharedPreferences;

public class ApplicationPreference implements Preferences {

    String packageName = getClass().getPackage().toString();
    SharedPreferences prefs;
    private static ApplicationPreference PREFERENCE;
    private static String GOOGLE_PLUS_ID_TAG = "GOOGLE_PLUS_ID";
    private static String USERNAME_TAG = "USERNAME";
    private static String EMAIL_TAG = "EMAIL";

    private ApplicationPreference(Context context) {
        prefs = context.getSharedPreferences(packageName, Context.MODE_PRIVATE);
    }

    public static ApplicationPreference getPreference(Context context) {
        if (PREFERENCE != null) {
            return PREFERENCE;
        }
        PREFERENCE = new ApplicationPreference(context.getApplicationContext());
        return PREFERENCE;
    }

    @Override
    public String GooglePlusId() {
        return prefs.getString(GOOGLE_PLUS_ID_TAG, "");
    }

    @Override
    public String username() {
        return prefs.getString(USERNAME_TAG, "");
    }

    @Override
    public String email() {
        return prefs.getString(EMAIL_TAG, "");
    }

    @Override
    public void GooglePlusId(String value) {
        prefs.edit().putString(GOOGLE_PLUS_ID_TAG, value).apply();
    }

    @Override
    public void username(String value) {
        prefs.edit().putString(USERNAME_TAG, value).apply();
    }

    @Override
    public void email(String value) {
        prefs.edit().putString(EMAIL_TAG, value).apply();
    }
}
