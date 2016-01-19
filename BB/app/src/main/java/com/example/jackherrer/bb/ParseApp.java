package com.example.jackherrer.bb;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.lang.reflect.Array;
import java.util.List;

/**
 * Created by jackherrer on 13-1-16.
 */
public class ParseApp extends Application {

    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "1NdzsnA614zjI8b8SavdKnIqgc0MRZ4CcNIziTwV", "WzigF4V1IKSlTRJendFDfyl9De0u1rl3sK554Och");

    }

    static void saveInParse(final String key,final String value){
        //retrieve parseobject based on currentuser
        ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");

        //fill array with objects from user
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> results, ParseException e) {
                if (e == null) {
                    for (ParseObject result : results) {
                        //add all the objects to the list
                        result.put(key, value);
                        result.saveInBackground();
                    }

                } else {
                    Log.d("mainactivity", "query lukt niet");
                }
            }
        });
    }

    static void saveInParse(final String key,final double value){
        //retrieve parseobject based on currentuser
        ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");

        //fill array with objects from user
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> results, ParseException e) {
                if (e == null) {
                    for (ParseObject result : results) {
                        //add all the objects to the list
                        result.put(key, value);
                        result.saveInBackground();
                    }

                } else {
                    Log.d("mainactivity", "query lukt niet");
                }
            }
        });
    }

    static void saveInParse(final String key,final Array array){
        //retrieve parseobject based on currentuser
        ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");

        //fill array with objects from user
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> results, ParseException e) {
                if (e == null) {
                    for (ParseObject result : results) {
                        //add all the objects to the list
                        result.put(key, array);
                        result.saveInBackground();
                    }

                } else {
                    Log.d("mainactivity", "query lukt niet");
                }
            }
        });
    }


}
