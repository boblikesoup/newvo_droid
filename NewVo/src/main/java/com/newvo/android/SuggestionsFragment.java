package com.newvo.android;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.newvo.android.parse.Post;
import com.newvo.android.parse.Suggestion;
import com.newvo.android.remote.PostSuggestionsRequest;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.List;

/**
 * Created by David on 4/21/2014.
 */
public class SuggestionsFragment extends Fragment {

    @InjectView(R.id.summary)
    LinearLayout summary;

    @InjectView(R.id.suggestions_list)
    ListView suggestionsList;

    private final Post post;

    public SuggestionsFragment(Post post) {
        this.post = post;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.suggestions, container, false);
        ButterKnife.inject(this, rootView);

        SummaryViewHolder summaryViewHolder = new SummaryViewHolder(getActivity(), summary);
        summaryViewHolder.setItem(post, new DeleteCallback() {
            @Override
            public void done(ParseException e) {
                getActivity().onBackPressed();
            }
        });

        new PostSuggestionsRequest(post).request(new FindCallback<Suggestion>() {
            @Override
            public void done(List<Suggestion> suggestions, ParseException e) {
                initSuggestions(suggestions);
            }
        });

        return rootView;
    }

    protected void initSuggestions(List<Suggestion> suggestions) {
        SuggestionAdapter adapter = new SuggestionAdapter(getActivity(), R.layout.suggestion_single);
        if (post != null && post.getNumberOfSuggestions() > 0) {
            adapter.addAll(suggestions);
        }
        suggestionsList.setAdapter(adapter);
    }
}
