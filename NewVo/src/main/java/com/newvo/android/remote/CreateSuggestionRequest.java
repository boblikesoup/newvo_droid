package com.newvo.android.remote;

import com.newvo.android.parse.Post;
import com.newvo.android.parse.Suggestion;
import com.newvo.android.parse.User;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.SaveCallback;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by David on 4/30/2014.
 */
public class CreateSuggestionRequest {

    private final Suggestion suggestion;

    public CreateSuggestionRequest(Post post, String suggestionText) {

        User currentUser = User.getCurrentUser();
        suggestion = new Suggestion();
        suggestion.setBody(suggestionText);
        suggestion.setUser(currentUser);
        suggestion.setPost(post);
        suggestion.setStatus(0);
    }


    public Suggestion getSuggestion(){
        return suggestion;
    }

    public void request(){
        request(null);
    }

    public void request(final SaveCallback saveCallback){
        SaveCallback tagCallback = new SaveCallback() {
            @Override
            public void done(ParseException e) {
                HashMap<String, Object> params = new HashMap<String, Object>();
                Post post = suggestion.getPost();
                params.put("userIDS", Arrays.asList(post.getUser().getUserId()));
                params.put("msg", suggestion.getUser().getPublicName() + " has commented on your post. Take a look!");
                params.put("searchObjectPost", post.getObjectId());
                ParseCloud.callFunctionInBackground("push_comment_notifications", params, null);
                if(saveCallback != null) {
                    saveCallback.done(e);
                }
            }
        };
        suggestion.saveInBackground(tagCallback);
    }
}
