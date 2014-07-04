package com.newvo.android.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.newvo.android.NewVoApplication;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.parse.ParseFile;

/**
 * Created by David on 6/18/2014.
 */
public class NewVoImageView extends ImageView {

    private ParseFile parseFile;
    private boolean local;

    public NewVoImageView(Context context) {
        super(context);
    }

    public NewVoImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public NewVoImageView(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
    }

    public void loadInBackground() {
        loadInBackground(null);
    }

    public void loadInBackground(final ImageLoadingListener imageLoadingListener) {
        if(parseFile != null){
            NewVoApplication.IMAGE_LOADER.displayImage(parseFile.getUrl(), this);
        }
    }

    public void setParseFile(com.parse.ParseFile file) {
        this.parseFile = file;
    }

    public void setLocal(boolean local) {
        this.local = local;
    }

    public boolean isLocal() {
        return local;
    }
}
