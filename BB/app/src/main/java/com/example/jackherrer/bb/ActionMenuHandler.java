package com.example.jackherrer.bb;

/**
 * Created by Michiel van der LIst on 25-1-16.
 * Student nr 10363521
 * michielvanderlist@gmail.com
 */

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.Toast;
import com.parse.ParseUser;

public class ActionMenuHandler {
    static public boolean handleMenu(MenuItem item, Activity activity) {

        switch (item.getItemId()) {
            case R.id.action_logout:

                // log out current user and send to input screen
                if(ParseUser.getCurrentUser() != null) {
                    ParseUser.logOut();

                    Intent intent = new Intent(activity, UpdateActivity.class);
                    intent.putExtra("firstUse", true);
                    activity.startActivity(intent);
                    activity.finish();

                    Toast.makeText(activity.getApplicationContext(), "Log out successful",
                            Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(activity.getApplicationContext(), "No user currently logged in",
                            Toast.LENGTH_SHORT).show();
                }
                return true;

            case R.id.action_login:
                Intent to_login = new Intent(activity, LoginActivity.class);
                activity.startActivity(to_login);
                activity.finish();
                return true;

            case R.id.action_exit:
                activity.finish();
                return true;

            default:
                return true;
        }
    }
}
