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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class BudgetViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_view);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        initialiseFields();
    }

    public void initialiseFields(){
        SharedPreferences values = getSharedPreferences("values", Context.MODE_PRIVATE);
        double bankBalance =  Update.getDouble(values, "bankBalance", 0.00);
        double budget = Update.getDouble(values, "budget", 0.00);
        double exchangerate = Update.getDouble(values, "exchange_rate", 0.00);
        String foreignCurrency = values.getString("foreign_currency", "n.a.");
        String homeCurrency = values.getString("home_currency", "n.a.");

        Toast.makeText(this, String.valueOf(bankBalance), Toast.LENGTH_SHORT).show();



        TextView balanceView = (TextView) findViewById(R.id.bv_ballance_amount_view);
        balanceView.setText("" + homeCurrency + " " + Update.roundDouble(bankBalance));

        TextView startBudgetView = (TextView) findViewById(R.id.bv_start_budget_view);
        startBudgetView.setText("Start Budget: " + homeCurrency + " " + Update.roundDouble(budget) + "    " + foreignCurrency + " " + Update.roundDouble(budget * exchangerate));

        TextView budgetView = (TextView) findViewById(R.id.bv_budget_left);
        budgetView.setText("Budget Left:  " + homeCurrency + " " + Update.roundDouble(budget) + "    " + foreignCurrency + " " + Update.roundDouble(budget * exchangerate));

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

    public boolean onOptionsItemSelected(MenuItem item){
        return ActionMenuHandler.handleMenu(item, this);
    }
}
