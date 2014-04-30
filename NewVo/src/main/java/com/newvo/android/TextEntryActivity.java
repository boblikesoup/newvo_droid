package com.newvo.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

/**
 * Created by David on 4/30/2014.
 */
public class TextEntryActivity extends Activity {

    public static final int TEXT_REQUEST_CODE = 15;
    public static final String TEXT = "text";
    public static final String HINT = "hint";

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_editor);
        Bundle extras = getIntent().getExtras();
        String hint = extras.getString(HINT);
        String text = extras.getString(TEXT);
        editText = (EditText) findViewById(R.id.text);
        if(hint != null) {
            editText.setHint(hint);
        }
        if(text != null){
            editText.setText(text);
            editText.setSelection(text.length());
        }
    }

    @Override
    public void finish() {
        Intent data = new Intent();
        data.putExtra(TEXT, editText.getText());
        // Activity finished ok, return the data
        setResult(RESULT_OK, data);
        super.finish();
    }


}
