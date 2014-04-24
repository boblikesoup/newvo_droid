package com.newvo.android.request;

import android.content.Context;
import android.widget.ArrayAdapter;
import com.koushikdutta.async.future.FutureCallback;
import com.newvo.android.ComparisonViewHolder;
import com.newvo.android.json.CurrentUserProfile;
import com.newvo.android.json.Post;

import java.util.List;

/**
 * Created by David on 4/6/2014.
 */
public class CurrentUserProfileRequest extends AbstractRequest {

    public CurrentUserProfileRequest(Context context) {
        super(context, "/api/v1/users", GET);
    }

    public void request(FutureCallback<CurrentUserProfile> callback) {
        super.makeRequest(CurrentUserProfile.class, callback);
    }

    public void request2(FutureCallback<String> callback) {
        super.makeRequest(null, callback);
    }

    public static void load(final ArrayAdapter adapter) {
        new CurrentUserProfileRequest(adapter.getContext()).request(new FutureCallback<CurrentUserProfile>() {
            @Override
            public void onCompleted(Exception e, CurrentUserProfile result) {
                if (result != null && result.getData() != null) {
                    List<Post> posts = result.getData().getPosts();
                    if (posts != null) {
                        adapter.addAll(posts);
                    }
                }
            }
        });
    }

    public static void loadSingle(Context context, final ComparisonViewHolder viewHolder) {
        new CurrentUserProfileRequest(context).request(new FutureCallback<CurrentUserProfile>() {
            @Override
            public void onCompleted(Exception e, CurrentUserProfile result) {
                if (result != null && result.getData() != null) {
                    List<Post> posts = result.getData().getPosts();
                    if (posts != null && !posts.isEmpty()) {
                        viewHolder.setItem(posts.get(0));
                    }
                }
            }
        });
    }
}
