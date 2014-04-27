package com.newvo.android.remote;

import com.newvo.android.parse.Suggestion;
import com.parse.DeleteCallback;

/**
 * Created by David on 4/27/2014.
 */
public class RemoveSuggestionRequest {

    private Suggestion suggestion;

    public RemoveSuggestionRequest(Suggestion suggestion){
        this.suggestion = suggestion;
    }

    public void request(DeleteCallback callback){
        suggestion.deleteInBackground(callback);
    }
}
