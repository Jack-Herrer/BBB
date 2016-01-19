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


    static public void currencyUpdate(final Context context){

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
                Toast.makeText(context, "Update Succesful", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                Toast.makeText(context, "Update Failed. Try again", Toast.LENGTH_SHORT).show();

            }

        });

    }

    static void changeForeignCurrency(Context context, String currency){
        SharedPreferences values = context.getSharedPreferences("values", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = values.edit();
        editor.putString("foreign_currency", currency);
        Log.i("fcurrency", currency);
    }

    static SharedPreferences.Editor putDouble(final SharedPreferences.Editor edit, final String key, final double value) {
        return edit.putLong(key, Double.doubleToRawLongBits(value));
    }

    static double getDouble(final SharedPreferences prefs, final String key, final double defaultValue) {
        if (!prefs.contains(key))
            return defaultValue;

        return Double.longBitsToDouble(prefs.getLong(key, 0));
    }

}
