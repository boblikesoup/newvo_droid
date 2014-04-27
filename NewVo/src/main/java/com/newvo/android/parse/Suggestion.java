package com.newvo.android.parse;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by David on 4/26/2014.
 */
@ParseClassName("Suggestion")
public class Suggestion extends ParseObject {

    public static final String BODY = "body";
    public static final String USER_ID = User.USER_ID;
    public static final String POST_ID = Post.POST_ID;

    public String getBody(){
        return getString(BODY);
    }

    public User getUser(){
        return (User) getParseObject(USER_ID);
    }

    public Post getPost(){
        return (Post) getParseObject(POST_ID);
    }
}
