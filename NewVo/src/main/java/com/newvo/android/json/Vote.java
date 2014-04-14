
package com.newvo.android.json;

import com.google.gson.annotations.SerializedName;

public class Vote {
   	private Integer id;
    @SerializedName("votable_id")
    private Integer votableId;
    @SerializedName("user_id")
    private Integer userId;
    private Integer value;
    @SerializedName("post_id")
    private Integer postId;
    @SerializedName("votable_type")
    private String votableType;
   	private String photo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVotableId() {
        return votableId;
    }

    public void setVotableId(Integer votableId) {
        this.votableId = votableId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getVotableType() {
        return votableType;
    }

    public void setVotableType(String votableType) {
        this.votableType = votableType;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
