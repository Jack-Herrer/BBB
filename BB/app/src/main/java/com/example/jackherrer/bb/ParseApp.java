package com.example.jackherrer.bb;

import android.app.Application;
import android.os.Bundle;

import com.parse.Parse;

/**
 * Created by jackherrer on 13-1-16.
 */
public class ParseApp extends Application {

    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "1NdzsnA614zjI8b8SavdKnIqgc0MRZ4CcNIziTwV", "WzigF4V1IKSlTRJendFDfyl9De0u1rl3sK554Och");

    }
}
