package com.example.covid2.webservices;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid2.models.User;
import com.example.covid2.utilities.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class WebServices {

    public static void retrieveFromDatabase(final Context c, final User u) {
        final ProgressDialog pd = new ProgressDialog(c);
        pd.setTitle("Loading");
        pd.setMessage("Please wait...");
        pd.show();

        RequestQueue queue = Volley.newRequestQueue(c);
        String url = Config.URL + Constants.RETRIEVE;

        StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                // Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                try {
                    JSONObject o = new JSONObject(s);
                    String data = o.getString("result");
                    if (data.equals("1")) {
                        Toast.makeText(c, "Loaded Successfully", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(c, "Loading Data Failed", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                pd.dismiss();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                pd.dismiss();
                String errorDescription = "";
                if (volleyError instanceof NetworkError) {
                    errorDescription = "Network Error";
                } else if (volleyError instanceof ServerError) {
                    errorDescription = "Server Error";
                } else if (volleyError instanceof AuthFailureError) {
                    errorDescription = "AuthFailureError";
                } else if (volleyError instanceof ParseError) {
                    errorDescription = "Parse Error";
                } else if (volleyError instanceof NoConnectionError) {
                    errorDescription = "No Connection";
                } else if (volleyError instanceof TimeoutError) {
                    errorDescription = "Time Out";
                } else {
                    errorDescription = "Connection Error";
                }
                Toast.makeText(c, errorDescription, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("nid", u.getNid());
                param.put("cardid", u.getCardid().toUpperCase());
                param.put("lang", u.getLang() + "");
                param.put("lat", u.getLat() + "");
                param.put("diseases", u.getDea().toString());
                return param;
            }
        };
        req.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(req);

        pd.dismiss();

    }

    public static void SubmitToDatabase(final Context c, final User u) {
        final ProgressDialog pd = new ProgressDialog(c);
        pd.setTitle("Loading");
        pd.setMessage("Please wait...");
        pd.show();

        RequestQueue queue = Volley.newRequestQueue(c);
        String url = Config.URL + Constants.ENTER;

        StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject o = new JSONObject(s);
                    String data = o.getString("result");
                    if (data.equals("1")) {
                        Toast.makeText(c, "Submitted Successfully", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(c, "Failed", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                pd.dismiss();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                pd.dismiss();
                String errorDescription = "";
                if (volleyError instanceof NetworkError) {
                    errorDescription = "Network Error";
                } else if (volleyError instanceof ServerError) {
                    errorDescription = "Server Error";
                } else if (volleyError instanceof AuthFailureError) {
                    errorDescription = "AuthFailureError";
                } else if (volleyError instanceof ParseError) {
                    errorDescription = "Parse Error";
                } else if (volleyError instanceof NoConnectionError) {
                    errorDescription = "No Connection";
                } else if (volleyError instanceof TimeoutError) {
                    errorDescription = "Time Out";
                } else {
                    errorDescription = "Connection Error";
                    Log.i("WhsatIsHERE", volleyError.getMessage());
                }
                Toast.makeText(c, errorDescription, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("nid", u.getNid());
                param.put("cardid", u.getCardid().toUpperCase());
                param.put("lang", u.getLang() + "");
                param.put("lat", u.getLat() + "");
                param.put("diseases", u.getDea().toString());
                return param;
            }
        };
        req.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(req);

        pd.dismiss();

    }



}
