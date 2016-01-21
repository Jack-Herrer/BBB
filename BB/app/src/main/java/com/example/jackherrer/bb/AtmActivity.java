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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class AtmActivity extends AppCompatActivity {
    public double exchangeRate = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atm);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        SharedPreferences values = getSharedPreferences("values", Context.MODE_PRIVATE);
        final String homeCurrency = values.getString("home_currency", "error");

        exchangeRate = Update.getDouble(values, "exchange_rate", 0.0);

        final double bankBalance = Update.getDouble(values, "bankBalance", 0.00);
        final TextView homeCurrencyView = (TextView) findViewById(R.id.atm_amount_entered_in_own_currency);
        final TextView balanceAfterView = (TextView) findViewById(R.id.atm_balance_after_withdrawal);
        final ProgressBar balanceBar = (ProgressBar) findViewById(R.id.atm_balance_bar);

        TextView balanceView = (TextView) findViewById(R.id.atm_current_balance);
        balanceView.setText("" + bankBalance);

        final EditText inputBox = (EditText) findViewById(R.id.atm_withdrawal_input);

        final TextView foreignCurrencySymbol = (TextView) findViewById(R.id.atm_foreign_symbol);
        final String foreignCurrency = values.getString("foreign_currency", "N.A.");
        Toast.makeText(this, foreignCurrency, Toast.LENGTH_SHORT).show();

        foreignCurrencySymbol.setText(foreignCurrency);
        homeCurrencyView.setText(homeCurrency);

        inputBox.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            // real time conversion during typing
            @Override
            public void afterTextChanged(Editable editable) {//
                try {
                    double inputAmount = Double.parseDouble(inputBox.getText().toString());
                    homeCurrencyView.setText(homeCurrency+ " " + inputAmount * exchangeRate);
                    balanceAfterView.setText(homeCurrency + " " + (bankBalance - (inputAmount * exchangeRate)));
                    balanceBar.setProgress((int) ((inputAmount / bankBalance) * 100 * exchangeRate));

                } catch (final NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    public void onWithdrawClick(View view) {
        SharedPreferences values = getSharedPreferences("values", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = values.edit();


        try {
            final double bankBalance = Update.getDouble(values, "bankBalance", 2.10);
            final EditText inputBox = (EditText) findViewById(R.id.atm_withdrawal_input);
            double inputAmount = Double.parseDouble(inputBox.getText().toString());
            Update.putDouble(editor, "bankBalance", bankBalance - (inputAmount * exchangeRate));
            editor.commit();
            ParseApp.saveInParse("bankBalance", bankBalance  - (inputAmount * exchangeRate));
        } catch (final NumberFormatException e) {
            //catch non doubles
        }

        Intent toBudgetView= new Intent(this, BudgetViewActivity.class);
        this.startActivity(toBudgetView);
        this.finish();
    }

}


