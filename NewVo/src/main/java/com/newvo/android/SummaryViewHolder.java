package com.newvo.android;

import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.newvo.android.parse.Post;
import com.newvo.android.parse.User;
import com.newvo.android.remote.ClearCountersRequest;
import com.newvo.android.remote.RemovePostRequest;
import com.newvo.android.remote.SetPostActiveRequest;
import com.newvo.android.util.ToastUtils;
import com.parse.*;

import java.util.List;

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

    @InjectView(R.id.photo2_layout)
    View secondImageLayout;

    @InjectView(R.id.buffer)
    View buffer;

    //Suggestions Section
    @InjectView(R.id.suggestions_icon)
    ImageButton suggestionsIcon;
    @InjectView(R.id.settings_icon)
    ImageButton settingsIcon;
    @InjectView(R.id.number_of_suggestions)
    TextView numberOfSuggestions;
    @InjectView(R.id.suggestions_notifications)
    TextView suggestionsNotification;

    private EditPostCallback saveCallback;
    private DeleteCallback deleteCallback;

    public SummaryViewHolder(Context context, View view) {
        this.context = context;
        ButterKnife.inject(this, view);
        firstVotes = new SideViewHolder(firstVotesView);
        secondVotes = new SideViewHolder(secondVotesView);
    }

    public void setItem(final Post item) {
        final ParseFile photo1 = item.getPhoto1();
        if (photo1 != null) {
            firstImage.setParseFile(photo1);
            firstImage.loadInBackground();
            firstImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((NewVoActivity) context).displayChildFragment(new ImageFragment(photo1), context.getString(R.string.title_home), "SummaryImage");
                }
            });
        } else {
            firstImage.setParseFile(null);
        }

        boolean userIsPoster = item.getUser().getUserId().equals(User.getCurrentUser().getUserId());
        List<String> votedOnArray = item.getVotedOnArray();
        boolean votedOn = false;
        if(votedOnArray != null) {
            votedOn = votedOnArray.contains(User.getCurrentUser().getUserId());
        }
        int votes1 = item.getVotes1();
        int votes2 = item.getVotes2();

        if(userIsPoster){
            int counter1 = item.getCounter1();
            if(counter1 > 0) {
                firstVotes.votesNotification.setText("+" + counter1);
                firstVotes.votesNotification.setVisibility(View.VISIBLE);
            } else {
                firstVotes.votesNotification.setText("");
                firstVotes.votesNotification.setVisibility(View.GONE);
            }

            int counter2 = item.getCounter2();
            if(counter2 > 0) {
                secondVotes.votesNotification.setText("+" + counter2);
                secondVotes.votesNotification.setVisibility(View.VISIBLE);
            } else {
                secondVotes.votesNotification.setText("");
                secondVotes.votesNotification.setVisibility(View.GONE);
            }
            new ClearCountersRequest(item).request(null);
        }

        if(votedOn || userIsPoster){
            if(votes1 > votes2){
                firstVotes.choiceIcon.setActivated(true);
                secondVotes.choiceIcon.setActivated(false);
            }  if(votes2 > votes1){
                firstVotes.choiceIcon.setActivated(false);
                secondVotes.choiceIcon.setActivated(true);
            } else {
                firstVotes.choiceIcon.setActivated(false);
                secondVotes.choiceIcon.setActivated(false);
            }

            firstVotes.percent.setVisibility(View.VISIBLE);
            firstVotes.votesLayout.setVisibility(View.VISIBLE);
            secondVotes.percent.setVisibility(View.VISIBLE);
            secondVotes.votesLayout.setVisibility(View.VISIBLE);
        }
        else {
            firstVotes.percent.setVisibility(View.GONE);
            firstVotes.votesLayout.setVisibility(View.GONE);
            secondVotes.percent.setVisibility(View.GONE);
            secondVotes.votesLayout.setVisibility(View.GONE);
        }

        final ParseFile photo2 = item.getPhoto2();
        if (photo2 != null) {
            secondImage.setParseFile(photo2);
            secondImage.loadInBackground();
            secondImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((NewVoActivity) context).displayChildFragment(new ImageFragment(photo2), context.getString(R.string.title_home), "SummaryImage");
                }
            });

            secondVotes.choiceIcon.setImageResource(R.drawable.check);
            secondImageLayout.setVisibility(View.VISIBLE);
            buffer.setVisibility(View.GONE);
        } else {
            secondImage.setParseFile(null);
            secondVotes.choiceIcon.setImageResource(R.drawable.x);
            secondImageLayout.setVisibility(View.GONE);
            buffer.setVisibility(View.INVISIBLE);
        }

        int totalVotes = votes1 + votes2;
        if (totalVotes == 0) {
            totalVotes = 1;
        }

        int percent1 = votes1 * 100 / totalVotes;
        int percent2 = votes2 * 100 / totalVotes;

        //Start with the one with the larger remainder.
        boolean addToFirst = (votes1 * 100.0 / totalVotes) - percent1 >= (votes2 * 100.0 / totalVotes) - percent2;
        while(percent1 + percent2 < 100){
            if(addToFirst){
                percent1++;
            } else {
                percent2++;
            }
            addToFirst = !addToFirst;
        }
        firstVotes.percent.setText(percent1 + "%");
        firstVotes.votes.setText(votes1 + "");

        secondVotes.percent.setText(percent2 + "%");
        secondVotes.votes.setText(votes2 + "");

        int numberOfSuggestions1 = item.getNumberOfSuggestions();
        numberOfSuggestions.setText(numberOfSuggestions1 + "");

        final PopupMenu popupMenu = new PopupMenu(context, settingsIcon);
        boolean writeAccess = item.getACL().getWriteAccess(User.getCurrentUser());
        //Populate popup with available choices
        if(writeAccess) {
            if(item.getStatus().equals(Post.ACTIVE)){
                popupMenu.getMenu().add("Set Inactive");
            } else {
                popupMenu.getMenu().add("Set Active");
            }
            popupMenu.getMenu().add("Delete");
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    if (menuItem.getTitle().equals("Delete")) {
                        new RemovePostRequest(item).request(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if(e == null) {
                                    ToastUtils.makeText(context, context.getString(R.string.post_deleted), Toast.LENGTH_LONG).show();
                                } else {
                                    ToastUtils.makeText(context, context.getString(R.string.could_not_delete_post), Toast.LENGTH_LONG).show();
                                }
                                if(deleteCallback != null){
                                    deleteCallback.done(e);
                                }
                            }
                        });
                    } else {
                        checkAndSetActive(item, menuItem);
                    }
                    return false;
                }
            });


            settingsIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupMenu.show();
                }
            });
            settingsIcon.setVisibility(View.VISIBLE);
        } else {
            settingsIcon.setVisibility(View.INVISIBLE);
        }

    }


    private void checkAndSetActive(final Post post, final MenuItem menuItem){
        final String primary = menuItem.getTitle().toString().contains("Inactive") ? "Inactive" : "Active";
        final String secondary = primary.equals("Active") ? "Inactive" : "Active";

        SaveCallback saveCallback2 = new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    menuItem.setTitle("Set " + secondary);
                    ToastUtils.makeText(context, "Set " + primary + " Successfully!", Toast.LENGTH_LONG).show();
                } else {
                    ToastUtils.makeText(context, "Could Not Be Set " + primary, Toast.LENGTH_LONG).show();
                }
                if(saveCallback != null) {
                    saveCallback.editPost(post);
                }
            }
        };
        new SetPostActiveRequest(post, primary.toLowerCase()).request(saveCallback2);
    }

    public DeleteCallback getDeleteCallback() {
        return deleteCallback;
    }

    public void setDeleteCallback(DeleteCallback deleteCallback) {
        this.deleteCallback = deleteCallback;
    }

    public EditPostCallback getSaveCallback() {
        return saveCallback;
    }

    public void setSaveCallback(EditPostCallback saveCallback) {
        this.saveCallback = saveCallback;
    }

    class SideViewHolder {

        @InjectView(R.id.choice_icon)
        ImageView choiceIcon;
        @InjectView(R.id.votes_layout)
        View votesLayout;
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
