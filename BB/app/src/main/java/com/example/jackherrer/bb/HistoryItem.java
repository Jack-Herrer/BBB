package com.example.jackherrer.bb;

/**
 * Created by Michiel van der List on 12-1-16.
 * Student nr 10363521
 * michielvanderlist@gmail.com
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;

public class HistoryItem{
    static void historyToJson(Context context, double withdrawn){

        // get values for history item
        SharedPreferences values = context.getSharedPreferences("values", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = values.edit();
        String foreignCurccency = values.getString("foreign_currency", "foreign error");
        String homeCurrency = values.getString("home_currency", "home error");
        double exchangeRate = Update.getDouble(values, "exchange_rate", 0.0);

        // put values in json object and save as string
        JSONObject historyObject = new JSONObject();
        try {
            historyObject.put("foreignCurrency", foreignCurccency);
            historyObject.put("homeCurrency", homeCurrency);
            historyObject.put("exchangeRate", exchangeRate);
            historyObject.put("withdrawn", withdrawn);
        } catch (JSONException e) {
            Toast.makeText(context, "Error getting transaction history", Toast.LENGTH_SHORT).show();
        }
        editor.putString("history", historyObject.toString());
        editor.commit();
    }
}