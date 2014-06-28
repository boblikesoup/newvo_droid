package com.newvo.android.parse;

import com.facebook.model.GraphUser;
import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 6/22/2014.
 */
@ParseClassName("Group")
public class Group extends ParseObject {

    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String MEMBER_IDS = "member_ids";
    public static final String PUSH_IDS = "push_ids";
    public static final String STATUS = "status";
    public static final String USER_ID = "user_id";

    public static final String CREATED_AT = "createdAt";
    public static final String DELETED = "Deleted";
    public static final String UNDELETED = "Undeleted";

    public String getTitle() {
        return getString(TITLE);
    }

    public void setTitle(String title) {
        put(TITLE, title);
    }

    public String getDescription() { return getString(DESCRIPTION);}

    public void setDescription(String description) {
        put(DESCRIPTION, description);
    }

    public List<String> getPushIds(){
        return (List<String>) get(PUSH_IDS);
    }

    public void setPushIds(List<User> users) {
        List<String> ids = new ArrayList<String>();
        for(User user : users){
            ids.add(user.getObjectId());
        }
        ids.add(User.getCurrentUser().getObjectId());
        put(PUSH_IDS, ids);
    }


    public List<String> getMemberIds(){
        return (List<String>) get(MEMBER_IDS);
    }

    public void setMemberIds(List<GraphUser> graphUsers){
        List<String> ids = new ArrayList<String>();
        for(GraphUser user : graphUsers){

            ids.add(user.getId());
        }
        ids.add(User.getCurrentUser().getFacebookId());
        put(MEMBER_IDS, ids);
    }

    public User getUserId(){
        return (User) getParseUser(USER_ID);
    }

    public void setUserId(User user){
        put(USER_ID, user);
    }

    public String getStatus(){
        int deleted = getInt(STATUS);
        if(deleted == 0){
            return DELETED;
        } else if(deleted == 1) {
            return UNDELETED;
        } else {
            return null;
        }
    }

    public static int getStatusValue(String status){
        if (status.equals(DELETED)) {
            return 0;
        } else if (status.equals(UNDELETED)) {
            return 1;
        }
        return -1;
    }


    public void setStatus(String deleted){
        int statusValue = getStatusValue(deleted);
        if(statusValue != -1) {
            put(STATUS, statusValue);
        }
    }
}
