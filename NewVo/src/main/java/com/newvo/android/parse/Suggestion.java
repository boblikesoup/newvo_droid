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
    public static final String STATUS = "status";
    public static final int UNDELETED = 0;
    public static final int DELETED = 1;

    public String getBody(){
        return getString(BODY);
    }
    public void setBody(String body){
        put(BODY, body);
    }

    public User getUser(){
        return (User) getParseObject(USER_ID);
    }
    public void setUser(User user){
        put(USER_ID, user);
    }

    public Post getPost(){
        return (Post) getParseObject(POST_ID);
    }
    public void setPost(Post post) {
        put(POST_ID, post);
    }

    public int getStatus(){
        return getInt(STATUS);
    }
    public void setStatus(int status) {
        put(STATUS, status);
    }

    private boolean loading = false;
    public void setLoading(boolean loading) {
        this.loading = loading;
    }
    public boolean isLoading() {
        return loading;
    }
}
