package com.walkiriaapps.adoptacat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.walkiriaapps.adoptacat.Classes.AppData;
import com.walkiriaapps.adoptacat.Classes.VolleyRequestClassWalkiria;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AdoptACatActivity {

    private EditText userName, password;
    private Button loginButton;
    private TextView signUpButton;
    private ProgressBar progressBar;

    @Override
    public void setActivityName() {
        mScreenName = AppData.LOGIN_SCREEN;
    }

    @Override
    public void onVolleyResponse(String response) {

        try {
            JSONObject object = new JSONObject(response);
            String message = object.getJSONObject(AppData.JSON_DATA_FIELD).getString(AppData.MESSAGE_FIELD);
            if (message.equals(AppData.JSON_OK)) {
                AppData.loginOK((AdoptACatActivity) this, object.getJSONObject(AppData.JSON_DATA_FIELD).getString(AppData.TOKEN_FIELD), prefs, userName.getText().toString(), AppData.encodePassword(password.getText().toString()));
            } else {
                displayAlertDialog(getString(R.string.error), message);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName = findViewById(R.id.et_user_name);
        password = findViewById(R.id.et_password);
        loginButton = findViewById(R.id.button_login);
        progressBar = findViewById(R.id.progress_bar_login);
        signUpButton = findViewById(R.id.signup_text);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userName.getText().toString().length() == 0 || password.getText().toString().length() == 0) {
                    displayAlertDialog(getString(R.string.error), getString(R.string.complete_all_the_fields));
                } else {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("user_name", userName.getText().toString());
                    params.put("password", AppData.encodePassword(password.getText().toString()));
                    new VolleyRequestClassWalkiria(Login.this, AppData.LOGIN_SERVICE_URL, Request.Method.GET, params, progressBar);
                }

            }
        });
    }
}
