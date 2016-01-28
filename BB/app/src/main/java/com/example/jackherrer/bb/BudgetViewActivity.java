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
import android.widget.ProgressBar;
import android.widget.TextView;
import com.parse.ParseUser;
import java.text.DecimalFormat;

public class BudgetViewActivity extends AppCompatActivity {
    DecimalFormat f = new DecimalFormat("#0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_view);

        firstUseCheck();
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        initialiseFields();
    }

    public void initialiseFields(){
        // get most recent values
        SharedPreferences values = getSharedPreferences("values", Context.MODE_PRIVATE);
        double bankBalance =  Update.getDouble(values, "bankBalance", 0.00);
        double budget = Update.getDouble(values, "budget", 0.00);
        double spent = Update.getDouble(values, "spent", 0.00);
        double exchangerate = Update.getDouble(values, "exchange_rate", 0.00);
        String foreignCurrency = values.getString("foreign_currency", "n.a.");
        String homeCurrency = values.getString("home_currency", "n.a.");

        TextView balanceView = (TextView) findViewById(R.id.bv_ballance_amount_view);
        TextView foreignBalanceView = (TextView) findViewById(R.id.bv_foreign_ballance_amount_view);
        TextView spentView = (TextView) findViewById(R.id.bv_budget_spent);
        TextView startBudgetView = (TextView) findViewById(R.id.bv_start_budget_view);
        TextView budgetView = (TextView) findViewById(R.id.bv_budget_left);
        TextView loggedInView = (TextView) findViewById(R.id.bv_logged_in_as);
        ProgressBar budgetBar = (ProgressBar) findViewById(R.id.bv_budget_bar);

        // adapt textviews to values
        balanceView.setText("" + homeCurrency + " " + f.format(Update.roundDouble(bankBalance)));
        foreignBalanceView.setText("" + foreignCurrency + " " +
                f.format(Update.roundDouble(bankBalance / exchangerate)));
        spentView.setText("Budget Spent" + " " + homeCurrency + " " +
                f.format(Update.roundDouble(spent)) + "    " + foreignCurrency + " " +
                f.format(Update.roundDouble(spent / exchangerate)));
        startBudgetView.setText("Start Budget: " + homeCurrency + " " +
                f.format(Update.roundDouble(budget)) + "    " + foreignCurrency + " " +
                f.format(Update.roundDouble(budget / exchangerate)));
        budgetView.setText("Budget Left:  " + homeCurrency + " " +
                f.format(Update.roundDouble(budget - spent)) + "    " + foreignCurrency + " " +
                f.format(Update.roundDouble(budget / exchangerate)));
        budgetBar.setProgress((int) (((budget - bankBalance) / budget) * 100));
        if(ParseUser.getCurrentUser() != null)
        loggedInView.setText("Logged in as: " + ParseUser.getCurrentUser().getString("username"));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    public void onUpdateClick(View view) {
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

    public void firstUseCheck(){
        SharedPreferences values = getSharedPreferences("values", Context.MODE_PRIVATE);
        boolean firstUse = values.getBoolean("firstUse", true);

        if(firstUse) {
            Intent toInputActivity = new Intent(this, UpdateActivity.class);
            toInputActivity.putExtra("firstUse", true);
            this.startActivity(toInputActivity);
            this.finish();
        }
    }
}