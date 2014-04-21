package com.newvo.android;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.newvo.android.json.Comment;

/**
 * Created by David on 4/20/2014.
 */
public class CommentAdapter extends ArrayAdapter<Comment> {

    public CommentAdapter(Context context, int resource) {
        super(context, resource);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = NewVo.inflate(R.layout.comment_single, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Comment item = getItem(position);
        holder.setItem(item);

        return convertView;
    }


    class ViewHolder {
        @InjectView(R.id.comment_text)
        TextView commentText;
        @InjectView(R.id.comment_x)
        ImageButton commentX;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }

        public void setItem(Comment comment){
            commentText.setText(comment.getBody());
        }

    }
}
