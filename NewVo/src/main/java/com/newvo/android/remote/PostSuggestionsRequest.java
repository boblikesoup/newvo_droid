package com.newvo.android.remote;

import com.newvo.android.parse.Post;
import com.newvo.android.parse.Suggestion;
import com.parse.FindCallback;
import com.parse.ParseQuery;

/**
 * Created by David on 4/26/2014.
 */
public class PostSuggestionsRequest {

    private ParseQuery<Suggestion> query;

    public PostSuggestionsRequest(Post post){

        query = ParseQuery.getQuery(Suggestion.class);
        query.whereEqualTo("post_id", post);
        query.whereNotEqualTo(Suggestion.STATUS, Suggestion.DELETED);
    }

    public void request(FindCallback<Suggestion> callback){
        query.findInBackground(callback);
    }
}
