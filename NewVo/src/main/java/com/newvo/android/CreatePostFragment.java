package com.newvo.android;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
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


    @InjectView(R.id.folder_camera_1)
    LinearLayout folderCameraLayout1;

    @InjectView(R.id.folder_camera_2)
    LinearLayout folderCameraLayout2;

    ViewHolder folderCamera1;

    ViewHolder folderCamera2;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.create_post, container, false);
        ButterKnife.inject(this, rootView);
        folderCamera1 = new ViewHolder(folderCameraLayout1);
        folderCamera2 = new ViewHolder(folderCameraLayout2);

        folderCamera1.cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intent, 1);
            }
        });
        folderCamera2.cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intent, 2);
            }
        });


        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            if(requestCode == 1){
                firstImage.setImageBitmap(imageBitmap);
                loadSecondOption();
            } else if(requestCode == 2){
                secondImage.setImageBitmap(imageBitmap);
            }

        }
    }

    private void loadSecondOption(){
        //TODO: Create loading sequence for second option.
    }

    class ViewHolder {
        @InjectView(R.id.camera_button)
        ImageButton cameraButton;
        @InjectView(R.id.folder_button)
        ImageButton folderButton;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }

    }
}
