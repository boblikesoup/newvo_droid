package com.newvo.android;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.newvo.android.util.ChildFragment;

/**
 * Created by David on 7/3/2014.
 */
public class TextFragment extends Fragment implements ChildFragment {

    private String caption;

    @InjectView(R.id.caption_single)
    TextView textView;

    public TextFragment(String caption){
        this.caption = caption;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.text_single, container, false);
        ButterKnife.inject(this, rootView);
        textView.setText(caption);
        return rootView;
    }
}
