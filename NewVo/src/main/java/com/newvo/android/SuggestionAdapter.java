package com.newvo.android;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.newvo.android.parse.Suggestion;
import com.newvo.android.parse.User;
import com.newvo.android.remote.RemoveSuggestionRequest;
import com.newvo.android.util.ToastUtils;
import com.parse.ParseException;
import com.parse.SaveCallback;

/**
 * Created by David on 4/20/2014.
 */
public class SuggestionAdapter extends ArrayAdapter<Suggestion> {

    public SuggestionAdapter(Context context, int resource) {
        super(context, resource);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.suggestion_single, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Suggestion item = getItem(position);
        holder.setItem(item);

        return convertView;
    }

    class ViewHolder {
        @InjectView(R.id.text)
        TextView suggestionText;
        @InjectView(R.id.x)
        ImageButton suggestionX;
        @InjectView(R.id.in_progress)
        ProgressBar inProgressBar;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }

        public void setItem(final Suggestion suggestion){
            suggestionText.setText(suggestion.getBody());
            boolean writeAccess = suggestion.getACL().getWriteAccess(User.getCurrentUser());
            suggestionX.setVisibility(writeAccess ? View.VISIBLE : View.GONE);
            suggestionX.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setInProgress(true);
                    new RemoveSuggestionRequest(suggestion).request(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e == null){
                                SuggestionAdapter.this.remove(suggestion);
                                ToastUtils.makeText(getContext(), getContext().getString(R.string.suggestion_removed), Toast.LENGTH_LONG).show();
                            } else {
                                ToastUtils.makeText(getContext(), getContext().getString(R.string.could_not_delete_suggestion), Toast.LENGTH_LONG).show();
                                setInProgress(false);
                                Log.e("NewVo", "Could not delete suggestion.");
                            }
                        }
                    });
                }
            });
            if(writeAccess){
                setInProgress(suggestion.isLoading());
            }
        }

        private void setInProgress(boolean inProgress){
            suggestionX.setVisibility(inProgress ? View.INVISIBLE : View.VISIBLE);
            inProgressBar.setVisibility(inProgress ? View.VISIBLE : View.INVISIBLE);
        }

    }
}
