package com.newvo.android;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.newvo.android.parse.Post;
import com.parse.ParseFile;
import com.parse.ParseImageView;

/**
 * Created by David on 4/21/2014.
 */
public class SummaryViewHolder {

    private final Context context;

    @InjectView(R.id.votes1)
    View firstVotesView;
    SideViewHolder firstVotes;

    @InjectView(R.id.votes2)
    View secondVotesView;
    SideViewHolder secondVotes;

    @InjectView(R.id.photo1)
    ParseImageView firstImage;
    @InjectView(R.id.photo2)
    ParseImageView secondImage;

    //Suggestions Section
    @InjectView(R.id.suggestions_icon)
    ImageButton suggestionsIcon;
    @InjectView(R.id.settings_icon)
    ImageButton settingsIcon;
    @InjectView(R.id.number_of_suggestions)
    TextView numberOfSuggestions;
    @InjectView(R.id.suggestions_notifications)
    TextView suggestionsNotification;

    public SummaryViewHolder(Context context, View view) {
        this.context = context;
        ButterKnife.inject(this, view);
        firstVotes = new SideViewHolder(firstVotesView);
        secondVotes = new SideViewHolder(secondVotesView);
    }

    public void setItem(final Post item) {
        ParseFile photo1 = item.getPhoto1();
        if(photo1 != null){
            firstImage.setParseFile(photo1);
            firstImage.loadInBackground();
        }

        ParseFile photo2 = item.getPhoto2();
        if (photo2 != null) {
            secondImage.setParseFile(photo2);
            secondImage.loadInBackground();
        }
        int votes1 = item.getVotes1();
        int votes2 = item.getVotes2();
        int totalVotes = votes1 + votes2;
        if(totalVotes == 0){
            totalVotes = 1;
        }

        int votes = votes1 * 100 / totalVotes;
        firstVotes.percent.setText(votes + "%");
        firstVotes.votes.setText(votes1 + "");

        votes = votes2 * 100 / totalVotes;
        secondVotes.percent.setText(votes + "%");
        secondVotes.votes.setText(votes2 + "");

        int numberOfSuggestions1 = item.getNumberOfSuggestions();
        numberOfSuggestions.setText(numberOfSuggestions1 + "");

        if(numberOfSuggestions1 != 0 && context instanceof DrawerActivity) {
            suggestionsIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        ((DrawerActivity)context).displayFragment(new SuggestionsFragment(item), "Profile > Suggestions");
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

}
