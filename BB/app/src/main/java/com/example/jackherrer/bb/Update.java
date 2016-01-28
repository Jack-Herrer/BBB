package com.example.jackherrer.bb;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import cz.msebera.android.httpclient.Header;

/**
 * Created by Michiel van der List on 19-1-16.
 * Student nr 10363521
 * michielvanderlist@gmail.com
 */

public class Update {

    private static final String CURRENCY_URL  = "https://openexchangerates.org/api/latest.json?app_id=7777406857a9410a90d1f4891a5e47fd";

    static public void currencyRatesUpdate(final Context context){
        final SharedPreferences values = context.getSharedPreferences("values", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = values.edit();

        // fetch currencies from internet
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(CURRENCY_URL, new AsyncHttpResponseHandler() {

            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                String decoded = null;
                try {
                    decoded = new String(response, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                // get rates object
                JSONObject ratesJson = null;
                try {
                    JSONObject responseJson = new JSONObject(decoded);
                    ratesJson = responseJson.getJSONObject("rates");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //save currencies in parse and shared prefs
                ParseApp.saveInParse("lastCurrencies", String.valueOf(ratesJson));
                editor.putBoolean("currency_updated", true);
                editor.putString("recent_currencies", decoded);
                editor.commit();
                Toast.makeText(context, "Currency rates updated", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                Toast.makeText(context, "No rates found, check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    static void changeForeignCurrency(Context context, String currency){
        SharedPreferences values = context.getSharedPreferences("values", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = values.edit();
        editor.putString("foreign_currency", currency);
        editor.commit();

        ParseApp.saveInParse("foreignCurrency", currency);
    }

    static void changeHomeCurrency(Context context, String currency){
        SharedPreferences values = context.getSharedPreferences("values", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = values.edit();
        editor.putString("home_currency", currency);
        editor.commit();

        ParseApp.saveInParse("homeCurrency", currency);
    }

    static void updateSpent(Activity activity){
        SharedPreferences values = activity.getSharedPreferences("values", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = values.edit();

        EditText spentBox = (EditText) activity.findViewById(R.id.inp_spent_field);
        CheckBox spentCheck = (CheckBox) activity.findViewById(R.id.inp_spent_box);
        if(spentCheck.isChecked()) {
            try {
                double spent = Double.parseDouble(spentBox.getText().toString());
                Update.putDouble(editor, "spent", spent);
                editor.commit();
                ParseApp.saveInParse("spent", spent);
            } catch (final NumberFormatException e) {
                Toast.makeText(activity.getApplicationContext(), "failed to update balance: no input", Toast.LENGTH_SHORT).show();
            }
        }
    }

    static void updateBalance(Activity activity){
        SharedPreferences values = activity.getSharedPreferences("values", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = values.edit();
        EditText bankBalanceBox = (EditText) activity.findViewById(R.id.inp_enter_balance);
        CheckBox balanceCheck = (CheckBox) activity.findViewById(R.id.balance_checkBox);
        if(balanceCheck.isChecked()) {
            try {
                double bankBalance = Double.parseDouble(bankBalanceBox.getText().toString());
                Update.putDouble(editor, "bankBalance", bankBalance);
                editor.commit();
                ParseApp.saveInParse("bankBalance", bankBalance);
            } catch (final NumberFormatException e) {
                Toast.makeText(activity.getApplicationContext(), "failed to update balance: no input", Toast.LENGTH_SHORT).show();
            }
        }
    }

    static void updateBudget(Activity activity){
        SharedPreferences values = activity.getSharedPreferences("values", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = values.edit();
        EditText budgetBox = (EditText) activity.findViewById(R.id.inp_budget_box);
        CheckBox budgetCheck = (CheckBox) activity.findViewById(R.id.budget_checkBox);
        if(budgetCheck.isChecked()) {
            try {
                double budget = Double.parseDouble(budgetBox.getText().toString());
                Update.putDouble(editor, "budget", budget);
                editor.commit();
                ParseApp.saveInParse("budget", budget);

            } catch (final NumberFormatException e) {
                Toast.makeText(activity.getApplicationContext(), "failed to update budget: no input", Toast.LENGTH_SHORT).show();
            }
        }
    }

    static void setExchangeRate(Context context) {
        SharedPreferences values = context.getSharedPreferences("values", Context.MODE_PRIVATE);
        String currenciesString = values.getString("recent_currencies", null);
        SharedPreferences.Editor editor = values.edit();
        double exchangeRate = 0.0;

        if(currenciesString != null) {
            // Get rates object
            JSONObject currenciesObject = null;
            try {
                currenciesObject = new JSONObject(currenciesString);
                currenciesObject = currenciesObject.getJSONObject("rates");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // get home and foreign currency
            String homecurrency = values.getString("home_currency", "error");
            double homeRate = 0;
            try {
                homeRate = currenciesObject.getDouble(homecurrency);
            } catch (JSONException e) {
                Toast.makeText(context, "No rates found, please update your rates" + homecurrency, Toast.LENGTH_SHORT).show();
            }

            String foreignCurrency = values.getString("foreign_currency", "error");
            double foreignRate = 0;
            try {
                foreignRate = currenciesObject.getDouble(foreignCurrency);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            exchangeRate = ((1 / foreignRate) * homeRate);
        }
        putDouble(editor, "exchange_rate", exchangeRate);
        Toast.makeText(context, "rate" + exchangeRate , Toast.LENGTH_SHORT).show();
        editor.commit();
    }

    // put double in shared prefs. source: http://stackoverflow.com/questions/16319237/cant-put-double-sharedpreferences
    static SharedPreferences.Editor putDouble(final SharedPreferences.Editor edit, final String key, final double value) {
        return edit.putLong(key, Double.doubleToRawLongBits(value));
    }

    // get double from shared prefs. source: http://stackoverflow.com/questions/16319237/cant-put-double-sharedpreferences
    static double getDouble(final SharedPreferences prefs, final String key, final double defaultValue) {
        if (!prefs.contains(key))
            return defaultValue;
        return Double.longBitsToDouble(prefs.getLong(key, 0));
    }

    static double roundDouble (double input){
        double inputDouble = Math.round(input * 100);
        return inputDouble / 100;
    }

    static void clearData(Context context){
        SharedPreferences values = context.getSharedPreferences("values", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = values.edit();

        changeHomeCurrency(context, "EUR");
        changeForeignCurrency(context, "EUR");

        putDouble(editor, "bankBalance", 0.0);
        putDouble(editor, "spent", 0.0);
        putDouble(editor, "budget", 0.0);

        ParseApp.saveInParse("spent", 0.0);
        ParseApp.saveInParse("bankBalance", 0.0);
        ParseApp.saveInParse("budget", 0.0);

        File filesDir = context.getFilesDir();
        File historyfile = new File(filesDir, "history.txt");
        try {
            FileUtils.writeLines(historyfile, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        editor.commit();
        Toast.makeText(context, "Data successfully cleared",
                Toast.LENGTH_SHORT).show();
    }
}