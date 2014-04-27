package com.newvo.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

/**
 * Created by David on 4/25/2014.
 */
public class SignInActivity extends Activity {

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
            Intent intent = new Intent(this, NewVo.class);
            startActivity(intent);
            finish();
        }
    }
}
