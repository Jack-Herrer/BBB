package com.example.jackherrer.bb;

import java.io.Serializable;

/**
 * Created by jackherrer on 12-1-16.
 */
public class HistoryItem implements Serializable {
    double withdrawn;

    public HistoryItem(double atmWithdrawn){
        this.withdrawn = atmWithdrawn;
    }
}
