package com.newvo.android.util;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * This EditText closes the activity when it closes the keyboard.
 * Useful for activities that only edit a text.
 *
 * Created by David on 5/1/2014.
 */
public class CloseableEditText extends EditText {

    public CloseableEditText(Context context) {
        super(context);
    }

    public CloseableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CloseableEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchKeyEventPreIme(KeyEvent event) {
        Context context = getContext();
        if (context != null && context instanceof Activity) {
            InputMethodManager imm = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);

            if (imm.isActive() && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                ((Activity)getContext()).finish();
            }
        }

        return super.dispatchKeyEventPreIme(event);
    }
}
