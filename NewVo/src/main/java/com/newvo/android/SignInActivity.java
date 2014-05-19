package com.newvo.android;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.newvo.android.parse.User;
import com.newvo.android.util.ImageFileUtils;
import com.parse.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by David on 4/25/2014.
 */
public class SignInActivity extends Activity {

    private Intent intent;

    public SignInActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ParseUser currentUser = ParseUser.getCurrentUser();
        if ((currentUser != null) && ParseFacebookUtils.isLinked(currentUser)) {
            // Go to the user info activity
            assignUser(currentUser);
        } else {

            LoginView view = new LoginView(this, new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException err) {
                    if (user != null) {
                        assignUser(user);
                    }
                }
            });

            setContentView(view);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
    }

    private void assignUser(ParseUser user) {
        if (user != null) {
            updateFacebookInfo();
            if(intent == null) {
                intent = new Intent(this, NewVo.class);
                startActivity(intent);
                finish();
            }
        }
    }

    private void updateFacebookInfo() {
        Session session = ParseFacebookUtils.getSession();
        if (session != null && session.isOpened()) {
            makeMeRequest();
        }
    }

    private void makeMeRequest() {
        Request request = Request.newMeRequest(ParseFacebookUtils.getSession(),
                new Request.GraphUserCallback() {
                    @Override
                    public void onCompleted(GraphUser user, Response response) {
                        User currentUser = User.getCurrentUser();
                        if (currentUser != null && user != null) {
                            currentUser.setGender((String) user.getProperty("gender"));
                            currentUser.setPublicName(user.getName());
                            try {
                                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                StrictMode.setThreadPolicy(policy);
                                //Request bitmap from server
                                String url = String.format(
                                        "https://graph.facebook.com/%s/picture?type=large",
                                        user.getId());
                                InputStream inputStream = new URL(url).openStream();
                                Bitmap icon = BitmapFactory.decodeStream(inputStream);
                                if (icon != null) {
                                    //Save file
                                    ParseFile profilePicture = new ParseFile("Image.jpg", ImageFileUtils.bitmapToByteArray(icon));
                                    profilePicture.saveInBackground();
                                    currentUser.setProfilePicture(profilePicture);
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            currentUser.saveInBackground();
                        }
                    }
                }
        );
        request.executeAsync();

    }
}
