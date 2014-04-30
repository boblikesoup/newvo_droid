package com.newvo.android.remote;

import com.newvo.android.parse.Post;
import com.newvo.android.parse.Suggestion;
import com.newvo.android.parse.User;
import com.parse.SaveCallback;

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



    public void request(){
        request(null);
    }

    public void request(SaveCallback saveCallback){
        if(saveCallback == null){
            suggestion.saveInBackground();
        } else {
            suggestion.saveInBackground(saveCallback);
        }
    }
}
