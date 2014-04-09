
package com.newvo.android.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Post {
   	private List<String> comments;

    @SerializedName("created_at")
   	private String createdAt;

   	private String description;

    @SerializedName("has_single_picture")
   	private boolean hasSinglePicture;

   	private List<Photo> photos;

    @SerializedName("post_id")
   	private Integer postId;

    @SerializedName("profile_pic")
   	private String profilePic;

    @SerializedName("user_id")
   	private Integer userId;

    @SerializedName("user_name")
   	private String userName;

    @SerializedName("user_voted?")
   	private boolean userVoted;

    @SerializedName("viewable_by")
   	private String viewableBy;

   	private List<Vote> votes;

 	public List<String> getComments(){
		return this.comments;
	}
	public void setComments(List<String> comments){
		this.comments = comments;
	}
 	public String getCreatedAt(){
		return this.createdAt;
	}
	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}
 	public String getDescription(){
		return this.description;
	}
	public void setDescription(String description){
		this.description = description;
	}
 	public boolean getHasSinglePicture(){
		return this.hasSinglePicture;
	}
	public void setHasSinglePicture(boolean hasSinglePicture){
		this.hasSinglePicture = hasSinglePicture;
	}
 	public List<Photo> getPhotos(){
		return this.photos;
	}
	public void setPhotos(List<Photo> photos){
		this.photos = photos;
	}
 	public Integer getPostId(){
		return this.postId;
	}
	public void setPostId(Integer postId){
		this.postId = postId;
	}
 	public String getProfilePic(){
		return this.profilePic;
	}
	public void setProfilePic(String profilePic){
		this.profilePic = profilePic;
	}
 	public Integer getUserId(){
		return this.userId;
	}
	public void setUserId(Integer userId){
		this.userId = userId;
	}
 	public String getUserName(){
		return this.userName;
	}
	public void setUserName(String userName){
		this.userName = userName;
	}
 	public boolean getUserVoted(){
		return this.userVoted;
	}
	public void setUserVoted(boolean userVoted){
		this.userVoted = userVoted;
	}
 	public String getViewableBy(){
		return this.viewableBy;
	}
	public void setViewableBy(String viewableBy){
		this.viewableBy = viewableBy;
	}
 	public List<Vote> getVotes(){
		return this.votes;
	}
	public void setVotes(List<Vote> votes){
		this.votes = votes;
	}
}
