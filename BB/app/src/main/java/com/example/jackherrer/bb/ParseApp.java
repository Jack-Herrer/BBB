package com.example.jackherrer.bb;

/**
 * Created by Michiel van der LIst on 25-1-16.
 * Student nr 10363521
 * michielvanderlist@gmail.com
 */

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class ParseApp extends Application {

    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "1NdzsnA614zjI8b8SavdKnIqgc0MRZ4CcNIziTwV", "WzigF4V1IKSlTRJendFDfyl9De0u1rl3sK554Och");
    }

    // save string in parse. source: https://parse.com/docs/android/guide
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

    // save double in parse
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

    // save files in parse
    static void saveInParse(final String key, final File file){
        //retrieve parseobject based on currentuser
        ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
        final ParseFile parseFile = new ParseFile(file);

        //fill array with objects from user
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> results, ParseException e) {
                if (e == null) {
                    for (ParseObject result : results) {
                        //add all the objects to the list
                        result.put(key, parseFile);
                        result.saveInBackground();
                    }
                } else {
                    Log.d("mainactivity", "query lukt niet");
                }
            }
        });
    }

    void getValuesFromParse(final Activity activity, final Context context){
        //retrieve parseobject based on currentuser
        if(ParseUser.getCurrentUser() != null) {

            ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
            query.whereEqualTo("objectId", ParseUser.getCurrentUser().getObjectId());

            //fill array with objects from user
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> results, ParseException e) {
                    if (e == null) {
                        for (ParseObject result : results) {
                            SharedPreferences values = context.getSharedPreferences("values", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = values.edit();

                            writeToLocalHistory(context, result.getParseFile("history"));
                            editor.putString("home_currency", result.getString("homeCurrency"));
                            editor.putString("foreign_currency", result.getString("foreignCurrency"));
                            Update.putDouble(editor, "bankBalance", result.getDouble("bankBalance"));
                            Update.putDouble(editor, "budget", result.getDouble("budget"));
                            editor.commit();
                        }

                        Intent intent = new Intent(activity.getApplicationContext(), BudgetViewActivity.class);
                        activity.startActivity(intent);
                        activity.finish();
                    } else {
                        Log.d("mainactivity", "query lukt niet");
                    }
                }
            });
        }
    }

    static void writeToLocalHistory(Context context, ParseFile parseFile){

        // use stringbuilder to read history parsefile
        try {
            BufferedReader br = new BufferedReader(new FileReader(parseFile.getFile()));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }

            // write lines to history file in local memory
            File filesDir = context.getFilesDir();
            File historyfile = new File(filesDir, "history.txt");
            FileUtils.writeStringToFile(historyfile, sb.toString());

            // clear memory of stringbuilder
            sb.delete(0, sb.length());

        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (ParseException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}