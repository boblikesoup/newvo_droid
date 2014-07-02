package com.newvo.android.remote;

import com.newvo.android.parse.Post;
import com.newvo.android.parse.User;
import com.parse.FindCallback;
import com.parse.ParseQuery;

import java.util.Arrays;

/**
 * Created by David on 4/28/2014.
 */
public class FeedRequest {

    public static final int NUMBER_OF_POSTS = 6;
    private ParseQuery<Post> query;

    public FeedRequest() {
        User user = User.getCurrentUser();
        String userId = user.getUserId();

        ParseQuery<Post> taggedPosts = ParseQuery.getQuery(Post.class);
        taggedPosts.whereEqualTo(Post.USER_TAG, userId);

        ParseQuery<Post> regularPosts = ParseQuery.getQuery(Post.class);
        regularPosts.whereEqualTo(Post.VIEWABLE_BY, 0);


        query = ParseQuery.or(Arrays.asList(taggedPosts, regularPosts));
        query.setLimit(NUMBER_OF_POSTS);
        query.whereNotEqualTo(Post.USER_ID, user);
        query.whereEqualTo(Post.STATUS, 0);
        query.whereNotEqualTo(Post.VOTED_ON_ARRAY, userId);
        query.orderByDescending(Post.CREATED_AT);
    }

    public void request(FindCallback<Post> callback) {
        query.findInBackground(callback);
    }
}
