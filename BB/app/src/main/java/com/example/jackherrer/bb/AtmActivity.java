package com.example.jackherrer.bb;
/**
 * Created by Michiel van der List on 6-1-16.
 * Student nr 10363521
 * michielvanderlist@gmail.com
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
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class AtmActivity extends AppCompatActivity {
    public double exchangeRate = 0.0;
    final DecimalFormat f = new DecimalFormat("#0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atm);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        final SharedPreferences values = getSharedPreferences("values", Context.MODE_PRIVATE);
        final String homeCurrency = values.getString("home_currency", "currency not set");
        final String foreignCurrency = values.getString("foreign_currency", "currency not set");

        final double bankBalance = Update.getDouble(values, "bankBalance", 0.00);
        exchangeRate = Update.getDouble(values, "exchange_rate", 0.0);

        final TextView homeCurrencyView = (TextView) findViewById(R.id.atm_amount_entered_in_own_currency);
        final TextView balanceAfterView = (TextView) findViewById(R.id.atm_balance_after_withdrawal);
        final TextView balanceView = (TextView) findViewById(R.id.atm_current_balance);
        final TextView foreignCurrencySymbol = (TextView) findViewById(R.id.atm_foreign_symbol);

        final EditText inputBox = (EditText) findViewById(R.id.atm_withdrawal_input);
        final ProgressBar balanceBar = (ProgressBar) findViewById(R.id.atm_balance_bar);

        balanceView.setText("" + f.format(Update.roundDouble(bankBalance)));
        balanceAfterView.setText("" + f.format(Update.roundDouble(bankBalance)));

        foreignCurrencySymbol.setText(foreignCurrency);
        homeCurrencyView.setText(homeCurrency);

        // Listener function for input
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
                    homeCurrencyView.setText(homeCurrency+ " " +
                            f.format(Update.roundDouble(inputAmount * exchangeRate)));
                    balanceAfterView.setText(homeCurrency + " " +
                            f.format(Update.roundDouble(bankBalance - (inputAmount * exchangeRate))));
                    balanceBar.setProgress((int) (Update.roundDouble(inputAmount / bankBalance) *
                            100 * exchangeRate));
                    if(bankBalance - inputAmount * exchangeRate < 0){
                        balanceBar.setProgress(100);
                    }

                // process empty input field
                } catch (final NumberFormatException e) {
                    homeCurrencyView.setText(homeCurrency+ " 0,00");
                    balanceAfterView.setText(homeCurrency + " " + f.format(bankBalance));
                    balanceBar.setProgress(0);
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
        double inputAmount;

        try {
            // update bankbalance
            final EditText inputBox = (EditText) findViewById(R.id.atm_withdrawal_input);
            inputAmount = Double.parseDouble(inputBox.getText().toString());
            double bankBalance = Update.getDouble(values, "bankBalance", 2.10);
            Update.putDouble(editor, "bankBalance", bankBalance - (inputAmount * exchangeRate));
            ParseApp.saveInParse("bankBalance", bankBalance - (inputAmount * exchangeRate));

            // update spent amount
            double spent = Update.getDouble(values, "spent", 0.0);
            spent = spent + inputAmount * exchangeRate;
            Update.putDouble(editor, "spent", spent);
            ParseApp.saveInParse("spent", spent);

            HistoryItem.historyToJson(this, inputAmount);
            editor.commit();

            // adapt historyview
            Intent toHistory = new Intent(this, HistoryViewActivity.class);
            toHistory.putExtra("withdrawn", true);
            this.startActivity(toHistory);
            this.finish();

        // don't update anything if input is empty
        } catch (final NumberFormatException e) {
            Toast.makeText(getApplicationContext(), "nothing to withdraw", Toast.LENGTH_SHORT).show();
            Intent toBudgetView = new Intent(this, BudgetViewActivity.class);
            this.startActivity(toBudgetView);
            this.finish();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        return ActionMenuHandler.handleMenu(item, this);
    }

    public void onUpdateClick(View view) {
        Intent toInputActivity= new Intent(this, UpdateActivity.class);
        this.startActivity(toInputActivity);
        this.finish();
    }

    public void onBack(View view) {
        Intent toBudgetView= new Intent(this, BudgetViewActivity.class);
        this.startActivity(toBudgetView);
        this.finish();
    }
}