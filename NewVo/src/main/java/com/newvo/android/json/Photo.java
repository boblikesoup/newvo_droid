
package com.newvo.android.json;

public class Photo {
   	private Integer downvotes;
   	private Integer id;
   	private Integer upvotes;
   	private String url;

 	public Integer getDownvotes(){
		return this.downvotes;
	}
	public void setDownvotes(Integer downvotes){
		this.downvotes = downvotes;
	}
 	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}
 	public Integer getUpvotes(){
		return this.upvotes;
	}
	public void setUpvotes(Integer upvotes){
		this.upvotes = upvotes;
	}
 	public String getUrl(){
		return this.url;
	}
	public void setUrl(String url){
		this.url = url;
	}
}
