package com.newvo.android.request;

import android.util.Log;

/**
 * Created by David on 4/6/2014.
 */
public class CreateVoteRequest extends PostRequest {

    public CreateVoteRequest(){
        super("/api/v1/votes");
    }

    public void setPhotoId(int id){
        addBodyParam("photo", id + "");
    }

    public void setValue(int value){
        if(value != -1 && value != 1){
            Log.e("JSON", value + " is not equal to -1 or 1.");
            return;
        }
        addBodyParam("value", value + "");
    }
}
