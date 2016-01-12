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

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;


public class AtmActivity extends AppCompatActivity {
    public double exchangeRate = 0.8;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atm);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

//      final double exchangeRate = 0.8;
        final String homeCurrency = "€";


        SharedPreferences values = getSharedPreferences("values", Context.MODE_PRIVATE);
        final double bankBalance = getDouble(values, "bankBalance", 0.00);
        final TextView homeCurrencyView = (TextView) findViewById(R.id.atm_amount_entered_in_own_currency);
        final TextView balanceAfterView = (TextView) findViewById(R.id.atm_balance_after_withdrawal);
        final ProgressBar balanceBar = (ProgressBar) findViewById(R.id.atm_balance_bar);

        TextView balanceView = (TextView) findViewById(R.id.atm_current_balance);
        balanceView.setText("" + bankBalance);

        final EditText inputBox = (EditText) findViewById(R.id.atm_withdrawal_input);

        inputBox.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
//
                try {
                    double inputAmount = Double.parseDouble(inputBox.getText().toString());
                    homeCurrencyView.setText(homeCurrency+ " " + inputAmount * exchangeRate);
                    balanceAfterView.setText(homeCurrency + " " + (bankBalance - (inputAmount * exchangeRate)));

                    balanceBar.setProgress((int) ((inputAmount / bankBalance) * 100 * exchangeRate));

                } catch (final NumberFormatException e) {

                    //catch non doubles

                }
            }
        });
    }



//    et1.addTextChangedListener(new TextWatcher() {
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            // TODO Auto-generated method stub
//        }
//
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            // TODO Auto-generated method stub
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//
//            // TODO Auto-generated method stub
//        }
//    });

    double getDouble(final SharedPreferences prefs, final String key, final double defaultValue) {
        if (!prefs.contains(key))
            return defaultValue;

        return Double.longBitsToDouble(prefs.getLong(key, 0));
    }

    SharedPreferences.Editor putDouble(final SharedPreferences.Editor edit, final String key, final double value) {
        return edit.putLong(key, Double.doubleToRawLongBits(value));
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
            final double bankBalance = getDouble(values, "bankBalance", 0.00);
            final EditText inputBox = (EditText) findViewById(R.id.atm_withdrawal_input);
            double inputAmount = Double.parseDouble(inputBox.getText().toString());
            putDouble(editor, "bankBalance", bankBalance - (inputAmount * exchangeRate));
            editor.commit();
        } catch (final NumberFormatException e) {
            //catch non doubles
        }




        Intent toBudgetView= new Intent(this, BudgetViewActivity.class);
        this.startActivity(toBudgetView);
        this.finish();
    }

//    private void writeItems() {
//        File filesDir = getFilesDir();
//        File todoFile = new File(filesDir, "history.txt");
//        try {
//            FileUtils.writeLines(todoFile, items);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
}


