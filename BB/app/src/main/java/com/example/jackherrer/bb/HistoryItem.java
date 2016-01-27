package com.example.jackherrer.bb;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jackherrer on 12-1-16.
 */
public class HistoryItem{
    static void historyToJson(Context context, double withdrawn){
        SharedPreferences values = context.getSharedPreferences("values", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = values.edit();

        String foreignCurccency = values.getString("foreign_currency", "foreign error");
        String homeCurrency = values.getString("home_currency", "home error");
        double exchangeRate = Update.getDouble(values, "exchange_rate", 0.0);

        JSONObject historyObject = new JSONObject();

        try {
            historyObject.put("foreignCurrency", foreignCurccency);
            historyObject.put("homeCurrency", homeCurrency);
            historyObject.put("exchangeRate", exchangeRate);
            historyObject.put("withdrawn", withdrawn);
        } catch (JSONException e) {
            Toast.makeText(context, "error in json", Toast.LENGTH_SHORT).show();
        }
        editor.putString("history", historyObject.toString());
        editor.commit();
    }
}
