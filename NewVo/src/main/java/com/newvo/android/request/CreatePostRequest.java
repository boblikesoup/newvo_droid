package com.newvo.android.request;

import android.content.Context;
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

    CreatePostRequest(Context context, String description, int viewableBy, String image1, String image2) {
        super(context, "/api/v1/posts");
        setDescription(description);
        setViewableBy(viewableBy);
        setImage1(image1);
        setImage2(image2);
    }

    private void setDescription(String description) {
        addUrlParam("description", description);
    }

    private void setViewableBy(int viewableBy) {
        if (viewableBy < 0 || viewableBy > 2) {
            Log.e("JSON", viewableBy + " is not within range 0-2.");
            return;
        }
        addBodyParam("viewable_by", viewableBy + "");
    }

    private void setImage1(String location) {
        this.image1Location = location;
    }

    private void setImage2(String location) {
        this.image2Location = location;
    }

    @Override
    void addMiscData(Builders.Any.B load) {
        if (image1Location != null) {
            load.setMultipartFile("image1", new File(image1Location));
        }
        if (image2Location != null) {
            load.setMultipartFile("image2", new File(image2Location));
        }
        super.addMiscData(load);
    }
}
