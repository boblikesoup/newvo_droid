package com.newvo.android;

import android.app.Application;
import android.os.StrictMode;
import com.newvo.android.parse.ParseReference;
import com.newvo.android.util.TypefaceUtil;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseInstallation;
import com.parse.PushService;

/**
 * Created by David on 4/27/2014.
 */
public class NewVoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ParseReference.initialize();
        Parse.initialize(this, "mnR3s9BtzI7VKxrPc6TJUovwxelScZcU8LRH5pLT", "TLZWWjjbzPxAQJiDfR1zcUkpN0jNaSVvQnyuqTaZ");
        ParseFacebookUtils.initialize("760522837298852");

        PushService.setDefaultPushCallback(this, SignInActivity.class);
        ParseInstallation currentInstallation = ParseInstallation.getCurrentInstallation();
        currentInstallation.saveInBackground();


        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", TypefaceUtil.UNIVERSAL_FONT); // font from assets: "assets/fonts/Roboto-Regular.ttf
    }
}
