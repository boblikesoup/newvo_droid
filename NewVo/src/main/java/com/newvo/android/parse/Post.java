package com.newvo.android.parse;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 4/26/2014.
 */
@ParseClassName("Post")
public class Post extends ParseObject implements Comparable<Post> {

    public static final String USER_ID = User.USER_ID;
    public static final String POST_ID = "post_id";

    public static final String CREATED_AT = "createdAt";

    public static final String CAPTION = "caption";
    public static final String PHOTO_1 = "photo_1";
    public static final String PHOTO_2 = "photo_2";
    public static final String VOTES_0 = "votes_0";
    public static final String VOTES_1 = "votes_1";
    public static final String SUGGESTIONS = "suggestions";
    public static final String STATUS = "status";
    public static final String VIEWABLE_BY = "viewable_by";
    public static final String VOTED_ON_ARRAY = "voted_on_array";

    public static final String COUNTER_0 = "counter_0";
    public static final String COUNTER_1 = "counter_1";

    public static final String GROUP_ID = "group_id";
    public static final String USER_TAG = "user_tags";

    //status strings
    public static final String ACTIVE = "active";
    public static final String INACTIVE = "inactive";
    public static final String DELETED = "deleted";

    //viewable by strings
    public static final String PUBLIC = "public";
    public static final String FRIENDS = "friends";


    public User getUser() {
        return (User) getParseUser(USER_ID);
    }

    public void setUser(User user) {
        put(USER_ID, user);
    }

    public String getPostId() {
        return  getString(POST_ID);
    }

    public String getCaption() {
        return getString(CAPTION);
    }

    public void setCaption(String caption) {
        put(CAPTION, caption);
        String[] split = caption.split("\\s+");
        for (String str : split) {
            add("caption_search", str);
        }
    }


    public ParseFile getPhoto1() {
        return getParseFile(PHOTO_1);
    }

    public void setPhoto1(ParseFile photo1) {
        put(PHOTO_1, photo1);
    }


    public ParseFile getPhoto2() {
        return getParseFile(PHOTO_2);
    }

    public void setPhoto2(ParseFile photo2) {
        put(PHOTO_2, photo2);
    }

    public int getVotes1() {
        return getInt(VOTES_0);
    }

    public void setVotes1(int votes1) {
        put(VOTES_0, votes1);
    }

    public int getVotes2() {
        return getInt(VOTES_1);
    }

    public void setVotes2(int votes2) {
        put(VOTES_1, votes2);
    }

    public int getCounter1() {
        return getInt(COUNTER_0);
    }

    public void setCounter1(int counter1) {
        put(COUNTER_0, counter1);
    }

    public int getCounter2() {
        return getInt(COUNTER_1);
    }

    public void setCounter2(int counter2) {
        put(COUNTER_1, counter2);
    }

    public int getNumberOfSuggestions() {
        return getInt(SUGGESTIONS);
    }

    public void setNumberOfSuggestions(int numberOfSuggestions) {
        put(SUGGESTIONS, numberOfSuggestions);
    }

    public String getStatus() {
        int status = getInt(STATUS);
        switch (status) {
            case 0:
                return ACTIVE;
            case 1:
                return INACTIVE;
            case 2:
                return DELETED;
        }
        return null;
    }

    public void setStatus(String status) {
        int statusInt = getStatusValue(status);
        if (statusInt > -1) {
            put(STATUS, statusInt);
        }
    }

    public static int getStatusValue(String status){
        if (status.equals(ACTIVE)) {
            return 0;
        } else if (status.equals(INACTIVE)) {
            return 1;
        } else if (status.equals(DELETED)) {
            return 2;
        }
        return -1;
    }

    public String getViewableBy() {
        int status = getInt(VIEWABLE_BY);
        switch (status) {
            case 0:
                return PUBLIC;
            case 3:
                return FRIENDS;
        }
        return null;
    }

    public void setViewableBy(String viewableBy) {
        int viewableByInt = -1;
        if (viewableBy.equals(PUBLIC)) {
            viewableByInt = 0;
        }
        if (viewableBy.equals(FRIENDS)) {
            viewableByInt = 3;
        }
        if (viewableByInt > -1) {
            put(VIEWABLE_BY, viewableByInt);
        }
    }

    public List<String> getVotedOnArray(){ return  (List<String>) get(VOTED_ON_ARRAY);}

    public List<String> getGroupIds(){
        return (List<String>) get(GROUP_ID);
    }

    public void setGroupIds(List<Group> groups) {
        List<String> ids = new ArrayList<String>();
        for(Group group : groups){
            ids.add(group.getObjectId());
        }
        put(GROUP_ID, ids);
    }

    public List<String> getUserTags(){
        return (List<String>) get(USER_TAG);
    }

    public void setUserTags(List<String> ids) {
        put(USER_TAG, ids);
    }

    @Override
    public boolean equals(Object o) {
        if(o == null || !(o instanceof Post)){
            return false;
        }
        String objectId = getObjectId();
        String otherObjectId = ((Post) o).getObjectId();
        if(objectId == null || otherObjectId == null){
            return false;
        }
        return objectId.equals(otherObjectId);
    }

    @Override
    public int compareTo(Post another) {
        return -this.getCreatedAt().compareTo(another.getCreatedAt());
    }
}
