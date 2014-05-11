package com.newvo.android;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.newvo.android.parse.Post;
import com.newvo.android.parse.Suggestion;
import com.newvo.android.remote.CreateSuggestionRequest;
import com.newvo.android.remote.PostSuggestionsRequest;
import com.newvo.android.util.ChildFragment;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.SaveCallback;

import java.util.List;

/**
 * Created by David on 4/21/2014.
 */
public class SuggestionsFragment extends Fragment implements ChildFragment {

    @InjectView(R.id.summary)
    LinearLayout summary;

    @InjectView(R.id.suggestions_list)
    ListView suggestionsList;

    @InjectView(R.id.text)
    TextView text;

    @InjectView(R.id.caption)
    TextView caption;


    @InjectView(R.id.post_text)
    Button postText;


    private final Post post;

    public SuggestionsFragment(Post post) {
        this.post = post;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        final View rootView = inflater.inflate(R.layout.suggestions, container, false);
        ButterKnife.inject(this, rootView);

        //Request the suggestions
        updateSuggestions();

        SummaryViewHolder summaryViewHolder = new SummaryViewHolder(getActivity(), summary);
        summaryViewHolder.setItem(post, new DeleteCallback() {
            @Override
            public void done(ParseException e) {
                getActivity().onBackPressed();
            }
        }, null);
        caption.setText(post.getCaption());
        postText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence text = SuggestionsFragment.this.text.getText();
                if(text != null && !text.equals("")){

                    new CreateSuggestionRequest(post, text.toString()).request(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e == null) {
                                //Hide the keyboard
                                hideKeyboard();
                                SuggestionsFragment.this.text.setText(null);
                                Toast.makeText(getActivity(), getActivity().getString(R.string.suggestion_added), Toast.LENGTH_LONG).show();
                                updateSuggestions();
                            } else {
                                Toast.makeText(getActivity(), getActivity().getString(R.string.suggestion_could_not_be_posted), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), getActivity().getString(R.string.missing_suggestion), Toast.LENGTH_LONG).show();
                }
            }
        });

        return rootView;
    }

    private void hideKeyboard() {
        Activity activity = getActivity();
        if(activity != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(this.text.getWindowToken(), 0);
        }
    }

    private void updateSuggestions(){
        new PostSuggestionsRequest(post).request(new FindCallback<Suggestion>() {
            @Override
            public void done(List<Suggestion> suggestions, ParseException e) {
                initSuggestions(suggestions);
            }
        });
    }

    @Override
    public void onDestroyView() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED);
        super.onDestroyView();
    }

    protected void initSuggestions(List<Suggestion> suggestions) {
        SuggestionAdapter adapter = new SuggestionAdapter(getActivity(), R.layout.suggestion_single);
        if (post != null && post.getNumberOfSuggestions() > 0) {
            adapter.addAll(suggestions);
        }
        suggestionsList.setAdapter(adapter);
    }
}
