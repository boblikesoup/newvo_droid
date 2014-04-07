package com.newvo.android.request;

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

    public SearchRequest() {
        super("/ap/v1/posts/search", GET);
    }

    public void setQuery(String query){
        super.addParam(QUERY, query);
    }

    public void addUsedPostId(int id){
        usedPostIds.add(id);
    }

    @Override
    public String makeRequest() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("[");
        for(Integer postId : usedPostIds){
            if(strBuilder.length() != 1){
                strBuilder.append(",");
            }
            strBuilder.append(postId);
        }
        strBuilder.append("]");
        addParam(USED_POST_IDS, strBuilder.toString());
        return super.makeRequest();
    }
}
