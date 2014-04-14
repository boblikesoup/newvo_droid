package com.newvo.android.json;

import com.google.gson.annotations.SerializedName;

/**
 * Created by David on 4/13/2014.
 */
public class Comment {

    private String body;
    @SerializedName("post_id")
    private Integer postId;
    @SerializedName("user_id")
    private Integer userId;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("posted_at")
    private String postedAt;
    private Integer status;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(String postedAt) {
        this.postedAt = postedAt;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
