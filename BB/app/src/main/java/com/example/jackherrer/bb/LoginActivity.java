package com.example.jackherrer.bb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void signUp(View v)
    {
        EditText username   = (EditText)findViewById(R.id.usernameEdt);
        EditText password   = (EditText)findViewById(R.id.passwordEdt);

        ParseUser user = new ParseUser();
        user.setUsername(username.getText().toString());
        user.setPassword(password.getText().toString());

        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("test", "hoi");
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                }
            }
        });

    }

    public void logIn(View v)
    {
        EditText username   = (EditText)findViewById(R.id.usernameEdt);
        EditText password   = (EditText)findViewById(R.id.passwordEdt);


        ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {

                    Toast.makeText(getApplicationContext(),  "Login Successful" , Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, BudgetViewActivity.class);
                    startActivity(intent);
                } else {

                    Toast.makeText(getApplicationContext(),  "Login Failed. Try again" , Toast.LENGTH_SHORT).show();

                    // Signup failed. Look at the ParseException to see what happened.
                }
            }
        });
    }

}
