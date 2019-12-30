package com.walkiriaapps.adoptacat;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


abstract class AdoptACatActivity extends AppCompatActivity {

    protected String mScreenName;

    protected SharedPreferences prefs;

    public abstract void setActivityName();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
        setActivityName();
    }
}
