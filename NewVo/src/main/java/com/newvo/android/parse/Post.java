package com.newvo.android.parse;

import android.graphics.Bitmap;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

/**
 * Created by David on 4/26/2014.
 */
@ParseClassName("Post")
public class Post extends ParseObject {

    public static final String USER_ID = User.USER_ID;
    public static final String POST_ID = "post_id";

    public static final String CAPTION = "caption";
    public static final String PHOTO_1 = "photo_1";
    public static final String PHOTO_2 = "photo_2";
    public static final String VOTES_0 = "votes_0";
    public static final String VOTES_1 = "votes_1";
    public static final String SUGGESTIONS = "suggestions";

    public String getUserId(){
        return getString(USER_ID);
    }
    public void setUserId(String userId){
        put(USER_ID, userId);
    }

    public String getCaption(){
        return getString(CAPTION);
    }
    public void setCaption(String caption){
        put(CAPTION, caption);
    }


    public ParseFile getPhoto1(){
        return getParseFile(PHOTO_1);
    }
    public void setPhoto1(Bitmap photo1){
        put(PHOTO_1,ParseReference.bitmapToParseFile(photo1));
    }


    public ParseFile getPhoto2(){
        return getParseFile(PHOTO_2);
    }
    public void setPhoto2(Bitmap photo2){
        put(PHOTO_2,ParseReference.bitmapToParseFile(photo2));
    }


    public int getVotes1(){
        return getInt(VOTES_0);
    }
    public void setVotes1(int votes1){
        put(VOTES_0,votes1);
    }

    public int getVotes2(){
        return getInt(VOTES_1);
    }
    public void setVotes2(int votes2){
        put(VOTES_1,votes2);
    }

    public int getNumberOfSuggestions(){
        return getInt(SUGGESTIONS);
    }
}
