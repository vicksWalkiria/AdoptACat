package com.walkiriaapps.adoptacat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.android.volley.Request;
import com.walkiriaapps.adoptacat.Classes.AppData;
import com.walkiriaapps.adoptacat.Classes.VolleyRequestClassWalkiria;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Splash extends AdoptACatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    public void setActivityName() {
        mScreenName = AppData.SPLASH_SCREEN;
    }

    @Override
    public void onVolleyResponse(String response, int request) {
        try {
            JSONObject object = new JSONObject(response);

            if (object.getJSONObject(AppData.JSON_DATA_FIELD).getString(AppData.MESSAGE_FIELD).equals(AppData.JSON_OK)) {
                AppData.loginOK((AdoptACatActivity) Splash.this, object.getJSONObject(AppData.JSON_DATA_FIELD).getString(AppData.TOKEN_FIELD), prefs,
                        prefs.getString(AppData.USER_NAME, ""), prefs.getString(AppData.PASSWORD, ""));
                // Phase #3 - add user id to AppData
                AppData.userId = object.getJSONObject(AppData.JSON_DATA_FIELD).getString(AppData.USER_ID_FIELD);

            }
            else
            {
                displayLogin();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (!prefs.getString(AppData.USER_NAME, "").equals("")) {
                    //Perform AutoLogin
                    Map<String,String> params = new HashMap<String,String>();
                    params.put("user_name", prefs.getString(AppData.USER_NAME,""));
                    params.put("password", prefs.getString(AppData.PASSWORD,""));
                    new VolleyRequestClassWalkiria(Splash.this, AppData.LOGIN_SERVICE_URL, Request.Method.GET, params, null,0);

                } else {
                    //Display Login-Register screen
                  displayLogin();
                }

            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void displayLogin() {
        Intent mainIntent = new Intent(Splash.this, Login.class);
        Splash.this.startActivity(mainIntent);
        Splash.this.finish();
    }


}
