package com.todo.enigma.models;

import android.content.Intent;
import android.graphics.Color;
import android.speech.RecognizerIntent;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;

import com.todo.enigma.MainActivity;
import com.todo.enigma.utils.DBUtils;
import com.todo.enigma.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;

import static android.app.Activity.RESULT_OK;


public enum Priority {
    LOW("low", 2, Color.rgb(204, 214, 0)),
    MEDIUM("medium", 1, Color.rgb(255, 170, 0)),
    HIGH("high", 0, Color.rgb(255, 0, 0));

    private final int value;
    private final String name;
    private final int color;

    Priority(final String newName, final int newValue, final int newColor) {
        name = newName;
        value = newValue;
        color = newColor;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return MainActivity.getContext().getString(MainActivity.getContext().getResources().getIdentifier(name, "string",  MainActivity.getContext().getPackageName()));
    }

    public int getColor() {
        return color;
    }

   /* @Override public String toString(){
        return MainActivity.getContext().getString(MainActivity.getContext().getResources().getIdentifier(name, "string",  MainActivity.getContext().getPackageName()));
    } */

}