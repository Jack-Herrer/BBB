package com.example.jackherrer.bb;

/**
 * Created by Michiel van der List on 11-1-16.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class HistoryViewActivity extends AppCompatActivity {
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_view);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        lvItems = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList<String>();

        readItems();
        itemsAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
        addHistory();
    }

    public void onBack(View view) {
        Intent toBudgetView= new Intent(this, BudgetViewActivity.class);
        this.startActivity(toBudgetView);
        this.finish();
    }

    public void addItem(String text) {
        itemsAdapter.insert(text, 0);
        writeItems();
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File historyfile = new File(filesDir, "history.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(historyfile));
        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }

    public void writeItems() {
        File filesDir = getFilesDir();
        File historyfile = new File(filesDir, "history.txt");
        try {
            FileUtils.writeLines(historyfile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ParseApp.saveInParse("history", historyfile);
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter,
                                                   View item, int pos, long id) {
                        // Remove the item within array at position
                        items.remove(pos);
                        // Refresh the adapter
                        itemsAdapter.notifyDataSetChanged();
                        writeItems();
                        // Return true consumes the long click event (marks it handled)
                        return true;
                    }
                });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return ActionMenuHandler.handleMenu(item, this);
    }

    public void addHistory(){
        SharedPreferences values = this.getSharedPreferences("values", Context.MODE_PRIVATE);
        String history = values.getString("history", "error");


        // add history item if withdrawn
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            boolean addToHistory = extras.getBoolean("withdrawn");
            if (addToHistory) {
                //JSONObject historyObject = null;
                String foreignCurrency = null;
                double withdrawn = 0;
                try {
                    JSONObject historyObject = new JSONObject(history);
                    foreignCurrency = historyObject.getString("foreignCurrency");
                    withdrawn = Update.roundDouble(historyObject.getDouble("withdrawn"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy [HH:mm]");
                String date = df.format(Calendar.getInstance().getTime());
                addItem(date + ": " + foreignCurrency + " " + withdrawn);

                // go to budgetview
                Intent toBudgetView = new Intent(this, BudgetViewActivity.class);
                this.startActivity(toBudgetView);
                this.finish();
            }
        }
    }
}
