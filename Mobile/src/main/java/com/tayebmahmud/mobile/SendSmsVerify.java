package com.tayebmahmud.mobile;

import android.content.Context;
import android.util.Log;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SendSmsVerify {
    private final Context context;
    private String SMS_SEND_URL;
    private String SMS_VERIFY_URL;
    private final String API_KEY;
    private HashMap<String, String> SNEDSMSMAP;
    private HashMap<String, String> VERIFYSMSMAP;

    public SendSmsVerify(Context context, String API_KEY, HashMap<String, String> SNEDSMSMAP, String SMS_SEND_URL) {
        //send sms
        this.context = context;
        this.API_KEY = API_KEY;
        this.SNEDSMSMAP = SNEDSMSMAP;
        this.SMS_SEND_URL = SMS_SEND_URL;
    }

    public SendSmsVerify(Context context, String SMS_VERIFY_URL, String API_KEY, HashMap<String, String> VERIFYSMSMAP) {
        //verify sms
        this.context = context;
        this.SMS_VERIFY_URL = SMS_VERIFY_URL;
        this.API_KEY = API_KEY;
        this.VERIFYSMSMAP = VERIFYSMSMAP;
    }

    public interface SMSCallback {
        void onResult(boolean success);
    }

    public void sendSMS(SMSCallback callback) {
        try {
            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(context);

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, SMS_SEND_URL,
                    response -> {
                        Log.d("SmsReceiver", "php Successfully: " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String error = jsonObject.getString("error");
                            callback.onResult(error.equals("false"));
                        } catch (JSONException e) {
                            callback.onResult(false);
                            throw new RuntimeException(e);
                        }
                    }, error -> {
                callback.onResult(false);
                Log.d("SmsReceiver", "php unSuccessfully: " + error.getMessage());
            }) {
                @Override
                public Map<String, String> getHeaders() {
                    HashMap<String, String> stringStringHashMap = new HashMap<>();
                    stringStringHashMap.put("api-key", API_KEY);
                    return stringStringHashMap;
                }

                @Nullable
                @Override
                protected Map<String, String> getParams() {
                    return SNEDSMSMAP;
                }
            };

            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void verifySMS(SMSCallback callback) {
        try {
            RequestQueue queue = Volley.newRequestQueue(context);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, SMS_VERIFY_URL,
                    response -> {
                        if (response != null && !response.isEmpty()) {
                            try {
                                // Parse the JSON response
                                JSONObject jsonResponse = new JSONObject(response);
                                String error = jsonResponse.getString("error");
                                callback.onResult("false".equals(error));

                            } catch (JSONException e) {
                                callback.onResult(false);
                                e.printStackTrace();
                            }
                        } else {
                            callback.onResult(false);
                        }
                    }, error -> {
                callback.onResult(false);
            }) {
                @Override
                public Map<String, String> getHeaders() {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("api-key", API_KEY);
                    return map;
                }

                @NonNull
                @Override
                protected Map<String, String> getParams() {
                    return VERIFYSMSMAP;
                }
            };
            queue.add(stringRequest);

        } catch (Exception e) {
            e.printStackTrace();
            callback.onResult(false);
        }
    }

}
