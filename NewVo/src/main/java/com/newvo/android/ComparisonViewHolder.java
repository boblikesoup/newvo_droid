package com.newvo.android;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.koushikdutta.ion.Ion;
import com.newvo.android.parse.Post;
import com.parse.ParseFile;
import com.parse.ParseImageView;

/**
 * Created by David on 4/15/2014.
 */
public class ComparisonViewHolder {
    @InjectView(R.id.question)
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

    public ComparisonViewHolder(View view) {
        ButterKnife.inject(this, view);
    }

    public void setItem(Post item){

        question.setText(item.getCaption());

        ParseFile photo2 = item.getPhoto2();
        if(photo2 != null){
            secondChoice.setImageResource(R.drawable.x_button);
        } else {
            secondChoice.setImageResource(R.drawable.check_button);
        }

        ParseFile photo1 = item.getPhoto1();
        if(photo1 != null){
            firstImage.setParseFile(photo1);
            firstImage.loadInBackground();
        }
        if(photo2 != null){
            secondImage.setParseFile(photo2);
            secondImage.loadInBackground();
            secondImageContainer.setVisibility(View.VISIBLE);
            buffer1.setVisibility(View.GONE);
            buffer2.setVisibility(View.GONE);

        } else {
            secondImageContainer.setVisibility(View.GONE);
            buffer1.setVisibility(View.INVISIBLE);
            buffer2.setVisibility(View.INVISIBLE);
        }

    }

    public void loadImage(ImageView view, String location){
        if(location != null){
            Ion.with(view)
                    .load(location);
        }
    }
}
