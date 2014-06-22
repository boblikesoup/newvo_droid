package com.newvo.android.parse;

import com.parse.ParseObject;

/**
 * Created by David on 6/22/2014.
 */
public class Group extends ParseObject {

    public static final String TITLE = "title";
    public static final String MEMBER_IDS = "member_ids";
    public static final String PUSH_IDS = "push_ids";
    public static final String STATUS = "status";

    public String getTitle() {
        return getString(TITLE);
    }

    public void setTitle(String caption) {
        put(TITLE, caption);
    }
}
