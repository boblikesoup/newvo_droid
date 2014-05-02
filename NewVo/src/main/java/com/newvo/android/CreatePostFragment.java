package com.newvo.android;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.newvo.android.parse.ParseReference;
import com.newvo.android.parse.Post;
import com.newvo.android.remote.CreatePostRequest;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by David on 4/16/2014.
 */
public class CreatePostFragment extends Fragment {

    public static final String IMAGE_NUMBER = "imageNumber";
    private static final int IMAGE_CAPTURE = 2;
    private static final int IMAGE_PICK = 4;

    @InjectView(R.id.caption)
    TextView caption;
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

    private String currentPhotoPath;

    private ParseFile file1;
    private ParseFile file2;
    private int imageNumber = -1;


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
                startIntent(1);
            }
        });
        folderCamera2.cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startIntent(2);
            }
        });

        folderCamera1.folderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                imageNumber = 1;
                startActivityForResult(intent, IMAGE_PICK);
            }
        });
        folderCamera2.folderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                imageNumber = 2;
                startActivityForResult(intent, IMAGE_PICK);
            }
        });

        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        new CreatePostRequest(activity, caption, file1, file2).request();
                        ((DrawerActivity) activity).refreshFragment();
                        Toast.makeText(activity, activity.getString(R.string.post_created), Toast.LENGTH_LONG).show();
                    } catch (CreatePostRequest.MissingCaptionError createPostError) {
                        Toast.makeText(activity, activity.getString(R.string.missing_caption), Toast.LENGTH_LONG).show();
                    } catch (CreatePostRequest.MissingImageError createPostError) {
                        Toast.makeText(activity, activity.getString(R.string.needs_image), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if(imageNumber == -1){
                return;
            }

            if (requestCode ==  Crop.REQUEST_CROP) {

                final ParseImageView imageView = (imageNumber % 2 == 1) ? firstImage : secondImage;
                LinearLayout folderCameraLayout = (imageNumber % 2 == 1) ? folderCameraLayout1 : folderCameraLayout2;

                if (imageNumber % 2 == 1) {
                    new AsyncTask() {
                        @Override
                        protected Object doInBackground(Object[] params) {
                            file1 = getParseFile(getActivity(), currentPhotoPath);
                            imageView.setParseFile(file1);
                            return null;
                        }
                    }.doInBackground(null);

                    loadSecondOption();
                } else {
                    new AsyncTask() {
                        @Override
                        protected Object doInBackground(Object[] params) {
                            file2 = getParseFile(getActivity(), currentPhotoPath);
                            imageView.setParseFile(file2);
                            return null;
                        }
                    }.doInBackground(null);
                }

                imageNumber = -1;

                imageView.loadInBackground();

                folderCameraLayout.setVisibility(View.GONE);

            } else if (requestCode == IMAGE_CAPTURE || requestCode == IMAGE_PICK) {
                String photoPath;
                if(requestCode == IMAGE_CAPTURE) {
                    photoPath = currentPhotoPath;
                } else {
                    photoPath = data.getData().toString();
                }
                try {
                    File file = createImageFile();
                    ParseReference.resizeFile(getActivity(), photoPath, file);
                    new Crop(Uri.parse(currentPhotoPath)).output(Uri.parse(currentPhotoPath)).start(getActivity(), this);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }

    }

    private void loadSecondOption() {
        buffer1.setVisibility(View.GONE);
        buffer2.setVisibility(View.GONE);
        secondImageContainer.setVisibility(View.VISIBLE);
        secondChoice.setImageResource(R.drawable.check_button);
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

    private void startIntent(int imageNumber) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            Log.e("newvo", "Failed to create image file.");
        }

        if (photoFile != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(photoFile));
            this.imageNumber = imageNumber;
            startActivityForResult(intent, IMAGE_CAPTURE);
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    private static ParseFile getParseFile(Context context, String image1) {
        ParseFile parseFile = Post.createParseFile(context.getContentResolver(), image1);
        parseFile.saveInBackground();
        return parseFile;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        file1 = null;
        firstImage.setParseFile(null);
        file2 = null;
        secondImage.setParseFile(null);
        caption.setText(null);
    }
}
