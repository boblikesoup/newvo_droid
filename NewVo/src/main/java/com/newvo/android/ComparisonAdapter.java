package com.newvo.android;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.koushikdutta.async.future.FutureCallback;
import com.newvo.android.json.CurrentUserProfile;
import com.newvo.android.json.Post;
import com.newvo.android.request.CurrentUserProfileRequest;

import java.util.List;

/**
 * Created by David on 4/11/2014.
 */
public class ComparisonAdapter extends ArrayAdapter<Post> {

    public ComparisonAdapter(Context context, int resource) {
        super(context, resource);
        load();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ComparisonViewHolder holder;
        if (convertView == null) {
            convertView = NewVo.inflate(R.layout.comparison, null);
            holder = new ComparisonViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ComparisonViewHolder) convertView.getTag();
        }

        Post item = getItem(position);
        holder.setItem(item);

        return convertView;
    }

    public void load() {
        new CurrentUserProfileRequest().request(new FutureCallback<CurrentUserProfile>() {
            @Override
            public void onCompleted(Exception e, CurrentUserProfile result) {
                if(result != null && result.getData() != null){
                    List<Post> posts = result.getData().getPosts();
                    if(posts != null){
                        addAll(posts);
                    }
                }
            }
        });
    }

}
