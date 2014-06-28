package com.newvo.android.parse;

import com.parse.ParseClassName;
import com.parse.ParseInstallation;

/**
 * Created by David on 6/28/2014.
 */
@ParseClassName("_Installation")
public class Installation extends ParseInstallation {

    public static Installation getCurrentInstallation(){
        return (Installation) ParseInstallation.getCurrentInstallation();
    }

    private static final String BADGE = "badge";
    private static final String PUBLIC_ID = "publicId";
    private static final String USER_NAME = "userName";
    private static final String USER = "user";

    public int getBadge(){
        return getInt(BADGE);
    }
    public void setBadge(int badge){
        put(BADGE, badge);
    }

    public String getPublicId(){
        return getString(PUBLIC_ID);
    }
    public void setPublicId(String publicId){
        put(PUBLIC_ID, publicId);
    }

    public String getUserName(){
        return getString(USER_NAME);
    }
    public void setUserName(String publicId){
        put(USER_NAME, publicId);
    }

    public User getUser(){
        return (User) getParseObject(USER);
    }
    public void setUser(User user) {
        put(USER, user);
    }

}
