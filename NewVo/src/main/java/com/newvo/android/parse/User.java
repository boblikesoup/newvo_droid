package com.newvo.android.parse;

import com.parse.ParseUser;

/**
 * Created by David on 4/26/2014.
 */
public class User extends ParseUser {

    public String getUserId(){
        return getString("username");
    }

}
