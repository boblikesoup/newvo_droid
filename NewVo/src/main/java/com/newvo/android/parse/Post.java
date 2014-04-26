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

    public String getUserId(){
        return getString("user_id");
    }
    public void setUserId(String userId){
        put("user_id", userId);
    }

    public String getCaption(){
        return getString("caption");
    }
    public void setCaption(String caption){
        put("caption", caption);
    }


    public Bitmap getPhoto1(){
        return ParseReference.parseFileToBitmap(getParseFile("photo_1"));
    }
    public String getPhoto1Url(){
        ParseFile photo1 = getParseFile("photo_1");
        if(photo1 == null){
            return null;
        }
        return photo1.getUrl();
    }
    public void setPhoto1(Bitmap photo1){
        put("photo_1",ParseReference.bitmapToParseFile(photo1));
    }


    public Bitmap getPhoto2(){
        return ParseReference.parseFileToBitmap(getParseFile("photo_2"));
    }
    public String getPhoto2Url(){
        ParseFile photo2 = getParseFile("photo_2");
        if(photo2 == null){
            return null;
        }
        return photo2.getUrl();
    }
    public void setPhoto2(Bitmap photo2){
        put("photo_2",ParseReference.bitmapToParseFile(photo2));
    }


    public int getVotes1(){
        return getInt("votes_0");
    }
    public void setVotes1(int votes1){
        put("votes_0",votes1);
    }

    public int getVotes2(){
        return getInt("votes_1");
    }
    public void setVotes2(int votes2){
        put("votes_1",votes2);
    }

    public int getNumberOfSuggestions(){
        return getInt("suggestions");
    }
}
