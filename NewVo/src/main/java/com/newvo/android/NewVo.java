package com.newvo.android;

import android.os.Bundle;


public class NewVo extends DrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void displayView(String name) {
        setTheme(R.style.CustomActionBarTheme);
        super.displayView(name);
    }
}
