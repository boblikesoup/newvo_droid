package com.newvo.android;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.newvo.android.remote.CreatePostRequest;
import com.newvo.android.util.ImageFileUtils;
import com.newvo.android.util.IntentUtils;
import com.soundcloud.android.crop.Crop;

import static com.newvo.android.util.IntentUtils.IMAGE_CAPTURE;
import static com.newvo.android.util.IntentUtils.IMAGE_PICK;

/**
 * Created by David on 4/16/2014.
 */
public class CreatePostFragment extends Fragment {

    @InjectView(R.id.caption)
    TextView caption;
    @InjectView(R.id.main_button)
    ImageButton mainButton;
    @InjectView(R.id.first_choice)
    ImageButton firstChoice;
    @InjectView(R.id.second_choice)
    ImageButton secondChoice;
    @InjectView(R.id.buffer1)
    LinearLayout buffer1;
    @InjectView(R.id.buffer2)
    LinearLayout buffer2;

    ViewHolder image1;
    ViewHolder image2;

    private int imageNumber = -1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.create_post, container, false);
        ButterKnife.inject(this, rootView);

        image1 = new FirstViewHolder(this, rootView.findViewById(R.id.image_container_1), 1);
        image2 = new SecondViewHolder(this, rootView.findViewById(R.id.image_container_2), 2);

        return rootView;
    }



    @OnClick(R.id.main_button)
    public void createPost() {
        CharSequence text = caption.getText();
        String caption;
        if (text == null) {
            caption = null;
        } else {
            caption = text.toString();
        }
        Activity activity = getActivity();
        if (activity != null) {
            try {
                new CreatePostRequest(activity, caption, image1.getParseFile(), image2.getParseFile()).request();
                ((DrawerActivity) activity).restartFragment();
                Toast.makeText(activity, activity.getString(R.string.post_created), Toast.LENGTH_LONG).show();
            } catch (CreatePostRequest.MissingCaptionError createPostError) {
                Toast.makeText(activity, activity.getString(R.string.missing_caption), Toast.LENGTH_LONG).show();
            } catch (CreatePostRequest.MissingImageError createPostError) {
                Toast.makeText(activity, activity.getString(R.string.needs_image), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Crop.REQUEST_CROP) {
                new AsyncTask() {
                    @Override
                    protected Object doInBackground(Object[] params) {
                        Uri uri = (Uri) data.getExtras().get(MediaStore.EXTRA_OUTPUT);
                        ImageFileUtils.resizeFile(getActivity(), uri.toString(), uri);
                        getSelectedImage().setPhoto(uri);
                        return null;
                    }
                }.doInBackground(null);

            } else if (requestCode == IMAGE_CAPTURE || requestCode == IMAGE_PICK) {
                Uri uri;
                if (requestCode == IMAGE_CAPTURE) {
                    uri = IntentUtils.photoFile;
                } else {
                    uri = data.getData();

                }
                getSelectedImage().setUncroppedPhoto(uri);

                IntentUtils.startImageCropIntent(this, uri.toString());

            }

        }

    }

    private void loadSecondOption() {
        buffer1.setVisibility(View.GONE);
        buffer2.setVisibility(View.GONE);
        image2.setVisibility(View.VISIBLE);
        secondChoice.setImageResource(R.drawable.check_button);
    }

    private void removeSecondOption() {
        buffer1.setVisibility(View.VISIBLE);
        buffer2.setVisibility(View.VISIBLE);
        image2.setVisibility(View.GONE);
        secondChoice.setImageResource(R.drawable.x_button);
    }

    private ViewHolder getSelectedImage() {
        if (imageNumber == 1) {
            return image1;
        }
        if (imageNumber == 2) {
            return image2;
        }
        return null;
    }

    private class SecondViewHolder extends ViewHolder {

        private SecondViewHolder(Fragment fragment, View view, int imageNumber) {
            super(fragment, view, imageNumber);
            this.swapButton.setVisibility(View.VISIBLE);
            this.swapButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    swapPhoto(image1);
                }
            });
        }

        @Override
        public void setPhoto(Uri photo) {
            super.setPhoto(photo);
            secondChoice.setImageResource(R.drawable.check_button);
        }

        @Override
        public void deletePhoto() {
            super.deletePhoto();
            secondChoice.setImageResource(R.drawable.x_button);
        }
    }

    private class FirstViewHolder extends ViewHolder {

        private FirstViewHolder(Fragment fragment, View view, int imageNumber) {
            super(fragment, view, imageNumber);
        }

        @Override
        public void setPhoto(Uri photo) {
            super.setPhoto(photo);
            loadSecondOption();
        }

        @Override
        public void deletePhoto() {
            super.deletePhoto();
            if(image2.getParseFile() != null){
                swapPhoto(image2);
            } else {
                removeSecondOption();
            }
        }
    }

    private class ViewHolder extends CreatePostPhotoViewHolder {

        private final int imageNumber;

        private ViewHolder(Fragment fragment, View view, int imageNumber) {
            super(fragment, view);
            this.imageNumber = imageNumber;
        }

        @Override
        public void startImageCaptureIntent() {
            CreatePostFragment.this.imageNumber = imageNumber;
            super.startImageCaptureIntent();
        }

        @Override
        public void startImagePickIntent() {
            CreatePostFragment.this.imageNumber = imageNumber;
            super.startImagePickIntent();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        image1.onDestroyView();
        image2.onDestroyView();
        caption.setText(null);
    }
}
