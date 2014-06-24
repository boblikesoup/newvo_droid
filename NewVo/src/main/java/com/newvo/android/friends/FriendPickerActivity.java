package com.newvo.android.friends;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;
import com.facebook.FacebookException;
import com.facebook.widget.FriendPickerFragment;
import com.facebook.widget.PickerFragment;
import com.newvo.android.R;

/**
 * Created by David on 6/24/2014.
 */
public class FriendPickerActivity extends FragmentActivity {
    FriendPickerFragment friendPickerFragment;

    // A helper to simplify life for callers who want to populate a Bundle with the necessary
// parameters. A more sophisticated Activity might define its own set of parameters; our needs
// are simple, so we just populate what we want to pass to the FriendPickerFragment.
    public static void populateParameters(Intent intent, String userId, boolean multiSelect, boolean showTitleBar) {
        intent.putExtra(FriendPickerFragment.USER_ID_BUNDLE_KEY, userId);
        intent.putExtra(FriendPickerFragment.MULTI_SELECT_BUNDLE_KEY, multiSelect);
        intent.putExtra(FriendPickerFragment.SHOW_TITLE_BAR_BUNDLE_KEY, showTitleBar);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pick_friends_layout);

        FragmentManager fm = getSupportFragmentManager();

        if (savedInstanceState == null) {
            // First time through, we create our fragment programmatically.
            final Bundle args = getIntent().getExtras();
            friendPickerFragment = new FriendPickerFragment(args);
            friendPickerFragment.setUserId(null);
            fm.beginTransaction()
                    .add(R.id.friend_picker_fragment, friendPickerFragment)
                    .commit();
        } else {
            // Subsequent times, our fragment is recreated by the framework and already has saved and
            // restored its state, so we don't need to specify args again. (In fact, this might be
            // incorrect if the fragment was modified programmatically since it was created.)
            friendPickerFragment = (FriendPickerFragment) fm.findFragmentById(R.id.friend_picker_fragment);
        }

        friendPickerFragment.setOnErrorListener(new PickerFragment.OnErrorListener() {
            @Override
            public void onError(PickerFragment<?> fragment, FacebookException error) {
                FriendPickerActivity.this.onError(error);
            }
        });

        friendPickerFragment.setOnDoneButtonClickedListener(new PickerFragment.OnDoneButtonClickedListener() {
            @Override
            public void onDoneButtonClicked(PickerFragment<?> fragment) {
                Intent resultData = new Intent();
                //TODO: Return values for identifying selected friends. friendPickerFragment.getSelection()
                finish();
            }
        });
    }

    private void onError(Exception error) {
        Toast toast = Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT);
        toast.show();
    }


    @Override
    protected void onStart() {
        super.onStart();
        friendPickerFragment.loadData(false);

    }
}
