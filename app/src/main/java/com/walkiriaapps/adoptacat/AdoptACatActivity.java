package com.walkiriaapps.adoptacat;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public abstract class AdoptACatActivity extends AppCompatActivity {

    protected String mScreenName;

    protected SharedPreferences prefs;

    public abstract void setActivityName();
    public abstract void onVolleyResponse(String response);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
        setActivityName();
    }

    public void displayAlertDialog(String title, String message)
    {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


    /////////////////////////PHASE 2 /////////////////////////
    public void displayDynamicDialog(String title, String message, DialogInterface.OnClickListener listener)
    {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, listener)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
