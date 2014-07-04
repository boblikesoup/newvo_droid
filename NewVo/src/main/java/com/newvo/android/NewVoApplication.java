package com.newvo.android;

import android.app.Application;
import android.os.StrictMode;
import com.newvo.android.parse.ParseReference;
import com.newvo.android.util.TypefaceUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.PushService;
import com.personagraph.api.PGAgent;

/**
 * Created by David on 4/27/2014.
 */
public class NewVoApplication extends Application {

    public static final ImageLoader IMAGE_LOADER = ImageLoader.getInstance();

    @Override
    public void onCreate() {
        super.onCreate();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ParseReference.initialize();
        Parse.initialize(this, "mnR3s9BtzI7VKxrPc6TJUovwxelScZcU8LRH5pLT", "TLZWWjjbzPxAQJiDfR1zcUkpN0jNaSVvQnyuqTaZ");
        ParseFacebookUtils.initialize("760522837298852");

        PushService.setDefaultPushCallback(this, SignInActivity.class);

        // The following lines trigger the
        // initialization of RichInsights SDK
        int sensorConfig = PGAgent.APP; // for including App Sensor
        sensorConfig |= PGAgent.FACEBOOK; // for including Facebook Sensor
        boolean enableAppSensorByDefault = true; // "true" if App sensor  is turned on by default


        PGAgent.init(this, "371:g0fEuapkmqxbTb3d", sensorConfig, enableAppSensorByDefault);

        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", TypefaceUtil.UNIVERSAL_FONT); // font from assets: "assets/fonts/Roboto-Regular.ttf

        //Universal Image Loader

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
            .cacheInMemory(true)
            .cacheOnDisk(true)
        .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
                .build();
        IMAGE_LOADER.init(config);
    }
}
