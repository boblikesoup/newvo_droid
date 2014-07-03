package com.newvo.android;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.newvo.android.friends.FriendPickerActivity;
import com.newvo.android.friends.TaggingFragment;
import com.newvo.android.groups.GroupPickerAdapter;
import com.newvo.android.remote.CreatePostRequest;
import com.newvo.android.util.ImageFileUtils;
import com.newvo.android.util.IntentUtils;
import com.newvo.android.util.ToastUtils;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.SaveCallback;
import com.soundcloud.android.crop.Crop;

import java.util.ArrayList;

import static com.newvo.android.util.IntentUtils.*;

/**
 * Created by David on 4/16/2014.
 */
public class CreatePostFragment extends Fragment {

    public static final int DP_OFFSET = 40;
    public static final String FACEBOOK_SHARING = "facebookSharing";
    @InjectView(R.id.caption)
    TextView caption;
    @InjectView(R.id.microphone)
    ImageButton microphone;
    @InjectView(R.id.tagging)
    ImageButton tagging;
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

    @InjectView(R.id.facebook_share_button)
    ImageButton facebookShareButton;

    @InjectView(R.id.progress_bar)
    protected ProgressBar progressBar;

    ViewHolder image1;
    ViewHolder image2;

    private int imageNumber = -1;

    protected boolean posted;

    public CreatePostFragment() {
        FriendPickerActivity.SELECTION = null;
        GroupPickerAdapter.SELECTION = null;
        TaggingFragment.FRIENDS_ONLY = false;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        IntentUtils.resetIntentLoading();
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.create_post, container, false);
        ButterKnife.inject(this, rootView);

        posted = false;

        if (image1 == null) {
            image1 = new FirstViewHolder(this, rootView.findViewById(R.id.image_container_1), 1);
        } else {
            image1.onCreateView(this, rootView.findViewById(R.id.image_container_1));
        }
        if (image2 == null) {
            image2 = new SecondViewHolder(this, rootView.findViewById(R.id.image_container_2), 2);
        } else {
            image2.onCreateView(this, rootView.findViewById(R.id.image_container_2));

        }

        image1.setPhoto(image1.photo);
        image2.setPhoto(image2.photo);

        microphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.startMicrophoneIntent(CreatePostFragment.this);
            }
        });
        tagging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NewVoActivity) getActivity()).displayChildFragment(new TaggingFragment(), getActivity().getString(R.string.title_create_post), "Tagging");
            }
        });

        facebookShareButton.setActivated(PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean(FACEBOOK_SHARING, false));
        facebookShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebookShareButton.setActivated(!facebookShareButton.isActivated());
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
                editor.putBoolean(FACEBOOK_SHARING, facebookShareButton.isActivated());
                editor.commit();
            }
        });


        return rootView;
    }

    @OnClick(R.id.main_button)
    public void createPost() {
        if (posted) {
            return;
        }
        CharSequence text = caption.getText();
        String caption;
        if (text == null) {
            caption = null;
        } else {
            caption = text.toString();
        }
        final Activity activity = getActivity();
        if (activity != null) {
            try {
                createPostRequest(activity, caption, image1.getParseFile(), image2.getParseFile());
                progressBar.setVisibility(View.VISIBLE);
                posted = true;

            } catch (CreatePostRequest.MissingCaptionError createPostError) {
                ToastUtils.makeText(activity, activity.getString(R.string.missing_caption), Toast.LENGTH_LONG, DP_OFFSET).show();
            } catch (CreatePostRequest.MissingImageError createPostError) {
                ToastUtils.makeText(activity, activity.getString(R.string.needs_image), Toast.LENGTH_LONG, DP_OFFSET).show();
            }
        }
    }

    protected void createPostRequest(final Activity activity, String caption, ParseFile parseFile1, ParseFile parseFile2) {
        new CreatePostRequest(activity, caption, parseFile1, parseFile2,
                GroupPickerAdapter.SELECTION,
                FriendPickerActivity.SELECTION,
                TaggingFragment.FRIENDS_ONLY).request(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    ToastUtils.makeText(activity, activity.getString(R.string.post_created), Toast.LENGTH_LONG, DP_OFFSET).show();
                    ((NewVoActivity) activity).restartFragment();
                } else {
                    ToastUtils.makeText(activity, activity.getString(R.string.could_not_create_post), Toast.LENGTH_LONG, DP_OFFSET).show();
                    progressBar.setVisibility(View.GONE);
                    posted = false;
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        IntentUtils.resetIntentLoading();
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

            } else if (requestCode == MICROPHONE_INTENT) {
                ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                if (text.size() > 0) {
                    caption.setText(text.get(0));
                }
            }

        }

    }

    private void loadSecondOption() {
        buffer1.setVisibility(View.GONE);
        buffer2.setVisibility(View.GONE);
        image2.setVisibility(View.VISIBLE);
    }

    private void removeSecondOption() {
        buffer1.setVisibility(View.VISIBLE);
        buffer2.setVisibility(View.VISIBLE);
        image2.setVisibility(View.GONE);
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
                    if (IntentUtils.loadingEitherIntent()) {
                        return;
                    }
                    swapPhoto(image1);
                }
            });
        }

        @Override
        public void setPhoto(Uri photo) {
            super.setPhoto(photo);
            if(photo != null) {
                secondChoice.setImageResource(R.drawable.check);
            } else {
                secondChoice.setImageResource(R.drawable.x);
            }
        }

        @Override
        public void deletePhoto() {
            if (IntentUtils.loadingEitherIntent()) {
                return;
            }
            super.deletePhoto();
            secondChoice.setImageResource(R.drawable.x);
        }
    }

    private class FirstViewHolder extends ViewHolder {

        private FirstViewHolder(Fragment fragment, View view, int imageNumber) {
            super(fragment, view, imageNumber);
        }

        @Override
        public void setPhoto(Uri photo) {
            super.setPhoto(photo);
            if(photo != null) {
                loadSecondOption();
            } else {
                removeSecondOption();
            }
        }

        @Override
        public void deletePhoto() {
            if (IntentUtils.loadingEitherIntent()) {
                return;
            }
            super.deletePhoto();
            if (image2.getParseFile() != null) {
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

        @Override
        public void startImageCropIntent() {
            CreatePostFragment.this.imageNumber = imageNumber;
            super.startImageCropIntent();
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
