package com.example.apple.shopphonee.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class UtilsSharePref {


    private static SharedPreferences sharedPreferences=null;


    public static SharedPreferences getSharedPreferences(Context context){
        if(sharedPreferences==null){
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        }
        return sharedPreferences;

    }

}
