package com.newvo.android.parse;

import com.parse.ParseClassName;
import com.parse.ParseUser;

/**
 * Created by David on 4/26/2014.
 */
@ParseClassName("_User")
public class User extends ParseUser {

    public static final String OBJECT_ID = "objectId";
    public static final String USER_ID = "user_id";

    public String getUserId(){
        return getString(OBJECT_ID);
    }

}
