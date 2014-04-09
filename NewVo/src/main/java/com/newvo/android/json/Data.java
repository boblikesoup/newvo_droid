
package com.newvo.android.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data{

    @SerializedName("followed_users")
   	private List<String> followedUsers;
    @SerializedName("following_users")
   	private List<String> followingUsers;
   	private List<String> friends;
   	private List<Post> posts;
   	private String relationship;
    @SerializedName("user_description")
   	private String userDescription;
    @SerializedName("user_info")
   	private UserInfo userInfo;

 	public List<String> getFollowedUsers(){
		return this.followedUsers;
	}
	public void setFollowedUsers(List<String> followedUsers){
		this.followedUsers = followedUsers;
	}
 	public List<String> getFollowingUsers(){
		return this.followingUsers;
	}
	public void setFollowingUsers(List<String> followingUsers){
		this.followingUsers = followingUsers;
	}
 	public List<String> getFriends(){
		return this.friends;
	}
	public void setFriends(List<String> friends){
		this.friends = friends;
	}
 	public List<Post> getPosts(){
		return this.posts;
	}
	public void setPosts(List<Post> posts){
		this.posts = posts;
	}
 	public String getRelationship(){
		return this.relationship;
	}
	public void setRelationship(String relationship){
		this.relationship = relationship;
	}
 	public String getUserDescription(){
		return this.userDescription;
	}
	public void setUserDescription(String userDescription){
		this.userDescription = userDescription;
	}
 	public UserInfo getUserInfo(){
		return this.userInfo;
	}
	public void setUserInfo(UserInfo userInfo){
		this.userInfo = userInfo;
	}
}
