package com.example.jackherrer.bb;

/**
 * Created by Michiel van der List on 6-1-16.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.*;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;


public class UpdateActivity extends AppCompatActivity {

    private static final String CURRENCY_URL  = "https://openexchangerates.org/api/latest.json?app_id=7777406857a9410a90d1f4891a5e47fd";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    public void onUpdateClick(View view) {
        SharedPreferences values = getSharedPreferences("values", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = values.edit();

        EditText bankBalanceBox = (EditText)findViewById(R.id.inp_enter_balance);
        EditText budgetBox = (EditText)findViewById(R.id.inp_budget_box);


        try{
            double bankBalance = Double.parseDouble(bankBalanceBox.getText().toString());
            putDouble(editor, "bankBalance", bankBalance);

            double budget = Double.parseDouble(budgetBox.getText().toString());
            putDouble(editor, "budget", budget);

            editor.commit();

        } catch (final NumberFormatException e) {

            //catch non doubles

        }

        Intent toBudgetView= new Intent(this, BudgetViewActivity.class);
        this.startActivity(toBudgetView);
        this.finish();
    }

    SharedPreferences.Editor putDouble(final SharedPreferences.Editor edit, final String key, final double value) {
        return edit.putLong(key, Double.doubleToRawLongBits(value));
    }

    double getDouble(final SharedPreferences prefs, final String key, final double defaultValue) {
        if (!prefs.contains(key))
            return defaultValue;

        return Double.longBitsToDouble(prefs.getLong(key, 0));
    }

    public void onUpdateCurrenciesClick(View view) {

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(CURRENCY_URL, new AsyncHttpResponseHandler() {

            public void onSuccess(int statusCode, Header[] headers, byte[] response) {

                String decoded = null;
                try {
                    decoded = new String(response, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                Log.i("Currencies:", decoded);
                Toast.makeText(getApplicationContext(),  "Update Succesful" , Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                Toast.makeText(getApplicationContext(),  "Update Failed. Try again" , Toast.LENGTH_SHORT).show();

            }

        });

    }
}
