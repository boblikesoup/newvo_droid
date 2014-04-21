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
import com.newvo.android.json.Post;

/**
 * Created by David on 4/21/2014.
 */
public class CommentsFragment extends Fragment {

    @InjectView(R.id.summary)
    LinearLayout summary;

    @InjectView(R.id.comments_list)
    ListView commentsList;

    private final Post post;

    public CommentsFragment(Post post) {
        this.post = post;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.comments, container, false);
        ButterKnife.inject(this, rootView);

        SummaryViewHolder summaryViewHolder = new SummaryViewHolder(getActivity(), summary);
        summaryViewHolder.setItem(post);

        CommentAdapter adapter = new CommentAdapter(inflater.getContext(), R.layout.comment_single);

        if(post != null && !post.getComments().isEmpty()){
            adapter.addAll(post.getComments());
        }

        commentsList.setAdapter(adapter);

        return rootView;
    }
}
