package com.walkiriaapps.adoptacat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.walkiriaapps.adoptacat.Classes.AppData;

public class Splash extends AdoptACatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    public void setActivityName() {
        mScreenName = AppData.SPLASH_SCREEN;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                if(!prefs.getString(AppData.USER_NAME, "").equals(""))
                {
                    //Perform AutoLogin

                    Intent mainIntent = new Intent(Splash.this,MainActivity.class);
                    Splash.this.startActivity(mainIntent);
                    Splash.this.finish();
                }
                else
                {
                    //Display Login-Register screen
                    Intent mainIntent = new Intent(Splash.this,Login.class);
                    Splash.this.startActivity(mainIntent);
                    Splash.this.finish();
                }

            }
        }, SPLASH_DISPLAY_LENGTH);
    }


}
