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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

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

        // update foreign currency
        Spinner foreignSpinner = (Spinner) findViewById(R.id.upd_foreign_spinner);
        CheckBox foreignCheck = (CheckBox) findViewById(R.id.foreign_checkbox);
        if(foreignCheck.isChecked()) {
            Update.changeForeignCurrency(this, foreignSpinner.getSelectedItem().toString());
        }

        // update home currency
        Spinner homeSpinner = (Spinner) findViewById(R.id.upd_home_spinner);
        CheckBox homeCheck = (CheckBox) findViewById(R.id.home_checkBox);
        if(homeCheck.isChecked()) {
            Update.changeHomeCurrency(this, homeSpinner.getSelectedItem().toString());
        }

        // update balance
        EditText bankBalanceBox = (EditText)findViewById(R.id.inp_enter_balance);
        CheckBox balanceCheck = (CheckBox) findViewById(R.id.balance_checkBox);
        if(balanceCheck.isChecked()) {
            try {
                double bankBalance = Double.parseDouble(bankBalanceBox.getText().toString());
                Update.putDouble(editor, "bankBalance", bankBalance);
                editor.commit();
                ParseApp.saveInParse("bankBalance", bankBalance);
            } catch (final NumberFormatException e) {
                Toast.makeText(getApplicationContext(), "failed to update balance: no input", Toast.LENGTH_SHORT).show();
            }
        }

        // update budget
        EditText budgetBox = (EditText)findViewById(R.id.inp_budget_box);
        CheckBox budgetCheck = (CheckBox) findViewById(R.id.budget_checkBox);
        if(budgetCheck.isChecked()) {
            try {
                double budget = Double.parseDouble(budgetBox.getText().toString());
                Update.putDouble(editor, "budget", budget);
                editor.commit();
                ParseApp.saveInParse("budget", budget);

            } catch (final NumberFormatException e) {
                Toast.makeText(getApplicationContext(), "failed to update budget: no input", Toast.LENGTH_SHORT).show();
            }
        }

        Update.setExchangeRate(this);
        Intent toBudgetView= new Intent(this, BudgetViewActivity.class);
        this.startActivity(toBudgetView);
        this.finish();
    }
    public void onUpdateCurrenciesClick(View view) {

        Update.currencyRatesUpdate(this);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        return ActionMenuHandler.handleMenu(item, this);
    }

}
