package com.newvo.android.remote;

import android.content.Context;
import com.facebook.model.GraphUser;
import com.newvo.android.parse.Group;
import com.newvo.android.parse.Post;
import com.newvo.android.parse.User;
import com.parse.*;

import java.util.*;

import static com.parse.ParseUser.getCurrentUser;

/**
 * Created by David on 4/27/2014.
 */
public class CreatePostRequest {

    private Post post;
    private List<GraphUser> users;
    private List<Group> groups;

    public CreatePostRequest(Context context, String caption, ParseFile image1, ParseFile image2, List<Group> groups, List<GraphUser> users) throws MissingCaptionError, MissingImageError {
        this(context, caption, image1, image2);
        this.users = users;
        this.groups = groups;

    }

    public CreatePostRequest(Context context, String caption, ParseFile image1, ParseFile image2) throws MissingCaptionError, MissingImageError {
        if (caption == null || caption.isEmpty()) {
            throw new MissingCaptionError();
        } else if (image1 == null) {
            throw new MissingImageError();
        }
        post = new Post();
        post.setCaption(caption);
        post.setPhoto1(image1);
        if (image2 != null) {
            post.setPhoto2(image2);
        }
        User currentUser = (User) getCurrentUser();
        post.setUser(currentUser);
        post.setStatus(Post.ACTIVE);
        post.setViewableBy(Post.PUBLIC);
        post.setVotes1(0);
        post.setVotes2(0);
        post.setCounter1(0);
        post.setCounter2(0);
        post.setNumberOfSuggestions(0);
    }


    public void request() {
        request(null);
    }

    public void request(final SaveCallback saveCallback) {

        final Set<String> ids = new HashSet<String>();

        //Notify users after tagging is successful.
        final SaveCallback nestedCallback = new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    if (!ids.isEmpty()) {
                        HashMap<String, Object> params = new HashMap<String, Object>();
                        params.put("userIDS", post.getUserTags());
                        params.put("msg", User.getCurrentUser().getPublicName() + " has tagged you in a post. Take a look!");
                        ParseCloud.callFunctionInBackground("push_tagged_notifications", params, null);
                    }
                }
                if (saveCallback != null) {
                    saveCallback.done(e);
                }
            }
        };



        //Tagging groups
        if(groups != null && !groups.isEmpty()){
            post.setGroupIds(groups);
            for(Group group : groups){
                for(String pushId : group.getPushIds()){
                    ids.add(pushId);
                }
            }
            if(users == null || users.isEmpty()){
                post.setUserTags(new ArrayList<String>(ids));
            }
        }

        //Tagging users
        if (users != null && !users.isEmpty()) {
            new ParseUsersFromFacebookUsersRequest(users).request(new FindCallback<User>() {
                @Override
                public void done(List<User> pushUsers, ParseException e) {
                    if (e == null) {
                        for(User pushUser : pushUsers){
                            ids.add(pushUser.getUserId());
                        }
                        post.setUserTags(new ArrayList<String>(ids));
                        post.saveInBackground(nestedCallback);
                    } else {
                        nestedCallback.done(e);
                    }
                }
            });
        } else {
            post.saveInBackground(nestedCallback);
        }
    }

    public class MissingCaptionError extends Error {
    }

    public class MissingImageError extends Error {
    }
}
