package com.newvo.android;

import android.content.Intent;
import android.os.Bundle;
import com.parse.*;


public class NewVo extends DrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
    }

    @Override
    protected void displayView(String name) {
        setTheme(R.style.CustomActionBarTheme);
        super.displayView(name);
    }
}
