package com.example.covid2.webservices;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Config {
    public static String URL = "http://192.168.1.2/covid/";
}
// "http://192.168.1.2/covid/"
// http://192.168.1.2:56889/phpmyadmin/
// http://192.168.1.2:56889/phpmyadmin/db_structure.php?server=1&db=covid
