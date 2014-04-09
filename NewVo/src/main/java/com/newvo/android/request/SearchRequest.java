package com.newvo.android.request;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by David on 4/5/2014.
 */
public class SearchRequest extends AbstractRequest {

    public static final String GLOBAL = "global";
    public static final String FRIENDS = "friends";
    public static final String FOLLOWING = "following";

    private static final String QUERY = "query";
    private static final String USED_POST_IDS = "used_post_ids";

    private Set<Integer> usedPostIds = new TreeSet<Integer>();

    public SearchRequest(String query, Integer... usedPostIds) {
        super("/ap/v1/posts/search/", GET);
        setQuery(query);
        if(usedPostIds != null && usedPostIds.length > 0){
            this.usedPostIds = new HashSet<Integer>(Arrays.asList(usedPostIds));
            //Add Used Post IDs to URL
            StringBuilder strBuilder = new StringBuilder();
            strBuilder.append("[");
            for(Integer postId : usedPostIds){
                if(strBuilder.length() != 1){
                    strBuilder.append(",");
                }
                strBuilder.append(postId);
            }
            strBuilder.append("]");
            addUrlParam(USED_POST_IDS, strBuilder.toString());
        }
    }

    private void setQuery(String query){
        super.addUrlParam(QUERY, query);
    }

}
