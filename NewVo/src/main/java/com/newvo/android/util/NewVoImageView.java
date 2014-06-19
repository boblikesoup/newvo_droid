package com.newvo.android.util;

import android.content.Context;
import android.util.AttributeSet;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseImageView;

/**
 * Created by David on 6/18/2014.
 */
public class NewVoImageView extends ParseImageView {

    public NewVoImageView(Context context) {
        super(context);
    }

    public NewVoImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public NewVoImageView(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
    }

    @Override
    public void loadInBackground() {
        loadInBackground(null);
    }

    @Override
    public void loadInBackground(final GetDataCallback completionCallback) {
        GetDataCallback callback = new GetDataCallback() {
            @Override
            public void done(byte[] bytes, ParseException e) {
                if(e != null){
                    loadInBackground(this);
                }
                if(completionCallback != null) {
                    completionCallback.done(bytes, e);
                }
            }
        };
        super.loadInBackground(callback);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
