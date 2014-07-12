package com.newvo.android.parse;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseUser;

/**
 * Created by David on 4/26/2014.
 */
@ParseClassName("_User")
public class User extends ParseUser {

    public static final String OBJECT_ID = "objectId";
    public static final String USER_ID = "user_id";

    public static final String PROFILE_PIC = "profile_pic";
    public static final String GENDER = "gender";
    public static final String PUBLIC_NAME = "public_name";

    public static final String FACEBOOK_ID = "facebook_id";

    public String getUserId(){
        return getObjectId();
    }

    public ParseFile getProfilePicture(){
        return getParseFile(PROFILE_PIC);
    }
    public void setProfilePicture(ParseFile parseFile){
        put(PROFILE_PIC, parseFile);
    }

    public String getGender(){
        return getString(GENDER);
    }
    public void setGender(String gender){
        put(GENDER, gender);
    }

    public String getPublicName(){
        return getString(PUBLIC_NAME);
    }
    public void setPublicName(String publicName){
        put(PUBLIC_NAME, publicName);
    }

    public static User getCurrentUser(){
        return (User) ParseUser.getCurrentUser();
    }

    public String getFacebookId() {
        return getString(FACEBOOK_ID);
    }
    public void setFacebookId(String facebookId) {
        put(FACEBOOK_ID, facebookId);
    }
}
