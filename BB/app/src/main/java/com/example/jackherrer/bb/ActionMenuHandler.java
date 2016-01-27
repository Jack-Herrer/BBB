package com.example.jackherrer.bb;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;

/**
 * Created by marley on 25-1-16.
 * Student nr 10363521
 * michielvanderlist@gmail.com
 */
public class ActionMenuHandler {
    static public boolean handleMenu(MenuItem item, Activity activity) {

        switch (item.getItemId()) {
            case R.id.action_help:
                Intent new_game = new Intent(activity, HelpActivity.class);
                activity.startActivity(new_game);
                activity.finish();
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
