package com.newvo.android.parse;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by David on 4/26/2014.
 */
@ParseClassName("Vote")
public class Vote extends ParseObject {

    public int getVote(){
        return getInt("vote");
    }
    public void setVote(int vote){
        put("vote", vote);
    }

    public User getUser(){
        return (User) getParseObject("user_id");
    }

    public Post getPost(){
        return (Post) getParseObject("post_id");
    }

}
