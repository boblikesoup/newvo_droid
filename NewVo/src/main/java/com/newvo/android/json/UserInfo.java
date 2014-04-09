
package com.newvo.android.json;

import com.google.gson.annotations.SerializedName;

public class UserInfo {
   	private Integer id;
   	private String name;

    @SerializedName("profile_pic")
   	private String profilePic;

 	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}
 	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
 	public String getProfilePic(){
		return this.profilePic;
	}
	public void setProfilePic(String profilePic){
		this.profilePic = profilePic;
	}
}
