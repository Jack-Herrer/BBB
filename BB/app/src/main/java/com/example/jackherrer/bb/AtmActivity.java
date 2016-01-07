package com.example.jackherrer.bb;

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
import android.widget.TextView;


public class AtmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atm);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        final double exchangeRate = 0.8;
        final String homeCurrency = "€";


        SharedPreferences values = getSharedPreferences("values", Context.MODE_PRIVATE);
        final double bank_balance =  getDouble(values, "bank_balance", 0.00);
        final TextView homeCurrencyView = (TextView) findViewById(R.id.atm_amount_entered_in_own_currency);
        final TextView balanceAfterView = (TextView) findViewById(R.id.atm_balance_after_withdrawal);

        TextView balanceView = (TextView) findViewById(R.id.atm_current_balance);
        balanceView.setText("" + bank_balance);



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
                    balanceAfterView.setText(homeCurrency + " " + (bank_balance - (inputAmount * exchangeRate)));

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

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    public void onWithdrawClick(View view) {
        Intent toBudgetView= new Intent(this, BudgetViewActivity.class);
        this.startActivity(toBudgetView);
        this.finish();
    }
}
