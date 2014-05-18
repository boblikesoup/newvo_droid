package com.newvo.android.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.newvo.android.R;

/**
 * Created by David on 5/18/2014.
 */
public class ToastUtils {

    private static Toast oldToast;

    public static Toast makeText(Context context, String text, int duration){
        return makeText(context, text, duration, 75);
    }

    public static Toast makeText(Context context, String text, int duration, int dpOffset){
        Activity activity = (Activity) context;
        LayoutInflater inflater = activity.getLayoutInflater();
        Toast toast = new Toast(context.getApplicationContext());

        View layout;
        layout = inflater.inflate(R.layout.toast_extend,
                (ViewGroup) activity.findViewById(R.id.toast_layout_root));
        toast.setMargin(0,0);
        if (dpOffset == -1) {
            toast.setGravity(Gravity.FILL_HORIZONTAL, 0, 0);
        } else {
            Resources r = context.getResources();
            int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpOffset, r.getDisplayMetrics());
            toast.setGravity(Gravity.FILL_HORIZONTAL|Gravity.BOTTOM, 0, px);
        }
        TextView textView = (TextView) layout.findViewById(R.id.text);
        textView.setText(text);

        toast.setDuration(duration);
        toast.setView(layout);

        //Always display toast immediately
        if (oldToast != null) {
            oldToast.cancel();
        }
        oldToast = toast;

        return toast;
    }
}
