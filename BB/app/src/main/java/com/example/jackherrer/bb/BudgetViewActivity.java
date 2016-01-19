package com.example.jackherrer.bb;

/**
 * Created by Michiel van der List on 6-1-16.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;

public class BudgetViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ParseUser currentUser = ParseUser.getCurrentUser();
        currentUser.put("emailVerified", true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_view);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        SharedPreferences values = getSharedPreferences("values", Context.MODE_PRIVATE);

        double bankBalance =  Update.getDouble(values, "bankBalance", 0.00);
        double budget = Update.getDouble(values, "budget", 0.00);
        boolean currenciesUpdated = values.getBoolean("currencies_updated", false);
        String foreignCurrency = values.getString("foreign_currency", "n.a.");


        if(currenciesUpdated) {

            //currencies = oldcurrencies
            //balance = (balance / by old currency) * new currency
            // set homecurrency
            // set guest currency

            String newCurrencies = values.getString("recent_currencies", "failed");


            try {
                JSONObject newCurrenciesObject = new JSONObject(newCurrencies);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            Log.i("currencies budgetview", newCurrencies);
            SharedPreferences.Editor editor = values.edit();
            editor.putBoolean("currency_updated", false);
            editor.commit();
        }

        TextView balanceView = (TextView) findViewById(R.id.bv_ballance_amount_view);
        balanceView.setText("" + bankBalance);

        TextView startBalanceView = (TextView) findViewById(R.id.bv_start_balance_view);
        startBalanceView.setText("Start Balance: " + budget);

        ProgressBar budgetBar = (ProgressBar) findViewById(R.id.bv_budget_bar);
        budgetBar.setProgress((int) (((budget - bankBalance) / budget) * 100));
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        return true;
    }


    public void onInputClick(View view) {
        Intent toInputActivity= new Intent(this, UpdateActivity.class);
        this.startActivity(toInputActivity);
        this.finish();
    }

    public void onAtmClick(View view) {
        Intent toAtmActivity= new Intent(this, AtmActivity.class);
        this.startActivity(toAtmActivity);
        this.finish();
    }

    public void onHistoryClick(View view) {
        Intent toHistoryViewActivity= new Intent(this, HistoryViewActivity.class);
        this.startActivity(toHistoryViewActivity);
        this.finish();
    }

    public void onLoginClick(View view) {
        Intent toLoginActivity= new Intent(this, LoginActivity.class);
        this.startActivity(toLoginActivity);
        this.finish();
    }
}
