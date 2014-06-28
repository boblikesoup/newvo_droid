package com.newvo.android.remote;

import com.newvo.android.parse.Suggestion;
import com.parse.SaveCallback;

/**
 * Created by David on 4/27/2014.
 */
public class RemoveSuggestionRequest {

    private Suggestion suggestion;

    public RemoveSuggestionRequest(Suggestion suggestion){
        this.suggestion = suggestion;
        this.suggestion.setStatus(Suggestion.DELETED);
    }

    public void request(SaveCallback callback){
        suggestion.saveInBackground(callback);
    }
}
