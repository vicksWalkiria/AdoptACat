package com.walkiriaapps.adoptacat.Classes;

import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.walkiriaapps.adoptacat.AdoptACatActivity;
import com.walkiriaapps.adoptacat.R;

import java.util.Map;

public class VolleyRequestClassWalkiria {

    // Phase #3 modification, added requestId
    public VolleyRequestClassWalkiria(final AdoptACatActivity ctx, String url, int requestMethod, final Map<String, String> params, final ProgressBar progressBar, final int requestId) {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }

        RequestQueue queue = Volley.newRequestQueue(ctx);
        StringRequest stringRequest = new StringRequest(requestMethod, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ctx.onVolleyResponse(response, requestId);
                        if (progressBar != null) {
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                if (response != null && response.data != null) {
                    ctx.displayAlertDialog(ctx.getString(R.string.error), response.statusCode + "");
                }

                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };
        stringRequest.setShouldCache(false);
        queue.add(stringRequest);
    }
}
