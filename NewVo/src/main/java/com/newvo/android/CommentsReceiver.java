package com.newvo.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by David on 7/3/2014.
 */
public class CommentsReceiver extends BroadcastReceiver {
    private static final String TAG = "NewVo";
    public static String POST_ID = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));

            Iterator itr = json.keys();
            while (itr.hasNext()) {
                String key = (String) itr.next();
                if(key.equals("searchObjectPost")){
                    POST_ID = json.getString(key);
                }
            }
        } catch (JSONException e) {
            Log.d(TAG, "JSONException: " + e.getMessage());
        }
    }
}
