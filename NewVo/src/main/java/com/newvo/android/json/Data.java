
package com.newvo.android.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("user_info")
    private UserInfo userInfo;
    @SerializedName("user_description")
   	private String userDescription;
    @SerializedName("followed_users")
    private List<String> followedUsers;
    @SerializedName("following_users")
    private List<String> followingUsers;
    private List<String> friends;
    private String relationship;
    private List<Post> posts;


    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getUserDescription() {
        return userDescription;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    public List<String> getFollowedUsers() {
        return followedUsers;
    }

    public void setFollowedUsers(List<String> followedUsers) {
        this.followedUsers = followedUsers;
    }

    public List<String> getFollowingUsers() {
        return followingUsers;
    }

    public void setFollowingUsers(List<String> followingUsers) {
        this.followingUsers = followingUsers;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
