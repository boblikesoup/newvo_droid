package com.newvo.android.remote;

import com.newvo.android.parse.Post;
import com.newvo.android.parse.User;
import com.parse.FindCallback;
import com.parse.ParseQuery;

/**
 * Created by David on 4/28/2014.
 */
public class FeedRequest {

    private ParseQuery<Post> query;

    public FeedRequest() {
        User user = User.getCurrentUser();

        query = ParseQuery.getQuery(Post.class);
        query.setLimit(6);
        query.whereEqualTo(Post.STATUS, 0);
        query.whereEqualTo(Post.VIEWABLE_BY, 0);
        String userId = user.getUserId();
        query.whereNotEqualTo(Post.USER_ID, user);
   //     query.whereNotContainedIn(Post.VOTED_ON_ARRAY, Arrays.asList(userId));
        query.orderByDescending(Post.CREATED_AT);
    }

    public void request(FindCallback<Post> callback) {
        query.findInBackground(callback);
    }
}
