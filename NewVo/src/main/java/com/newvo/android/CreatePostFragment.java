package com.newvo.android;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by David on 4/16/2014.
 */
public class CreatePostFragment extends Fragment {
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
    FrameLayout firstImageContainer;
    @InjectView(R.id.second_image_container)
    FrameLayout secondImageContainer;
    @InjectView(R.id.buffer1)
    LinearLayout buffer1;
    @InjectView(R.id.buffer2)
    LinearLayout buffer2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.create_post, container, false);
        ButterKnife.inject(this, rootView);

        return rootView;
    }
}
