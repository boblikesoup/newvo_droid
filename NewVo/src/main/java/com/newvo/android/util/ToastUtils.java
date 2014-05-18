package com.newvo.android.util;

import android.app.Activity;
import android.content.Context;
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

    public static Toast makeText(Context context, String text, int duration){
        Activity activity = (Activity) context;
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast,
                (ViewGroup) activity.findViewById(R.id.toast_layout_root));

        TextView textView = (TextView) layout.findViewById(R.id.text);
        textView.setText(text);

        Toast toast = new Toast(context.getApplicationContext());
        toast.setDuration(duration);
        toast.setView(layout);
        return toast;
    }
}
