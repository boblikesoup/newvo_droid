package com.newvo.android.util;

import android.content.ContentResolver;
import android.content.Context;
import com.parse.ParseFile;

/**
 * Created by David on 5/3/2014.
 */
public class ParseFileUtils {

    private ParseFileUtils(){

    }

    public static ParseFile getParseFile(Context context, String image1) {
        ParseFile parseFile = createParseFile(context.getContentResolver(), image1);
        parseFile.saveInBackground();
        return parseFile;
    }

    public static ParseFile createParseFile(ContentResolver contentResolver, String uri) {
        byte[] data = ImageFileUtils.pathToByteArray(contentResolver, uri);
        if(data == null){
            return null;
        }
        ParseFile parseFile = new ParseFile("Image.jpg", data);
        return parseFile;
    }

}
