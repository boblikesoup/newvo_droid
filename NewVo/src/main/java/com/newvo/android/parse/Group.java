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

    public static final String CREATED_AT = "createdAt";

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
}
