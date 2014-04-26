package com.newvo.android;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.facebook.Session;
import com.parse.ParseUser;

/**
 * Created by David on 4/11/2014.
 */
public class SettingsFragment extends Fragment {

    @InjectView(R.id.facebook_button)
    View settings;

    //Prevent intent from being opened multiple times
    private Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.settings, container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @OnClick(R.id.facebook_button)
    public void submit(){
        ParseUser.logOut();
        Activity activity = getActivity();
        callFacebookLogout(activity);
        if(intent == null){
            intent = new Intent(activity, SignInActivity.class);
            startActivity(intent);
            activity.finish();
        }
    }

    public static void callFacebookLogout(Context context) {
        Session session = Session.getActiveSession();
        if (session != null) {

            if (!session.isClosed()) {
                session.closeAndClearTokenInformation();
                //clear your preferences if saved
            }
        } else {

            session = new Session(context);
            Session.setActiveSession(session);

            session.closeAndClearTokenInformation();
            //clear your preferences if saved

        }

    }

}
