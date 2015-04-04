package com.nisostech.dynamiclistview.networkcalls;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.nisostech.dynamiclistview.Interface.OnTaskCompleted;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Soham Banerjee
 */

public class VolleyJsonRequest {
    public String TAG = "Volley Response";
    OnTaskCompleted listener;
    Context context;
    JsonObjectRequest jsonObjReq = null;
    private String tag_json_obj = "jobj_req";

    public VolleyJsonRequest(OnTaskCompleted listener, Context mcontext) {
        this.listener = listener;
        context = mcontext;
    }

    public void makeJsonObjReqPost(String url) {
        try {

            jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    url, null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d(TAG, response.toString());
                            listener.onTaskCompleted(response);
                            //                hideProgressDialog();
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    //Toast.makeText(context, error.getMessage(),Toast.LENGTH_LONG).show();
                    AppController.getInstance().cancelPendingRequests(jsonObjReq
                    );
                    listener.onError(error.getMessage());
                    //      hideProgressDialog();
                }
            }) {

                /**
                 * Passing some request headers
                 */
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    return headers;
                }


                @Override
                protected VolleyError parseNetworkError(VolleyError volleyError) {
                    if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                        VolleyError error = new VolleyError(new String(volleyError.networkResponse.data));
                        volleyError = error;
                        AppController.getInstance().cancelPendingRequests(jsonObjReq
                        );
                        //    listener.onError(error.getMessage());
                        //  Toast.makeText(context,String.valueOf(volleyError.printStackTrace();),Toast.LENGTH_LONG).show();
                    }

                    return volleyError;
                }


            };

            jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(50000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(jsonObjReq,
                    tag_json_obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


