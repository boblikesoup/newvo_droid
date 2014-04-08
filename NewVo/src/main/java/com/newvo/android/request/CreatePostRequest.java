package com.newvo.android.request;

import android.util.Log;
import com.koushikdutta.ion.builder.Builders;

import java.io.File;

/**
 * Created by David on 4/6/2014.
 */
public class CreatePostRequest extends PostRequest {

    public static final int PUBLIC = 0;
    public static final int FRIENDS = 1;
    public static final int GROUP = 2;

    private String image1Location;
    private String image2Location;

    CreatePostRequest() {
        super("/api/v1/posts");
    }

    public void setDescription(String description){
        addUrlParam("description", description);
    }

    public void setViewableBy(int viewableBy){
        if(viewableBy < 0 || viewableBy > 2){
            Log.e("JSON", viewableBy + " is not within range 0-2.");
            return;
        }
        addBodyParam("viewable_by", viewableBy + "");
    }

    public void setImage1(String location){
        this.image1Location = location;
    }

    public void setImage2(String location){
        this.image2Location = location;
    }

    @Override
    void addMiscData(Builders.Any.B load) {
        if(image1Location != null){
            load.setMultipartFile("image1", new File(image1Location));
        }
        if(image2Location != null){
            load.setMultipartFile("image2", new File(image2Location));
        }
        super.addMiscData(load);
    }
}
