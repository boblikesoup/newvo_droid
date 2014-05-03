package com.newvo.android;

import android.app.Fragment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.newvo.android.util.ImageFileUtils;
import com.newvo.android.util.IntentUtils;
import com.newvo.android.util.ParseFileUtils;
import com.parse.ParseFile;
import com.parse.ParseImageView;

import java.io.File;

/**
 * Created by David on 5/3/2014.
 */
public class CreatePostPhotoViewHolder {

    @InjectView(R.id.camera_button)
    ImageButton cameraButton;

    @InjectView(R.id.folder_button)
    ImageButton folderButton;

    @InjectView(R.id.photo)
    ParseImageView photoView;

    @InjectView(R.id.folder_camera_layout)
    LinearLayout folderCameraLayout;

    View createPostImageContainer;

    private File photo;

    private ParseFile parseFile;

    private Fragment fragment;

    public CreatePostPhotoViewHolder(Fragment fragment, View view) {
        this.fragment = fragment;
        createPostImageContainer = view;
        ButterKnife.inject(this, view);
    }

    @OnClick(R.id.camera_button)
    public void startImageCaptureIntent(){
        IntentUtils.startImageCaptureIntent(fragment);
    }

    @OnClick(R.id.folder_button)
    public void startImagePickIntent(){
        IntentUtils.startImagePickIntent(fragment);
    }

    public String getPhotoPath(){
        return ImageFileUtils.getPhotoPath(photo);
    }


    public void onDestroyView() {
        parseFile = null;
        photoView.setParseFile(null);
    }

    public void setPhoto(File photo) {
        this.photo = photo;
        parseFile = ParseFileUtils.getParseFile(fragment.getActivity(), ImageFileUtils.getPhotoPath(photo));
        photoView.setParseFile(parseFile);
        photoView.loadInBackground();
        photoView.setVisibility(View.VISIBLE);
        folderCameraLayout.setVisibility(View.GONE);
    }

    public ParseFile getParseFile() {
        return parseFile;
    }

    public void setVisibility(int visibility) {
        createPostImageContainer.setVisibility(visibility);
    }
}