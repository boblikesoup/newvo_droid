package com.newvo.android.parse;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by David on 4/26/2014.
 */
@ParseClassName("Vote")
public class Vote extends ParseObject {

    public static final String POST_ID = Post.POST_ID;
    public static final String USER_ID = User.USER_ID;
    public static final String VOTE = "vote";

    public int getVote(){
        return getInt(VOTE);
    }
    public void setVote(int vote){
        put(VOTE, vote);
    }

    public User getUser(){
        return (User) getParseObject(USER_ID);
    }

    public Post getPost(){
        return (Post) getParseObject(POST_ID);
    }

}
