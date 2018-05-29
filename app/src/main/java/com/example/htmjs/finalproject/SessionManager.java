package com.example.htmjs.finalproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SessionManager {

    private static String TAG = SessionManager.class.getSimpleName();

    private SharedPreferences sharedPreferences;

    private SharedPreferences.Editor editor;
    private Context context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "Harjoitustyö";

    private static final String KEY_IS_LOGGEDIN = "kirjautunut";

    @SuppressLint("CommitPrefEdits")
    public SessionManager(Context _context) {
        this.context = _context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();

    }

    public void setLogin(boolean isLogged) {
        editor.putBoolean(KEY_IS_LOGGEDIN, isLogged);

        editor.commit();

        Log.d(TAG, "Kirjautuminen onnistui");
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGEDIN, false);
    }

    public boolean logOut() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }
}
