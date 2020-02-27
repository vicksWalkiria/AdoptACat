package com.walkiriaapps.adoptacat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.bumptech.glide.Glide;
import com.walkiriaapps.adoptacat.Classes.AppData;
import com.walkiriaapps.adoptacat.Classes.Cat;
import com.walkiriaapps.adoptacat.Classes.VolleyRequestClassWalkiria;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class CatActivity extends AdoptACatActivity {
    private CatActivity ctx;
    private ProgressBar progressBar;
    private TextView adoptionType, catName;
    private ImageView catImage;
    private Button adoptButton;
    private String catID;

    @Override
    public void setActivityName() {
        this.mScreenName = AppData.CAT_SCREEN;
    }

    @Override
    public void onVolleyResponse(String response, int request) {

        progressBar.setVisibility(View.GONE);
        try {
            JSONObject object = new JSONObject(response);
            String code = object.getString(AppData.CODE);
            if (request == AppData.REQUEST_GET_CAT) {
                if (code.equals(AppData.JSON_200)) {
                    AppData.token = object.getJSONObject(AppData.JSON_DATA_FIELD).getString(AppData.TOKEN_FIELD);

                    final Cat cat = new Cat(object.getJSONObject(AppData.JSON_DATA_FIELD).getJSONObject(AppData.CAT_FIELD));
                    Glide.with(CatActivity.this).load(cat.getPictureUrl()).error(Glide.with(ctx).load(R.drawable.cat_placeholder)).into(catImage);
                    catName.setText(cat.getName());

                    adoptButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                    "mailto","info@walkiriaapps.com", null));
                            emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject));
                            emailIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.email_content)+" "+cat.toStringForEmail());
                            startActivity(Intent.createChooser(emailIntent, getString(R.string.send_email)));
                        }
                    });

                    if(cat.isAdoption())
                    {
                        adoptionType.setTextColor(ctx.getResources().getColor(R.color.colorPrimary));
                        adoptionType.setText(getString(R.string.permanent_adoption));
                    }
                    else
                    {
                        adoptionType.setTextColor(ctx.getResources().getColor(R.color.colorAccent));
                        adoptionType.setText(getString(R.string.temporary_adoption));
                    }

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

        setContentView(R.layout.activity_cat);

        catID = getIntent().getStringExtra(AppData.CAT_ID);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ctx = this;
        progressBar = findViewById(R.id.progress_bar_cat_activity);
        adoptionType = findViewById(R.id.adoption_type);
        catImage = findViewById(R.id.cat_picture);
        catName = findViewById(R.id.cat_name);
        adoptButton = findViewById(R.id.button_adopt_me);


        Map<String, String> params = new HashMap<String, String>();
        params.put("user_id", AppData.userId);
        params.put("token", AppData.token);
        params.put("cat_id", catID);

        new VolleyRequestClassWalkiria(CatActivity.this, AppData.GET_CAT_SERVICE_URL, Request.Method.GET, params, progressBar, AppData.REQUEST_GET_CAT);

    }
}
