
package com.newvo.android.json;

import com.google.gson.annotations.SerializedName;

public class Vote {
   	private Integer id;

   	private String photo;

    @SerializedName("post_id")
   	private Integer postId;

    @SerializedName("user_id")
   	private Integer userId;

   	private Integer value;

    @SerializedName("votable_id")
   	private Integer votableId;

    @SerializedName("votable_type")
   	private String votableType;

 	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}
 	public String getPhoto(){
		return this.photo;
	}
	public void setPhoto(String photo){
		this.photo = photo;
	}
 	public Integer getPostId(){
		return this.postId;
	}
	public void setPostId(Integer postId){
		this.postId = postId;
	}
 	public Integer getUserId(){
		return this.userId;
	}
	public void setUserId(Integer userId){
		this.userId = userId;
	}
 	public int getValue(){
		return this.value;
	}
	public void setValue(int value){
		this.value = value;
	}
 	public int getVotableId(){
		return this.votableId;
	}
	public void setVotableId(int votableId){
		this.votableId = votableId;
	}
 	public String getVotableType(){
		return this.votableType;
	}
	public void setVotableType(String votableType){
		this.votableType = votableType;
	}
}
