package com.newvo.android.parse;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by David on 4/26/2014.
 */
@ParseClassName("Suggestion")
public class Suggestion extends ParseObject {

    public String getBody(){
        return getString("body");
    }

    public User getUser(){
        return (User) getParseObject("user_id");
    }

    public Post getPost(){
        return (Post) getParseObject("post_id");
    }
}
