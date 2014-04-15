package com.newvo.android;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.koushikdutta.ion.Ion;
import com.newvo.android.json.Post;

/**
 * Created by David on 4/15/2014.
 */
public class ComparisonViewHolder {
    @InjectView(R.id.question)
    TextView question;
    @InjectView(R.id.first_image)
    ImageView firstImage;
    @InjectView(R.id.second_image)
    ImageView secondImage;
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

    public ComparisonViewHolder(View view) {
        ButterKnife.inject(this, view);
    }

    public void setItem(Post item){

        question.setText(item.getDescription());

        if(item.isSinglePicture()){
            secondChoice.setImageResource(R.drawable.x_button);
        } else {
            secondChoice.setImageResource(R.drawable.check_button);
        }

        if(item.getPhotos().size() > 0){
            loadImage(firstImage, item.getPhotos().get(0).getUrl());
        }
        if(item.getPhotos().size() > 1){
            loadImage(secondImage, item.getPhotos().get(1).getUrl());
            secondImageContainer.setVisibility(View.VISIBLE);
        } else {
            secondImageContainer.setVisibility(View.GONE);
        }

        mainButton.setImageResource(R.drawable.comments);
    }

    public void loadImage(ImageView view, String location){
        if(location != null){
            Ion.with(view)
                    .placeholder(R.drawable.x_image)
                    .load(location);
        }
    }
}
