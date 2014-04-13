package com.newvo.android;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;


public class NewVo extends DrawerActivity {

    private static NewVo CONTEXT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NewVo.CONTEXT = this;

    }

    public static Context getContext(){
        return CONTEXT;
    }

    public static View inflate(int resource, ViewGroup root){
        return CONTEXT.getLayoutInflater().inflate(resource, root);
    }

}
