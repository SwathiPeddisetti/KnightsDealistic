package ucf.knightsdealistic;

/**
 * Created by Swathi on 4/6/2015.
 */
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class UserSessionManager {


    SharedPreferences pref;
    Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;
    private static final String PREFER_NAME = "KnightsDealisticPref";
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";
    public static final String KEY_NAME = "name";
    public static final String KEY_ID = "id";

    // Constructor
    public UserSessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    //Create login session
    public void createUserLoginSession(String name, String id){
        editor.putBoolean(IS_USER_LOGIN, true);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_ID, id);
        editor.commit();
    }

    /**
     * Check login method will check user login status
     * If false it will redirect user to login page
     * Else do anything
     * */
    public boolean isLogggedIn(){
        if(!this.isUserLoggedIn()){
            Intent i = new Intent(_context, AdminLogin.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
            return false;
        }
        return true;
    }

    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_ID, pref.getString(KEY_ID, null));
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(_context, AdminLogin.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);
    }


    // Check for login
    public boolean isUserLoggedIn(){
        return pref.getBoolean(IS_USER_LOGIN, false);
    }
}
