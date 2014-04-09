package com.newvo.android.request;

/**
 * Created by David on 4/5/2014.
 */
public class DeleteCommentRequest extends PostRequest {

    private int postId = -1;
    private int id = -1;

    public DeleteCommentRequest(int postId, int id) {
        super("/api/v1/posts/");
        setPostId(postId);
        setId(id);
        updateUrlData();
    }

    private void setPostId(int postId){
        this.postId = postId;
    }

    private void setId(int id){
        this.id = id;
    }

    private void updateUrlData(){
        if(postId != -1 && id != -1){
            setUrlData(postId + "/comments/" + id);
        }
    }
}
