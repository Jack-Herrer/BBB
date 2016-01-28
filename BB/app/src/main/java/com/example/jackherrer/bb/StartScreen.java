package com.example.jackherrer.bb;

/**
 * Created by Michiel van der LIst on 28-1-16.
 * Student nr 10363521
 * michielvanderlist@gmail.com
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

// source: http://stackoverflow.com/questions/5486789/how-do-i-make-a-splash-screen
public class StartScreen extends Activity {
    private static int splashInterval = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splashscreen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(StartScreen.this, BudgetViewActivity.class);
                startActivity(i);
                this.finish();
            }
            private void finish() {
            }
        }, splashInterval);
    };
}