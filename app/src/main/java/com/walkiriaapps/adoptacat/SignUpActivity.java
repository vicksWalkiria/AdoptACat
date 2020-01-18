package com.walkiriaapps.adoptacat;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.walkiriaapps.adoptacat.Classes.AppData;
import com.walkiriaapps.adoptacat.Classes.VolleyRequestClassWalkiria;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AdoptACatActivity {

    private EditText userName, password;
    private Button signUpButton;
    private ProgressBar progressBar;
    private EditText passwordRepeat;

    @Override
    public void setActivityName() {
        mScreenName = AppData.SIGNUP_SCREEN;
    }

    @Override
    public void onVolleyResponse(String response, int request) {

        try {
            Log.d("WALKIRIA", "SIGNUP RESPONSE: "+response);
            JSONObject object = new JSONObject(response);
            String message = object.getJSONObject(AppData.JSON_DATA_FIELD).getString(AppData.MESSAGE_FIELD);
            if (message.equals(AppData.JSON_OK)) {
//                AppData.loginOK((AdoptACatActivity) this, object.getJSONObject(AppData.JSON_DATA_FIELD).getString(AppData.TOKEN_FIELD), prefs, userName.getText().toString(), AppData.encodePassword(password.getText().toString()));
                SharedPreferences.Editor edit = prefs.edit();

                edit.putString(AppData.USER_NAME, userName.getText().toString());
                edit.putString(AppData.PASSWORD, password.getText().toString());
                edit.commit();


                DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setResult(Activity.RESULT_OK);
                        finish();
                    }
                };

                displayDynamicDialog(getString(R.string.action_sign_up), getString(R.string.signup_successful), listener);

            } else {
                displayAlertDialog(getString(R.string.signup_error), message);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        userName = findViewById(R.id.et_user_name);
        password = findViewById(R.id.et_password);
        passwordRepeat = findViewById(R.id.et_password_repeat);
        signUpButton = findViewById(R.id.button_signup);
        progressBar = findViewById(R.id.progress_bar_signup);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (testForm()) {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("user_name", userName.getText().toString());
                    params.put("password", AppData.encodePassword(password.getText().toString()));
                    new VolleyRequestClassWalkiria(SignUpActivity.this, AppData.SIGNUP_SERVICE_URL, Request.Method.POST, params, progressBar, 0);
                }
            }
        });
    }

    private boolean testForm() {
        clearErrors();
        boolean valid = true;
        String errorMessage = "";

        if (!passwordRepeat.getText().toString().equals(password.getText().toString())) {
            valid = false;
            passwordRepeat.setError(getString(R.string.passwords_do_not_match));
            errorMessage += getString(R.string.passwords_do_not_match);
        }

        if (!valid) {
            displayAlertDialog(getString(R.string.signup_error), errorMessage);
        }

        return valid;

    }

    private void clearErrors() {
        userName.setError(null);
        password.setError(null);
        passwordRepeat.setError(null);
    }
}