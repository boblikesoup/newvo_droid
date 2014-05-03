package com.newvo.android.parse;

import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by David on 4/26/2014.
 */
public class ParseReference {

    public static void initialize() {
        ParseUser.registerSubclass(User.class);
        ParseObject.registerSubclass(Post.class);
        ParseObject.registerSubclass(Suggestion.class);
        ParseObject.registerSubclass(Vote.class);
        ParseACL acl = new ParseACL();
        acl.setPublicReadAccess(true);
        ParseACL.setDefaultACL(acl, true);
    }

}
