package com.newvo.android.remote;

import com.newvo.android.parse.Group;
import com.newvo.android.parse.User;
import com.parse.FindCallback;
import com.parse.ParseQuery;

import java.util.Arrays;

/**
 * Created by David on 6/22/2014.
 */
public class UserGroupsRequest {

    private ParseQuery<Group> query;

    public UserGroupsRequest(){
        User user = User.getCurrentUser();

        query = ParseQuery.getQuery(Group.class);
        String userId = user.getFacebookId();
        query.whereContainsAll(Group.MEMBER_IDS, Arrays.asList(userId));
        query.whereEqualTo(Group.STATUS, 1);
        query.orderByDescending(Group.CREATED_AT);
    }

    public void request(FindCallback<Group> callback){
        query.findInBackground(callback);
    }
}
