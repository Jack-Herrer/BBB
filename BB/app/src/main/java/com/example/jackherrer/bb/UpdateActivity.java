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
import android.view.View;
import android.widget.EditText;


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

        EditText bankBalanceBox = (EditText)findViewById(R.id.inp_enter_balance);
        EditText budgetBox = (EditText)findViewById(R.id.inp_budget_box);

        //test foreign currency
        Update.changeForeignCurrency(this, "GBP");


        try{
            double bankBalance = Double.parseDouble(bankBalanceBox.getText().toString());
            Update.putDouble(editor, "bankBalance", bankBalance);

            editor.commit();
            ParseApp.saveInParse("bankBalance", bankBalance);

        } catch (final NumberFormatException e) {

            //catch non doubles

        }


        try{
            double budget = Double.parseDouble(budgetBox.getText().toString());
            Update.putDouble(editor, "budget", budget);
            editor.commit();
            ParseApp.saveInParse("budget", budget);



        } catch (final NumberFormatException e) {

            //catch non doubles
        }

        Intent toBudgetView= new Intent(this, BudgetViewActivity.class);
        this.startActivity(toBudgetView);
        this.finish();
    }



    public void onUpdateCurrenciesClick(View view) {

        Update.currencyUpdate(this);
    }
}
