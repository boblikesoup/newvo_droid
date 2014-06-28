package com.newvo.android.parse;

import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by David on 4/26/2014.
 */
public class ParseReference {

    public static void initialize() {
        ParseObject.registerSubclass(Installation.class);
        ParseUser.registerSubclass(User.class);
        ParseUser.registerSubclass(Group.class);
        ParseObject.registerSubclass(Post.class);
        ParseObject.registerSubclass(Suggestion.class);
        ParseObject.registerSubclass(Vote.class);
        ParseACL acl = new ParseACL();
        acl.setPublicReadAccess(true);
        //Public read access. Current user write access.
        ParseACL.setDefaultACL(acl, true);
    }

}
