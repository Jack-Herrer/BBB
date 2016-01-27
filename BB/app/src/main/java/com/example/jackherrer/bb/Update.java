package com.example.jackherrer.bb;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import cz.msebera.android.httpclient.Header;

/**
 * Created by Michiel on 19-1-16.
 */
public class Update {

    private static final String CURRENCY_URL  = "https://openexchangerates.org/api/latest.json?app_id=7777406857a9410a90d1f4891a5e47fd";

    static public void currencyRatesUpdate(final Context context){

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(CURRENCY_URL, new AsyncHttpResponseHandler() {

            public void onSuccess(int statusCode, Header[] headers, byte[] response) {

                String decoded = null;
                try {
                    decoded = new String(response, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                JSONObject ratesJson = null;
                try {
                    JSONObject responseJson = new JSONObject(decoded);
                    ratesJson = responseJson.getJSONObject("rates");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //save currencies in parse
                ParseApp.saveInParse("lastCurrencies", String.valueOf(ratesJson));

                SharedPreferences values = context.getSharedPreferences("values", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = values.edit();
                editor.putBoolean("currency_updated", true);
                editor.putString("recent_currencies", decoded);
                editor.commit();

                Log.i("Currencies:", String.valueOf(ratesJson));
                Toast.makeText(context, "Currency rates updated", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                Toast.makeText(context, "Update Failed. Try again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    static void changeForeignCurrency(Context context, String currency){

        ParseApp.saveInParse("foreignCurrency", currency);
        SharedPreferences values = context.getSharedPreferences("values", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = values.edit();
        editor.putString("foreign_currency", currency);
        editor.commit();
    }

    static void changeHomeCurrency(Context context, String currency){

        ParseApp.saveInParse("homeCurrency", currency);
        SharedPreferences values = context.getSharedPreferences("values", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = values.edit();
        editor.putString("home_currency", currency);
        editor.commit();
    }

    static void setExchangeRate(Context context) {
        SharedPreferences values = context.getSharedPreferences("values", Context.MODE_PRIVATE);
        String currenciesString = values.getString("recent_currencies", "error");
        SharedPreferences.Editor editor = values.edit();

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
            Toast.makeText(context, "error in json" +  homecurrency, Toast.LENGTH_SHORT).show();
        }

        String foreignCurrency = values.getString("foreign_currency", "error");
        double foreignRate = 0;
        try {
            foreignRate = currenciesObject.getDouble(foreignCurrency);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        double exchangeRate = ((1 / foreignRate) * homeRate);
        putDouble(editor, "exchange_rate", exchangeRate);
        Toast.makeText(context, "rate" + exchangeRate , Toast.LENGTH_SHORT).show();
        editor.commit();

    }

    static SharedPreferences.Editor putDouble(final SharedPreferences.Editor edit, final String key, final double value) {
        return edit.putLong(key, Double.doubleToRawLongBits(value));
    }

    static double getDouble(final SharedPreferences prefs, final String key, final double defaultValue) {
        if (!prefs.contains(key))
            return defaultValue;

        return Double.longBitsToDouble(prefs.getLong(key, 0));
    }

    static double roundDouble (double input){
        double inputDouble = Math.round(input * 100);
        return inputDouble / 100;
    }

}
