package com.example.jackherrer.bb;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;

public class BudgetViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_view);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        SharedPreferences values = getSharedPreferences("values", Context.MODE_PRIVATE);
        double bank_balance =  getDouble(values, "bank_balance", 0.00);

        TextView balanceView = (TextView) findViewById(R.id.bv_ballance_amount_view);
        balanceView.setText("" + bank_balance);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        return true;
    }


    double getDouble(final SharedPreferences prefs, final String key, final double defaultValue) {
        if (!prefs.contains(key))
            return defaultValue;

        return Double.longBitsToDouble(prefs.getLong(key, 0));
    }

    public void onInputClick(View view) {
        Intent toInputActivity= new Intent(this, InputActivity.class);
        this.startActivity(toInputActivity);
        this.finish();
    }
}
