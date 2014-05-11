package com.newvo.android.remote;

import com.newvo.android.parse.Post;
import com.newvo.android.parse.User;
import com.newvo.android.parse.Vote;
import com.parse.SaveCallback;

/**
 * Created by David on 4/28/2014.
 */
public class VoteOnPostRequest {

    private final Vote vote;

    public VoteOnPostRequest(Post post, int voteValue){
        vote = new Vote();
        vote.setVote(voteValue);
        vote.setPost(post);
        vote.setUser(User.getCurrentUser());
    }

    public void request(){
        request(null);
    }

    public void request(SaveCallback saveCallback){
        if(saveCallback == null){
            vote.saveInBackground();
        } else {
            vote.saveInBackground(saveCallback);
        }
    }
}
