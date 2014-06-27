package com.newvo.android.remote;

import com.facebook.model.GraphUser;
import com.newvo.android.parse.User;
import com.parse.FindCallback;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 6/25/2014.
 */
public class ParseUsersFromFacebookUsersRequest {

    private ParseQuery<User> query;


    public ParseUsersFromFacebookUsersRequest(List<String> ids, boolean isId) {

        query = ParseQuery.getQuery(User.class);
        query.whereContainedIn(User.FACEBOOK_ID, ids);
    }

    public ParseUsersFromFacebookUsersRequest(List<GraphUser> users) {

        query = ParseQuery.getQuery(User.class);

        List<String> ids = new ArrayList<String>();
        for(GraphUser user : users){

            ids.add(user.getId());
        }
        query.whereContainedIn(User.FACEBOOK_ID, ids);
    }

    public void request(FindCallback<User> callback) {
        query.findInBackground(callback);
    }
}
