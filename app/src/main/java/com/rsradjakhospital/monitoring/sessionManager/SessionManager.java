package com.rsradjakhospital.monitoring.sessionManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.rsradjakhospital.monitoring.MainActivity;

import java.util.HashMap;

public class SessionManager {

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "Sisimangi";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)

    public static final String KEY_USERNAME = "username";
    public static final String KEY_STATUS = "status";
    public static final String KEY_UNIT = "unit";
    public static final String KEY_NOTIF_ID = "notifID";


    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    /**
     * Create login session
     * */
    public void createLoginSession( String username,  String unit ,  String notifID){ //storelogin
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_UNIT, unit);
        editor.putString(KEY_NOTIF_ID, notifID);
        //  editor.putString(KEY_PASSWORD, password);

        editor.commit();
    }


    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect to Login Activity

            Intent i = new Intent(_context, MainActivity.class);

            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }


    public String getUsername() {
        return pref.getString(KEY_USERNAME, null);
    }

    public String getUnit() {
        return pref.getString(KEY_UNIT, null);
    }


    public String getStatus(){
        return pref.getString(KEY_STATUS, null);
    }


    public String getNotifID(){
        return pref.getString(KEY_NOTIF_ID, null);
    }

    /**
     * Get stored session data
     * */
    public HashMap getUserDetails(){

        HashMap<String, String> user = new HashMap<>();
        // user name
        user.put(KEY_USERNAME, pref.getString(KEY_USERNAME, null));
        // user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));
        user.put(KEY_STATUS, pref.getString(KEY_STATUS, null));
        // return user
        return user;
    }

    /**
     * Hapus Data Session
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}