
package com.newvo.android.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Post {

    @SerializedName("post_id")
    private Integer postId;
    @SerializedName("user_id")
    private Integer userId;
    @SerializedName("user_name")
    private String userName;
    @SerializedName("profile_pic")
    private String profilePic;
    private String description;
    @SerializedName("has_single_picture")
    private boolean singlePicture;
    private List<Photo> photos;
    private List<Vote> votes;
    @SerializedName("user_voted?")
    private boolean userVoted;
    private List<Comment> comments;
    @SerializedName("created_at")
   	private String createdAt;
    @SerializedName("viewable_by")
   	private String viewableBy;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSinglePicture() {
        return singlePicture;
    }

    public void setSinglePicture(boolean singlePicture) {
        this.singlePicture = singlePicture;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public boolean isUserVoted() {
        return userVoted;
    }

    public void setUserVoted(boolean userVoted) {
        this.userVoted = userVoted;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getViewableBy() {
        return viewableBy;
    }

    public void setViewableBy(String viewableBy) {
        this.viewableBy = viewableBy;
    }
}
