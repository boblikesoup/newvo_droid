package com.newvo.android.remote;

import com.newvo.android.parse.Post;
import com.newvo.android.parse.User;
import com.parse.FindCallback;
import com.parse.ParseQuery;

/**
 * Created by David on 4/26/2014.
 */
public class CurrentUserProfileRequest {

    private ParseQuery<Post> query;

    public CurrentUserProfileRequest(){
        User user = (User) User.getCurrentUser();

        query = ParseQuery.getQuery(Post.class);
    }

    public void request(FindCallback<Post> callback){
        query.findInBackground(callback);
    }


}
