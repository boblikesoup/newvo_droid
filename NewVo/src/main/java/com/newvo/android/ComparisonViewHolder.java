package com.newvo.android;

import android.app.Fragment;
import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.newvo.android.parse.Post;
import com.newvo.android.remote.VoteOnPostRequest;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.SaveCallback;

/**
 * Created by David on 4/15/2014.
 */
public class ComparisonViewHolder {
    @InjectView(R.id.caption)
    TextView question;
    @InjectView(R.id.photo1)
    ParseImageView firstImage;
    @InjectView(R.id.photo2)
    ParseImageView secondImage;
    @InjectView(R.id.main_button)
    ImageButton mainButton;
    @InjectView(R.id.first_choice)
    ImageButton firstChoice;
    @InjectView(R.id.second_choice)
    ImageButton secondChoice;
    @InjectView(R.id.first_image_container)
    LinearLayout firstImageContainer;
    @InjectView(R.id.second_image_container)
    LinearLayout secondImageContainer;
    @InjectView(R.id.buffer1)
    LinearLayout buffer1;
    @InjectView(R.id.buffer2)
    LinearLayout buffer2;

    private Post post;
    private Context context;
    private boolean voted = false;

    private Fragment container;

    public ComparisonViewHolder(View view, Fragment container) {
        setView(view);
        this.container = container;
    }

    public void setView(View view){
        ButterKnife.inject(this, view);
        context = view.getContext();
    }

    public void setItem(Post item){
        this.post = item;

        question.setText(item.getCaption());

        final ParseFile photo2 = item.getPhoto2();
        if(photo2 == null){
            secondChoice.setImageResource(R.drawable.x_button);
        } else {
            secondChoice.setImageResource(R.drawable.check_button);
        }

        final ParseFile photo1 = item.getPhoto1();
        if(photo1 != null){
            firstImage.setParseFile(photo1);
            firstImage.loadInBackground();
            firstImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((DrawerActivity) context).displayChildFragment(new ImageFragment(photo1), context.getString(R.string.title_home), "Image1");
                }
            });
        }
        if(photo2 != null){
            secondImage.setParseFile(photo2);
            secondImage.loadInBackground();
            secondImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((DrawerActivity) context).displayChildFragment(new ImageFragment(photo2), context.getString(R.string.title_home), "Image2");
                }
            });
            secondImageContainer.setVisibility(View.VISIBLE);
            buffer1.setVisibility(View.GONE);
            buffer2.setVisibility(View.GONE);

        } else {
            secondImageContainer.setVisibility(View.GONE);
            buffer1.setVisibility(View.INVISIBLE);
            buffer2.setVisibility(View.INVISIBLE);
        }

        firstChoice.setOnClickListener(new ChoiceClickListener(context, 0));
        secondChoice.setOnClickListener(new ChoiceClickListener(context, 1));

        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DrawerActivity) context).displayChildFragment(new SuggestionsFragment(post), context.getString(R.string.title_suggestions), "AddSuggestion");
            }
        });

        voted = false;

    }

    public Post getPost() {
        return post;
    }

    private class ChoiceClickListener implements View.OnClickListener {

        private Context context;
        private int vote;



        public ChoiceClickListener(Context context, int vote){
            this.context = context;
            this.vote = vote;
        }

        @Override
        public void onClick(View v) {
            if(!voted) {
                voted = true;
                new VoteOnPostRequest(post, vote).request(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null){
                            ((DrawerActivity) context).refreshFragment();
                        } else {
                            voted = false;
                        }
                    }
                });
            }

        }
    }

    public boolean hasVoted() {
        return voted;
    }
}
