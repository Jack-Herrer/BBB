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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        Update.currencyRatesUpdate(this);
        firstUse();
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

        Update.updateBalance(this);
        Update.updateBudget(this);
        Update.updateSpent(this);
        Update.setExchangeRate(this);

        editor.commit();

        Intent toBudgetView= new Intent(this, BudgetViewActivity.class);
        this.startActivity(toBudgetView);
        this.finish();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        return ActionMenuHandler.handleMenu(item, this);
    }

    public void onClearDataClick(View view) {
        Update.clearData(this);
        Intent toBudgetView= new Intent(this, BudgetViewActivity.class);
        this.startActivity(toBudgetView);
        this.finish();
    }

    public void firstUse() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            boolean firstUse = extras.getBoolean("firstUse");
            if (firstUse) {
                SharedPreferences values = getSharedPreferences("values", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = values.edit();
                editor.putBoolean("firstUse", false);
                editor.commit();

                CheckBox homeCheck = (CheckBox) findViewById(R.id.home_checkBox);
                CheckBox foreignCheck = (CheckBox) findViewById(R.id.foreign_checkbox);
                CheckBox balanceCheck = (CheckBox) findViewById(R.id.balance_checkBox);
                CheckBox budgetCheck = (CheckBox) findViewById(R.id.budget_checkBox);
                CheckBox spentCheck = (CheckBox) findViewById(R.id.inp_spent_box);

                homeCheck.setChecked(true);
                foreignCheck.setChecked(true);
                balanceCheck.setChecked(true);
                budgetCheck.setChecked(true);
                spentCheck.setChecked(true);

                homeCheck.setClickable(false);
                foreignCheck.setClickable(false);
                balanceCheck.setClickable(false);
                budgetCheck.setClickable(false);
                spentCheck.setClickable(false);

                Toast.makeText(getApplicationContext(), "Please fill in all fields before you start", Toast.LENGTH_LONG).show();
                Update.currencyRatesUpdate(this);
            }
        }
    }

    public void onBack(View view) {
        Intent toBudgetView= new Intent(this, BudgetViewActivity.class);
        this.startActivity(toBudgetView);
        this.finish();
    }
}