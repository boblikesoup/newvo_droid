package com.newvo.android;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.newvo.android.util.ChildFragment;
import com.newvo.android.util.ParseTouchImageView;
import com.parse.ParseFile;

/**
 * Created by David on 5/1/2014.
 */
public class ImageFragment extends Fragment implements ChildFragment {

    private final ParseFile imageSource;

    public ImageFragment(ParseFile imageSource){
        this.imageSource = imageSource;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ParseTouchImageView imageView = new ParseTouchImageView(container.getContext());
        imageView.setParseFile(imageSource);
        imageView.loadInBackground();
        return imageView;
    }
}
