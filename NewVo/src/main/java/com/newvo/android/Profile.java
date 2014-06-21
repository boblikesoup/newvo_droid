package com.newvo.android;

import com.newvo.android.parse.Post;

import java.util.List;

/**
 * Created by David on 6/21/2014.
 */
public class Profile {

    List<Post> activePosts;
    List<Post> inactivePosts;

    Post selectedPost;

    public boolean loaded(){
        return activePosts != null && inactivePosts != null;
    }

    public void populateList(String name, List<Post> posts){
        if(name.equals(Post.ACTIVE)){
            activePosts = posts;
        } else {
            inactivePosts = posts;
        }
    }
}
