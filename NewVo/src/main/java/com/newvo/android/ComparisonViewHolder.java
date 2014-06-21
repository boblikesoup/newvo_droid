package com.newvo.android;

import android.app.Fragment;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.newvo.android.parse.Post;
import com.newvo.android.remote.VoteOnPostRequest;
import com.newvo.android.util.ToastUtils;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.SaveCallback;

import java.util.LinkedHashSet;
import java.util.Set;

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
    private Post lastVotedPost;
    private Set<Post> votedPosts = new LinkedHashSet<Post>();

    public ComparisonViewHolder(View view, Fragment container) {
        setView(view);
    }

    public void setView(View view) {
        ButterKnife.inject(this, view);
        context = view.getContext();
    }

    public void setItem(Post item) {
        this.post = item;

        question.setText(item.getCaption());

        final ParseFile photo2 = item.getPhoto2();
        if (photo2 == null) {
            secondChoice.setImageResource(R.drawable.x);
        } else {
            secondChoice.setImageResource(R.drawable.check);
        }

        final ParseFile photo1 = item.getPhoto1();
        if (photo1 != null) {
            firstImage.setParseFile(photo1);
            firstImage.loadInBackground();
            firstImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((NewVoActivity) context).displayChildFragment(new ImageFragment(photo1), context.getString(R.string.title_home), "Image1");
                }
            });
        }
        if (photo2 != null) {
            secondImage.setParseFile(photo2);
            secondImage.loadInBackground();
            secondImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((NewVoActivity) context).displayChildFragment(new ImageFragment(photo2), context.getString(R.string.title_home), "Image2");
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
                ((NewVoActivity) context).displayChildFragment(new SuggestionsFragment(post), context.getString(R.string.title_suggestions), "AddSuggestion");
            }
        });

        lastVotedPost = null;
    }

    public Post getPost() {
        return post;
    }

    private class ChoiceClickListener implements View.OnClickListener {

        private Context context;
        private int vote;


        public ChoiceClickListener(Context context, int vote) {
            this.context = context;
            this.vote = vote;
        }

        @Override
        public void onClick(View v) {
            lastVotedPost = post;
            votedPosts.add(post);
            new VoteOnPostRequest(post, vote).request(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        //Success!
                    }
                }
            });

            new Handler().postDelayed(new Runnable() {
                public void run() {
                    ((NewVoActivity) context).attachDetachFragment();
                }
            }, 2500);

            int votes1 = post.getVotes1();
            int votes2 = post.getVotes2();
            int totalVotes = votes1 + votes2;
            if (totalVotes == 0) {
                ToastUtils.makeText(context, "First vote! Congrats!", Toast.LENGTH_SHORT, -1).show();
            } else {
                int votes = ((vote == 0) ? votes1 : votes2) * 100 / totalVotes;
                ToastUtils.makeText(context, votes + "% agreed with you.", Toast.LENGTH_SHORT, -1).show();
            }
        }
    }

    public Set<Post> getVotedPosts() {
        return votedPosts;
    }

    public Post getLastVotedPost() {
        return lastVotedPost;
    }
}
