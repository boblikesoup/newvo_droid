package com.newvo.android.request;

/**
 * Created by David on 4/5/2014.
 */
public class DeleteCommentRequest extends PostRequest {

    private int postId = -1;
    private int id = -1;

    public DeleteCommentRequest() {
        super("/api/v1/posts/");
    }

    public void setPostId(int postId){
        this.postId = postId;
        updateUrlData();
    }

    public void setId(int id){
        this.id = id;
        updateUrlData();
    }

    private void updateUrlData(){
        if(postId != -1 && id != -1){
            setUrlData(postId + "/comments/" + id);
        }
    }
}
