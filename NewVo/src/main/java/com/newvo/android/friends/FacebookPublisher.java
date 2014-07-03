package com.newvo.android.friends;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.facebook.*;
import com.newvo.android.util.ToastUtils;
import com.parse.ParseFile;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by David on 7/3/2014.
 */
public class FacebookPublisher {

    private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");
    private static final String PENDING_PUBLISH_KEY = "pendingPublishReauthorization";

    private final Activity activity;
    private Session.StatusCallback statusCallback;

    public FacebookPublisher(Activity activity){
        this.activity = activity;
    }

    public Activity getActivity() {
        return activity;
    }

    public void publishStory(final ParseFile shareImage, final String caption, final String postId, final String facebookId) {
        Session session = Session.getActiveSession();

        if (session != null){
            // Check for publish permissions
            session.refreshPermissions();
            List<String> permissions = session.getPermissions();
            if (!isSubsetOf(PERMISSIONS, permissions)) {
                statusCallback = new Session.StatusCallback() {
                    @Override
                    public void call(Session session, SessionState sessionState, Exception e) {
                        if (!isSubsetOf(PERMISSIONS, session.getDeclinedPermissions())) {
                            publishStory(shareImage, caption, postId, facebookId);
                        }
                        session.removeCallback(statusCallback);
                    }
                };
                Session.NewPermissionsRequest newPermissionsRequest = new Session
                        .NewPermissionsRequest(getActivity(), PERMISSIONS).setCallback(statusCallback);
                session.requestNewPublishPermissions(newPermissionsRequest);
                return;
            }

            Bundle postParams = new Bundle();
            postParams.putString("name", "Tell Me What You Think");
            postParams.putString("caption", "Help decide by voting on NewVo");
            postParams.putString("description", caption);
            postParams.putString("link", "http://newvo.parseapp.com/objectId?message=" + postId);
            postParams.putString("picture", shareImage.getUrl());

            Request.Callback callback= new Request.Callback() {
                public void onCompleted(Response response) {
                    JSONObject graphResponse = response
                            .getGraphObject()
                            .getInnerJSONObject();
                    String postId = null;
                    try {
                        postId = graphResponse.getString("id");
                    } catch (JSONException e) {
                        Log.i("NewVo",
                                "JSON error " + e.getMessage());
                    }
                    FacebookRequestError error = response.getError();
                    if (error != null) {
                        Toast.makeText(getActivity(),
                                error.getErrorMessage(),
                                Toast.LENGTH_SHORT
                        ).show();
                    } else {
                        ToastUtils.makeText(getActivity(),
                                "Posted To Facebook Successfully!",
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
            };

            Request request = new Request(session, facebookId + "/feed", postParams,
                    HttpMethod.POST, callback);

            RequestAsyncTask task = new RequestAsyncTask(request);
            task.execute();
        }

    }

    private boolean isSubsetOf(Collection<String> subset, Collection<String> superset) {
        for (String string : subset) {
            if (!superset.contains(string)) {
                return false;
            }
        }
        return true;
    }


    public static Bitmap getScreenShot(View image1, View image2){
        image1.setDrawingCacheEnabled(true);
        image1.buildDrawingCache(true);
        Bitmap bitmap1 = Bitmap.createBitmap(image1.getDrawingCache());
        image1.setDrawingCacheEnabled(false);
        if (image2 != null) {
            image2.setDrawingCacheEnabled(true);
            image2.buildDrawingCache(true);
            Bitmap bitmap2 = Bitmap.createBitmap(image2.getDrawingCache());
            image2.setDrawingCacheEnabled(false);
            return combineImages(bitmap1, bitmap2);
        }


        return bitmap1;

    }

    public static Bitmap combineImages(Bitmap c, Bitmap s) { // can add a 3rd parameter 'String loc' if you want to save the new image - left some code to do that at the bottom
        Bitmap cs = null;

        int width, height = 0;

        if(c.getWidth() > s.getWidth()) {
            width = c.getWidth() + s.getWidth();
            height = c.getHeight();
        } else {
            width = s.getWidth() + s.getWidth();
            height = c.getHeight();
        }

        cs = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas comboImage = new Canvas(cs);

        comboImage.drawBitmap(c, 0f, 0f, null);
        comboImage.drawBitmap(s, c.getWidth(), 0f, null);

        // this is an extra bit I added, just incase you want to save the new image somewhere and then return the location
    /*String tmpImg = String.valueOf(System.currentTimeMillis()) + ".png";

    OutputStream os = null;
    try {
      os = new FileOutputStream(loc + tmpImg);
      cs.compress(CompressFormat.PNG, 100, os);
    } catch(IOException e) {
      Log.e("combineImages", "problem combining images", e);
    }*/

        return cs;
    }
}
