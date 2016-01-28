package com.example.jackherrer.bb;

/**
 * Created by Michiel van der List on 14-1-16.
 * Student nr 10363521
 * michielvanderlist@gmail.com
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

    public void signUp(View v)
    {
        Intent toSignup = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(toSignup);
        this.finish();
    }

    public void logIn(View v)
    {
        EditText username   = (EditText)findViewById(R.id.usernameEdt);
        EditText password   = (EditText)findViewById(R.id.passwordEdt);
        ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    Toast.makeText(getApplicationContext(), "Login Successful",
                            Toast.LENGTH_SHORT).show();

                    updateDataPrompt();

                } else {
                    Toast.makeText(getApplicationContext(), "Login Failed. Try again",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        return ActionMenuHandler.handleMenu(item, this);
    }

    public void logOut(View view) {
        ParseUser.logOut();
        Intent intent = new Intent(this, BudgetViewActivity.class);
        Update.clearData(this);
        startActivity(intent);
        finish();
    }

    // user choice: local or cloud data
    // source: http://stackoverflow.com/questions/2115758/how-do-i-display-an-alert-dialog-on-android
    public void updateDataPrompt () {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Download data form cloud?");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ParseApp parseApp = new ParseApp();
                        parseApp.getValuesFromParse(LoginActivity.this, LoginActivity.this);
                        Update.currencyRatesUpdate(LoginActivity.this);
                        dialog.cancel();
                    }
                });
        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(LoginActivity.this, BudgetViewActivity.class);
                        startActivity(intent);
                        finish();
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void onBack(View view) {
        Intent toBudgetView= new Intent(this, BudgetViewActivity.class);
        this.startActivity(toBudgetView);
        this.finish();
    }
}