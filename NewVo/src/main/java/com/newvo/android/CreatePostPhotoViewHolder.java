package com.newvo.android;

import android.app.Fragment;
import android.net.Uri;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.newvo.android.util.IntentUtils;
import com.newvo.android.util.ParseFileUtils;
import com.parse.ParseFile;
import com.parse.ParseImageView;

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

    @InjectView(R.id.photo_layout)
    FrameLayout photoLayout;

    @InjectView(R.id.swap_photo)
    protected ImageButton swapButton;

    View createPostImageContainer;

    private Uri photo;
    private Uri uncroppedPhoto;

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

    @OnClick(R.id.photo)
    public void startImageCropIntent(){
        if(uncroppedPhoto != null && uncroppedPhoto.toString() != null) {
            IntentUtils.startImageCropIntent(fragment, uncroppedPhoto.toString());
        }
    }


    public void onDestroyView() {
        parseFile = null;
        photoView.setParseFile(null);
    }

    public void setUncroppedPhoto(Uri photo){
        this.uncroppedPhoto = photo;
    }

    public void setPhoto(Uri photo) {
        this.photo = photo;
        parseFile = ParseFileUtils.getParseFile(fragment.getActivity(), photo.toString());
        updateImageView();

    }

    private void updateImageView(){
        if(parseFile != null) {
            photoView.setParseFile(parseFile);
            photoView.loadInBackground();
            photoLayout.setVisibility(View.VISIBLE);
            folderCameraLayout.setVisibility(View.GONE);
        } else {
            photoView.setParseFile(null);
            photoLayout.setVisibility(View.GONE);
            folderCameraLayout.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.delete_photo)
    public void deletePhoto(){
        this.uncroppedPhoto = null;
        this.photo = null;
        parseFile = null;
        updateImageView();
    }

    public void swapPhoto(CreatePostPhotoViewHolder holder){
        ParseFile parseFile = this.parseFile;
        Uri uncroppedPhoto = this.uncroppedPhoto;
        Uri photo = this.photo;

        this.parseFile = holder.parseFile;
        this.uncroppedPhoto = holder.uncroppedPhoto;
        this.photo = holder.photo;

        holder.parseFile = parseFile;
        holder.uncroppedPhoto = uncroppedPhoto;
        holder.photo = photo;

        holder.updateImageView();
        updateImageView();
    }

    public ParseFile getParseFile() {
        return parseFile;
    }

    public void setVisibility(int visibility) {
        createPostImageContainer.setVisibility(visibility);
    }
}