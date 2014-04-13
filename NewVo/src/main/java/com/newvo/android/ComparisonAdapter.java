package com.newvo.android;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = NewVo.inflate(R.layout.comparison, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Post item = getItem(position);

        holder.name.setText(item.getUserName());
        holder.question.setText(item.getDescription());
        holder.numberOfComments.setText(item.getComments().size());

        if(item.getHasSinglePicture()){
            holder.secondChoice.setImageResource(R.drawable.x);
        } else {
            holder.secondChoice.setImageResource(R.drawable.check);
        }

        loadImage(holder.userIcon, item.getProfilePic());
        if(item.getPhotos().size() > 0){
            loadImage(holder.firstImage, item.getPhotos().get(0).getUrl());
        }
        if(item.getPhotos().size() > 1){
            loadImage(holder.secondImage, item.getPhotos().get(1).getUrl());
        }

        holder.expandButton.setImageResource(android.R.drawable.ic_menu_more);



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

    public void loadImage(ImageView view, String location){
        if(location != null){
            Ion.with(view)
                    .placeholder(R.drawable.x)
                    .load(location);
        }
    }

    static class ViewHolder {
        @InjectView(R.id.name)
        TextView name;
        @InjectView(R.id.question)
        TextView question;
        @InjectView(R.id.user_icon)
        ImageButton userIcon;
        @InjectView(R.id.first_image)
        ImageView firstImage;
        @InjectView(R.id.second_image)
        ImageView secondImage;
        @InjectView(R.id.number_of_comments)
        TextView numberOfComments;
        @InjectView(R.id.expand_button)
        ImageButton expandButton;
        @InjectView(R.id.first_choice)
        ImageButton firstChoice;
        @InjectView(R.id.second_choice)
        ImageButton secondChoice;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
