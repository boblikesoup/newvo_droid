package com.newvo.android;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.koushikdutta.ion.Ion;
import com.newvo.android.json.Photo;
import com.newvo.android.json.Post;

/**
 * Created by David on 4/21/2014.
 */
public class SummaryViewHolder {

    private final Context context;

    @InjectView(R.id.first_votes)
    View firstVotesView;
    SideViewHolder firstVotes;

    @InjectView(R.id.second_votes)
    View secondVotesView;
    SideViewHolder secondVotes;

    @InjectView(R.id.first_image)
    ImageView firstImage;
    @InjectView(R.id.second_image)
    ImageView secondImage;

    //Comments Section
    @InjectView(R.id.comments_icon)
    ImageButton commentsIcon;
    @InjectView(R.id.settings_icon)
    ImageButton settingsIcon;
    @InjectView(R.id.number_of_comments)
    TextView numberOfComments;
    @InjectView(R.id.comments_notification)
    TextView commentsNotification;

    public SummaryViewHolder(Context context, View view) {
        this.context = context;
        ButterKnife.inject(this, view);
        firstVotes = new SideViewHolder(firstVotesView);
        secondVotes = new SideViewHolder(secondVotesView);
    }

    public void setItem(final Post item) {
        Photo photo = item.getPhotos().get(0);
        loadImage(firstImage, photo.getUrl());
        firstVotes.votes.setText(photo.getUpvotes() + "");
        int totalVotes;
        if (item.getPhotos().size() > 1) {
            Photo photo2 = item.getPhotos().get(1);
            loadImage(secondImage, photo2.getUrl());
            totalVotes = photo.getUpvotes() + photo2.getUpvotes();
            int votes = photo2.getUpvotes() * 100 / totalVotes;
            secondVotes.percent.setText(votes + "%");

        } else {
            secondVotes.votes.setText(photo.getDownvotes() + "");
            secondVotes.choiceIcon.setImageResource(R.drawable.x_button);
            totalVotes = photo.getUpvotes() + photo.getDownvotes();
            int votes = photo.getDownvotes() * 100 / totalVotes;
            secondVotes.percent.setText(votes + "%");
        }
        int votes = photo.getUpvotes() * 100 / totalVotes;
        firstVotes.percent.setText(votes + "%");

        numberOfComments.setText(item.getComments().size() + "");

        if(!item.getComments().isEmpty() && context instanceof DrawerActivity) {
            commentsIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        ((DrawerActivity)context).displayFragment(new CommentsFragment(item), "Profile > Comments");
                }
            });
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

    public void loadImage(ImageView view, String location) {
        if (location != null) {
            Ion.with(view)
                    .load(location);
        }
    }
}
