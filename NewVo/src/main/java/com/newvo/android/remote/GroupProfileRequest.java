package com.newvo.android.remote;

import com.newvo.android.parse.Group;
import com.newvo.android.parse.Post;
import com.parse.FindCallback;
import com.parse.ParseQuery;

import java.util.Arrays;

/**
 * Created by David on 6/26/2014.
 */
public class GroupProfileRequest {

    private ParseQuery<Post> query;

    public GroupProfileRequest(Group group) {

        query = ParseQuery.getQuery(Post.class);
        query.whereEqualTo(Post.STATUS, Post.getStatusValue(Post.ACTIVE));
        query.whereNotEqualTo(Post.STATUS, Post.getStatusValue(Post.DELETED));
        query.whereContainsAll(Post.GROUP_ID, Arrays.asList(group.getObjectId()));
        query.orderByDescending(Post.CREATED_AT);
    }

    public void request(FindCallback<Post> callback) {
        query.findInBackground(callback);
    }


}
