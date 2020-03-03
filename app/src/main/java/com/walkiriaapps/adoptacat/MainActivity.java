package com.walkiriaapps.adoptacat;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.bumptech.glide.Glide;
import com.walkiriaapps.adoptacat.Adapters.AdoptionAdapter;
import com.walkiriaapps.adoptacat.Classes.AppData;
import com.walkiriaapps.adoptacat.Classes.Cat;
import com.walkiriaapps.adoptacat.Classes.VolleyRequestClassWalkiria;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AdoptACatActivity {

    private ArrayList<Cat> catsForAdoption;
    private ArrayList<Cat> catsForTemporaryAdoption;
    private ProgressBar progressBar;
    private Context ctx;
    private AdoptionAdapter adoptionsAdapter;
    private RecyclerView recyclerViewAdoptions;
    private LinearLayoutManager layoutManagerAdoptions;
    private RecyclerView recyclerViewTemporary;
    private AdoptionAdapter temporaryAdapter;
    private LinearLayoutManager layoutManagerTemporary;

    @Override
    public void setActivityName() {
        mScreenName = AppData.MAIN_SCREEN;
    }

    @Override
    public void onVolleyResponse(String response, int requestId) {

        Log.d("WALKIRIA", "RESPONSE: "+response);
        try {
            JSONObject object = new JSONObject(response);
            String code = object.getString(AppData.CODE);
            if (requestId == AppData.REQUEST_ADOPTION) {
                if (code.equals(AppData.JSON_200)) {
                    /*
                    MAKE THE NEXT CALL BEFORE LOADING THE RECYCLERVIEW
                     */

                    AppData.token = object.getJSONObject(AppData.JSON_DATA_FIELD).getString(AppData.TOKEN_FIELD);
                    catsForTemporaryAdoption = new ArrayList<Cat>();
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("user_id", AppData.userId);
                    params.put("token", AppData.token);
                    params.put("adoption", AppData.REQUEST_TEMPORARY + ""); //!!!
                    new VolleyRequestClassWalkiria(MainActivity.this, AppData.ADOPT_SERVICE_URL, Request.Method.GET, params, progressBar, AppData.REQUEST_TEMPORARY);
                    JSONArray message = object.getJSONObject(AppData.JSON_DATA_FIELD).getJSONArray(AppData.CATS_FIELD);

                    for (int i = 0; i < message.length(); i++) {
                        catsForAdoption.add(new Cat(message.getJSONObject(i)));
                    }
                    /*
                    Create first adapter
                     */
                    adoptionsAdapter = new AdoptionAdapter(catsForAdoption, Glide.with(MainActivity.this), ctx, AppData.REQUEST_ADOPTION);
                    recyclerViewAdoptions.setAdapter(adoptionsAdapter);

                } else {
                    displayAlertDialog(getString(R.string.error), getString(R.string.generic_error));
                }

            } else {
                if (code.equals(AppData.JSON_200)) {
                    /*
                    MAKE THE NEXT CALL BEFORE LOADING THE RECYCLERVIEW
                     */

                    AppData.token = object.getJSONObject(AppData.JSON_DATA_FIELD).getString(AppData.TOKEN_FIELD);
                    JSONArray message = object.getJSONObject(AppData.JSON_DATA_FIELD).getJSONArray(AppData.CATS_FIELD);

                    for (int i = 0; i < message.length(); i++) {
                        catsForTemporaryAdoption.add(new Cat(message.getJSONObject(i)));
                    }

                    temporaryAdapter = new AdoptionAdapter(catsForTemporaryAdoption, Glide.with(MainActivity.this), ctx, AppData.REQUEST_TEMPORARY);
                    recyclerViewTemporary.setAdapter(temporaryAdapter);

                } else {
                    displayAlertDialog(getString(R.string.error), getString(R.string.generic_error));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ctx = this;
        progressBar = findViewById(R.id.progress_bar_main_activity);
        recyclerViewAdoptions = findViewById(R.id.adoption_rv);
        recyclerViewTemporary = findViewById(R.id.temporary_rv);
        recyclerViewAdoptions.setHasFixedSize(true);
        recyclerViewTemporary.setHasFixedSize(true);
        // use a linear layout manager
        layoutManagerAdoptions = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        layoutManagerTemporary = new LinearLayoutManager(this);
        
        recyclerViewAdoptions.setLayoutManager(layoutManagerAdoptions);
        recyclerViewTemporary.setLayoutManager(layoutManagerTemporary);

    }

    @Override
    protected void onResume() {
        super.onResume();
        catsForAdoption = new ArrayList<Cat>();
        Map<String, String> params = new HashMap<String, String>();
        params.put("user_id", AppData.userId);
        params.put("token", AppData.token);
        params.put("adoption", AppData.REQUEST_ADOPTION + "");
        Log.d("WALKIRIA", "Params: "+params);

        new VolleyRequestClassWalkiria(MainActivity.this, AppData.ADOPT_SERVICE_URL, Request.Method.GET, params, progressBar, AppData.REQUEST_ADOPTION);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.logout)
        {
            Toast.makeText(this,getString(R.string.logout),Toast.LENGTH_LONG).show();
            SharedPreferences.Editor edit = prefs.edit();

            edit.putString(AppData.USER_NAME, "");
            edit.putString(AppData.PASSWORD, "");
            edit.commit();

            startActivity(new Intent(MainActivity.this,Login.class));
            finish();
        }
        return true;
    }
}
