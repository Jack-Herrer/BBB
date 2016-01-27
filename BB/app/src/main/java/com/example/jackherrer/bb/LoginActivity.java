package com.example.jackherrer.bb;

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

                    ParseApp parseApp = new ParseApp();
                    parseApp.getValuesFromParse(LoginActivity.this, LoginActivity.this);
                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();

                    // wait till parse finished in background
                    Update.currencyRatesUpdate(LoginActivity.this);

                } else {
                    Toast.makeText(getApplicationContext(), "Login Failed. Try again", Toast.LENGTH_SHORT).show();
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

}
