package com.newvo.android;

import android.app.Activity;
import android.widget.Button;
import android.widget.LinearLayout;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.parse.LogInCallback;
import com.parse.ParseFacebookUtils;

/**
 * Created by David on 4/25/2014.
 */
public class LoginView extends LinearLayout {

    @InjectView(R.id.facebook_button)
    Button facebookButton;

    Activity activity;
    LogInCallback callback;

    public LoginView(Activity activity, LogInCallback callback){
        super(activity);
        inflate(activity, R.layout.login, this);
        this.activity = activity;
        this.callback = callback;
        ButterKnife.inject(this);
    }


    @OnClick (R.id.facebook_button)
    public void submit(){
        ParseFacebookUtils.logIn(activity, callback);
    }
}