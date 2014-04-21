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
import com.newvo.android.json.Post;
import com.newvo.android.request.CurrentUserProfileRequest;

/**
 * Created by David on 4/20/2014.
 */
public class SummaryAdapter extends ArrayAdapter<Post> {

    public SummaryAdapter(Context context, int resource) {
        super(context, resource);
        CurrentUserProfileRequest.load(this);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = NewVo.inflate(R.layout.summary, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Post item = getItem(position);
        holder.setItem(item);

        return convertView;
    }


    class ViewHolder {

        @InjectView(R.id.first_votes)
        View firstVotesView;
        SideViewHolder firstVotes;

        @InjectView(R.id.second_votes)
        View secondVotesView;
        SideViewHolder secondVotes;

        @InjectView(R.id.first_image)
        ImageView firstImage;
        @InjectView(R.id.second_image)
        ImageView secondView;

        //Comments Section
        @InjectView(R.id.comments_icon)
        ImageButton commentsIcon;
        @InjectView(R.id.settings_icon)
        ImageButton settingsIcon;
        @InjectView(R.id.number_of_comments)
        TextView numberOfComments;
        @InjectView(R.id.comments_notification)
        TextView commentsNotification;


        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
            firstVotes = new SideViewHolder(firstVotesView);
            secondVotes = new SideViewHolder(secondVotesView);
        }

        public void setItem(Post post){
        }

    }

    class SideViewHolder {

        @InjectView(R.id.choice_icon)
        ImageView choiceIcon;
        @InjectView(R.id.votes)
        TextView votes;
        @InjectView(R.id.votes_notification)
        TextView votesNotification;
        @InjectView(R.id.percent)
        TextView percent;

        public SideViewHolder(View view) {
            ButterKnife.inject(this, view);
        }

    }
}
