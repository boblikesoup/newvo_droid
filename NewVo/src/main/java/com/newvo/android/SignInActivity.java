package com.newvo.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.parse.*;

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
        Parse.initialize(this, "mnR3s9BtzI7VKxrPc6TJUovwxelScZcU8LRH5pLT", "TLZWWjjbzPxAQJiDfR1zcUkpN0jNaSVvQnyuqTaZ");
        ParseFacebookUtils.initialize("760522837298852");

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
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }
    }
}
