package pl.lewandowskimaciej.codeconceptrecruitmentapp;

import android.content.Context;
import android.net.UrlQuerySanitizer;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private String TAG = "MyHttpRequestLog: ";
    private String id;
    private String response;

    private void setIDvalue(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    private void setResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return this.response;
    }

    public void callbackResponse() {}

    public HttpRequest(Context context, int requestMethod, String url, JSONObject jsonBody) {
        //request a json object response
        JsonObjectRequest stringRequest = new JsonObjectRequest(requestMethod, url, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d(TAG, "onResponse: " + response.toString());
                        setResponse(response.toString());
                        callbackResponse();
                        if (requestMethod != 0) {
                            try {
                                setIDvalue((new JSONObject(response.getString("data"))).getString("id"));
                                Log.d(TAG, "new id = " + id);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //handle the error
                try {
                    Log.d(TAG, "onErrorResponse status code: " + error.networkResponse.statusCode + "   " + error.toString() + "       " + jsonBody.toString());
                } catch (Exception e) {
                    Log.d(TAG, "Exception of exception is: " + e);
                }
            }
        }) {    //this is the part, that adds the header to the request
            @Override
            public Map<String, String> getHeaders()  throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json");
                if (requestMethod != 0) headers.put("Authorization", "Bearer 36067fa90385d4ab5b331ab1e2f1943539567fe10704c91bcdbb6d71e1aa6846");

                return headers;
            }

            @Override
            protected  Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                return super.parseNetworkResponse(response);
            }
        };

// Add the request to the queue
        Volley.newRequestQueue(context).add(stringRequest);
    }
}
